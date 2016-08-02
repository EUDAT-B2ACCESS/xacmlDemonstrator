package eu.eudat.b2access.authz.pdp;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

import eu.eudat.b2access.authz.Utils;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.RequestType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ResponseType;

@Path("/pdp")
public class PdpResource {
	private Pdp p = null;

	public PdpResource() {
		this.p = new Pdp();
	}

	@POST
	@Produces({ "application/xacml+xml" })
	@Consumes({ "application/xacml+xml" })
	public Response evaluate(JAXBElement<RequestType> request) {
		JAXBElement<ResponseType> rt = null;
		try {
			rt = (JAXBElement<ResponseType>)Utils.StringToJAXBElement(p.evaluate(Utils.JAXBElementToString(request)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(rt).build();
	}
}
