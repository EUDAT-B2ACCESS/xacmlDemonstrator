package eu.eudat.b2access.authz.pdp;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

import eu.eudat.b2access.authz.Utils;
import eu.eudat.b2access.authz.server.XACMLServer;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.RequestType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ResponseType;

@Path("/authorization")
public class PdpResource {
	private Pdp p = null;

	public PdpResource() {
		this.p = new Pdp(XACMLServer.getConfig().getXACMLPdpConfigPath(), XACMLServer.getConfig().getXACMLPolicyDirPath());
	}

	@POST
	@Path("/pdp")
	@Produces({ MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_XML })
	public Response evaluate(JAXBElement<RequestType> request) {
		JAXBElement<ResponseType> rt = null;
		String response = new String();
		try {
			String req = Utils.JAXBElementToString(request, RequestType.class);
			response = p.evaluate(req);
			rt = (JAXBElement<ResponseType>) Utils.StringToJAXBElement(response, ResponseType.class);
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
		if (rt == null) {
			return Response.serverError().entity("Null response").build();
		}
		return Response.ok().entity(rt).build();
	}
	
}