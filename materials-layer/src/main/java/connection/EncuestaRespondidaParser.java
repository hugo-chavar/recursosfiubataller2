package connection;

import java.util.HashMap;

import model.EncuestaRespondida;

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

	public String serializeEncuestaRespondida(EncuestaRespondida respondida) {

		Document doc = buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);

		Element nodeElement = doc.createElement(ENCUESTA_RESPONDIDA_TAG);
		rootElement.appendChild(nodeElement);

		Element IDEncuesta = doc.createElement(IDRECURSO_TAG);
		IDEncuesta.appendChild(doc.createTextNode(String.valueOf(respondida.getIdRecurso())));
		nodeElement.appendChild(IDEncuesta);
		Element IDUsuario = doc.createElement(IDUSUARIO_TAG);
		IDUsuario.appendChild(doc.createTextNode(String.valueOf(respondida.getIdUsuario())));
		nodeElement.appendChild(IDUsuario);
		// TODO: Andy No siempre se debe incluir este campo, si es no evaluado no
		// if(respondida.getEvaluacion()!=-1)
		Element evaluacion = doc.createElement(EVALUACION_TAG);
		evaluacion.appendChild(doc.createTextNode(String.valueOf(respondida.getEvaluacion())));
		nodeElement.appendChild(evaluacion);

		String respondidas_str = respondida.marshallPreguntasRespondidas();
		Element respondidas = doc.createElement(PREGUNTAS_RESPONDIDAS_TAG);
		respondidas.appendChild(doc.createTextNode(respondidas_str));
		nodeElement.appendChild(respondidas);

		return convertDocumentToXml(doc);

	}

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
}