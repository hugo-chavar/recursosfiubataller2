package connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Recurso;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class RecursosParser extends Parser {
	
	
	public Recurso deserializeRecurso(String xml) {
		
		Recurso recurso = null;
		
		Document doc = this.convertXmlToDocument(xml);
		if (doc == null) {
			return recurso;
		}
		NodeList nodes = doc.getElementsByTagName(Parser.RECURSO_TAG);
		NodeList childNodes = nodes.item(0).getChildNodes();
		HashMap<String, String> fields = new HashMap<String, String>();
		
	    if (childNodes != null) {
	    	
	        for (int j = 0; j < childNodes.getLength(); j++) {
        	   Element element = (Element) childNodes.item(j);
        	   fields.put(element.getNodeName(), element.getTextContent());
	        }
	        
			int IDRecurso = Integer.parseInt(fields.get(Parser.RECURSOID_TAG));
			int IDAmbito = Integer.parseInt(fields.get(Parser.AMBITOID_TAG));
			String descripcion = fields.get(Parser.DESCRIPCION_TAG);
			String tipo = fields.get(Parser.TIPO_TAG);

			recurso = new Recurso(IDRecurso, IDAmbito, descripcion, tipo);
		    
	    }
	    
		return recurso;
		
	}
	
	public List<Recurso> deserializeRecursos(String xml) {
		
		List<Recurso> recursos = new ArrayList<Recurso>();
		
		Document doc = this.convertXmlToDocument(xml);
		if (doc == null) {
			return null;
		}
		NodeList nodes = doc.getElementsByTagName(Parser.RECURSO_TAG);
		
		for (int i = 0; i < nodes.getLength(); i++) {
		
			NodeList childNodes = nodes.item(i).getChildNodes();
			HashMap<String, String> fields = new HashMap<String, String>();
			
		    if (childNodes != null) {
		    	
		        for (int j = 0; j < childNodes.getLength(); j++) {
	        	   Element element = (Element) childNodes.item(j);
	        	   fields.put(element.getNodeName(), element.getTextContent());
		        }
		        
				int IDRecurso = Integer.parseInt(fields.get(Parser.RECURSOID_TAG));
				int IDAmbito = Integer.parseInt(fields.get(Parser.AMBITOID_TAG));
				String descripcion = fields.get(Parser.DESCRIPCION_TAG);
				String tipo = fields.get(Parser.TIPO_TAG);//Este tipo da la reflexion que estoy buscando
	
				Recurso recurso = new Recurso(IDRecurso, IDAmbito, descripcion, tipo);
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
		
		Element recursoID = doc.createElement(Parser.RECURSOID_TAG);
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
		
		Element IDRecurso_el = doc.createElement(EncuestaParser.RECURSOID_TAG);
		IDRecurso_el.appendChild(doc.createTextNode(String.valueOf(IDRecurso)));
		nodeElement.appendChild(IDRecurso_el);
		
		return convertDocumentToXml(doc);
		
	}
	
}
