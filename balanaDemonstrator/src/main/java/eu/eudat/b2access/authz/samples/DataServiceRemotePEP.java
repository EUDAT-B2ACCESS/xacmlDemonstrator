package eu.eudat.b2access.authz.samples;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.wso2.balana.ctx.xacml3.Result;

import eu.eudat.b2access.authz.AuthorisationException;
import eu.eudat.b2access.authz.pdp.Pdp;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.DecisionType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ObjectFactory;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.RequestType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ResponseType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ResultType;

/**
 * The PEP class responsible to call-out to a remote PDP (in same JVM though)
 * 
 * @author a.memon
 *
 */
public class DataServiceRemotePEP {
	Pdp pdp;
	StringBuffer msg; 
	public DataServiceRemotePEP() {
		System.out.println("Initialising PDP");
		pdp = new Pdp();
		msg = new StringBuffer();
	}

	public void authorise(final String user, final String group, final String action, final String resource) throws AuthorisationException{
		String request = createRequest(user, group, action, resource);
		System.out.println("\nPDP Request:");
		msg.append("Preparing a PDP request with parameters:"+"\n\t* user:"+user+"\n\t* group:"+group+"\n\t* action:"+action+"\n\t* resource:"+resource+"\n\n");
		ResultType rt = null;
		try {
			printRequest(request);
			String response = pdp.evaluate(request);
			rt = getResult(response);

			if (rt.getDecision().equals(DecisionType.PERMIT)) {
				String m = "Access granted to User: "+user+" of Group:"+group;
				System.out.println(m);
				msg.append(m+"\n");
				printResponse(response);
			}

			if (rt.getDecision().equals(DecisionType.DENY)) {
				String m = "Access denied to User: "+user+" of Group:"+group;
				System.out.println(m);
				msg.append(m+"\n");
				printResponse(response);
				throw new AuthorisationException(m);				
			}

			if (rt.getDecision().equals(DecisionType.INDETERMINATE)) {
				System.out.println("Indeterminate");
			}

			if (rt.getDecision().equals(DecisionType.NOT_APPLICABLE)) {
				System.out.println("Not applicable");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
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

	private String createRequest(String user, String group, String action, String resource) {
		return "<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" CombinedDecision=\"false\" ReturnPolicyIdList=\"false\">\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:action\">\n"
				+ "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" IncludeInResult=\"false\">\n"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" +action + "</AttributeValue>\n"
				+ "</Attribute>\n" + "</Attributes>\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\">\n"
				+ "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject:subject-id\" IncludeInResult=\"false\">\n"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + user + "</AttributeValue>\n"
				+ "</Attribute>\n" + "</Attributes>\n"
				+ "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:resource\">\n"
				+ "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" IncludeInResult=\"false\">\n"
				+ "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">"
				+ resource + "</AttributeValue>\n" + "</Attribute>\n" + "</Attributes>\n"
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
		msg.append(xml+"\n");
	}
	
	public String getMessage(){
		return msg.toString();
	}

}
