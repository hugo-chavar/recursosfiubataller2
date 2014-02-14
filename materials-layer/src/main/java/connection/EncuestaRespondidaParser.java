package connection;

import java.util.HashMap;

import model.EncuestaRespondida;
import model.Link;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class EncuestaRespondidaParser extends Parser {

	public static String ENCUESTA_RESPONDIDA_TAG = "EncuestaRespondida";
	public static String IDUSUARIO_TAG = "usuarioId";
	public static String EVALUACION_TAG = "evaluacion";
	public static String PREGUNTAS_RESPONDIDAS_TAG = "preguntasRespondidas";
	public static String IDRECURSO_TAG = "recursoId";

	public EncuestaRespondidaParser() {
		baseTag = ENCUESTA_RESPONDIDA_TAG;
	}

//	public String serialize(EncuestaRespondida respondida) {
//
//		Document document = buildXMLDocument();
//		Element rootElement = document.createElement(Parser.INITIAL_TAG);
//		document.appendChild(rootElement);
//
//		Element baseNode = document.createElement(ENCUESTA_RESPONDIDA_TAG);
//		rootElement.appendChild(baseNode);
//
//		Element IDEncuesta = document.createElement(IDRECURSO_TAG);
//		IDEncuesta.appendChild(document.createTextNode(String.valueOf(respondida.getIdRecurso())));
//		baseNode.appendChild(IDEncuesta);
//		
//		Element IDUsuario = document.createElement(IDUSUARIO_TAG);
//		IDUsuario.appendChild(document.createTextNode(String.valueOf(respondida.getIdUsuario())));
//		baseNode.appendChild(IDUsuario);
//		// TODO: Andy No siempre se debe incluir este campo, si es no evaluado no
//		// if(respondida.getEvaluacion()!=-1)
//		Element evaluacion = document.createElement(EVALUACION_TAG);
//		evaluacion.appendChild(document.createTextNode(String.valueOf(respondida.getEvaluacion())));
//		baseNode.appendChild(evaluacion);
//
//		String respondidas_str = respondida.marshallPreguntasRespondidas();
//		Element respondidas = document.createElement(PREGUNTAS_RESPONDIDAS_TAG);
//		respondidas.appendChild(document.createTextNode(respondidas_str));
//		baseNode.appendChild(respondidas);
//
//		return convertDocumentToXml(document);
//
//	}

	@Override
	protected Serializable createSerializable(HashMap<String, String> fields) {
		EncuestaRespondida respondida;
		int idEncuesta = Integer.parseInt(fields.get(IDRECURSO_TAG));
		int idUsuario = Integer.parseInt(fields.get(IDUSUARIO_TAG));

		respondida = new EncuestaRespondida(idEncuesta, idUsuario);
		if(fields.get(EVALUACION_TAG) != null){
			int evaluacion = Integer.parseInt(fields.get(EVALUACION_TAG));
			respondida.setEvaluacion(evaluacion);
		}
		respondida.unmarshallPreguntasRespondidas(fields.get(PREGUNTAS_RESPONDIDAS_TAG));
		return respondida;

	}

	public String serializeEncuestaRespondidaQuery(int IDUsuario, int IDEncuesta) {

		Document doc = buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);

		Element nodeElement = doc.createElement(ENCUESTA_RESPONDIDA_TAG);
		rootElement.appendChild(nodeElement);

		Element IDEncuesta_el = doc.createElement(IDRECURSO_TAG);
		IDEncuesta_el.appendChild(doc.createTextNode(String.valueOf(IDEncuesta)));
		nodeElement.appendChild(IDEncuesta_el);
		
		Element IDUsuario_el = doc.createElement(IDUSUARIO_TAG);
		IDUsuario_el.appendChild(doc.createTextNode(String.valueOf(IDUsuario)));
		nodeElement.appendChild(IDUsuario_el);

		return convertDocumentToXml(doc);

	}
	
	@Override
	protected void addElements(Serializable serializable, Element baseNode) {
		EncuestaRespondida respondida = (EncuestaRespondida) serializable;
		addTextElement(baseNode, IDRECURSO_TAG, String.valueOf(respondida.getIdRecurso()));
		addTextElement(baseNode, IDUSUARIO_TAG, String.valueOf(respondida.getIdUsuario()));
		
		if (respondida.getEvaluacion() != null) {
			addTextElement(baseNode, EVALUACION_TAG, String.valueOf(respondida.getEvaluacion()));
		}
		
		String respondidas_str = respondida.marshallPreguntasRespondidas();
		addTextElement(baseNode, PREGUNTAS_RESPONDIDAS_TAG, respondidas_str);
		
	}
}