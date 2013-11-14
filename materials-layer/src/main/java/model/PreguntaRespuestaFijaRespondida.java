package model;

import java.util.Vector;

public class PreguntaRespuestaFijaRespondida extends PreguntaRespondida{

  public PreguntaRespuestaFijaRespondida(Integer idPregunta) {
		super(idPregunta);
	}

public Vector<Integer> respuestas;

  public void responder(Vector<Integer> respuestas) {
	  this.respuestas=respuestas;
  }

public Vector<Integer> getRespuestas() {
	return respuestas;
}

@Override
public Integer evaluar(PreguntaRespuestaACompletar pregunta) {
	return null;
}

@Override
public Integer evaluar(PreguntaRespuestaFija preguntaFija) {
	if(preguntaFija.getRespuestasCorrectas().size()!=this.getRespuestas().size())
		return 0;
	for(int i=0;i<preguntaFija.getRespuestasCorrectas().size();i++){
		//Chequeo una por una que las respuestas sean las mismas
		if(preguntaFija.getRespuestasCorrectas().elementAt(i).compareTo(this.getRespuestas().elementAt(i))!=0){
			return 0;
		}
	}
		return 1;
}

}