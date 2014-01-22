package connection;

import java.util.List;

import model.EncuestaRespondida;
import model.PreguntaRespondida;
import model.PreguntaRespuestaACompletarRespondida;
import model.PreguntaRespuestaFijaRespondida;

import org.junit.Assert;
import org.junit.Test;

import connection.exceptions.GetException;

public class EncuestaRespondidaRequesterTest {

	@Test
	public void getEncuestaRespondidaWithPreguntaRespuestaACompletarRespondida() throws GetException {

		EncuestaRespondida encuesta_rtn = (EncuestaRespondida) Requester.INSTANCE.getEncuestaRespondida(15, 4);      
		
		Assert.assertEquals(new Integer(50), encuesta_rtn.getEvaluacion());

		List<PreguntaRespondida> respondidas = encuesta_rtn.getPreguntasRespondidas();
		Assert.assertEquals("Azul",((PreguntaRespuestaACompletarRespondida) respondidas.get(0)).getRespuesta());
		Assert.assertEquals(false,respondidas.get(0).getIsCorrecta());
		Assert.assertEquals("4", ((PreguntaRespuestaACompletarRespondida) respondidas.get(1)).getRespuesta());
		Assert.assertEquals(true,respondidas.get(1).getIsCorrecta());
	}

	@Test
	public void getEncuestaRespondidaWithPreguntaRespuestaFijaRespondida() throws GetException {
		EncuestaRespondida encuesta_rtn = (EncuestaRespondida) Requester.INSTANCE.getEncuestaRespondida(10, 5);

		Assert.assertEquals(new Integer(100), encuesta_rtn.getEvaluacion());

		List<PreguntaRespondida> respondidas = encuesta_rtn.getPreguntasRespondidas();
		Assert.assertEquals(new Integer(1), ((PreguntaRespuestaFijaRespondida) respondidas.get(0)).getRespuestasFijas().get(0));
		Assert.assertEquals(true, respondidas.get(0).getIsCorrecta());
		Assert.assertEquals(new Integer(2), ((PreguntaRespuestaFijaRespondida) respondidas.get(1)).getRespuestasFijas().get(0));
		Assert.assertEquals(true,respondidas.get(1).getIsCorrecta());
	}
	
//	if (xml.equals("<WS><encuestarespondida><recursoId>15</recursoId><usuarioId>5</usuarioId></encuestarespondida></WS>")) {
//	xml_resp_e = "<WS><encuestarespondida><recursoId>15</recursoId><usuarioId>5</usuarioId><evaluacion>50</evaluacion><preguntasrespondidas>C;1;false;Azul|" +
//			"C;2;true;4</preguntas></encuestarespondida></WS>";
//} else if (xml.equals("<WS><encuestarespondida><recursoId>10</recursoId><usuarioId>5</usuarioId></encuestarespondida></WS>")) {
//	xml_resp_e = "<WS><encuestarespondida><recursoId>10</recursoId><usuarioId>5</usuarioId><evaluacion>100</evaluacion><preguntasrespondidas>F;1;true;1|" +
//			"F;2;true;2</preguntasrespondidas></encuestarespondida></WS>";
//
	
//	@Test
//	public void getEncuestaFromCache() throws GetException {
//		Encuesta encuesta = new Encuesta(1003, -1, "una encuesta chica", false);
//
//		EncuestaResponse response = (EncuestaResponse) Requester.INSTANCE.getRecurso(encuesta);
//		Encuesta encuesta_rtn = response.getEncuesta();       
//
//		Assert.assertEquals(encuesta, encuesta_rtn);
//		Assert.assertEquals("una encuesta chica", encuesta_rtn.getDescripcion());
//		Assert.assertEquals(encuesta.isEvaluada(), encuesta_rtn.isEvaluada());
//
//		List<Pregunta> preguntas = encuesta_rtn.getPreguntas();
//		Assert.assertEquals("cuantas materias te faltan para recibirte?", preguntas.get(0).getEnunciado());
//		Assert.assertEquals("que materia fue la mas dificil?", preguntas.get(1).getEnunciado());
//	}
	
}
