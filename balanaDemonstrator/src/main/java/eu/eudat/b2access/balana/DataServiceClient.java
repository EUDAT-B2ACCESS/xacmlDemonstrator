package eu.eudat.b2access.balana;

public class DataServiceClient {
	public static void main(String[] args) {
		DataService d = new DataService();
		d.read("admin", "admin");
		
		//non-allowed group
		d.read("admin", "business");
	}
}
