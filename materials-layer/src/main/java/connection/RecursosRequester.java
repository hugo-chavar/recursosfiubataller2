package connection;

import java.util.ArrayList;
import java.util.List;

import model.Recurso;
import connection.cache.Cache;
import connection.exceptions.GetException;
import connection.exceptions.ParseException;
import connection.responses.OperationResponse;
import connection.responses.RecursosResponse;

public class RecursosRequester extends HandlerRequester {
	
	private boolean all;

	private RecursosParser parser;
	private Cache<Recurso> cache;
	private List<Recurso> recursosEjemplo;
	private List<Recurso> recursosCurrent;

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
		setAll(false);
		current = target;
		// Busco en el cache de recursos.
		OperationResponse response = getFromCache();
		if (response.getSuccess()) {
			return response;
		}
		String xml = parser.serializeRecursoQuery(target.getRecursoId());
		return get(xml);
	}

	public OperationResponse getAll(int IDAmbito) {

		setAll(true);
		

		// Consulto los recursos guardados
		String xml = parser.serializeRecursosQuery(IDAmbito);
		
			try {
				return get(xml);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				e.printStackTrace();
				return OperationResponse.createFailed(e.getMessage());
			} catch (GetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
				return OperationResponse.createFailed(e.getMessage());
			}
			// TODO : (Hugo) devuelvo datos de ejemplo, mientras no funcione integracion
//			 String message = e.getMessage();
//			 return OperationResponse.createFailed(message);
//			RecursosResponse recursosResponse = new RecursosResponse();
//			recursosResponse.setRecursos(recursosEjemplo);
//			recursosResponse.setSuccess(true);
//			return recursosResponse;
		
//		try {
//			String xml_resp_e = proxy.seleccionar(xml);
//			recursosCurrent = parser.deserializeRecursos(xml_resp_e);
//
//			recursosResponse.setRecursos(recursosCurrent);
//
//			cache.addAll(recursosCurrent);
//
//			recursosResponse.setSuccess(true);
//			return recursosResponse;
//		} catch (ParseException e) {
//			message = e.getMessage();
//
//			// TODO : (Hugo) devuelvo datos de ejemplo, mientras no funcione integracion
//			// return OperationResponse.createFailed(message);
//			recursosResponse.setRecursos(recursosEjemplo);
//			recursosResponse.setSuccess(true);
//			return recursosResponse;
//		} catch (ConnectionException e) {
//			message = "Intentando obtener los recursos del IDAmbito: " + IDAmbito;
//			message = e.getMessage() + message;
//			// TODO : (Hugo) devuelvo datos de ejemplo, mientras no funcione integracion
//			// return OperationResponse.createFailed(message);
//			recursosResponse.setRecursos(recursosEjemplo);
//			recursosResponse.setSuccess(true);
//			return recursosResponse;
//		}

	}

	public OperationResponse delete(Recurso recurso) {
		setAll(false);
		current = recurso;
		String xml = parser.serializeDeleteQuery(recurso.getRecursoId());
		try {
			return delete(xml);
		} catch (GetException e) {
			return OperationResponse.createFailed(e.getMessage());
		}
	}

	public void updateCache(Recurso target) {
		setAll(false);
		current = target;
		updateCache();

	}

	@Override
	protected void deserialize(String xml_resp_e) throws ParseException {
		if (isAll()) {
			System.out.println(xml_resp_e);
			recursosCurrent = parser.deserializeRecursos(xml_resp_e);
		} else {
			current = parser.deserialize(xml_resp_e);
		}
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
	
	@Override
	protected OperationResponse createResponse() {
		if (!isAll()) {
			return super.createResponse();
		}
		RecursosResponse response = new RecursosResponse();;
		response.setRecursos(recursosCurrent);
		response.setSuccess(true);
		return response;
	}
	
	@Override
	protected void updateCache() {
		if (!isAll()) {
			super.updateCache();
		} else {
			cache.addAll(recursosCurrent);
		}
	}
	

	protected boolean currentIsInvalid() {
		if (!isAll()) {
			return super.currentIsInvalid();
		}
		return (recursosCurrent == null) || recursosCurrent.isEmpty();
	}

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

}
