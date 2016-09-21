package eu.eudat.b2access.authz.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Security;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import eu.eudat.b2access.authz.pdp.BalanaPdp;
import eu.eudat.b2access.authz.utils.PathObservables;

/***
 * It initialises the server configuration and invoke the start-up
 * processes
 * 
 * @author a.memon
 *
 */
public class AuthzServletContextListener implements ServletContextListener {
	private static Logger log = LogManager.getLogger(AuthzServletContextListener.class);
	private static XACMLServerConfig config;
	public static final String CONFIG_FILE_PARAM = "conf-file";
	static{
		Security.addProvider(new BouncyCastleProvider());
	}
	@Override
	public void contextDestroyed(ServletContextEvent args) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent args) {
		ServletContext ctx = args.getServletContext();
		if (config == null) {
			config = new XACMLServerConfig(ctx.getInitParameter(CONFIG_FILE_PARAM));
		}
		log.debug("Config directory path: "+config.getConfigDirPath());
		log.info("XACML policy directory path: "+config.getXACMLPolicyDirPath());
		log.info("XACML Policy configuration path: "+config.getXACMLPdpConfigPath());
		
		startPdpFileListener();
	}

	public void startPdpFileListener() {
		File f = new File(config.getXACMLPdpConfigPath());
		Executors.newFixedThreadPool(1).execute(() -> {
			try {
				PathObservables.watchNonRecursive(Paths.get(f.getParent())).subscribe(event -> {
					log.info("PDP XACML Configuration updated");
					BalanaPdp.reloadConfiguration(config.getXACMLPdpConfigPath(), config.getXACMLPolicyDirPath());
				});

				PathObservables.watchRecursive(Paths.get(config.getXACMLPolicyDirPath())).subscribe(event -> {
					log.info("PDP policies updated");
					BalanaPdp.reloadConfiguration(config.getXACMLPdpConfigPath(), config.getXACMLPolicyDirPath());
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}
	
	public static XACMLServerConfig getServerConfig(){
		return config;
	}
}
