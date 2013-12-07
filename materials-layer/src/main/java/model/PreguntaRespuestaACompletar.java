package model;

public class PreguntaRespuestaACompletar extends Pregunta{
	

  public PreguntaRespuestaACompletar() {
		super();
		type = ANSWER_TO_COMPLETE_TYPE;
	}
  
  public PreguntaRespuestaACompletar(String s) {
		unmarshall(s);
		type = ANSWER_TO_COMPLETE_TYPE;
	}

  
//public PreguntaRespuestaACompletar(String enunciado, Integer idPregunta) {
//		super(enunciado, idPregunta);
//		type = ANSWER_TO_COMPLETE_TYPE;
//
//	}

public String respuesta;

  public void setRespuesta( String respuesta) {
	  this.respuesta=respuesta;
  }

  public String getRespuesta() {
	  return respuesta;
  }

@Override
public Integer evaluar(PreguntaRespondida respondida) {
	
	return respondida.evaluar(this);
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

@Override
public void addRespuestaCorrecta(String string) {
	respuesta = string;
}

@Override
public boolean isCorrect(String respuesta) {
	return this.respuesta.equalsIgnoreCase(respuesta);
}

@Override
public boolean isCorrect(Integer respuesta) {
	return false;
}

@Override
public Integer getNroCorrectas() {
	return 1;
}

}