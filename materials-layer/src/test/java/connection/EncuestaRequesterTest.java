package connection;

import java.util.List;

import model.Encuesta;
import model.Pregunta;
import model.PreguntaRespuestaACompletar;
import model.PreguntaRespuestaFija;

import org.junit.Assert;
import org.junit.Test;

import connection.exceptions.GetException;
import connection.responses.OperationResponse;


public class EncuestaRequesterTest {

	//      @Test
	//      public void saveEncuestaWithPreguntaRespuestaACompletarEvaluada() {
	//              Encuesta encuesta = new Encuesta(15, 2, "Encuesta con preguntas a completar", true);
	//              
	//              PreguntaRespuestaACompletar pregunta1 = new PreguntaRespuestaACompletar();
	//              pregunta1.setEnunciado("De que color es el caballo blanco de San Martin?");
	//              pregunta1.addRespuestaCorrecta("blanco");
	//              encuesta.addPregunta(pregunta1);
	//              PreguntaRespuestaACompletar pregunta2 = new PreguntaRespuestaACompletar();
	//              pregunta2.setEnunciado("cuantas patas tiene un gato?");
	//              pregunta2.addRespuestaCorrecta("4");
	//              encuesta.addPregunta(pregunta2);
	//              
	//              Requester.INSTANCE.saveEncuesta(encuesta);
	//      }

	@Test
	public void getEncuestaWithPreguntaRespuestaACompletar() throws GetException {
		Encuesta encuesta = new Encuesta(15, 2, "Encuesta con preguntas a completar", true);

//		EncuestaResponse response = (EncuestaResponse) Requester.INSTANCE.getRecurso(encuesta);
		OperationResponse response = Requester.INSTANCE.getRecurso(encuesta);
		Encuesta encuesta_rtn = (Encuesta)response.getRecurso();       

		Assert.assertEquals(encuesta, encuesta_rtn);
		Assert.assertEquals("Encuesta con preguntas a completar", encuesta_rtn.getDescripcion());
		Assert.assertEquals(encuesta.isEvaluada(), encuesta_rtn.isEvaluada());

		List<Pregunta> preguntas = encuesta_rtn.getPreguntas();
		Assert.assertEquals("De que color es el caballo blanco de San Martin?", preguntas.get(0).getEnunciado());
		Assert.assertEquals("blanco", ((PreguntaRespuestaACompletar)preguntas.get(0)).getRespuesta());
		Assert.assertEquals("Cuantas patas tiene un gato?", preguntas.get(1).getEnunciado());
		Assert.assertEquals("4", ((PreguntaRespuestaACompletar)preguntas.get(1)).getRespuesta());
	}

	@Test
	public void getEncuestaWithPreguntaRespuestaFija() throws GetException {
		Encuesta encuesta = new Encuesta(10, 3, "Encuesta con preguntas fijas", false);

//		EncuestaResponse response = (EncuestaResponse) Requester.INSTANCE.getRecurso(encuesta);
//		Encuesta encuesta_rtn = response.getEncuesta();
//		EncuestaResponse response = (EncuestaResponse) Requester.INSTANCE.getRecurso(encuesta);
		OperationResponse response = Requester.INSTANCE.getRecurso(encuesta);
		Encuesta encuesta_rtn = (Encuesta)response.getRecurso();    

		Assert.assertEquals(encuesta, encuesta_rtn);
		Assert.assertEquals("Encuesta con preguntas fijas", encuesta_rtn.getDescripcion());

		List<Pregunta> preguntas = encuesta_rtn.getPreguntas();
		Assert.assertEquals("De que color es el caballo blanco de San Martin?", preguntas.get(0).getEnunciado());
		Assert.assertEquals("negro", ((PreguntaRespuestaFija)preguntas.get(0)).getRespuestasPosibles().get(0));
		Assert.assertEquals("Cuantas patas tiene un gato?", preguntas.get(1).getEnunciado());
		Assert.assertEquals("3", ((PreguntaRespuestaFija)preguntas.get(1)).getRespuestasPosibles().get(0));
	}
	
//	@Test
//	public void getEncuestaWithPreguntaRespuestaFijaWithSpecialCharacters() throws GetException {
//		Encuesta encuesta = new Encuesta(11, 3, "Encuesta con preguntas fijas", false);
//
////		EncuestaResponse response = (EncuestaResponse) Requester.INSTANCE.getRecurso(encuesta);
////		Encuesta encuesta_rtn = response.getEncuesta();
////		EncuestaResponse response = (EncuestaResponse) Requester.INSTANCE.getRecurso(encuesta);
//		OperationResponse response = Requester.INSTANCE.getRecurso(encuesta);
//		Encuesta encuesta_rtn = (Encuesta)response.getRecurso();    
//
//		Assert.assertEquals(encuesta, encuesta_rtn);
//		Assert.assertEquals("Encuesta con preguntas fijas", encuesta_rtn.getDescripcion());
//
//		List<Pregunta> preguntas = encuesta_rtn.getPreguntas();
//		Assert.assertEquals("De que color; es, el| caballo blanco de San Martin?", preguntas.get(0).getEnunciado());
//		Assert.assertEquals("negro", ((PreguntaRespuestaFija)preguntas.get(0)).getRespuestasPosibles().get(0));
//		Assert.assertEquals("Cuantas patas tiene un gato?", preguntas.get(1).getEnunciado());
//		Assert.assertEquals("3", ((PreguntaRespuestaFija)preguntas.get(1)).getRespuestasPosibles().get(0));
//	}
	
	@Test
	public void getEncuestaFromCache() throws GetException {
		Encuesta encuesta = new Encuesta(11003, -1, "una encuesta chica", false);

//		EncuestaResponse response =  (EncuestaResponse) Requester.INSTANCE.getRecurso(encuesta);
		OperationResponse response = Requester.INSTANCE.getRecurso(encuesta);
		Encuesta encuesta_rtn = (Encuesta)response.getRecurso();       

		Assert.assertEquals(encuesta, encuesta_rtn);
		Assert.assertEquals("una encuesta chica", encuesta_rtn.getDescripcion());
		Assert.assertEquals(encuesta.isEvaluada(), encuesta_rtn.isEvaluada());

		List<Pregunta> preguntas = encuesta_rtn.getPreguntas();
		Assert.assertEquals("cuantas materias| te, faltan; para recibirte?", preguntas.get(0).getEnunciado());
		Assert.assertEquals("que materia fue la mas dificil?", preguntas.get(1).getEnunciado());
	}

}

