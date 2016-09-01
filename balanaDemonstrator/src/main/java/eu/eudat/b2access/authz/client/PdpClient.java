package eu.eudat.b2access.authz.client;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
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

import org.eclipse.jetty.server.handler.AllowSymLinkAliasChecker;
import org.glassfish.jersey.client.internal.HttpUrlConnector;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.logging.LoggingFeature;

import eu.eudat.b2access.authz.server.XACMLServerConfig;
import eu.eudat.b2access.authz.utils.Utils;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ObjectFactory;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.RequestType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ResponseType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ResultType;

public class PdpClient {
	private Client client;
	private String address;

	public PdpClient(String address) {
		this.address = address;

		
		try {
			client = ClientHelper.IgnoreSSLClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String evaluate(String request) throws Exception {
		JAXBElement<RequestType> requestType = (JAXBElement<RequestType>) Utils.StringToJAXBElement(request,
				RequestType.class);
		JAXBElement<ResponseType> rt = evaluate(requestType);
		return Utils.JAXBElementToString(rt, ResponseType.class);
	}

	public JAXBElement<ResponseType> evaluate(JAXBElement<RequestType> request) throws Exception {
		Entity<JAXBElement<RequestType>> entity = Entity.entity(request, MediaType.APPLICATION_XML);
		Response resultPermit = client.target(address).request().accept(MediaType.APPLICATION_XML_TYPE).post(entity);
		
		if (resultPermit.getStatus() == 200) {
			GenericType<JAXBElement<ResponseType>> genericType = new GenericType<JAXBElement<ResponseType>>() {
			};
			final JAXBElement jaxbElement = resultPermit.readEntity(genericType);
			return jaxbElement;
		}
		return null;
	}
	
	public String testPdp(){
		Response r = client.target("https://localhost:8445/xacml/authorization/pdp").request().accept(MediaType.TEXT_PLAIN_TYPE).get();
		String t = r.readEntity(String.class);
		System.out.println(t);
		return t;
	}
}
