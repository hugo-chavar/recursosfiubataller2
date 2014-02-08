package connection;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import connection.exceptions.ParseException;


public class Parser {	

	public static String INITIAL_TAG = "WS";
	public static String RECURSO_TAG = "Recurso";
	public static String ID_TAG = "id";
	public static String AMBITOID_TAG = "ambitoId";
	public static String DESCRIPCION_TAG = "descripcion";
	public static String TIPO_TAG = "tipo";
	public static String RECURSOS_TAG = "recursos";
	public static String JOIN_TAG = "join";
	
	public static String SPECIAL_CHARACTERS = ",;|";
	
	protected String baseTag;
	
	
	public Document buildXMLDocument() {
		Document document = null;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			return docBuilder.newDocument();
		} catch (ParserConfigurationException pce) {
			System.out.println(pce.getMessage());
		}
		return document;
	}
	
	public Document convertXmlToDocument(String xml) throws ParseException {		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			return docBuilder.parse(new InputSource(new StringReader(xml)));
		} catch (SAXParseException e) {
			String rcv = xml.substring(0, xml.indexOf('<') - 2);
//			System.out.println("RECIBIDO:");
//			System.out.println(xml);
			throw new ParseException("Xml recibido de integracion contiene errores. Recibido: " + rcv);
		} catch (ParserConfigurationException e) {
			throw new ParseException("ParserConfigurationException al convertir: " + xml);
		} catch (SAXException e) {
			throw new ParseException("SAXException al convertir: " + xml);
		} catch (IOException e) {
			throw new ParseException("IOException al convertir: " + xml);
		}
	}

	public String convertDocumentToXml(Document doc) {
		String response;
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		
		LSSerializer serializer = domImplLS.createLSSerializer();
		
		serializer.getDomConfig().setParameter("xml-declaration", false);
		 try{
			response =  serializer.writeToString(doc);
		 }catch(Exception e){
			 response = "error";
		 }
		return response;
	}
	
	@SuppressWarnings("rawtypes")
	public String convertToXml(Object source, Class... type ) {
		String result;
        StringWriter sw = new StringWriter();
        try {
            JAXBContext carContext = JAXBContext.newInstance(type);
            Marshaller carMarshaller = carContext.createMarshaller();
            carMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            carMarshaller.marshal(source, sw);
            result = sw.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        return result;
	}
	
	@SuppressWarnings("rawtypes")
	public Object unmarshal(String xml, Class... type ) {
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(type);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			return unmarshaller.unmarshal(reader);
		} catch (UnmarshalException ue) {
			// do nothing
		} catch (JAXBException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public String serializeQueryByType(int IDRecurso, String RecursoType) {
		
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);

		Element typeNode = doc.createElement(RecursoType);
		rootElement.appendChild(typeNode);
		
		Element recursoID = doc.createElement(Parser.ID_TAG);
		recursoID.appendChild(doc.createTextNode(String.valueOf(IDRecurso)));
		typeNode.appendChild(recursoID);
		
		return convertDocumentToXml(doc);
		
	}
	
	public Serializable deserialize(String xml) throws ParseException {

		NodeList nodes = getBaseTagNodes(xml);
		
		NodeList linkChildNodes = nodes.item(0).getChildNodes();
		HashMap<String, String> fields;

		if (linkChildNodes != null) {
			fields = fillFieldValues(linkChildNodes);
			return createSerializable(fields);
		}

		return null;
	}

	protected NodeList getBaseTagNodes(String xml) throws ParseException {
		Document doc = convertXmlToDocument(xml);
		
		if (doc == null) {
			throw new ParseException("Xml invalido: " + xml);
		}

		NodeList nodes = doc.getElementsByTagName(baseTag);
		
		
		if (nodes.getLength() == 0) {
			throw new ParseException("No existe tag " + baseTag);
		}
		return nodes;
	}

	protected HashMap<String, String> fillFieldValues(NodeList linkChildNodes) {
		HashMap<String, String> fields;
		fields = new HashMap<String, String>();
		int a = linkChildNodes.getLength();
		for (int i = 0; i < linkChildNodes.getLength(); i++) {
			Element element = (Element) linkChildNodes.item(i);
			fields.put(element.getNodeName(), element.getTextContent());
		}
		return fields;
	}

	protected Serializable createSerializable(HashMap<String, String> fields) {
		return null;
	}

}