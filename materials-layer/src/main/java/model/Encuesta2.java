package model;

import java.util.ArrayList;
import java.util.List;

public class Encuesta2 {
	private String titulo;
	private List<Pregunta2> preguntas = new ArrayList<Pregunta2>();
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public List<Pregunta2> getPreguntas() {
		return preguntas;
	}
	public void setPreguntas(List<Pregunta2> preguntas) {
		this.preguntas = preguntas;
	}
	
	public void rellenar() {
		titulo = "Encuesta general de no se que";
		Pregunta2 p1 = new Pregunta2();
		p1.setEnunciado("de que color es el caballo blanco de san martin?");
		p1.getOpciones().add("rojo");
		p1.getOpciones().add("verde");
		p1.getOpciones().add("azul");
		p1.getOpciones().add("blanco");
		
		getPreguntas().add(p1);
		
		Pregunta2 p2 = new Pregunta2();
		p2.setEnunciado("a que equipo del futbol argentino le denominan Millo");
		p2.getOpciones().add("velez");
		p2.getOpciones().add("River Plate");
		p2.getOpciones().add("crucero del norte");
		p2.getOpciones().add("estudiantes");
		
		getPreguntas().add(p2);
		
		Pregunta2 p3 = new Pregunta2();
		p3.setEnunciado("cual es un patron de dise�o creacional");
		p3.getOpciones().add("command");
		p3.getOpciones().add("mediator");
		p3.getOpciones().add("builder");
		p3.getOpciones().add("facade");
		
		getPreguntas().add(p3);
	}

}
