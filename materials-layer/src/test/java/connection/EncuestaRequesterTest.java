package connection;

import java.util.List;

import model.Encuesta;
import model.Pregunta;
import model.PreguntaRespuestaACompletar;
import model.PreguntaRespuestaFija;

import org.junit.Assert;
import org.junit.Test;


// Falta subir la parte de integracion modificada. No va a funcionar...

public class EncuestaRequesterTest {

//	@Test
//	public void saveEncuestaWithPreguntaRespuestaACompletarEvaluada() {
//		Encuesta encuesta = new Encuesta(15, 2, "Encuesta con preguntas a completar", true);
//		
//		PreguntaRespuestaACompletar pregunta1 = new PreguntaRespuestaACompletar();
//		pregunta1.setEnunciado("De que color es el caballo blanco de San Martin?");
//		pregunta1.addRespuestaCorrecta("blanco");
//		encuesta.addPregunta(pregunta1);
//		PreguntaRespuestaACompletar pregunta2 = new PreguntaRespuestaACompletar();
//		pregunta2.setEnunciado("cuantas patas tiene un gato?");
//		pregunta2.addRespuestaCorrecta("4");
//		encuesta.addPregunta(pregunta2);
//		
//		Requester.INSTANCE.saveEncuesta(encuesta);
//	}
	
	@Test
	public void getEncuestaWithPreguntaRespuestaACompletar() {
		Encuesta encuesta = new Encuesta(15, 2, "Encuesta con preguntas a completar", true);
		
		Encuesta encuesta_rtn = Requester.INSTANCE.getEncuesta(2, 15);		

		Assert.assertEquals(encuesta, encuesta_rtn);
		Assert.assertEquals("Encuesta con preguntas a completar", encuesta_rtn.getDescripcion());
		Assert.assertEquals(encuesta.esEvaluada(), encuesta_rtn.esEvaluada());
		
		List<Pregunta> preguntas = encuesta_rtn.getPreguntas();
		Assert.assertEquals("De que color es el caballo blanco de San Martin?", preguntas.get(0).getEnunciado());
		Assert.assertEquals("blanco", ((PreguntaRespuestaACompletar)preguntas.get(0)).getRespuesta());
		Assert.assertEquals("cuantas patas tiene un gato?", preguntas.get(1).getEnunciado());
		Assert.assertEquals("4", ((PreguntaRespuestaACompletar)preguntas.get(1)).getRespuesta());
	}
	
	@Test
	public void getEncuestaWithPreguntaRespuestaFija() {
		Encuesta encuesta = new Encuesta(10, 3, "Encuesta con preguntas fijas", false);
		
		Encuesta encuesta_rtn = Requester.INSTANCE.getEncuesta(3, 10);		

		Assert.assertEquals(encuesta, encuesta_rtn);
		Assert.assertEquals("Encuesta con preguntas fijas", encuesta_rtn.getDescripcion());
		
		List<Pregunta> preguntas = encuesta_rtn.getPreguntas();
		Assert.assertEquals("De que color es el caballo blanco de San Martin?", preguntas.get(0).getEnunciado());
		Assert.assertEquals("negro", ((PreguntaRespuestaFija)preguntas.get(0)).getRespuestasPosibles().get(0));
		Assert.assertEquals("cuantas patas tiene un gato?", preguntas.get(1).getEnunciado());
		Assert.assertEquals("3", ((PreguntaRespuestaFija)preguntas.get(1)).getRespuestasPosibles().get(0));
	}

}
