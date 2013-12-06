package model;

import java.util.ArrayList;
import java.util.List;

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

	private List<Pregunta> preguntas = new ArrayList<Pregunta>();

	public List<Pregunta> getPreguntas() {
		return preguntas;
	}
	
	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}

	private List<EncuestaRespondida> encuestasRespondidas;

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

	private List<EncuestaRespondida> getEncuestaRespondida(Integer idAmbiente, int idUsuario, Integer idRecurso) {
		// TODO Auto-generated method stub
		return null;
	}

	public String marshallRespuestas() {
		StringBuilder sb = new StringBuilder("");
		for (Pregunta p: preguntas) {
			sb.append(p.marshall());
			sb.append("|");
		}
		return sb.toString();
	}
	
	public void unmarshallRespuestas(String field) {
		preguntas = Pregunta.unmarshallAll(field);
	}

}