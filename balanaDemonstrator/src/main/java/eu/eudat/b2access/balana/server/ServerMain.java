package eu.eudat.b2access.balana.server;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class ServerMain {
	Server server = null;
	private ServerMain() {
		server = new Server(8080);
		
	}
	
	private void start() throws Exception{
		server.setHandler(createRootHandler());
		server.start();
		server.join();
	}
	public static void main(String[] args) throws Exception {
		 ServerMain m = new ServerMain();
		 m.start();
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
