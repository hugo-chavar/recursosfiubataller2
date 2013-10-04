package Recursos.Encuestas;

public class PreguntaRespuestaACompletar extends Pregunta{

	private String respuesta;
	private int tamanio;
	
	public PreguntaRespuestaACompletar(String pregunta) {
		super(pregunta);
		respuesta = "";
	}
	public void setMaxTamanio(int tamanio){
		this.tamanio = tamanio; 
	}
	public void setRespuesta(String respuesta){
		if(respuesta.length()> tamanio )
		{
			//TODO Exception
		}
		this.respuesta = respuesta;
	}
	public String getRespuesta(){
		return respuesta;
	}
		// TODO Auto-generated constructor stub
	
}


