package eu.eudat.b2access.authz;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import oasis.names.tc.xacml._3_0.core.schema.wd_17.ResponseType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ResultType;

public class Utils {
	public static String JAXBElementToString(JAXBElement<?> type) {
		StringWriter xml = new StringWriter();
		JAXB.marshal(type, xml);
		String s = new String();
		xml.write(s);
		return s;
	}
	
	public static JAXBElement<?> StringToJAXBElement(String xml) throws Exception {
		StringReader reader = new StringReader(xml);
		JAXBContext jaxbContext = JAXBContext.newInstance(ResponseType.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		JAXBElement<?> rt = (JAXBElement<?>) unmarshaller.unmarshal(reader);
		return rt;
	}
}
