package eu.eudat.b2access.balana;

public class DataService {
	DataServicePEP pep;
	public DataService() {
		pep = new DataServicePEP();
	}


	public void read(String user, String group) {
		try {
			pep.authorise(user, group);
			System.out.println("Data read!");	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
