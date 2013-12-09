package model;

import java.util.List;

public class PreguntaRespuestaFijaRespondida extends PreguntaRespondida {

	public List<Integer> respuestas;

	public PreguntaRespuestaFijaRespondida(Integer idPregunta) {
		super(idPregunta);
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
		if (!pregunta.getNroCorrectas().equals(this.respuestas.size()))
			return 0;
		// Chequeo cada una de las respuestas
		for (Integer respuesta : respuestas) {
			if (!pregunta.isCorrect(respuesta)) {
				return 0;
			}
		}
		// if(this.areAnsweredRepeated()){
		// return 0;
		// }
		return 1;
	}

	// No deberia pasar
	// private boolean areAnsweredRepeated() {
	// Set<Integer> set = new HashSet<Integer>(this.respuestas);
	// return respuestas.size() != set.size();
	// }
}