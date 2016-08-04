package eu.eudat.b2access.authz.samples;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import eu.eudat.b2access.authz.client.ClientConfig;
import eu.eudat.b2access.authz.server.XACMLServer;
import eu.eudat.b2access.authz.server.XACMLServerConfig;

public class DataServiceClient {
	public static void main(String[] args) throws Exception {
		XACMLServer server = new XACMLServer("src/main/conf/xacmlServer.config");
//		localClient();
//		remoteClient();
		remotePdpClient();
	}

	/***
	 * Accessing Data Service with remote PDP
	 */
	private static void remotePdpClient() {
		ClientConfig config = new ClientConfig("src/test/conf/client.config");
		Client client = ClientBuilder.newClient();
		//Calling the data service with minimal rights 
		String resultPermit = client.target("http://"+config.getHostname()+":"+config.getPort()).path("/data").queryParam("user", "alex")
				.queryParam("group", "admin").request().get(String.class);
		System.out.println(resultPermit);
		
		//Calling the data service with priviliged rights
		String resultDeny = client.target("http://"+config.getHostname()+":"+config.getPort()).path("/data").queryParam("user", "bob")
				.queryParam("group", "other").request().get(String.class);
		System.out.println(resultDeny);
		
	}

	private static void localClient() {
		DataService d = new DataService();
		// authorised operation
		d.read("admin", "admin");

		// non-authorised operation
		d.read("admin", "business");
	}
	
	/***
	 * Accessing Data Service with embedded PDP
	 */
	private static void remoteClient() {
		ClientConfig config = new ClientConfig("src/test/conf/client.config");
		Client client = ClientBuilder.newClient();
		//Calling the data service with minimal rights 
		String resultPermit = client.target("http://"+config.getHostname()+":"+config.getPort()).path("/dataRemotePdp").queryParam("user", "alex")
				.queryParam("group", "admin").request().get(String.class);
		System.out.println(resultPermit);
		
		//Calling the data service with priviliged rights
		String resultDeny = client.target("http://"+config.getHostname()+":"+config.getPort()).path("/dataRemotePdp").queryParam("user", "bob")
				.queryParam("group", "other").request().get(String.class);
		System.out.println(resultDeny);

	}
}
