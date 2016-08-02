package eu.eudat.b2access.authz.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.glassfish.jersey.client.internal.HttpUrlConnector;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.logging.LoggingFeature;

import eu.eudat.b2access.authz.Utils;
import eu.eudat.b2access.authz.server.XACMLServerConfig;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ObjectFactory;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.RequestType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ResponseType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ResultType;

public class PdpClient {
	private Client client;
	private XACMLServerConfig config;

	public PdpClient(XACMLServerConfig config) {
		this.config = config;
		client = ClientBuilder.newClient().register(LoggingFeature.class);

	}

	public String evaluate(String request) throws Exception {
		JAXBElement<RequestType> requestType = (JAXBElement<RequestType>) Utils.StringToJAXBElement(request,
				RequestType.class);
		JAXBElement<ResponseType> rt = evaluate(requestType);
		return Utils.JAXBElementToString(rt, ResponseType.class);
	}

	public JAXBElement<ResponseType> evaluate(JAXBElement<RequestType> request) throws Exception {
		Entity<JAXBElement<RequestType>> entity = Entity.entity(request, MediaType.APPLICATION_XML);
		Response resultPermit = client.target("http://" + config.getHostname() + ":" + config.getPort())
				.path("/authorization/pdp").request().accept(MediaType.APPLICATION_XML_TYPE).post(entity);
		ObjectFactory factory = new ObjectFactory();
		if (resultPermit.getStatus() == 200) {
			GenericType<JAXBElement<ResponseType>> genericType = new GenericType<JAXBElement<ResponseType>>(){};
			final JAXBElement jaxbElement = resultPermit.readEntity(genericType);
			return jaxbElement;
		}
		return null;
	}
}
