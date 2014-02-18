package connection.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Recurso;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import connection.Serializable;
import connection.exceptions.ParseException;


public class RecursosParser extends Parser {
	
	public RecursosParser() {
		baseTag = Parser.RECURSO_TAG;
	}
	
	public List<Recurso> deserializeRecursos(String xml) throws ParseException {
		List<Recurso> recursos = new ArrayList<Recurso>();
		NodeList nodes = getBaseTagNodes(xml);
		
		for (int i = 0; i < nodes.getLength(); i++) {
			NodeList childNodes = nodes.item(i).getChildNodes();
			HashMap<String, String> fields;
			
		    if (childNodes != null) {
		    	fields = fillFieldValues(childNodes);
		        
				Recurso recurso = (Recurso) createSerializable(fields);
				recursos.add(recurso);
		    }
		}
		return recursos;
		
	}
	
	@Override
	protected Serializable createSerializable(HashMap<String, String> fields) {
		int IDRecurso = Integer.parseInt(fields.get(Parser.ID_TAG));
		int IDAmbito = Integer.parseInt(fields.get(Parser.AMBITOID_TAG));
		String descripcion = fields.get(Parser.DESCRIPCION_TAG);
		String tipo = fields.get(Parser.TIPO_TAG);

		return new Recurso(IDRecurso, IDAmbito, descripcion, tipo);
	}
	
	@Override
	protected void addElements(Serializable serializable, Element baseNode) {
		Recurso recurso = (Recurso) serializable;
		addTextElement(baseNode, Parser.ID_TAG, String.valueOf(recurso.getRecursoId()));
		addTextElement(baseNode, Parser.AMBITOID_TAG, String.valueOf(recurso.getAmbitoId()));
		addTextElement(baseNode, Parser.DESCRIPCION_TAG, recurso.getDescripcion());
		addTextElement(baseNode, Parser.TIPO_TAG, recurso.getTipo());
		
	}
	
}
