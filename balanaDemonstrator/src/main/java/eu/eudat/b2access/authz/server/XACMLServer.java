package eu.eudat.b2access.authz.server;

/***
 * Entry point to the B2Access AuthZ Server
 * 
 * @author a.memon
 *
 */
public class XACMLServer {
	XACMLServerConfig config;
	HttpServer httpServer;
	private XACMLServer(String propsFile) throws Exception {
		config = new XACMLServerConfig(propsFile);
		httpServer = new HttpServer(config);
	}
	
	private void start(){
		httpServer.start();		
	}
	public static void main(String[] args) throws Exception {
		 XACMLServer m = new XACMLServer(args[0]);
		 m.start();
	}
	
}
