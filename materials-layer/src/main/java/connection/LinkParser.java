package connection;

import java.util.HashMap;

import model.Link;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class LinkParser extends Parser {

	public static String LINK_TAG = "Link";
	public static String NOMBRE_TAG = "nombre";
	
	public LinkParser() {
		baseTag = LINK_TAG;
	}
	
	public String serialize(Serializable serializable) {

		Document doc = buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);

//		// cargo datos del recurso
//		Element recursoNode = doc.createElement(Parser.RECURSO_TAG);
//		rootElement.appendChild(recursoNode);
//
//		if ((link.getRecursoId() != null) && (!link.getRecursoId().equals(0))) {
//			Element recursoId = doc.createElement(Parser.ID_TAG);
//			recursoId.appendChild(doc.createTextNode(String.valueOf(link.getRecursoId())));
//			recursoNode.appendChild(recursoId);
//		}
//		
//		Element ambitoId = doc.createElement(Parser.AMBITOID_TAG);
//		ambitoId.appendChild(doc.createTextNode(String.valueOf(link.getAmbitoId())));
//		recursoNode.appendChild(ambitoId);
//		
//		Element descripcion = doc.createElement(Parser.DESCRIPCION_TAG);
//		descripcion.appendChild(doc.createTextNode(link.getDescripcion()));
//		recursoNode.appendChild(descripcion);
//		
//		Element tipo = doc.createElement(Parser.TIPO_TAG);
//		tipo.appendChild(doc.createTextNode(link.getTipo()));
//		recursoNode.appendChild(tipo);
//
//		// Creo el elemento Recursos
//		Element recursos = doc.createElement(Parser.RECURSOS_TAG);
//		recursoNode.appendChild(recursos);

		// Creo el elemento Link
		Element linkNode = doc.createElement(baseTag);
//		recursos.appendChild(linkNode);
		rootElement.appendChild(linkNode);

		Element recursoId = doc.createElement(Parser.ID_TAG);
		recursoId.appendChild(doc.createTextNode(String.valueOf(((Link) serializable).getRecursoId())));
		linkNode.appendChild(recursoId);
		Element nombre = doc.createElement(LinkParser.NOMBRE_TAG);
		nombre.appendChild(doc.createTextNode(String.valueOf(((Link) serializable).getNombre())));
		linkNode.appendChild(nombre);

		return convertDocumentToXml(doc);

	}
	
	@Override
	protected Serializable createSerializable(HashMap<String, String> fields) {
		Link link = new Link(0, 0, "");
		link.setNombre(fields.get(NOMBRE_TAG));
		return link;
	}

}
