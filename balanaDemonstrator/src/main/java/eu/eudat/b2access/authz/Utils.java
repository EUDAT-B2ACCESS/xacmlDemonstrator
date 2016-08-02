package eu.eudat.b2access.authz;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import oasis.names.tc.xacml._3_0.core.schema.wd_17.ResponseType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.ResultType;

public class Utils {
	/***
	 * 
	 * @param type
	 * @return
	 * @throws Exception 
	 */
	public static String JAXBElementToString(JAXBElement<?> xmlObject, Class clazz) throws Exception {
		StringWriter xml = new StringWriter();
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.marshal(xmlObject, xml);
		return xml.toString();
	}
	
	
	/***
	 * 
	 * @param xml XML document in String format
	 * @param clazz JAXB XML Type to which the XML string should be converted to
	 * @return {@link JAXBElement} of the particular type
	 * @throws Exception
	 */
	public static JAXBElement<?> StringToJAXBElement(String xml, Class clazz) throws Exception {
		StringReader reader = new StringReader(xml);
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		JAXBElement<?> rt = (JAXBElement<?>) unmarshaller.unmarshal(reader);
		return rt;
	}
}
