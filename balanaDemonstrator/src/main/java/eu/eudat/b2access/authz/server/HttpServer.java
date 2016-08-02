package eu.eudat.b2access.authz.server;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

/***
 * 
 * @author a.memon
 *
 */
public class HttpServer {
	//TODO: add SSL functionality
	Server server = null;
	XACMLServerConfig config;

	public HttpServer(XACMLServerConfig config) throws Exception {
		this.config = config;
		server =  new Server(config.getIntegerValue(config.HTTP_SERVER_PORT));
		server.setHandler(createRootHandler());
	}
	
	public void start(){
		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	protected Handler createRootHandler() {
		ServletContextHandler root = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
		ServletHolder sh = new ServletHolder(ServletContainer.class);
		Map<String, String> map = new HashMap<String, String>();
		map.put("javax.ws.rs.Application", AuthZJaxrsApplication.class.getCanonicalName());
		sh.setInitParameters(map);
		root.addServlet(sh, "/*");
		return root;
	}
}
