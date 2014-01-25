package model;

import java.util.ArrayList;
import java.util.List;

public class PreguntaRespuestaFijaRespondida extends PreguntaRespondida {

	private List<Integer> respuestasfijas = new ArrayList<Integer>();

	public PreguntaRespuestaFijaRespondida(Integer idPregunta) {
		super(idPregunta);
		type = ANSWERED_QUESTION_FIXED_TYPE;
	}

	public PreguntaRespuestaFijaRespondida(String s) {
		unmarshall(s);
		type = ANSWERED_QUESTION_FIXED_TYPE;
	}

	public PreguntaRespuestaFijaRespondida() {
	}

	public void unmarshall(String s) {
		super.unmarshall(s);
		String[] splited = s.split(";");
		splited = ignoreSpecialCharactersInSplit(splited);
		unmarshallRespuestas(splited[3]);
		
	}

	public void responder(List<Integer> respuestas) {
		this.respuestasfijas = respuestas;
	}

	public List<Integer> getRespuestasFijas() {
		return respuestasfijas;
	}

	@Override
	public Integer evaluar(Pregunta pregunta) {
		// Chequeo que coincida cantidad de respuestas
		if (!pregunta.getNroCorrectas().equals(this.respuestasfijas.size())) {
			isCorrecta = false;
			return 0;
		}
		// Chequeo cada una de las respuestas
		for (Integer respuesta : respuestasfijas) {
			if (!pregunta.isCorrect(respuesta)) {
				isCorrecta = false;
				return 0;
			}
		}
		isCorrecta = true;
		return 1;
	}

//	@Override
//	public void completarDatosVisibles(Pregunta pregunta) {
//		if (this.respuestas.isEmpty()) {
//			pregunta.completarDatosVisibles();
//			List<String> posibles = pregunta.getOpciones();
//			for (Integer indice : respuestasfijas) {
//				this.respuestas.add(posibles.get(indice));
//			}
//		}
//	}

	public void addRespuesta(Integer indiceRespuesta) {
		this.respuestasfijas.add(indiceRespuesta);
	}
	
	@Override
	public String marshall() {
		StringBuilder sb = new StringBuilder("");
		sb.append(marshallRespuestas());
		return super.marshall() + sb.toString();

	}

	private String marshallRespuestas() {
		if (respuestasfijas.isEmpty()) {
			return "null";
		}
		StringBuilder sb = new StringBuilder("");
		for (Integer rta : respuestasfijas) {
			sb.append(rta);
			sb.append(",");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}
	
	private void unmarshallRespuestas(String rtas) {
		if ("null".equals(rtas)) {
			return;
		}
		String[] splited = rtas.split(",");
		respuestasfijas = new ArrayList<Integer>();
		for (String s : splited) {
			respuestasfijas.add(Integer.valueOf(s));
		}

	}
}