package connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Recurso;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
	
	public String serializeRecursoQuery(int recursoId) {
		
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);

		Element recursoNode = doc.createElement(Parser.RECURSO_TAG);
		rootElement.appendChild(recursoNode);
		
		Element recursoID = doc.createElement(Parser.ID_TAG);
		recursoID.appendChild(doc.createTextNode(String.valueOf(recursoId)));
		recursoNode.appendChild(recursoID);
		
		return convertDocumentToXml(doc);
		
	}
	
	public String serializeRecursosQuery(int IDAmbito) {
		
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(Parser.RECURSO_TAG);
		rootElement.appendChild(nodeElement);
		
		Element IDAmbito_el = doc.createElement(Parser.AMBITOID_TAG);
		IDAmbito_el.appendChild(doc.createTextNode(String.valueOf(IDAmbito)));
		nodeElement.appendChild(IDAmbito_el);
		
		return convertDocumentToXml(doc);
		
	}
	
	public String serializeDeleteQuery(int IDRecurso) {
		
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(EncuestaParser.RECURSO_TAG);
		rootElement.appendChild(nodeElement);
		
		Element IDRecurso_el = doc.createElement(EncuestaParser.ID_TAG);
		IDRecurso_el.appendChild(doc.createTextNode(String.valueOf(IDRecurso)));
		nodeElement.appendChild(IDRecurso_el);
		
		return convertDocumentToXml(doc);
		
	}
	
	@Override
	protected Serializable createSerializable(HashMap<String, String> fields) {
		int IDRecurso = Integer.parseInt(fields.get(Parser.ID_TAG));
		int IDAmbito = Integer.parseInt(fields.get(Parser.AMBITOID_TAG));
		String descripcion = fields.get(Parser.DESCRIPCION_TAG);
		String tipo = fields.get(Parser.TIPO_TAG);

		return new Recurso(IDRecurso, IDAmbito, descripcion, tipo);
	}
	
}
