package connection;

import java.util.HashMap;

import model.Link;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import connection.exceptions.ParseException;


public class LinkParser extends Parser {

	public static String LINK_TAG = "Link";
	public static String NOMBRE_TAG = "nombre";
	
	
	public String serializeLink(Link link) {

		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);

		// cargo datos del recurso
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

		return convertDocumentToXml(doc);

	}

	public Link deserializeLink(String xml) throws ParseException {

		Link link = null;

		Document doc = this.convertXmlToDocument(xml);
		if (doc == null) {
			return null;
		}
		HashMap<String, String> fields = new HashMap<String, String>();

		NodeList linkNode = doc.getElementsByTagName(LinkParser.LINK_TAG);
		NodeList linkChildNodes = linkNode.item(0).getChildNodes();

		if (linkChildNodes != null) {

			for (int i = 0; i < linkChildNodes.getLength(); i++) {
				Element element = (Element) linkChildNodes.item(i);
				fields.put(element.getNodeName(), element.getTextContent());
			}

			String nombre = fields.get(LinkParser.NOMBRE_TAG);
			link = new Link(0, 0, "");
			link.setNombre(nombre);

		}

		return link;

	}

}
