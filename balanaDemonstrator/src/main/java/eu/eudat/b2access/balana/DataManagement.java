package eu.eudat.b2access.balana;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.wso2.balana.Balana;
import org.wso2.balana.ConfigurationStore;
import org.wso2.balana.PDP;
import org.wso2.balana.PDPConfig;
//import org.wso2.balana.samples.kmarket.trading.SampleAttributeFinderModule;
import org.wso2.balana.finder.impl.FileBasedPolicyFinderModule;

import oasis.names.tc.xacml._3_0.core.schema.wd_17.RequestType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ResponseType;

/***
 * http://docs.oasis-open.org/xacml/3.0/xacml-3.0-core-spec-os-en.html#
 * _Toc325047260
 * 
 * @author a.memon
 *
 */

public class DataManagement {
	private static Balana balana;

	/**
	 * Returns a new PDP instance with new XACML policies
	 *
	 * @return a PDP instance
	 */
	private static PDP getPDPNewInstance() {
		PDPConfig pdpConfig = balana.getPdpConfig();
		return new PDP(pdpConfig);
	}

	private static void initBalana() throws IOException {
		System.setProperty(ConfigurationStore.PDP_CONFIG_PROPERTY, "src/main/conf/xacmlPdpConfig.xml");
		System.setProperty(FileBasedPolicyFinderModule.POLICY_DIR_PROPERTY, "src/main/conf/policies");
		balana = Balana.getInstance();
	}

	public static void main(String[] args) throws Exception {
		initBalana();
		PDP pdp = getPDPNewInstance();
		
		printRequest(requestPermit());
		
		String resp = pdp.evaluate(requestPermit());
		printResponse(resp);
		
		printRequest(requestDeny());
		
		String resp1 = pdp.evaluate(requestDeny());
		printResponse(resp1);
	}

	public static String requestPermit() {

		return "<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" CombinedDecision=\"false\" ReturnPolicyIdList=\"false\">\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:action\">\n"
				+ "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" IncludeInResult=\"false\">\n"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">read</AttributeValue>\n"
				+ "</Attribute>\n" + "</Attributes>\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\">\n"
				+ "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject:subject-id\" IncludeInResult=\"false\">\n"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + "admin"
				+ "</AttributeValue>\n" + "</Attribute>\n" + "</Attributes>\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:resource\">\n"
				+ "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" IncludeInResult=\"false\">\n"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">"
				+ "http://localhost:8280/services/echo/" + "</AttributeValue>\n" + "</Attribute>\n" + "</Attributes>\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:group\">\n"
				+ "<Attribute AttributeId=\"group\" IncludeInResult=\"false\">"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">admin</AttributeValue>"
				+ "</Attribute></Attributes>"
				+ "</Request>";

	}
	
	public static String requestDeny() {

		return "<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" CombinedDecision=\"false\" ReturnPolicyIdList=\"false\">\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:action\">\n"
				+ "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" IncludeInResult=\"false\">\n"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">read</AttributeValue>\n"
				+ "</Attribute>\n" + "</Attributes>\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\">\n"
				+ "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject:subject-id\" IncludeInResult=\"false\">\n"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + "admin"
				+ "</AttributeValue>\n" + "</Attribute>\n" + "</Attributes>\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:resource\">\n"
				+ "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" IncludeInResult=\"false\">\n"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">"
				+ "http://localhost:8280/services/echo/" + "</AttributeValue>\n" + "</Attribute>\n" + "</Attributes>\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:group\">\n"
				+ "<Attribute AttributeId=\"group\" IncludeInResult=\"false\">"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">business</AttributeValue>"
				+ "</Attribute></Attributes>"
				+ "</Request>";

	}

	public static void printRequest(String request) throws Exception {
		StringReader reader = new StringReader(request);
		JAXBContext jaxbContext = JAXBContext.newInstance(RequestType.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		JAXBElement<RequestType> rt = (JAXBElement<RequestType>) unmarshaller.unmarshal(reader);
		print(rt);
	}
	
	public static void printResponse(String response) throws Exception {
		StringReader reader = new StringReader(response);
		JAXBContext jaxbContext = JAXBContext.newInstance(ResponseType.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		JAXBElement<ResponseType> rt = (JAXBElement<ResponseType>) unmarshaller.unmarshal(reader);
		print(rt);
	}
	
	public static void print(JAXBElement<?> type){
		StringWriter xml = new StringWriter();
		JAXB.marshal(type, xml);
		String s = new String();
		xml.write(s);
		System.out.println(xml);
	}
	
	
	
}
