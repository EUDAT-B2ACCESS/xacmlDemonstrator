package eu.eudat.b2access.balana;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.wso2.balana.ctx.xacml3.Result;

import oasis.names.tc.xacml._3_0.core.schema.wd_17.DecisionType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ObjectFactory;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.RequestType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ResponseType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ResultType;

public class DataServicePEP {
	DataServicePDP pdp;

	public DataServicePEP() {
		System.out.println("Initialising PDP");
		pdp = new DataServicePDP();
	}

	public void authorise(String user, String group) throws AuthorisationException{
		String request = createRequest(user, group);
		System.out.println("\nPDP Request:");
		ResultType rt = null;
		try {
			printRequest(request);
			String response = pdp.evaluate(request);
			rt = getResult(response);
			printResponse(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rt.getDecision().equals(DecisionType.PERMIT)) {
			System.out.println("Permit: Access Granted");
		}
		
		if (rt.getDecision().equals(DecisionType.DENY)) {
			System.out.println("Access Denied to User: "+user+" of Group:"+group);
			throw new AuthorisationException("Access Denied to User: "+user+" of Group:"+group);
		}
		
		if (rt.getDecision().equals(DecisionType.INDETERMINATE)) {
			System.out.println("Indeterminate");
		}
		
		if (rt.getDecision().equals(DecisionType.NOT_APPLICABLE)) {
			System.out.println("Not applicable");
		}
	}

	private ResultType getResult(String response) throws Exception {
		StringReader reader = new StringReader(response);
		JAXBContext jaxbContext = JAXBContext.newInstance(ResponseType.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		JAXBElement<ResponseType> rt = (JAXBElement<ResponseType>) unmarshaller.unmarshal(reader);
		ResponseType r = rt.getValue();
		return r.getResult().get(0);
	}

	private String createRequest(String user, String group) {
		return "<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" CombinedDecision=\"false\" ReturnPolicyIdList=\"false\">\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:action\">\n"
				+ "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" IncludeInResult=\"false\">\n"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">read</AttributeValue>\n"
				+ "</Attribute>\n" + "</Attributes>\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\">\n"
				+ "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject:subject-id\" IncludeInResult=\"false\">\n"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + user + "</AttributeValue>\n"
				+ "</Attribute>\n" + "</Attributes>\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:resource\">\n"
				+ "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" IncludeInResult=\"false\">\n"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">"
				+ "http://localhost:8280/services/echo/" + "</AttributeValue>\n" + "</Attribute>\n" + "</Attributes>\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:group\">\n"
				+ "<Attribute AttributeId=\"group\" IncludeInResult=\"false\">"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + group + "</AttributeValue>"
				+ "</Attribute></Attributes>" + "</Request>";
	}

	public void printRequest(String request) throws Exception {
		StringReader reader = new StringReader(request);
		JAXBContext jaxbContext = JAXBContext.newInstance(RequestType.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		JAXBElement<RequestType> rt = (JAXBElement<RequestType>) unmarshaller.unmarshal(reader);
		print(rt);
	}

	public void printResponse(String response) throws Exception {
		StringReader reader = new StringReader(response);
		JAXBContext jaxbContext = JAXBContext.newInstance(ResponseType.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		JAXBElement<ResponseType> rt = (JAXBElement<ResponseType>) unmarshaller.unmarshal(reader);
		print(rt);
	}

	public void print(JAXBElement<?> type) {
		StringWriter xml = new StringWriter();
		JAXB.marshal(type, xml);
		String s = new String();
		xml.write(s);
		System.out.println(xml);
	}

}
