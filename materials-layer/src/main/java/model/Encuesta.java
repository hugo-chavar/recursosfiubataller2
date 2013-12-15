package model;

import java.util.ArrayList;
import java.util.List;

public class Encuesta extends Recurso {

	private int countOptions = 0;
	private boolean evaluada;
	private List<Pregunta> preguntas = new ArrayList<Pregunta>();

	public Encuesta(Integer idRecurso, Integer idAmbiente, String descripcion, boolean evaluada) {
		super(idRecurso, idAmbiente, descripcion);
		this.tipo = "Encuesta";
		this.evaluada = evaluada;
	}

	public Encuesta() {
		super();
		this.tipo = "Encuesta";
	}

	public boolean esEvaluada() {
		return evaluada;
	}

	public List<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}

	public void addPregunta(Pregunta pregunta) {
		countOptions++;
		pregunta.setIdPregunta(countOptions);
		preguntas.add(pregunta);
	}

	public String marshallPreguntas() {
		StringBuilder sb = new StringBuilder("");
		for (Pregunta p : preguntas) {
			sb.append(p.marshall());
			sb.append("|");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	public void unmarshallPreguntas(String field) {
		preguntas = Pregunta.unmarshallAll(field);
	}

	public void completarDatosVisibles() {
		for (Pregunta pregunta : this.getPreguntas()) {
			pregunta.completarDatosVisibles();
		}
	}

	public void recuperarDatosVisibles() {
		List<Pregunta> preguntasAux = this.getPreguntas();
		this.preguntas = new ArrayList<Pregunta>();
		for (Pregunta pregunta : preguntasAux) {
			if (pregunta.getOpciones().isEmpty()) {
				PreguntaRespuestaACompletar pregunta2 = new PreguntaRespuestaACompletar();
				pregunta2.setEnunciado(pregunta.getEnunciado());
				if (!pregunta.getCorrectas().isEmpty()) {
					pregunta2.setRespuesta(pregunta.getCorrectas().get(0));
				}
				this.preguntas.add(pregunta2);
			} else {
				PreguntaRespuestaFija pregunta2 = new PreguntaRespuestaFija();
				pregunta2.setEnunciado(pregunta.getEnunciado());
				pregunta2.setRespuestasPosibles(pregunta.getOpciones());
				for (String res : pregunta.getCorrectas()) {
					pregunta2.addRespuestaCorrecta(res);
				}
				this.preguntas.add(pregunta2);
			}
		}
	}

}