package model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PreguntaRespondidaTest {
	
	private PreguntaRespuestaACompletarRespondida p1;
	private PreguntaRespuestaFijaRespondida p2, p3;

	@Before
	public void setUp() throws Exception {
		p1 = new PreguntaRespuestaACompletarRespondida(5);
		p2 = new PreguntaRespuestaFijaRespondida(7);
		p3 = new PreguntaRespuestaFijaRespondida(10);
		p1.responder("Hola");
		p3.getRespuestas().add("command");
		p3.getRespuestas().add("mediator");
		Pregunta p = new PreguntaRespuestaFija();
		List<String> opciones = new ArrayList<String>();
		p.setEnunciado("cual es un patron de diseno creacional");
		opciones.add("command");
		opciones.add("mediator");
		opciones.add("builder");
		opciones.add("facade");

		((PreguntaRespuestaFija) p).setRespuestasPosibles(opciones);
		p.addRespuestaCorrecta("builder");
		
		p3.evaluar(p);
		
	}

	@Test
	public void idPreguntaRespuestaACompletarRespondidaIsCorrect() {
		Integer expected = 5;
		Assert.assertEquals(expected, p1.getIdPregunta());
	}

	@Test
	public void respuestaPreguntaRespuestaACompletarRespondidaIsCorrect() {
		Assert.assertEquals("Hola", p1.getRespuesta());
	}
	
	@Test
	public void idPreguntaRespuestaFijaRespondidaIsCorrect() {
		Integer expected = 7;
		Assert.assertEquals(expected, p2.getIdPregunta());
	}
	
	@Test
	public void marshallPreguntaRespuestaACompletarRespondidaWorksAsExpected() {
		Assert.assertEquals("C;5;null;Hola", p1.marshall());
	}
	
	@Test
	public void marshallPreguntaRespuestaFijaRespondidaWorksAsExpectedWhenNoData() {
		Assert.assertEquals("F;7;null;null", p2.marshall());
	}
	
	@Test
	public void marshallPreguntaRespuestaFijaRespondidaWorksAsExpected() {
		Assert.assertEquals("F;10;false;3,8,345", p3.marshall());
	}
	
	@Test
	public void unmarshallPreguntaRespuestaFijaRespondidaSetIdCorrectly() {
		String marshalledPregunta = p3.marshall();
		PreguntaRespuestaFijaRespondida p = new PreguntaRespuestaFijaRespondida();
		p.unmarshall(marshalledPregunta);
		Assert.assertEquals(p.getIdPregunta(), p3.getIdPregunta());
		//Assert.assertEquals(p.getRespuestas().isEmpty(), p3.getRespuestas().isEmpty());
		
	}
	
	@Test
	public void unmarshallPreguntaRespuestaFijaRespondidaSetAttrCorrectly() {
		String marshalledPregunta = p2.marshall();
		PreguntaRespuestaFijaRespondida p = new PreguntaRespuestaFijaRespondida();
		p.unmarshall(marshalledPregunta);
		Assert.assertEquals(p.getIdPregunta(), p2.getIdPregunta());
		Assert.assertEquals(p.getRespuestas().isEmpty(), p2.getRespuestas().isEmpty());
		
	}

}
