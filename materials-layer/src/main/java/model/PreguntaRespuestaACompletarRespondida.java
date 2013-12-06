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
//@Override
//public Integer evaluar(PreguntaRespuestaACompletar pregunta) {
////	if(pregunta.respuesta.toLowerCase()==this.respuesta.toLowerCase())
////		return 1;
//	if(pregunta.getRespuesta().equalsIgnoreCase(respuesta)) {
//		return 1;
//	}
//	return 0;
//}
//
//@Override
//public Integer evaluar(PreguntaRespuestaFija preguntaFija) {
//	return null;
//}

//1 si respuesta correcta, 0 en caso contrario
//andy... hay que usar polimorfismo..
@Override
public Integer evaluar(Pregunta pregunta) {
//	if(pregunta.respuesta.toLowerCase()==this.respuesta.toLowerCase())
//		return 1;
	//andy.. simplificate la vida..
	if (pregunta.isCorrect(respuesta)) {
		return 1;
	}
	return 0;
}

}