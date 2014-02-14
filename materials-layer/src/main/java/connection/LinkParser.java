package connection;

import java.util.HashMap;

import model.Link;

import org.w3c.dom.Element;


public class LinkParser extends Parser {

	public static String LINK_TAG = "Link";
	public static String NOMBRE_TAG = "nombre";
	
	public LinkParser() {
		baseTag = LINK_TAG;
	}
	
//	public String serialize(Serializable serializable) {
//
//		Document doc = buildXMLDocument();
//		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
//		doc.appendChild(rootElement);
//
//		Element baseNode = doc.createElement(baseTag);
//		rootElement.appendChild(baseNode);
//
//		Element recursoId = doc.createElement(Parser.ID_TAG);
//		recursoId.appendChild(doc.createTextNode(String.valueOf(((Link) serializable).getRecursoId())));
//		baseNode.appendChild(recursoId);
//		Element nombre = doc.createElement(LinkParser.NOMBRE_TAG);
//		nombre.appendChild(doc.createTextNode(String.valueOf(((Link) serializable).getNombre())));
//		baseNode.appendChild(nombre);
//
//		return convertDocumentToXml(doc);
//
//	}
	
	@Override
	protected Serializable createSerializable(HashMap<String, String> fields) {
		Link link = new Link(0, 0, "");
		link.setNombre(fields.get(NOMBRE_TAG));
		return link;
	}
	
	@Override
	protected void addElements(Serializable serializable, Element baseNode) {
		Link link = (Link) serializable;
		addTextElement(baseNode, Parser.ID_TAG, String.valueOf(link.getRecursoId()));
		addTextElement(baseNode, NOMBRE_TAG, link.getNombre());
		
	}

}
