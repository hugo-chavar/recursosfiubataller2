package Recursos.Encuestas;

import java.util.Vector;

import Recursos.Recurso;

public class Encuesta extends Recurso {

	public Encuesta(int idAmbiente)
	{
		this.idAmbiente = idAmbiente;
	}
	
	public Vector<Pregunta> getPreguntas() {
		return preguntas;
	}
	
	private int idEncuesta;
	private int idAmbiente;
	private Vector<Pregunta> preguntas;
	
	public void agregarPregunta(Pregunta pregunta)
	{
		preguntas.add(pregunta);
	}
	public void eliminarPregunta(int numero)
	{
		preguntas.remove(numero-1);
		//TODO: VALIDAR POSICION
	}


}
