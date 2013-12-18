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
		if (!pregunta.getNroCorrectas().equals(this.respuestas.size())){
			esCorrecta=false;
			return 0;
		}
		// Chequeo cada una de las respuestas
		for (Integer respuesta : respuestas) {
			if (!pregunta.isCorrect(respuesta)) {
				esCorrecta=false;
				return 0;
			}
		}
		esCorrecta=true;
		return 1;
	}
	
	@Override
	public void completarDatosVisibles(Pregunta pregunta) {
		pregunta.completarDatosVisibles();
		List<String> posibles=pregunta.getOpciones();
		for(Integer indice : respuestas){
			this.respuestasVisibles.add(posibles.get(indice));
		}
	}

	public void addRespuesta(Integer indiceRespuesta) {
		this.respuestas.add(indiceRespuesta);
		
	}
}