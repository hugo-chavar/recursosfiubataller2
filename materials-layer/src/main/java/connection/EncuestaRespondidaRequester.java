package connection;

import java.util.ArrayList;
import java.util.List;

import model.EncuestaRespondida;
import model.PreguntaRespondida;
import model.PreguntaRespuestaACompletarRespondida;
import model.PreguntaRespuestaFijaRespondida;
import connection.cache.Cache;
import connection.exceptions.ParseException;
import connection.responses.OperationResponse;

public class EncuestaRespondidaRequester extends HandlerRequester {
	private EncuestaRespondidaParser parser;
	private Cache<EncuestaRespondida> cache;

	public EncuestaRespondidaRequester() {
		parser = new EncuestaRespondidaParser();
		cache = new Cache<EncuestaRespondida>();

		generateTestData();

	}

	private void generateTestData() {
		EncuestaRespondida resp = new EncuestaRespondida(2, 15);

		PreguntaRespuestaFijaRespondida p1 = new PreguntaRespuestaFijaRespondida(1);
		PreguntaRespuestaACompletarRespondida p2 = new PreguntaRespuestaACompletarRespondida(2);

		List<PreguntaRespondida> preguntasRespondidas = new ArrayList<PreguntaRespondida>();
		p1.addRespuesta(0);
		p1.addRespuesta(3);
		p2.responder("Buenos Aires");
		p1.setCorrecta(true);
		p2.setCorrecta(false);
		preguntasRespondidas.add(p1);
		preguntasRespondidas.add(p2);
		resp.setEvaluacion(50);
		resp.setPreguntasRespondidas(preguntasRespondidas);
		cache.add(resp);
	}

	public OperationResponse save(EncuestaRespondida respondida) {
		current = respondida;
		String xml = parser.serializeEncuestaRespondida(respondida);
		return save(xml);
	}

	public OperationResponse getRespondida(int IDEncuesta, int IDUsuario) {
		// Busco en el cache de encuestas respondidas
		current = new EncuestaRespondida(IDEncuesta, IDUsuario);
		OperationResponse response = getFromCache();

		if (response.getSuccess()) {
			return response;
		}
		String xml = parser.serializeEncuestaRespondidaQuery(IDUsuario, IDEncuesta);
		return get(xml);

	}

	@Override
	protected void deserialize(String xml_resp_e) throws ParseException {
		current = parser.deserialize(xml_resp_e);
	}

	@Override
	protected Parser getParser() {
		return parser;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Cache getCache() {
		return cache;
	}

}
