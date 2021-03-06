package connection;

import java.util.List;

import model.EncuestaRespondida;
import model.PreguntaRespondida;
import model.PreguntaRespuestaACompletarRespondida;
import model.PreguntaRespuestaFijaRespondida;
import model.Usuario;

import org.junit.Assert;
import org.junit.Test;

import connection.exceptions.GetException;

public class EncuestaRespondidaRequesterTest {

	@Test
	public void getEncuestaRespondidaWithPreguntaRespuestaACompletarRespondida() throws GetException {
		
		Usuario u = new Usuario();
		u.setUsername("4");

		EncuestaRespondida encuesta_rtn = (EncuestaRespondida) Requester.INSTANCE.getEncuestaRespondida(15, u).getSerializable();      
		
		Assert.assertEquals(new Integer(50), encuesta_rtn.getEvaluacion());

		List<PreguntaRespondida> respondidas = encuesta_rtn.getPreguntasRespondidas();
		Assert.assertEquals("Azul",((PreguntaRespuestaACompletarRespondida) respondidas.get(0)).getRespuesta());
		Assert.assertEquals(false,respondidas.get(0).getCorrecta());
		Assert.assertEquals("4", ((PreguntaRespuestaACompletarRespondida) respondidas.get(1)).getRespuesta());
		Assert.assertEquals(true,respondidas.get(1).getCorrecta());
	}

	@Test
	public void getEncuestaRespondidaNoEvaluadaWithPreguntaRespuestaFijaRespondida() throws GetException {
		Usuario u = new Usuario();
		u.setUsername("5");
		EncuestaRespondida encuesta_rtn = (EncuestaRespondida) Requester.INSTANCE.getEncuestaRespondida(10, u).getSerializable();

		Assert.assertEquals(null, encuesta_rtn.getEvaluacion());

		List<PreguntaRespondida> respondidas = encuesta_rtn.getPreguntasRespondidas();
		Assert.assertEquals("1", ((PreguntaRespuestaFijaRespondida) respondidas.get(0)).getRespuestasFijas());
		Assert.assertEquals(null, respondidas.get(0).getCorrecta());
		Assert.assertEquals("2", ((PreguntaRespuestaFijaRespondida) respondidas.get(1)).getRespuestasFijas());
		Assert.assertEquals(null,respondidas.get(1).getCorrecta());
	}
	

	
	@Test
	public void getEncuestaRespondidaFromCache() throws GetException {

		Usuario u = new Usuario();
		u.setUsername("15");
		EncuestaRespondida respondida = (EncuestaRespondida) Requester.INSTANCE.getEncuestaRespondida(2, u).getSerializable();     

		Assert.assertEquals(new Integer(50), respondida.getEvaluacion());
		List<PreguntaRespondida> respondidas = respondida.getPreguntasRespondidas();
		Assert.assertEquals("1,4", ((PreguntaRespuestaFijaRespondida) respondidas.get(0)).getRespuestasFijas());
		Assert.assertEquals(true, respondidas.get(0).getCorrecta());
		Assert.assertNotEquals("Salta", ((PreguntaRespuestaACompletarRespondida) respondidas.get(1)).getRespuesta());
		Assert.assertEquals(false,respondidas.get(1).getCorrecta());
	}
	
}
