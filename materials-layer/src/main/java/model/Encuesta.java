package model;

import java.util.ArrayList;
import java.util.List;

public class Encuesta extends Recurso {
	
	private int countOptions = 0;

	public Encuesta(Integer idRecurso, Integer idAmbiente, String descripcion, boolean evaluada) {
		super(idRecurso, idAmbiente, descripcion);
		this.tipo = "Encuesta";
		this.evaluada = evaluada;
	}

	public Encuesta() {
		super();
		this.tipo = "Encuesta";
	}

	private boolean evaluada;

	public boolean esEvaluada() {
		return evaluada;
	}

	private List<Pregunta> preguntas = new ArrayList<Pregunta>();

	public List<Pregunta> getPreguntas() {
		return preguntas;
	}
	
	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}

//	private List<EncuestaRespondida> encuestasRespondidas;

//	public void guardar() {
//		// TODO:Escribir en la base de datos la info actualizada de la encuesta
//	}

	public void addPregunta(Pregunta pregunta) {
		countOptions++;
		pregunta.setIdPregunta(countOptions);
		preguntas.add(pregunta);
	}

//	private List<EncuestaRespondida> getEncuestaRespondida(Integer idAmbiente, int idUsuario, Integer idRecurso) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public String marshallRespuestas() {
		StringBuilder sb = new StringBuilder("");
		for (Pregunta p: preguntas) {
			sb.append(p.marshall());
			sb.append("|");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}
	
	public void unmarshallRespuestas(String field) {
		preguntas = Pregunta.unmarshallAll(field);
	}

}