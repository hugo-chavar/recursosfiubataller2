package connection;

import java.util.HashMap;

import model.EncuestaRespondida;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class EncuestaRespondidaParser extends Parser {
	
	public static String ENCUESTA_TAG = "encuesta";
	public static String ENCUESTA_RESPONDIDA_TAG = "encuestaRespondida";
	public static String IDUSUARIO_TAG = "usuarioId";
	public static String EVALUACION_TAG = "evaluacion";
	public static String PREGUNTAS_RESPONDIDAS_TAG = "preguntasRespondidas";

	public String serializeEncuestaRespondida(EncuestaRespondida respondida) {

		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);

		Element nodeElement = doc
				.createElement(EncuestaRespondidaParser.ENCUESTA_RESPONDIDA_TAG);
		rootElement.appendChild(nodeElement);

		Element IDEncuesta = doc.createElement(EncuestaRespondidaParser.RECURSOID_TAG);
		IDEncuesta.appendChild(doc.createTextNode(String.valueOf(respondida
				.getIdRecurso())));
		nodeElement.appendChild(IDEncuesta);
		Element IDUsuario = doc.createElement(EncuestaRespondidaParser.IDUSUARIO_TAG);
		IDUsuario.appendChild(doc.createTextNode(String.valueOf(respondida
				.getIdUsuario())));
		nodeElement.appendChild(IDUsuario);
		Element evaluacion = doc.createElement(EncuestaRespondidaParser.EVALUACION_TAG);
		evaluacion.appendChild(doc.createTextNode(String.valueOf(respondida
				.getEvaluacion())));
		nodeElement.appendChild(evaluacion);

		String respondidas_str = respondida.marshallPreguntasRespondidas();
		Element respondidas = doc
				.createElement(EncuestaRespondidaParser.PREGUNTAS_RESPONDIDAS_TAG);
		respondidas.appendChild(doc.createTextNode(respondidas_str));
		nodeElement.appendChild(respondidas);

		return convertDocumentToXml(doc);

	}
	
	public EncuestaRespondida deserializeEncuestaRespondida(String xml) {
		
		EncuestaRespondida respondida = null;
		
		Document doc = this.convertXmlToDocument(xml);
		NodeList nodes = doc.getElementsByTagName(EncuestaRespondidaParser.ENCUESTA_TAG);
		NodeList childNodes = nodes.item(0).getChildNodes(); 
		HashMap<String, String> fields = new HashMap<String, String>();
		
	    if (childNodes != null) {
	    	
	        for (int i = 0; i < childNodes.getLength(); i++) {
        	   Element element = (Element) childNodes.item(i);
        	   fields.put(element.getNodeName(), element.getTextContent());
	        }
	        
			int IDEncuesta = Integer.parseInt(fields.get(EncuestaRespondidaParser.RECURSOID_TAG));
			int IDUsuario = Integer.parseInt(fields.get(EncuestaRespondidaParser.IDUSUARIO_TAG));
			int evaluacion = Integer.parseInt(fields.get(EncuestaRespondidaParser.EVALUACION_TAG));
			
			respondida = new EncuestaRespondida(IDEncuesta, IDUsuario, evaluacion);
			
			respondida.unmarshallPreguntasRespondidas(fields.get(EncuestaRespondidaParser.PREGUNTAS_RESPONDIDAS_TAG));
			
	    }

		return respondida;
		
	}
	
	public String serializeEncuestaRespondidaQuery(int IDAmbiente, int IDUsuario, int IDEncuesta) {
		
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(EncuestaRespondidaParser.ENCUESTA_TAG);
		rootElement.appendChild(nodeElement);
		
		Element IDAmbiente_el = doc.createElement(EncuestaRespondidaParser.AMBITOID_TAG);
		IDAmbiente_el.appendChild(doc.createTextNode(String.valueOf(IDAmbiente)));
		nodeElement.appendChild(IDAmbiente_el);
		Element IDEncuesta_el = doc.createElement(EncuestaRespondidaParser.RECURSOID_TAG);
		IDEncuesta_el.appendChild(doc.createTextNode(String.valueOf(IDEncuesta)));
		nodeElement.appendChild(IDEncuesta_el);
		Element IDUsuario_el = doc.createElement(EncuestaRespondidaParser.IDUSUARIO_TAG);
		IDUsuario_el.appendChild(doc.createTextNode(String.valueOf(IDUsuario)));
		nodeElement.appendChild(IDUsuario_el);
		
		return convertDocumentToXml(doc);
		
	}

}
