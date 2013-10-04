package Recursos.Encuestas;

import java.util.Vector;

public class PreguntaRespuestaFija extends Pregunta {

	private int cantidadRespuestasPosibles;
	private Vector<Integer> respuestasDadas; 
	
	public PreguntaRespuestaFija(String pregunta) {
		super(pregunta);
		
	}
	public void agregarRespuesta(Respuesta unaRespuesta ){
		this.respuestas.add(unaRespuesta);
	}
	public int getCantidadRespuestasPosibles() {
		return cantidadRespuestasPosibles;
	}
	public void setCantidadRespuestasPosibles(int cantidadRespuestasPosibles) 
	{
		this.cantidadRespuestasPosibles = cantidadRespuestasPosibles;
	}
	
	public void respuestasElegidas(Vector<Integer> rtas){
		if(rtas.size() > this.cantidadRespuestasPosibles){
			//TODO: lanzar exception throw new 
		}
		this.respuestasDadas = rtas;
	}

	

}
