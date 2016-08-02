package eu.eudat.b2access.authz.client;

import javax.xml.bind.JAXBElement;

import org.junit.Test;

import eu.eudat.b2access.authz.TestServerBase;
import eu.eudat.b2access.authz.Utils;
import eu.eudat.b2access.authz.server.XACMLServerConfig;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ObjectFactory;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.RequestType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ResponseType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.StatusCodeType;

import static org.junit.Assert.*;

public class TestPdpClient extends TestServerBase {
	@Test
	public void test() throws Exception {
		PdpClient p = new PdpClient(getConfig());
		JAXBElement<ResponseType> response = p.evaluate((JAXBElement<RequestType>) Utils
				.StringToJAXBElement(createRequest("alex", "admin", "read", "data"), RequestType.class));

		assertEquals(response.getValue().getResult().get(0).getStatus().getStatusCode().getValue(),
				"urn:oasis:names:tc:xacml:1.0:status:ok");
	}

	private XACMLServerConfig getConfig() {
		XACMLServerConfig c = new XACMLServerConfig("src/main/conf/xacmlServer.config");
		return c;
	}

	public String createRequest(String user, String group, String action, String resource) {
		return "<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" CombinedDecision=\"false\" ReturnPolicyIdList=\"false\">\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:action\">\n"
				+ "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" IncludeInResult=\"false\">\n"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + action
				+ "</AttributeValue>\n" + "</Attribute>\n" + "</Attributes>\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\">\n"
				+ "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject:subject-id\" IncludeInResult=\"false\">\n"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + user + "</AttributeValue>\n"
				+ "</Attribute>\n" + "</Attributes>\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:resource\">\n"
				+ "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" IncludeInResult=\"false\">\n"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + resource
				+ "</AttributeValue>\n" + "</Attribute>\n" + "</Attributes>\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:group\">\n"
				+ "<Attribute AttributeId=\"group\" IncludeInResult=\"false\">"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + group + "</AttributeValue>"
				+ "</Attribute></Attributes>" + "</Request>";
	}
}
