package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Pregunta {

	protected static String FIXED_ANSWER_TYPE = "F";
	protected static String ANSWER_TO_COMPLETE_TYPE = "C";

	public static List<Pregunta> unmarshallAll(String field) {
		String[] splited = field.split("\\|");
		List<Pregunta> result = new ArrayList<Pregunta>();

		for (String s : splited) {
			if (unmarshallType(s).equals(FIXED_ANSWER_TYPE)) {
				result.add(new PreguntaRespuestaFija(s));
			} else {
				result.add(new PreguntaRespuestaACompletar(s));
			}
		}
		return result;
	}

	private static String unmarshallType(String marshalledPregunta) {
		return marshalledPregunta.substring(0, marshalledPregunta.indexOf(";") - 1);
	}

	protected String enunciado;
	protected Integer idPregunta = 0;
	protected String type;

	public Pregunta() {
	}
//	public Pregunta(String s) {
//		//unmarshall(s);
//	}

//	public Pregunta(String enunciado, Integer idPregunta) {
//		this.setEnunciado(enunciado);
//		this.setIdPregunta(idPregunta);
//	}

	public abstract Integer evaluar(PreguntaRespondida respondida);

	public String getEnunciado() {
		return enunciado;
	}

	public Integer getIdPregunta() {
		return idPregunta;
	}

	public void setIdPregunta(Integer idPregunta) {
		this.idPregunta = idPregunta;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}
	
	protected void addRespuestaCorrecta(String rta) {
		
	}

	protected String marshall() {
		StringBuilder sb = new StringBuilder("");
		sb.append(type);
		sb.append(";");
		sb.append(idPregunta);
		sb.append(";");
		sb.append(enunciado);
		sb.append(";");
		return sb.toString();
	}

	protected void unmarshall(String s) {
		String[] splited = s.split(";");
		type = splited[0];
		idPregunta = Integer.valueOf(splited[1]);
		enunciado = splited[2];
	}

	public abstract boolean isCorrect(Integer respuesta);	

	public abstract boolean isCorrect(String respuesta);
	
	public abstract Integer getNroCorrectas();

}
