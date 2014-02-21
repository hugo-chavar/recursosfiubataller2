package connection.parsers;

import java.util.HashMap;

import model.Encuesta;

import org.w3c.dom.Element;

import connection.Serializable;


public class EncuestaParser extends Parser {
	
	public static String ENCUESTA_TAG = "Encuesta";
	public static String EVALUADA_TAG = "evaluada";
	public static String PREGUNTAS_TAG = "preguntas";
	
	public EncuestaParser() {
		baseTag = ENCUESTA_TAG;
	}
	
	@Override
	protected Serializable createSerializable(HashMap<String, String> fields) {
		Boolean evaluada = Boolean.parseBoolean(fields.get(EVALUADA_TAG));
		Encuesta encuesta = new Encuesta(0, 0, "", evaluada);
		//System.out.println("PREGUNTAS: "+fields.get(PREGUNTAS_TAG));
		encuesta.unmarshallPreguntas(fields.get(PREGUNTAS_TAG));
		return encuesta;
	}
	
	@Override
	protected void addElements(Serializable serializable, Element baseNode) {
		Encuesta encuesta = (Encuesta) serializable;
		
		addTextElement(baseNode,isSaveMode?RECURSOID_TAG:ID_TAG, String.valueOf(encuesta.getRecursoId()));
		addTextElement(baseNode, EVALUADA_TAG, String.valueOf(encuesta.isEvaluada()));
		addTextElement(baseNode, PREGUNTAS_TAG, encuesta.marshallPreguntas());

	}
}