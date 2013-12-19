package model;

public class PreguntaRespuestaACompletarRespondida extends PreguntaRespondida {

	private String respuesta;

	public PreguntaRespuestaACompletarRespondida(Integer idPregunta) {
		super(idPregunta);
		type = ANSWERED_QUESTION_TO_COMPLETE_TYPE;
	}

	public PreguntaRespuestaACompletarRespondida(String s) {
		unmarshall(s);
		type = ANSWERED_QUESTION_TO_COMPLETE_TYPE;
	}

	public void responder(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	// 1 si respuesta correcta, 0 en caso contrario
	@Override
	public Integer evaluar(Pregunta pregunta) {
		if (pregunta.isCorrect(respuesta)) {
			esCorrecta = true;
			return 1;
		}
		esCorrecta = false;
		return 0;
	}

	@Override
	public void completarDatosVisibles(Pregunta pregunta) {
		respuestasVisibles.add(this.getRespuesta());
	}
	
	@Override
	public String marshall() {
		return super.marshall() + respuesta;
	}

	public void unmarshall(String s) {
		super.unmarshall(s);
		String[] splited = s.split(";");
		respuesta = splited[3];
	}

}