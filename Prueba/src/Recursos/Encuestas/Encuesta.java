package Recursos.Encuestas;


import java.io.Serializable;
import java.util.ArrayList;



import Recursos.Recurso;

public class Encuesta extends Recurso implements Serializable {


	private int idEncuesta;
	private int idAmbiente;
	private String[] preguntas = new String[10];

	public Encuesta(){
		
	}
	public Encuesta(int idAmbiente)
	{
		this.idAmbiente = idAmbiente;
	}
	
	public String[] getPreguntas() {
		return preguntas;
	}
		
	public void agregarPregunta(String pregunta)
	{
		//preguntas[0] = pregunta;
		System.out.println("Hola mundo chau");
	}
	/*public void eliminarPregunta(int numero)
	{
		
	}*/


}
