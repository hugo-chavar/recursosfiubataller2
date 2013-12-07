package model;

public class PreguntaRespuestaACompletarRespondida extends PreguntaRespondida {

  public PreguntaRespuestaACompletarRespondida(Integer idPregunta) {
		super(idPregunta);
		// TODO Auto-generated constructor stub
	}

  public String respuesta;

  public void responder( String respuesta) {
	  this.respuesta=respuesta;
  }

  public String getRespuesta() {
	  return respuesta;
  }	

  //1 si respuesta correcta, 0 en caso contrario
  @Override
  public Integer evaluar(Pregunta pregunta) {
	  if (pregunta.isCorrect(respuesta)) {
		  return 1;
	  }
	  return 0;
  }

}