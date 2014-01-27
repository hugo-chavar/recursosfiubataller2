package connection;

import java.util.HashMap;

import model.EncuestaRespondida;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import connection.exceptions.ParseException;

public class EncuestaRespondidaParser extends Parser {
	
	public static String ENCUESTA_RESPONDIDA_TAG = "EncuestaRespondida";
	public static String IDUSUARIO_TAG = "usuarioId";
	public static String EVALUACION_TAG = "evaluacion";
	public static String PREGUNTAS_RESPONDIDAS_TAG = "preguntasRespondidas";
	public static String IDRECURSO_TAG = "recursoId";

	public String serializeEncuestaRespondida(EncuestaRespondida respondida) {

		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);

		Element nodeElement = doc.createElement(ENCUESTA_RESPONDIDA_TAG);
		rootElement.appendChild(nodeElement);

		Element IDEncuesta = doc.createElement(IDRECURSO_TAG);
		IDEncuesta.appendChild(doc.createTextNode(String.valueOf(respondida
				.getIdRecurso())));
		nodeElement.appendChild(IDEncuesta);
		Element IDUsuario = doc.createElement(IDUSUARIO_TAG);
		IDUsuario.appendChild(doc.createTextNode(String.valueOf(respondida
				.getIdUsuario())));
		nodeElement.appendChild(IDUsuario);
		//TODO: No siempre se debe incluir este campo, si es no evaluado no
		//if(respondida.getEvaluacion()!=-1)
		Element evaluacion = doc.createElement(EVALUACION_TAG);
		evaluacion.appendChild(doc.createTextNode(String.valueOf(respondida
				.getEvaluacion())));
		nodeElement.appendChild(evaluacion);

		String respondidas_str = respondida.marshallPreguntasRespondidas();
		Element respondidas = doc.createElement(PREGUNTAS_RESPONDIDAS_TAG);
		respondidas.appendChild(doc.createTextNode(respondidas_str));
		nodeElement.appendChild(respondidas);

		return convertDocumentToXml(doc);

	}
	
	public EncuestaRespondida deserializeEncuestaRespondida(String xml) throws ParseException {
		
		EncuestaRespondida respondida = null;
		String tag = ENCUESTA_RESPONDIDA_TAG;
		
		Document doc = this.convertXmlToDocument(xml);
		if (doc == null) {
			return null;
		}
		HashMap<String, String> fields = new HashMap<String, String>();
		
		NodeList nodes = doc.getElementsByTagName(tag);
		if (nodes.getLength() == 0) {
			throw new ParseException("No existe tag " + tag);
		}
		NodeList childNodes = nodes.item(0).getChildNodes(); 
		
		
	    if (childNodes != null) {
	    	
	        for (int i = 0; i < childNodes.getLength(); i++) {
        	   Element element = (Element) childNodes.item(i);
        	   fields.put(element.getNodeName(), element.getTextContent());
	        }
	        
			int IDEncuesta = Integer.parseInt(fields.get(IDRECURSO_TAG));
			int IDUsuario = Integer.parseInt(fields.get(IDUSUARIO_TAG));
			int evaluacion = Integer.parseInt(fields.get(EVALUACION_TAG));
			
			respondida = new EncuestaRespondida(IDEncuesta, IDUsuario);
			respondida.setEvaluacion(evaluacion);
			respondida.unmarshallPreguntasRespondidas(fields.get(PREGUNTAS_RESPONDIDAS_TAG));
			
	    }

		return respondida;
		
	}
	
	public String serializeEncuestaRespondidaQuery(int IDUsuario, int IDEncuesta) {
		
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(ENCUESTA_RESPONDIDA_TAG);
		rootElement.appendChild(nodeElement);
		
//		Element IDAmbiente_el = doc.createElement(EncuestaRespondidaParser.AMBITOID_TAG);
//		IDAmbiente_el.appendChild(doc.createTextNode(String.valueOf(IDAmbiente)));
//		nodeElement.appendChild(IDAmbiente_el);
		Element IDEncuesta_el = doc.createElement(IDRECURSO_TAG);
		IDEncuesta_el.appendChild(doc.createTextNode(String.valueOf(IDEncuesta)));
		nodeElement.appendChild(IDEncuesta_el);
		Element IDUsuario_el = doc.createElement(IDUSUARIO_TAG);
		IDUsuario_el.appendChild(doc.createTextNode(String.valueOf(IDUsuario)));
		nodeElement.appendChild(IDUsuario_el);
		
		return convertDocumentToXml(doc);
		
	}

}
