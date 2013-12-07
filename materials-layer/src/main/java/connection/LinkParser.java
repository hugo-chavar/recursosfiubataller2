package connection;

import java.util.HashMap;

import model.Link;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;


public class LinkParser extends Parser {

	public static String LINK_TAG = "Link";
	public static String IDLINK_TAG = "IDLink";
	public static String IDAMBIENTE_TAG = "IDAmbiente";
	public static String DESCRIPCION_TAG = "Descripcion";
	public static String NOMBRE_TAG = "Nombre";
	
	
	public String serializeLink(Link link) {
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(LinkParser.LINK_TAG);
		rootElement.appendChild(nodeElement);
		
		Element IDAmbiente = doc.createElement(LinkParser.IDAMBIENTE_TAG);
		IDAmbiente.appendChild(doc.createTextNode(String.valueOf(link.getIdAmbiente())));
		nodeElement.appendChild(IDAmbiente);
		Element IDLink = doc.createElement(LinkParser.IDLINK_TAG);
		IDLink.appendChild(doc.createTextNode(String.valueOf(link.getIdRecurso())));
		nodeElement.appendChild(IDLink);
		Element nombre = doc.createElement(LinkParser.NOMBRE_TAG);
		nombre.appendChild(doc.createTextNode(String.valueOf(link.getNombre())));
		nodeElement.appendChild(nombre);
		Element descripcion = doc.createElement(LinkParser.DESCRIPCION_TAG);
		descripcion.appendChild(doc.createTextNode(link.getDescripcion()));
		nodeElement.appendChild(descripcion);
		
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		String xml = serializer.writeToString(doc);		
		
		return xml;
	}
	
	public Link deserializeLink(String xml) {
		Document doc = this.convertToXMLDocument(xml);
		NodeList nodes = doc.getElementsByTagName(LinkParser.LINK_TAG);
		NodeList childNodes = nodes.item(0).getChildNodes(); 
		HashMap<String, String> fields = new HashMap<String, String>();
	    if (childNodes != null) {
	        for (int i = 0; i < childNodes.getLength(); i++) {
        	   Element element = (Element) childNodes.item(i);
        	   fields.put(element.getNodeName(), element.getTextContent());
	        }
	    }
	    
		int IDAmbiente = Integer.parseInt(fields.get(LinkParser.IDAMBIENTE_TAG));
		int IDLink = Integer.parseInt(fields.get(LinkParser.IDLINK_TAG));
		String nombre = fields.get(LinkParser.NOMBRE_TAG);
		String descripcion = fields.get(LinkParser.DESCRIPCION_TAG);		
		
		Link link = new Link(IDLink, IDAmbiente, descripcion);
		link.setNombre(nombre);
		return link;
	}
	
	public String serializeLinkQuery(int IDAmbiente, int IDLink) {
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(LinkParser.LINK_TAG);
		rootElement.appendChild(nodeElement);
		
		Element IDAmbiente_el = doc.createElement(LinkParser.IDAMBIENTE_TAG);
		IDAmbiente_el.appendChild(doc.createTextNode(String.valueOf(IDAmbiente)));
		nodeElement.appendChild(IDAmbiente_el);
		Element IDLink_el = doc.createElement(LinkParser.IDLINK_TAG);
		IDLink_el.appendChild(doc.createTextNode(String.valueOf(IDLink)));
		nodeElement.appendChild(IDLink_el);
		
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		String xml = serializer.writeToString(doc);		
		
		return xml;
	}
	
}
