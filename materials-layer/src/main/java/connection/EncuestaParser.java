package connection;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import model.Encuesta;
import model.Pregunta;
import model.PreguntaRespuestaACompletar;


public class EncuestaParser extends Parser {

	public static String ENCUESTA_TAG = "Encuesta";
	public static String IDENCUESTA_TAG = "IDEncuesta";
	public static String IDAMBIENTE_TAG = "IDAmbiente";
	public static String DESCRIPCION_TAG = "Descripcion";
	public static String EVALUADA_TAG = "Evaluada";
	
	public static String PREGUNTA_TAG = "Pregunta";
	public static String IDPREGUNTA_TAG = "IDPregunta";
	public static String ENUNCIADO_TAG = "Enunciado";
	
	
	public String serializeEncuesta(Encuesta encuesta) {
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(EncuestaParser.ENCUESTA_TAG);
		rootElement.appendChild(nodeElement);
		
		Element IDAmbiente = doc.createElement(EncuestaParser.IDAMBIENTE_TAG);
		IDAmbiente.appendChild(doc.createTextNode(String.valueOf(encuesta.getIdAmbiente())));
		nodeElement.appendChild(IDAmbiente);
		Element IDEncuesta = doc.createElement(EncuestaParser.IDENCUESTA_TAG);
		IDEncuesta.appendChild(doc.createTextNode(String.valueOf(encuesta.getIdRecurso())));
		nodeElement.appendChild(IDEncuesta);
		Element descripcion = doc.createElement(EncuestaParser.DESCRIPCION_TAG);
		descripcion.appendChild(doc.createTextNode(encuesta.getDescripcion()));
		nodeElement.appendChild(descripcion);
		Element evaluada = doc.createElement(EncuestaParser.EVALUADA_TAG);
		evaluada.appendChild(doc.createTextNode(String.valueOf(encuesta.esEvaluada())));
		nodeElement.appendChild(evaluada);
		
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		String xml = serializer.writeToString(doc);		
		
		return xml;
	}
	
	public Encuesta deserializeEncuesta(String xml) {
		Document doc = this.convertToXMLDocument(xml);
		NodeList nodes = doc.getElementsByTagName(EncuestaParser.ENCUESTA_TAG);
		NodeList childNodes = nodes.item(0).getChildNodes(); 
		HashMap<String, String> fields = new HashMap<String, String>();
	    if (childNodes != null) {
	        for (int i = 0; i < childNodes.getLength(); i++) {
        	   Element element = (Element) childNodes.item(i);
        	   fields.put(element.getNodeName(), element.getTextContent());
	        }
	    }
	    
		int IDAmbiente = Integer.parseInt(fields.get(EncuestaParser.IDAMBIENTE_TAG));
		int IDEncuesta = Integer.parseInt(fields.get(EncuestaParser.IDENCUESTA_TAG));
		String descripcion = fields.get(EncuestaParser.DESCRIPCION_TAG);
		Boolean evaluada = Boolean.parseBoolean(fields.get(EncuestaParser.DESCRIPCION_TAG));
		
		Encuesta encuesta = new Encuesta(IDEncuesta, IDAmbiente, descripcion, evaluada);
		return encuesta;
	}
	
	public String serializeEncuestaQuery(int IDAmbiente, int IDEncuesta) {
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(EncuestaParser.ENCUESTA_TAG);
		rootElement.appendChild(nodeElement);
		
		Element IDAmbiente_el = doc.createElement(EncuestaParser.IDAMBIENTE_TAG);
		IDAmbiente_el.appendChild(doc.createTextNode(String.valueOf(IDAmbiente)));
		nodeElement.appendChild(IDAmbiente_el);
		Element IDEncuesta_el = doc.createElement(EncuestaParser.IDENCUESTA_TAG);
		IDEncuesta_el.appendChild(doc.createTextNode(String.valueOf(IDEncuesta)));
		nodeElement.appendChild(IDEncuesta_el);
		
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		String xml = serializer.writeToString(doc);		
		
		return xml;
	}
	
	public String serializePreguntas(Encuesta encuesta) { // 	TODO: para todo tipo de preguntas.
		ArrayList<Pregunta> preguntas = encuesta.getPreguntas();
		
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);

		for (int i=0; i<preguntas.size(); i++) {
			Element nodeElement = doc.createElement(EncuestaParser.PREGUNTA_TAG);
			rootElement.appendChild(nodeElement);
			
			Element IDAmbiente = doc.createElement(EncuestaParser.IDAMBIENTE_TAG);
			IDAmbiente.appendChild(doc.createTextNode(String.valueOf(encuesta.getIdAmbiente())));
			nodeElement.appendChild(IDAmbiente);
			Element IDEncuesta = doc.createElement(EncuestaParser.IDENCUESTA_TAG);
			IDEncuesta.appendChild(doc.createTextNode(String.valueOf(encuesta.getIdRecurso())));
			nodeElement.appendChild(IDEncuesta);
			Element IDPregunta = doc.createElement(EncuestaParser.IDPREGUNTA_TAG);
			IDPregunta.appendChild(doc.createTextNode(String.valueOf(preguntas.get(i).getIdPregunta())));
			nodeElement.appendChild(IDPregunta);
			Element enunciado = doc.createElement(EncuestaParser.ENUNCIADO_TAG);
			enunciado.appendChild(doc.createTextNode(preguntas.get(i).getEnunciado()));
			nodeElement.appendChild(enunciado);
		}
		
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		String xml = serializer.writeToString(doc);		
		
		return xml;
	}
	
	public ArrayList<Pregunta> deserializePreguntas(String xml) {
		ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();
		
		Document doc = this.convertToXMLDocument(xml);
		NodeList nodes = doc.getElementsByTagName(EncuestaParser.PREGUNTA_TAG);
		for (int i=0; i<nodes.getLength(); i++) {
			NodeList childNodes = nodes.item(i).getChildNodes(); 
			HashMap<String, String> fields = new HashMap<String, String>();
		    if (childNodes != null) {
		        for (int j = 0; j < childNodes.getLength(); j++) {
	        	   Element element = (Element) childNodes.item(j);
	        	   fields.put(element.getNodeName(), element.getTextContent());
		        }
		    }
			int IDPregunta = Integer.parseInt(fields.get(EncuestaParser.IDPREGUNTA_TAG));
			String enunciado = fields.get(EncuestaParser.ENUNCIADO_TAG);
			Pregunta pregunta = new PreguntaRespuestaACompletar(enunciado, IDPregunta); // 	TODO: para todo tipo de preguntas.
			preguntas.add(pregunta);
		}
		
		return preguntas;
	}
	
	public String serializePreguntasQuery(int IDAmbiente, int IDEncuesta) {
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(EncuestaParser.PREGUNTA_TAG);
		rootElement.appendChild(nodeElement);
		
		Element IDAmbiente_el = doc.createElement(EncuestaParser.IDAMBIENTE_TAG);
		IDAmbiente_el.appendChild(doc.createTextNode(String.valueOf(IDAmbiente)));
		nodeElement.appendChild(IDAmbiente_el);
		Element IDEncuesta_el = doc.createElement(EncuestaParser.IDENCUESTA_TAG);
		IDEncuesta_el.appendChild(doc.createTextNode(String.valueOf(IDEncuesta)));
		nodeElement.appendChild(IDEncuesta_el);
		
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		String xml = serializer.writeToString(doc);		
		
		return xml;
	}
	
}
