package model;


public class PreguntaRespondida {

	private Integer idPregunta;
	
	//Si no es evaluado siempre estara en false este atributo
	protected boolean esCorrecta=false;
	
	public PreguntaRespondida(Integer idPregunta) {
		this.idPregunta = idPregunta;
	}

	public Integer getIdPregunta() {
		return idPregunta;
	}
	
	public Integer evaluar(Pregunta pregunta) {
		return -1;
	}

}