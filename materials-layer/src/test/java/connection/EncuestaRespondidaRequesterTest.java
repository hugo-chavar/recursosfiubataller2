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

		EncuestaRespondida encuesta_rtn = (EncuestaRespondida) Requester.INSTANCE.getEncuestaRespondida(15, 4).getSerializable();      
		
		Assert.assertEquals(new Integer(50), encuesta_rtn.getEvaluacion());

		List<PreguntaRespondida> respondidas = encuesta_rtn.getPreguntasRespondidas();
		Assert.assertEquals("Azul",((PreguntaRespuestaACompletarRespondida) respondidas.get(0)).getRespuesta());
		Assert.assertEquals(false,respondidas.get(0).getCorrecta());
		Assert.assertEquals("4", ((PreguntaRespuestaACompletarRespondida) respondidas.get(1)).getRespuesta());
		Assert.assertEquals(true,respondidas.get(1).getCorrecta());
	}

	@Test
	public void getEncuestaRespondidaWithPreguntaRespuestaFijaRespondida() throws GetException {
		EncuestaRespondida encuesta_rtn = (EncuestaRespondida) Requester.INSTANCE.getEncuestaRespondida(10, 5).getSerializable();

		Assert.assertEquals(new Integer(100), encuesta_rtn.getEvaluacion());

		List<PreguntaRespondida> respondidas = encuesta_rtn.getPreguntasRespondidas();
		Assert.assertEquals(new Integer(1), ((PreguntaRespuestaFijaRespondida) respondidas.get(0)).getRespuestasFijas().get(0));
		Assert.assertEquals(true, respondidas.get(0).getCorrecta());
		Assert.assertEquals(new Integer(2), ((PreguntaRespuestaFijaRespondida) respondidas.get(1)).getRespuestasFijas().get(0));
		Assert.assertEquals(true,respondidas.get(1).getCorrecta());
	}
	

	
	@Test
	public void getEncuestaRespondidaFromCache() throws GetException {

		EncuestaRespondida respondida = (EncuestaRespondida) Requester.INSTANCE.getEncuestaRespondida(2, 15).getSerializable();     

		Assert.assertEquals(new Integer(50), respondida.getEvaluacion());
		List<PreguntaRespondida> respondidas = respondida.getPreguntasRespondidas();
		Assert.assertEquals(new Integer(1), ((PreguntaRespuestaFijaRespondida) respondidas.get(0)).getRespuestasFijas().get(0));
		Assert.assertEquals(new Integer(4), ((PreguntaRespuestaFijaRespondida) respondidas.get(0)).getRespuestasFijas().get(1));
		Assert.assertEquals(true, respondidas.get(0).getCorrecta());
		Assert.assertNotEquals("Salta", ((PreguntaRespuestaACompletarRespondida) respondidas.get(1)).getRespuesta());
		Assert.assertEquals(false,respondidas.get(1).getCorrecta());
	}
	
}
