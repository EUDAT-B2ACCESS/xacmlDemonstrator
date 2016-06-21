package eu.eudat.b2access.balana;

public class DataServiceClient {
	public static void main(String[] args) {
		DataService d = new DataService();
		//authorised operation
		d.read("admin", "admin");
		
		//non-authorised operation
		d.read("admin", "business");
	}
}
