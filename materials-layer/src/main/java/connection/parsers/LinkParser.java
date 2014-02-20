package connection.parsers;

import java.util.HashMap;

import model.Link;

import org.w3c.dom.Element;

import connection.Serializable;


public class LinkParser extends Parser {

	public static String LINK_TAG = "Link";
	public static String NOMBRE_TAG = "nombre";
	
	public LinkParser() {
		baseTag = LINK_TAG;
	}
	
	@Override
	protected Serializable createSerializable(HashMap<String, String> fields) {
		Link link = new Link(0, 0, "");
		link.setNombre(fields.get(NOMBRE_TAG));
		return link;
	}
	
	@Override
	protected void addElements(Serializable serializable, Element baseNode) {
		Link link = (Link) serializable;
		addTextElement(baseNode, Parser.RECURSOID_TAG, String.valueOf(link.getRecursoId()));
		addTextElement(baseNode, NOMBRE_TAG, link.getNombre());
		
	}

}
