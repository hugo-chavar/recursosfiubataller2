package model;

public abstract class Pregunta {
	
	protected static String FIXED_ANSWER_TYPE = "F";
	protected static String ANSWER_TO_COMPLETE_TYPE = "C";

	protected String enunciado;
	protected String type;
	protected Integer idPregunta;

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

	public String marshall() {
		StringBuilder sb = new StringBuilder("");
		sb.append(idPregunta);
		sb.append(";");
		sb.append(type);
		sb.append(";");
		sb.append(enunciado);
		sb.append(";");
		return sb.toString();
	}
}
