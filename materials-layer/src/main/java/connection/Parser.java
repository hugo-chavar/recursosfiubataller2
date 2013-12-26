package connection;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class Parser {	

	public static String INITIAL_TAG = "WS";
	public static String RECURSO_TAG = "recurso";
	public static String RECURSOID_TAG = "recursoId";
	public static String AMBITOID_TAG = "ambitoId";
	public static String DESCRIPCION_TAG = "descripcion";
	public static String TIPO_TAG = "tipo";
	public static String RECURSOS_TAG = "recursos";
	public static String JOIN_TAG = "join";
	
	
	public Document buildXMLDocument() {
		Document document = null;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			return docBuilder.newDocument();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		return document;
	}
	
	public Document convertXmlToDocument(String xml) {		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			return docBuilder.parse(new InputSource(new StringReader(xml)));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String convertDocumentToXml(Document doc) {
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		return serializer.writeToString(doc);
	}
	
}