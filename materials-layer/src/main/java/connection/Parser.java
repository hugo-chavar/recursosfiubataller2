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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import connection.exceptions.ParseException;


public class Parser {	

	public static String TAMANIO = "tamanio";
	public static String INITIAL_TAG = "WS";
	public static String RECURSO_TAG = "Recurso";
	public static String ID_TAG = "id";
	public static String RECURSOID_TAG = "recursoId";
	public static String AMBITOID_TAG = "ambitoId";
	public static String DESCRIPCION_TAG = "descripcion";
	public static String TIPO_TAG = "tipo";
	public static String RECURSOS_TAG = "recursos";
	public static String JOIN_TAG = "join";
	
	public static String SPECIAL_CHARACTERS = ",;|";
	
	protected String baseTag;
	protected Document document;
	
	
	public Document buildXMLDocument() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			return docBuilder.newDocument();
		} catch (ParserConfigurationException pce) {
			System.out.println(pce.getMessage());
		}
		return null;
	}
	
	public Document convertXmlToDocument(String xml) throws ParseException {		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			return docBuilder.parse(new InputSource(new StringReader(xml)));
		} catch (SAXParseException e) {
			String rcv = xml.substring(0, xml.indexOf('<') - 2);
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
		} catch (UnmarshalException e) {
			// do nothing
			System.out.println(e.getMessage());
		} catch (JAXBException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public String serializeXmlQuery(int recursoId) {
		
		document = buildXMLDocument();
		Element rootElement = document.createElement(Parser.INITIAL_TAG);
		document.appendChild(rootElement);

		Element typeNode = document.createElement(baseTag);
		rootElement.appendChild(typeNode);
		
		addTextElement(typeNode, ID_TAG, String.valueOf(recursoId));

		return convertDocumentToXml(document);
		
	}
	
	public Serializable deserialize(String xml) throws ParseException {

		NodeList nodes = getBaseTagNodes(xml);
		
		NodeList childNodes = nodes.item(0).getChildNodes();
		HashMap<String, String> fields;

		if (childNodes != null) {
			fields = fillFieldValues(childNodes);
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
		for (int i = 0; i < linkChildNodes.getLength(); i++) {
			Node childNode = linkChildNodes.item(i);
			if (childNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) childNode;
				fields.put(element.getNodeName(), element.getTextContent());
			}
		}
		return fields;
	}

	public String serialize(Serializable serializable) {
		document = buildXMLDocument();
		Element rootElement = document.createElement(Parser.INITIAL_TAG);
		document.appendChild(rootElement);

		Element baseNode = document.createElement(baseTag);
		rootElement.appendChild(baseNode);

		addElements(serializable, baseNode);

		return convertDocumentToXml(document);
	}

	protected void addTextElement(Element element, String tag, String text) {
		Element nombre = document.createElement(tag);
		nombre.appendChild(document.createTextNode(text));
		element.appendChild(nombre);
	}

	protected void addElements(Serializable serializable, Element baseNode) {
	}
	
	protected Serializable createSerializable(HashMap<String, String> fields) {
		return null;
	}

}