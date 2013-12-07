package model;

public abstract class PreguntaRespondida {

  public Integer idPregunta;

  public PreguntaRespondida(Integer idPregunta){
	  this.idPregunta=idPregunta;
  }

public Integer getIdPregunta() {
	return idPregunta;
}

public abstract Integer evaluar(Pregunta pregunta);
  
}