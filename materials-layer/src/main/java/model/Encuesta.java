package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Encuesta extends Recurso {

	public Encuesta(Integer idRecurso, Integer idAmbiente, String descripcion, boolean evaluada) {
		super(idRecurso, idAmbiente, descripcion);
		this.tipo = "Encuesta";
		this.evaluada = evaluada;
	}

	public Encuesta() {
		super();
		this.tipo = "Archivo";
	}

	private Boolean evaluada;

	public Boolean esEvaluada() {
		return evaluada;
	}

	private ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();

	public ArrayList<Pregunta> getPreguntas() {
		return preguntas;
	}

	private ArrayList<EncuestaRespondida> encuestasRespondidas;

	public void guardar() {
		// TODO:Escribir en la base de datos la info actualizada de la encuesta
	}

	public void agregarPregunta(Pregunta pregunta) {
		preguntas.add(pregunta);
		// TODO: Escribir cambio en la base de datos
	}

	public void eliminarPregunta(int nPregunta) {
		// TODO: Escribir cambio en la base de datos
		preguntas.remove(nPregunta);
	}

	private ArrayList<EncuestaRespondida> getEncuestaRespondida(Integer idAmbiente, int idUsuario, Integer idRecurso) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void Guardar() {
		// Guardar en la base de Datos
	}

	@Override
	public void Crear() {

	}

}