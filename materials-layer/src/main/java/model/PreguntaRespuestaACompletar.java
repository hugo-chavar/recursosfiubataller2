package model;

public class PreguntaRespuestaACompletar extends Pregunta{

  public PreguntaRespuestaACompletar(String enunciado, Integer idPregunta) {
		super(enunciado, idPregunta);

	}

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

}