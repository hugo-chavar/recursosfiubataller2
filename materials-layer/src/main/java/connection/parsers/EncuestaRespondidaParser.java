package connection.parsers;

import java.util.HashMap;

import model.EncuestaRespondida;

import org.w3c.dom.Element;

import connection.Serializable;

public class EncuestaRespondidaParser extends Parser {

	public static String ENCUESTA_RESPONDIDA_TAG = "EncuestaRespondida";
	public static String IDUSUARIO_TAG = "usuarioId";
	public static String EVALUACION_TAG = "evaluacion";
	public static String PREGUNTAS_RESPONDIDAS_TAG = "preguntasRespondidas";
	public static String IDRECURSO_TAG = "recursoId";

	public EncuestaRespondidaParser() {
		baseTag = ENCUESTA_RESPONDIDA_TAG;
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

	@Override
	protected void addElements(Serializable serializable, Element baseNode) {
		EncuestaRespondida respondida = (EncuestaRespondida) serializable;
		addTextElement(baseNode, IDRECURSO_TAG, String.valueOf(respondida.getIdRecurso()));
		addTextElement(baseNode, IDUSUARIO_TAG, String.valueOf(respondida.getIdUsuario()));
		addTextElement(baseNode, EVALUACION_TAG, String.valueOf(respondida.getEvaluacion()));
		
		String respondidas_str = respondida.marshallPreguntasRespondidas();
		addTextElement(baseNode, PREGUNTAS_RESPONDIDAS_TAG, respondidas_str);
		
	}
}