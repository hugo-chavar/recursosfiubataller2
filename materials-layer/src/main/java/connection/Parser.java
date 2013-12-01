package connection;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Parser {

	public static String INITIAL_TAG = "WS";
	
	
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
	
	public Document convertToXMLDocument(String xml) {
		// TODO: Pedir a Integracion sacar el texto previo al xml.
		int index = xml.indexOf(":")+2;
		xml = xml.substring(index);
		// Eliminar lo anterior una vez que Integracion haga lo pedido.
		
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
	
	public String addTag(String xml, String tag) {
		xml = "<" + tag + ">" + xml;
		xml += "</" + tag + ">";
		return xml;
	}
	
	public String addField(String xml, String field, String value) {
		xml += "<" + field + ">" + value + "</" + field + ">";
		return xml;
	}
	
	public String removeTag(String xml) {
		int index = xml.indexOf(">") + 1;
		xml = xml.substring(index);
		index = xml.lastIndexOf("<");
		xml = xml.substring(0, index);
		return xml;
	}
	
	public String getFieldValue(String xml) {
		int index = xml.indexOf(">") + 1;
		xml = xml.substring(index);
		index = xml.indexOf("<");
		String field = xml.substring(0, index);
		return field;
	}
	
	public String removeField(String xml) {
		int index = xml.indexOf(">") + 1;
		xml = xml.substring(index);
		index = xml.indexOf(">") + 1;
		xml = xml.substring(index);
		return xml;
	}
	
}