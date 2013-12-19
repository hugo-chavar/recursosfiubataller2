package model;

import java.util.ArrayList;
import java.util.List;

public class PreguntaRespuestaFijaRespondida extends PreguntaRespondida {

	public List<Integer> respuestas = new ArrayList<Integer>();

	public PreguntaRespuestaFijaRespondida(Integer idPregunta) {
		super(idPregunta);
		type = ANSWERED_QUESTION_FIXED_TYPE;
	}

	public PreguntaRespuestaFijaRespondida(String s) {
		unmarshall(s);
		type = ANSWERED_QUESTION_FIXED_TYPE;
	}

	public void unmarshall(String s) {
		super.unmarshall(s);
		String[] splited = s.split(";");
		unmarshallRespuestas(splited[3]);
		
	}

	public void responder(List<Integer> respuestas) {
		this.respuestas = respuestas;
	}

	public List<Integer> getRespuestas() {
		return respuestas;
	}

	@Override
	public Integer evaluar(Pregunta pregunta) {
		// Chequeo que coincida cantidad de respuestas
		if (!pregunta.getNroCorrectas().equals(this.respuestas.size())) {
			esCorrecta = false;
			return 0;
		}
		// Chequeo cada una de las respuestas
		for (Integer respuesta : respuestas) {
			if (!pregunta.isCorrect(respuesta)) {
				esCorrecta = false;
				return 0;
			}
		}
		esCorrecta = true;
		return 1;
	}

	@Override
	public void completarDatosVisibles(Pregunta pregunta) {
		pregunta.completarDatosVisibles();
		List<String> posibles = pregunta.getOpciones();
		for (Integer indice : respuestas) {
			this.respuestasVisibles.add(posibles.get(indice));
		}
	}

	public void addRespuesta(Integer indiceRespuesta) {
		this.respuestas.add(indiceRespuesta);
	}
	
	@Override
	public String marshall() {
		StringBuilder sb = new StringBuilder("");
		sb.append(marshallRespuestas());
		return super.marshall() + sb.toString();

	}

	private String marshallRespuestas() {
		if (respuestas.isEmpty()) {
			return "null";
		}
		StringBuilder sb = new StringBuilder("");
		for (Integer rta : respuestas) {
			sb.append(rta);
			sb.append(",");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}
	
	private void unmarshallRespuestas(String rtas) {
		String[] splited = rtas.split(",");
		respuestas = new ArrayList<Integer>();
		for (String s : splited) {
			respuestas.add(Integer.valueOf(s));
		}

	}
}