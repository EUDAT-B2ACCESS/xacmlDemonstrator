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
 * The resource depends on a remote PDP
 * @author a.memon
 *
 */
@Path("/dataRemotePdp")
public class DataServiceWithRemotePDP {
	DataServiceRemotePEP pep;
	public DataServiceWithRemotePDP() {
		pep = new DataServiceRemotePEP();
	}
	
	@GET
	@Produces ({ MediaType.TEXT_PLAIN })
	public Response readRemote(@DefaultValue("bob") @QueryParam("user") String user, @DefaultValue("other") @QueryParam("group") String group) {
		StringBuilder builder = new StringBuilder();
		try {
			System.out.println("Invoking remote data access....");
			builder.append("------------------------ BEGIN -------------------------\n");
			builder.append("Invoking remote data access....\n");
			pep.authorise(user, group, "read", "data");
			builder.append(pep.getMessage()+"\n");
			System.out.println("Remote data read!");
			builder.append("Remote data read!\n");
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
