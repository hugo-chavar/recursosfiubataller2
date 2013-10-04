package Recursos.Encuestas;

import java.util.Vector;


public class Pregunta {

	protected  Vector<Respuesta> respuestas;
	private String pregunta;
	
	public Pregunta(String pregunta)
	{
		this.pregunta=pregunta;
	}

	public void agregarRespuesta(Respuesta respuesta)
	{
		respuestas.add(respuesta);
	}
	
	public void eliminarRespuesta(int nRespuesta)
	{
		respuestas.remove(nRespuesta);
	}

/*	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}*/

	public String getPregunta() {
		return pregunta;
	}

}
