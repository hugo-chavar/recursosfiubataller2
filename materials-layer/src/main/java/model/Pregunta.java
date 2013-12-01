package model;

public abstract class Pregunta {

	private String enunciado;

	private Integer idPregunta;

	public Pregunta(String enunciado, Integer idPregunta) {
		this.setEnunciado(enunciado);
		this.setIdPregunta(idPregunta);
	}

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
}
