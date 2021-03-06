package eu.eudat.b2access.authz.samples;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jettison.json.JSONObject;
/***
 * REST interface
 * 
 * @author a.memon
 *
 */
@Path("/data")
public class DataService {
	DataServiceLocalPEP pep;
	public DataService() {
		pep = new DataServiceLocalPEP();
	}

	@GET
	@Produces ({ MediaType.TEXT_PLAIN })
	public Response readRemote(@QueryParam("user") String user, @QueryParam("group") String group) {
		if (user.isEmpty() || user == null) {
			return Response.serverError().entity("user information is missing").build();	
		}
		if (group.isEmpty() || group == null) {
			return Response.serverError().entity("group information is missing").build();	
		}
		StringBuilder builder = new StringBuilder();
		try {
			System.out.println("Invoking local data access....");
			builder.append("------------------------ BEGIN -------------------------\n");
			builder.append("Invoking local data access....\n");
			pep.authorise(user, group, "read", "data");
			builder.append(pep.getMessage()+"\n");
			System.out.println("local data read!");
			builder.append("local data read!\n");
			builder.append("------------------------ END -------------------------\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.ok(builder.toString()).build();
	}
	
	public void read(String user, String group) {
		try {
			System.out.println("Invoking local data access....");
			pep.authorise(user, group, "read", "data");
			System.out.println("Local data read!");	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
