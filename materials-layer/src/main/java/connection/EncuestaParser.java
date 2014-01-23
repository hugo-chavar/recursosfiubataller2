package connection;

import java.util.HashMap;

import model.Encuesta;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class EncuestaParser extends Parser {
	
	public static String ENCUESTA_TAG = "encuesta";
	public static String EVALUADA_TAG = "evaluada";
	public static String PREGUNTAS_TAG = "preguntas";
	
//	public static String ENCUESTA_RESPONDIDA_TAG = "encuestaRespondida";
//	public static String IDUSUARIO_TAG = "usuarioId";
//	public static String EVALUACION_TAG = "evaluacion";
//	public static String PREGUNTAS_RESPONDIDAS_TAG = "preguntasRespondidas";
	
	
	public String serializeEncuesta(Encuesta encuesta) {
		
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(Parser.RECURSO_TAG);
		rootElement.appendChild(nodeElement);
		
		if ((encuesta.getRecursoId() != null) && (!encuesta.getRecursoId().equals(0))) {
			Element IDRecurso = doc.createElement(Parser.RECURSOID_TAG);
			IDRecurso.appendChild(doc.createTextNode(String.valueOf(encuesta.getRecursoId())));
			nodeElement.appendChild(IDRecurso);
		}
		Element IDAmbito = doc.createElement(Parser.AMBITOID_TAG);
		IDAmbito.appendChild(doc.createTextNode(String.valueOf(encuesta.getAmbitoId())));
		nodeElement.appendChild(IDAmbito);
		Element descripcion = doc.createElement(Parser.DESCRIPCION_TAG);
		descripcion.appendChild(doc.createTextNode(encuesta.getDescripcion()));
		nodeElement.appendChild(descripcion);
		Element tipo = doc.createElement(Parser.TIPO_TAG);
		tipo.appendChild(doc.createTextNode(encuesta.getTipo()));
		nodeElement.appendChild(tipo);
		
		Element recursos = doc.createElement(Parser.RECURSOS_TAG);
		nodeElement.appendChild(recursos);
		
		Element encuesta_el = doc.createElement(EncuestaParser.ENCUESTA_TAG);
		recursos.appendChild(encuesta_el);
		
		if ((encuesta.getRecursoId() != null) && (!encuesta.getRecursoId().equals(0))) {
			Element IDRecurso = doc.createElement(Parser.RECURSOID_TAG);
			IDRecurso.appendChild(doc.createTextNode(String.valueOf(encuesta.getRecursoId())));
			encuesta_el.appendChild(IDRecurso);
		}
		Element evaluada = doc.createElement(EncuestaParser.EVALUADA_TAG);
		evaluada.appendChild(doc.createTextNode(String.valueOf(encuesta.isEvaluada())));
		encuesta_el.appendChild(evaluada);
		
		String preguntas_str = encuesta.marshallPreguntas();
		Element preguntas = doc.createElement(EncuestaParser.PREGUNTAS_TAG);
		preguntas.appendChild(doc.createTextNode(preguntas_str));
		encuesta_el.appendChild(preguntas);
		
		return convertDocumentToXml(doc);
		
	}
	
//	public String serializeEncuestaRespondida(EncuestaRespondida respondida) {
//		
//		Document doc = this.buildXMLDocument();
//		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
//		doc.appendChild(rootElement);
//		
//		Element nodeElement = doc.createElement(EncuestaParser.ENCUESTA_RESPONDIDA_TAG);
//		rootElement.appendChild(nodeElement);
//
//		Element IDEncuesta = doc.createElement(EncuestaParser.RECURSOID_TAG);
//		IDEncuesta.appendChild(doc.createTextNode(String.valueOf(respondida.getIdRecurso())));
//		nodeElement.appendChild(IDEncuesta);
//		Element IDUsuario = doc.createElement(EncuestaParser.IDUSUARIO_TAG);
//		IDUsuario.appendChild(doc.createTextNode(String.valueOf(respondida.getIdUsuario())));
//		nodeElement.appendChild(IDUsuario);
//		Element evaluacion = doc.createElement(EncuestaParser.EVALUACION_TAG);
//		evaluacion.appendChild(doc.createTextNode(String.valueOf(respondida.getEvaluacion())));
//		nodeElement.appendChild(evaluacion);
//		
//		String respondidas_str = respondida.marshallPreguntasRespondidas();
//		Element respondidas = doc.createElement(EncuestaParser.PREGUNTAS_RESPONDIDAS_TAG);
//		respondidas.appendChild(doc.createTextNode(respondidas_str));
//		nodeElement.appendChild(respondidas);
//
//		return convertDocumentToXml(doc);
//		
//	}
	
	public Encuesta deserializeEncuesta(String xml) {

		Encuesta encuesta = null;

		Document doc = this.convertXmlToDocument(xml);
		HashMap<String, String> fields = new HashMap<String, String>();

		NodeList encuestaNode = doc.getElementsByTagName(EncuestaParser.ENCUESTA_TAG);
		NodeList encuestaChildNodes = encuestaNode.item(0).getChildNodes();
		
		if (encuestaChildNodes != null) {

			for (int i = 0; i < encuestaChildNodes.getLength(); i++) {
				Element element = (Element) encuestaChildNodes.item(i);
				fields.put(element.getNodeName(), element.getTextContent());
			}

			Boolean evaluada = Boolean.parseBoolean(fields.get(EncuestaParser.EVALUADA_TAG));
			encuesta = new Encuesta(0, 0, "", evaluada);
			encuesta.unmarshallPreguntas(fields.get(EncuestaParser.PREGUNTAS_TAG));

		}

		return encuesta;

	}
	
//	public EncuestaRespondida deserializeEncuestaRespondida(String xml) {
//		
//		EncuestaRespondida respondida = null;
//		
//		Document doc = this.convertXmlToDocument(xml);
//		NodeList nodes = doc.getElementsByTagName(EncuestaParser.ENCUESTA_TAG);
//		NodeList childNodes = nodes.item(0).getChildNodes(); 
//		HashMap<String, String> fields = new HashMap<String, String>();
//		
//	    if (childNodes != null) {
//	    	
//	        for (int i = 0; i < childNodes.getLength(); i++) {
//        	   Element element = (Element) childNodes.item(i);
//        	   fields.put(element.getNodeName(), element.getTextContent());
//	        }
//	        
//			int IDEncuesta = Integer.parseInt(fields.get(EncuestaParser.RECURSOID_TAG));
//			int IDUsuario = Integer.parseInt(fields.get(EncuestaParser.IDUSUARIO_TAG));
//			int evaluacion = Integer.parseInt(fields.get(EncuestaParser.EVALUACION_TAG));
//			
//			respondida = new EncuestaRespondida(IDEncuesta, IDUsuario, evaluacion);
//			
//			respondida.unmarshallPreguntasRespondidas(fields.get(EncuestaParser.PREGUNTAS_RESPONDIDAS_TAG));
//			
//	    }
//
//		return respondida;
//		
//	}
//	
//	public String serializeEncuestaRespondidaQuery(int IDAmbiente, int IDUsuario, int IDEncuesta) {
//		
//		Document doc = this.buildXMLDocument();
//		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
//		doc.appendChild(rootElement);
//		
//		Element nodeElement = doc.createElement(EncuestaParser.ENCUESTA_TAG);
//		rootElement.appendChild(nodeElement);
//		
//		Element IDAmbiente_el = doc.createElement(EncuestaParser.AMBITOID_TAG);
//		IDAmbiente_el.appendChild(doc.createTextNode(String.valueOf(IDAmbiente)));
//		nodeElement.appendChild(IDAmbiente_el);
//		Element IDEncuesta_el = doc.createElement(EncuestaParser.RECURSOID_TAG);
//		IDEncuesta_el.appendChild(doc.createTextNode(String.valueOf(IDEncuesta)));
//		nodeElement.appendChild(IDEncuesta_el);
//		Element IDUsuario_el = doc.createElement(EncuestaParser.IDUSUARIO_TAG);
//		IDUsuario_el.appendChild(doc.createTextNode(String.valueOf(IDUsuario)));
//		nodeElement.appendChild(IDUsuario_el);
//		
//		return convertDocumentToXml(doc);
//		
//	}
	
}
