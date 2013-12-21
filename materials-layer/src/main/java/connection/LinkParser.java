package connection;

import java.util.HashMap;

import model.Link;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;


public class LinkParser extends Parser {

	public static String LINK_TAG = "link";
	public static String NOMBRE_TAG = "nombre";
	
	
	public String serializeLink(Link link) {
		
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		//cargo datos del recurso
		Element recursoNode = doc.createElement(Parser.RECURSO_TAG);
		rootElement.appendChild(recursoNode);

		if ((link.getRecursoId() != null) && (!link.getRecursoId().equals(0))) {
			Element recursoId = doc.createElement(Parser.RECURSOID_TAG);
			recursoId.appendChild(doc.createTextNode(String.valueOf(link.getRecursoId())));
			recursoNode.appendChild(recursoId);
		}
		Element ambitoId = doc.createElement(Parser.AMBITOID_TAG);
		ambitoId.appendChild(doc.createTextNode(String.valueOf(link.getAmbitoId())));
		recursoNode.appendChild(ambitoId);		
		Element descripcion = doc.createElement(Parser.DESCRIPCION_TAG);
		descripcion.appendChild(doc.createTextNode(link.getDescripcion()));
		recursoNode.appendChild(descripcion);
		Element tipo = doc.createElement(Parser.TIPO_TAG);
		tipo.appendChild(doc.createTextNode(link.getTipo()));
		recursoNode.appendChild(tipo);
		
		// Creo el elemento Recursos
		Element recursos = doc.createElement(Parser.RECURSOS_TAG);
		recursoNode.appendChild(recursos);
		
		// Creo el elemento Link
		Element linkNode = doc.createElement(LinkParser.LINK_TAG);
		recursos.appendChild(linkNode);

		if ((link.getRecursoId() != null) && (!link.getRecursoId().equals(0))) {
			Element recursoId = doc.createElement(Parser.RECURSOID_TAG);
			recursoId.appendChild(doc.createTextNode(String.valueOf(link.getRecursoId())));
			linkNode.appendChild(recursoId);
		}
		Element nombre = doc.createElement(LinkParser.NOMBRE_TAG);
		nombre.appendChild(doc.createTextNode(String.valueOf(link.getNombre())));
		linkNode.appendChild(nombre);
		
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		String xml = serializer.writeToString(doc);		
		
		return xml;
		
	}
	
	public Link deserializeLink(String xml) {
		
		Link link = null;
		
		Document doc = this.convertToXMLDocument(xml);
		NodeList nodes = doc.getElementsByTagName(Parser.RECURSO_TAG);
		NodeList childNodes = nodes.item(0).getChildNodes();
		HashMap<String, String> fields = new HashMap<String, String>();

		if (childNodes != null) {
			
			for (int i = 0; i < childNodes.getLength(); i++) {
				Element element = (Element) childNodes.item(i);
				fields.put(element.getNodeName(), element.getTextContent());
			}

			int IDRecurso = Integer.parseInt(fields.get(Parser.RECURSOID_TAG));
			int IDAmbito = Integer.parseInt(fields.get(Parser.AMBITOID_TAG));
			String descripcion = fields.get(Parser.DESCRIPCION_TAG);
			
			String recursos = fields.get(Parser.RECURSOS_TAG);
			Document subdoc = this.convertToXMLDocument(recursos);
			NodeList subnodes = subdoc.getElementsByTagName(LinkParser.LINK_TAG);
			NodeList subchildNodes = subnodes.item(0).getChildNodes();
			HashMap<String, String> subfields = new HashMap<String, String>();
			
		    if (subchildNodes != null) {
		    	
		        for (int i = 0; i < subchildNodes.getLength(); i++) {
	        	   Element element = (Element) subchildNodes.item(i);
	        	   subfields.put(element.getNodeName(), element.getTextContent());
		        }

				String nombre = subfields.get(LinkParser.NOMBRE_TAG);	
				link = new Link(IDRecurso, IDAmbito, descripcion);
				link.setNombre(nombre);
				
		    }
			
		}

		return link;
		
	}
	
	public String serializeLinkQuery(int IDRecurso) {
		
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(Parser.RECURSO_TAG);
		rootElement.appendChild(nodeElement);
		
		Element joinElement = doc.createElement(Parser.JOIN_TAG);
		nodeElement.appendChild(joinElement);
		
		Element link = doc.createElement(LinkParser.LINK_TAG);
		link.appendChild(doc.createTextNode(String.valueOf(IDRecurso)));
		joinElement.appendChild(link);
		
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		String xml = serializer.writeToString(doc);		
		
		return xml;
		
	}
	
}
