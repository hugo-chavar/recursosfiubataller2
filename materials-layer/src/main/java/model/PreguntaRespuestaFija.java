package model;

import java.util.Vector;

public class PreguntaRespuestaFija extends Pregunta{

  private Vector<String> respuestasPosibles;

  private boolean multiplesRespuestas;

  private Vector<Integer>  respuestasCorrectas;
  
  public PreguntaRespuestaFija(boolean multiplesRespuestas,String enunciado,Integer idPregunta){
  	super(enunciado,idPregunta);
  	this.multiplesRespuestas=multiplesRespuestas;
}

public Vector<String> getRespuestasPosibles() {
	return respuestasPosibles;
}

public void setRespuestasPosibles(Vector<String> respuestasPosibles) {
	this.respuestasPosibles = respuestasPosibles;
}

public Vector<Integer> getRespuestasCorrectas() {
	return respuestasCorrectas;
}

//retorna false si no puede agregar esas respuestas
public boolean setRespuestasCorrectas(Vector<Integer> respuestasCorrectas) {
	if(!this.isMultiplesRespuestas() && respuestasCorrectas.size()!=1)
		return false;
	this.respuestasCorrectas = respuestasCorrectas;
	return true;
}

@Override
public Integer evaluar(PreguntaRespondida respondida) {
	return respondida.evaluar(this);
}

public boolean isMultiplesRespuestas() {
	return multiplesRespuestas;
}

}