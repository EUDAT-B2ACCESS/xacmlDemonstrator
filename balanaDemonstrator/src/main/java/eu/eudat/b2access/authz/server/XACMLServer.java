package eu.eudat.b2access.authz.server;

/***
 * Entry point to the B2Access AuthZ Server
 * 
 * @author a.memon
 *
 */
public class XACMLServer {
	private static XACMLServerConfig config;
	HttpServer httpServer;
	private String propsFile; 
	public XACMLServer(String propsFile) throws Exception {
		this.propsFile = propsFile;
		config = new XACMLServerConfig(propsFile);
		httpServer = new HttpServer(config);
	}
	
	public void start(){
		httpServer.start();	
	}
	
	public void stop(){
		httpServer.stop();	
	}
	
	public static void main(String[] args) throws Exception {
		 XACMLServer m = new XACMLServer(args[0]);
		 m.start();
	}
	
	public static XACMLServerConfig getConfig(){
		if (config == null) {
			throw new NullPointerException("Failed to load server configuration");
		}
		return config;
	}
	
}
