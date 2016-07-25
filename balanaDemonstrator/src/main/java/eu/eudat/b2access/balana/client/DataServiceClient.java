package eu.eudat.b2access.balana.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import eu.eudat.b2access.balana.DataService;

public class DataServiceClient {
	public static void main(String[] args) {
//		localClient();
		remoteClient();
	}

	private static void localClient() {
		DataService d = new DataService();
		// authorised operation
		d.read("admin", "admin");

		// non-authorised operation
		d.read("admin", "business");
	}

	private static void remoteClient() {
		Client client = ClientBuilder.newClient();
		//Calling the data service with minimal rights 
		String resultPermit = client.target("http://localhost:8080").path("/data").queryParam("user", "alex")
				.queryParam("group", "admin").request().get(String.class);
		System.out.println(resultPermit);
		
		//Calling the data service with priviliged rights
		String resultDeny = client.target("http://localhost:8080").path("/data").queryParam("user", "bob")
				.queryParam("group", "other").request().get(String.class);
		System.out.println(resultDeny);

	}
}
