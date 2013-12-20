package connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Link;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;


public class LinkParser extends Parser {

	public static String LINK_TAG = "link";
	public static String DESCRIPCION_TAG = "descripcion";
	public static String NOMBRE_TAG = "nombre";
	
	
	public String serializeLink(Link link) {
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		//cargo datos del recurso
		Element recursoNode = doc.createElement(Parser.RECURSO_TAG);
		rootElement.appendChild(recursoNode);
		
		Element ambitoId = doc.createElement(Parser.AMBITOID_TAG);
		ambitoId.appendChild(doc.createTextNode(String.valueOf(link.getIdAmbiente())));
		recursoNode.appendChild(ambitoId);

		if ((link.getIdRecurso() != null) && (!link.getIdRecurso().equals(0))) {
			Element recursoId = doc.createElement(Parser.RECURSOID_TAG);
			recursoId.appendChild(doc.createTextNode(String.valueOf(link.getIdRecurso())));
			recursoNode.appendChild(recursoId);
		}
		
		Element descripcion = doc.createElement(LinkParser.DESCRIPCION_TAG);
		descripcion.appendChild(doc.createTextNode(link.getDescripcion()));
		recursoNode.appendChild(descripcion);
		
		//creo elemento Link
		Element linkNode = doc.createElement(LinkParser.LINK_TAG);
		recursoNode.appendChild(linkNode);

		Element nombre = doc.createElement(LinkParser.NOMBRE_TAG);
		nombre.appendChild(doc.createTextNode(String.valueOf(link.getNombre())));
		linkNode.appendChild(nombre);

		
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		String xml = serializer.writeToString(doc);		
		
		return xml;
	}
	
//	public List<Link> deserializeLink(String xml) {
//		List<Link> links = new ArrayList<Link>();
//		Document doc = this.convertToXMLDocument(xml);
//		NodeList nodes = doc.getElementsByTagName(LinkParser.LINK_TAG);
//		
//		for (int i = 0; i < nodes.getLength(); i++) {
//			NodeList childNodes = nodes.item(0).getChildNodes(); 
//			HashMap<String, String> fields = new HashMap<String, String>();
//		    if (childNodes != null) {
//		        for (int j = 0; j < childNodes.getLength(); j++) {
//	        	   Element element = (Element) childNodes.item(j);
//	        	   fields.put(element.getNodeName(), element.getTextContent());
//		        }
//		    }
//		    
//			int IDAmbiente = Integer.parseInt(fields.get(Parser.AMBITOID_TAG));
//			int IDLink = Integer.parseInt(fields.get(Parser.RECURSOID_TAG));
//			String nombre = fields.get(LinkParser.NOMBRE_TAG);
//			String descripcion = fields.get(LinkParser.DESCRIPCION_TAG);		
//			
//			Link link = new Link(IDLink, IDAmbiente, descripcion);
//			link.setNombre(nombre);
//			links.add(link);
//		}
//		
//		return links;
//	}
	
	public Link deserializeLink(String xml) {
		HashMap<String, String> fields = new HashMap<String, String>();
		Document doc = this.convertToXMLDocument(xml);
		NodeList linkNodes = doc.getElementsByTagName(LinkParser.LINK_TAG);
		NodeList recursonNodes = doc.getElementsByTagName(Parser.RECURSO_TAG);

		NodeList recursoChildNodes = recursonNodes.item(0).getChildNodes();
		NodeList linkChildNodes = linkNodes.item(0).getChildNodes();

		if (recursoChildNodes != null) {
			for (int j = 0; j < recursoChildNodes.getLength(); j++) {
				Element element = (Element) recursoChildNodes.item(j);
				// los que no tienen childs son campos!
				if (!element.hasChildNodes()) { 
					fields.put(element.getNodeName(), element.getTextContent());
				}
			}
		}

		if (linkChildNodes != null) {
			for (int j = 0; j < linkChildNodes.getLength(); j++) {
				Element element = (Element) linkChildNodes.item(j);
				fields.put(element.getNodeName(), element.getTextContent());
			}
		}

		int ambitoId = Integer.parseInt(fields.get(Parser.AMBITOID_TAG));
		int recursoId = Integer.parseInt(fields.get(Parser.RECURSOID_TAG));
		String nombre = fields.get(LinkParser.NOMBRE_TAG);
		String descripcion = fields.get(LinkParser.DESCRIPCION_TAG);

		Link link = new Link(recursoId, ambitoId, descripcion);
		link.setNombre(nombre);

		return link;
	}
	
	public String serializeLinkQuery(int IDAmbiente, int IDLink) {
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(LinkParser.LINK_TAG);
		rootElement.appendChild(nodeElement);
		
		Element IDAmbiente_el = doc.createElement(Parser.AMBITOID_TAG);
		IDAmbiente_el.appendChild(doc.createTextNode(String.valueOf(IDAmbiente)));
		nodeElement.appendChild(IDAmbiente_el);
		
		// Si IDLink es -1 se buscan todas los links de un IDAmbiente.
		if (IDLink >= 0) {
			Element IDLink_el = doc.createElement(Parser.RECURSOID_TAG);
			IDLink_el.appendChild(doc.createTextNode(String.valueOf(IDLink)));
			nodeElement.appendChild(IDLink_el);
		}
		
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		String xml = serializer.writeToString(doc);		
		
		return xml;
	}
	
}
