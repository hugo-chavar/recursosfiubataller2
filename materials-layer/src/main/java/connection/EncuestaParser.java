package connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import model.Encuesta;
import model.EncuestaRespondida;


public class EncuestaParser extends Parser {

	public static String ENCUESTA_TAG = "Encuesta";
	public static String IDENCUESTA_TAG = "idRecurso";
	public static String IDAMBIENTE_TAG = "idAmbiente";
	public static String DESCRIPCION_TAG = "descripcion";
	public static String EVALUADA_TAG = "evaluada";
	public static String PREGUNTAS_TAG = "preguntas";
	
	public static String ENCUESTA_RESPONDIDA_TAG = "encuestaRespondida";
	public static String IDUSUARIO_TAG = "idUsuario";
	public static String EVALUACION_TAG = "Evaluacion";
	public static String PREGUNTAS_RESPONDIDAS_TAG = "preguntasRespondidas";
	
	
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
		evaluada.appendChild(doc.createTextNode(String.valueOf(encuesta.isEvaluada())));
		nodeElement.appendChild(evaluada);
		
		String preguntas_str = encuesta.marshallPreguntas();
		Element preguntas = doc.createElement(EncuestaParser.PREGUNTAS_TAG);
		preguntas.appendChild(doc.createTextNode(preguntas_str));
		nodeElement.appendChild(preguntas);
		
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		String xml = serializer.writeToString(doc);		
		
		return xml;
	}
	
	public String serializeEncuestaRespondida(EncuestaRespondida respondida) {
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(EncuestaParser.ENCUESTA_RESPONDIDA_TAG);
		rootElement.appendChild(nodeElement);
		
		Element IDAmbiente = doc.createElement(EncuestaParser.IDAMBIENTE_TAG);
		IDAmbiente.appendChild(doc.createTextNode(String.valueOf(respondida.getIdAmbiente())));
		nodeElement.appendChild(IDAmbiente);
		Element IDEncuesta = doc.createElement(EncuestaParser.IDENCUESTA_TAG);
		IDEncuesta.appendChild(doc.createTextNode(String.valueOf(respondida.getIdRecurso())));
		nodeElement.appendChild(IDEncuesta);
		Element IDUsuario = doc.createElement(EncuestaParser.IDUSUARIO_TAG);
		IDUsuario.appendChild(doc.createTextNode(String.valueOf(respondida.getIdUsuario())));
		nodeElement.appendChild(IDUsuario);
		Element evaluacion = doc.createElement(EncuestaParser.EVALUACION_TAG);
		evaluacion.appendChild(doc.createTextNode(String.valueOf(respondida.getEvaluacion())));
		nodeElement.appendChild(evaluacion);
		
		String respondidas_str = respondida.marshallPreguntasRespondidas();
		Element respondidas = doc.createElement(EncuestaParser.PREGUNTAS_RESPONDIDAS_TAG);
		respondidas.appendChild(doc.createTextNode(respondidas_str));
		nodeElement.appendChild(respondidas);
		
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		String xml = serializer.writeToString(doc);		
		
		return xml;
	}
	
	public List<Encuesta> deserializeEncuesta(String xml) {
		List<Encuesta> encuestas = new ArrayList<Encuesta>();
		Document doc = this.convertToXMLDocument(xml);
		NodeList nodes = doc.getElementsByTagName(EncuestaParser.ENCUESTA_TAG);
		
		for (int i = 0; i < nodes.getLength(); i++) {
			NodeList childNodes = nodes.item(i).getChildNodes(); 
			HashMap<String, String> fields = new HashMap<String, String>();
		    if (childNodes != null) {
		        for (int j = 0; j < childNodes.getLength(); j++) {
	        	   Element element = (Element) childNodes.item(j);
	        	   fields.put(element.getNodeName(), element.getTextContent());
		        }
		    }
		    
			int IDAmbiente = Integer.parseInt(fields.get(EncuestaParser.IDAMBIENTE_TAG));
			int IDEncuesta = Integer.parseInt(fields.get(EncuestaParser.IDENCUESTA_TAG));
			String descripcion = fields.get(EncuestaParser.DESCRIPCION_TAG);
			Boolean evaluada = Boolean.parseBoolean(fields.get(EncuestaParser.EVALUADA_TAG));		
			
			Encuesta encuesta = new Encuesta(IDEncuesta, IDAmbiente, descripcion, evaluada);
			
			encuesta.unmarshallPreguntas(fields.get(EncuestaParser.PREGUNTAS_TAG));
			
			encuestas.add(encuesta);
		}
		return encuestas;
	}
	
	public EncuestaRespondida deserializeEncuestaRespondida(String xml) {
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
		int IDUsuario = Integer.parseInt(fields.get(EncuestaParser.IDUSUARIO_TAG));
		int evaluacion = Integer.parseInt(fields.get(EncuestaParser.EVALUACION_TAG));
		
		EncuestaRespondida respondida = new EncuestaRespondida(IDAmbiente, IDEncuesta, IDUsuario, evaluacion);
		
		respondida.unmarshallPreguntasRespondidas(fields.get(EncuestaParser.PREGUNTAS_RESPONDIDAS_TAG));
		
		return respondida;
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
		
		// Si IDEncuesta es -1 se buscan todas las encuestas de un IDAmbiente.
		if (IDEncuesta >= 0) {
			Element IDEncuesta_el = doc.createElement(EncuestaParser.IDENCUESTA_TAG);
			IDEncuesta_el.appendChild(doc.createTextNode(String.valueOf(IDEncuesta)));
			nodeElement.appendChild(IDEncuesta_el);
		}
		
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		String xml = serializer.writeToString(doc);		
		
		return xml;
	}
	
	public String serializeEncuestaRespondidaQuery(int IDAmbiente, int IDUsuario, int IDEncuesta) {
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
		Element IDUsuario_el = doc.createElement(EncuestaParser.IDUSUARIO_TAG);
		IDUsuario_el.appendChild(doc.createTextNode(String.valueOf(IDUsuario)));
		nodeElement.appendChild(IDUsuario_el);
		
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		String xml = serializer.writeToString(doc);		
		
		return xml;
	}
	
}
