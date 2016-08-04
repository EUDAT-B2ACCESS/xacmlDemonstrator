package eu.eudat.b2access.authz.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import eu.eudat.b2access.authz.pdp.BalanaPdp;
import eu.eudat.b2access.authz.utils.PathObservables;

/***
 * Entry point to the B2Access AuthZ Server
 * 
 * @author a.memon
 *
 */
public class XACMLServer {
	private static Logger log = LogManager.getLogger(XACMLServer.class);

	private static XACMLServerConfig config;
	HttpServer httpServer;
	private String propsFile;

	public XACMLServer(String propsFile) throws Exception {
		this.propsFile = propsFile;
		config = new XACMLServerConfig(propsFile);
		httpServer = new HttpServer(config);
	}

	public void start() {
		log.info("Starting XACML server");
		startPdpFileListener();
		httpServer.start();
	}

	public void stop() {
		log.info("Stopping XACML server");
		httpServer.stop();
	}

	public static void main(String[] args) throws Exception {
		XACMLServer m = new XACMLServer(args[0]);
		m.start();
	}

	public static XACMLServerConfig getConfig() {
		if (config == null) {
			throw new NullPointerException("Failed to load server configuration");
		}
		return config;
	}

	public void startPdpFileListener() {
		Executors.newFixedThreadPool(1).execute(() -> {
			try {
				PathObservables.watchNonRecursive(Paths.get(config.getConfigDirPath()+"/xacml")).subscribe(event -> {
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

}
