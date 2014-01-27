package connection;

import java.util.ArrayList;
import java.util.List;

import model.Recurso;
import connection.cache.Cache;
import connection.exceptions.ConnectionException;
import connection.exceptions.GetException;
import connection.exceptions.ParseException;
import connection.responses.OperationResponse;
import connection.responses.RecursosResponse;

public class RecursosRequester extends HandlerRequester {

	private RecursosParser parser;
	private Cache<Recurso> cache;
	private List<Recurso> recursosEjemplo;

	public RecursosRequester() {
		parser = new RecursosParser();
		cache = new Cache<Recurso>();
		cache.changeSize(1000);

		generateTestData();

	}

	protected void generateTestData() {
		recursosEjemplo = new ArrayList<Recurso>();
		Recurso r = new Recurso(11002, -1, "un link a google copado", "Link");
		recursosEjemplo.add(r);
		r = new Recurso(11003, -1, "una encuesta chica", "Encuesta");
		recursosEjemplo.add(r);
		r = new Recurso(11004, -1, "una encuesta grande", "Encuesta");
		recursosEjemplo.add(r);
		r = new Recurso(1003, -1, "la foto del siglo", "Archivo");
		recursosEjemplo.add(r);
		r = new Recurso(996, -1, "encuesta sobre el dolar", "Encuesta");
		recursosEjemplo.add(r);
		r = new Recurso(997, -1, "link a respuestas oficiales", "Link");
		recursosEjemplo.add(r);
		r = new Recurso(998, -1, "pdf con todo lo que necesitas", "Archivo");
		recursosEjemplo.add(r);
		r = new Recurso(999, -1, "link con lo mejor de lo mejor", "Link");
		recursosEjemplo.add(r);
		cache.addAll(recursosEjemplo);
	}

	public OperationResponse get(Recurso target) throws GetException, ParseException {
		current = target;
		// Busco en el cache de recursos.
		OperationResponse response = getFromCache(target.getRecursoId());
		if (response.getSuccess()) {
			return response;
		}
		String xml = parser.serializeRecursoQuery(target.getRecursoId());
		// System.out.println("Esto es lo que voy a buscar:");
		// System.out.println(xml);
		return get(xml);
	}

	public OperationResponse getAll(int IDAmbito) {

		List<Recurso> recursos;
		RecursosResponse recursosResponse = new RecursosResponse();
		String message;

		try {

			// Consulto los recursos guardados
			String xml = parser.serializeRecursosQuery(IDAmbito);
			String xml_resp_e = proxy.seleccionar(xml);
			System.out.println(xml_resp_e);
			recursos = parser.deserializeRecursos(xml_resp_e);

			recursosResponse.setRecursos(recursos);

			cache.addAll(recursos);

			recursosResponse.setSuccess(true);
			return recursosResponse;
		} catch (ParseException e) {
			message = e.getMessage();

			// TODO : (Hugo) devuelvo datos de ejemplo, mientras no funcione integracion
			// return OperationResponse.createFailed(message);
			recursosResponse.setRecursos(recursosEjemplo);
			recursosResponse.setSuccess(true);
			return recursosResponse;
		} catch (ConnectionException e) {
			message = "Intentando obtener los recursos del IDAmbito: " + IDAmbito;
			message = e.getMessage() + message;
			// TODO : (Hugo) devuelvo datos de ejemplo, mientras no funcione integracion
			// return OperationResponse.createFailed(message);
			recursosResponse.setRecursos(recursosEjemplo);
			recursosResponse.setSuccess(true);
			return recursosResponse;
			
		}

	}

	public OperationResponse delete(Recurso recurso) {
		current = recurso;
		String xml = parser.serializeDeleteQuery(recurso.getRecursoId());
		try {
			return delete(xml);
		} catch (GetException e) {
			return OperationResponse.createFailed(e.getMessage());
		}
	}

	public void updateCache(Recurso target) {
		current = target;
		updateCache();
		// if (cache.contains(target)) {
		// cache.remove(target);
		// }
		// cache.add(target);
	}

	@Override
	protected boolean cacheContains(int recursoId) {
		return cache.contains(new Recurso(recursoId, 0, ""));
	}

	@Override
	protected Recurso retrieveCached(int recursoId) {
		return cache.get(new Recurso(recursoId, 0, ""));
	}

	@Override
	protected void deserialize(String xml_resp_e) throws ParseException {
		current = parser.deserializeRecurso(xml_resp_e);
	}

	@Override
	protected String getHandledType() {
		return "Recurso";
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
