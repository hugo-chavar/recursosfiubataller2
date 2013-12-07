package model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EncuestaTest {
	
	private static Integer INCORRECT = 0;
	private static Integer CORRECT = 1;

	Encuesta encuesta;
	Pregunta p1, p2, p3, p4, p5;

	@Before
	public void setUp() throws Exception {
		encuesta = new Encuesta();
		encuesta.setDescripcion("Encuesta general de no se que");
		p1 = new PreguntaRespuestaFija();
		p1.setEnunciado("de que color es el caballo blanco de san martin?");
		List<String> opciones = new ArrayList<String>();
		opciones.add("rojo");
		opciones.add("verde");
		opciones.add("azul");
		opciones.add("blanco");
		((PreguntaRespuestaFija) p1).setRespuestasPosibles(opciones);

		encuesta.addPregunta(p1);

		p2 = new PreguntaRespuestaFija();
		opciones = new ArrayList<String>();
		p2.setEnunciado("a que equipo del futbol argentino le denominan Millo");
		opciones.add("velez");
		opciones.add("River Plate");
		opciones.add("crucero del norte");
		opciones.add("estudiantes");
		((PreguntaRespuestaFija) p2).setRespuestasPosibles(opciones);

		encuesta.addPregunta(p2);

		p3 = new PreguntaRespuestaFija();
		opciones = new ArrayList<String>();
		p3.setEnunciado("cual es un patron de diseno creacional");
		opciones.add("command");
		opciones.add("mediator");
		opciones.add("builder");
		opciones.add("facade");

		((PreguntaRespuestaFija) p3).setRespuestasPosibles(opciones);
		p3.addRespuestaCorrecta("builder");

		encuesta.addPregunta(p3);
		
		p4 = new PreguntaRespuestaFija();
		opciones = new ArrayList<String>();
		p4.setEnunciado("Un test unitario debe presentar las siguientes características");
		opciones.add("Rapido");
		opciones.add("Moldeable");
		opciones.add("Configurable");
		opciones.add("Acoplable");
		opciones.add("Lento");
		opciones.add("Extensible");
		opciones.add("Repetible");
		opciones.add("Profesional");
		opciones.add("Maduro");
		opciones.add("Amplio");
		opciones.add("Simple");
		opciones.add("Independiente");
		opciones.add("Automatizable");

		((PreguntaRespuestaFija) p4).setRespuestasPosibles(opciones);
		
		p4.addRespuestaCorrecta("Rapido");
		p4.addRespuestaCorrecta("Profesional");
		p4.addRespuestaCorrecta("Simple");
		p4.addRespuestaCorrecta("Independiente");
		p4.addRespuestaCorrecta("Automatizable");
		p4.addRespuestaCorrecta("Repetible");

		encuesta.addPregunta(p4);
		
		p5 = new PreguntaRespuestaACompletar();
		p5.setEnunciado("cuantas patas tiene un gato?");
		p5.addRespuestaCorrecta("4");
	}

	@Test
	public void marshallPreguntaRespuestaACompletarWorksAsExpected() {
		Assert.assertEquals("C;0;cuantas patas tiene un gato?;4", p5.marshall());
	}
	
	@Test
	public void marshallPreguntaRespuestaFijaWorksAsExpected() {
		Assert.assertEquals("F;3;cual es un patron de diseno creacional;false;command,mediator,builder,facade;2", p3.marshall());
	}
	
	@Test
	public void idPreguntaIsCorrect() {
		Integer expected = 4;
		Assert.assertEquals(expected, p4.getIdPregunta());
	}
	
	@Test
	public void unmarshallPreguntaRespuestaACompletarSetEnunciadoCorrectly() {
		String marshalledPregunta = p5.marshall();
		PreguntaRespuestaACompletar pregunta = new PreguntaRespuestaACompletar();
		pregunta.unmarshall(marshalledPregunta);
		Assert.assertEquals(pregunta.getEnunciado(), p5.getEnunciado());
	}
	
	@Test
	public void unmarshallPreguntaRespuestaACompletarSetIdCorrectly() {
		String marshalledPregunta = p5.marshall();
		PreguntaRespuestaACompletar pregunta = new PreguntaRespuestaACompletar();
		pregunta.unmarshall(marshalledPregunta);
		Assert.assertEquals(pregunta.getIdPregunta(), p5.getIdPregunta());
	}
	
	@Test
	public void unmarshallPreguntaRespuestaACompletarSetRespuestaCorrectly() {
		String marshalledPregunta = p5.marshall();
		PreguntaRespuestaACompletar pregunta = new PreguntaRespuestaACompletar();
		pregunta.unmarshall(marshalledPregunta);
		Assert.assertEquals(pregunta.getRespuesta(), ((PreguntaRespuestaACompletar)p5).getRespuesta());
	}

	@Test
	public void answeredPreguntaRespuestaACompletarEvaluationReturns0WhenWrong() {
		PreguntaRespuestaACompletarRespondida response = new PreguntaRespuestaACompletarRespondida(p5.getIdPregunta());
		response.responder("8");
		Integer expected = INCORRECT;

		Assert.assertEquals(expected, response.evaluar(p5));
	}
	
	@Test
	public void answeredPreguntaRespuestaACompletarEvaluationReturns1WhenCorrect() {
		//TODO: andy.. crea tests parecidos pero con multiples respuestas
		PreguntaRespuestaACompletarRespondida response = new PreguntaRespuestaACompletarRespondida(p5.getIdPregunta());
		response.responder("4");
		Integer expected = CORRECT;
		Assert.assertEquals(expected, response.evaluar(p5));
	}
	
	@Test
	public void answeredPreguntaRespuestaFijaEvaluationReturns1WhenAllTheAnswersCorrect(){
		PreguntaRespuestaFijaRespondida response = new PreguntaRespuestaFijaRespondida(p4.getIdPregunta());
		List<Integer> respuestas = new ArrayList<Integer>();
		respuestas.add(0);
		respuestas.add(7);
		respuestas.add(10);
		respuestas.add(11);
		respuestas.add(12);
		respuestas.add(6);
		response.responder(respuestas);
		Integer expected = CORRECT;
		Assert.assertEquals(expected, response.evaluar(p4));
	}	
		
	@Test
	public void answeredPreguntaRespuestaFijaEvaluationReturns0WhenThereIsOneMoreAnsweredThanTheCorrects(){
		PreguntaRespuestaFijaRespondida response = new PreguntaRespuestaFijaRespondida(p4.getIdPregunta());
		List<Integer> respuestas = new ArrayList<Integer>();
		respuestas.add(0);
		respuestas.add(7);
		respuestas.add(10);
		respuestas.add(11);
		respuestas.add(12);
		respuestas.add(6);
		respuestas.add(9);
		response.responder(respuestas);
		Integer expected = INCORRECT;
		Assert.assertEquals(expected, response.evaluar(p4));
		}
	
	@Test
	public void answeredPreguntaRespuestaFijaEvaluationReturns0WhenThereIsOneLessAnsweredThanTheCorrects(){
		PreguntaRespuestaFijaRespondida response = new PreguntaRespuestaFijaRespondida(p4.getIdPregunta());
		List<Integer> respuestas = new ArrayList<Integer>();
		respuestas.add(0);
		respuestas.add(7);
		respuestas.add(10);
		respuestas.add(11);
		respuestas.add(12);
		response.responder(respuestas);
		Integer expected = INCORRECT;
		Assert.assertEquals(expected, response.evaluar(p4));
		}
	
	@Test
	public void answeredPreguntaRespuestaFijaEvaluationReturns0WhenAnswersAreRepeteadAndCorrect(){
		PreguntaRespuestaFijaRespondida response = new PreguntaRespuestaFijaRespondida(p4.getIdPregunta());
		List<Integer> respuestas = new ArrayList<Integer>();
		respuestas.add(0);
		respuestas.add(7);
		respuestas.add(7);
		respuestas.add(11);
		respuestas.add(12);
		respuestas.add(6);
		response.responder(respuestas);
		Integer expected = INCORRECT;
		Assert.assertEquals(expected, response.evaluar(p4));
	}
}
