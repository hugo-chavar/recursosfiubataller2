package connection;

import model.Link;
import model.Recurso;
import connection.cache.Cache;
import connection.exceptions.GetException;
import connection.exceptions.ParseException;
import connection.responses.OperationResponse;

public class LinkRequester extends HandlerRequester {

	private LinkParser parser;
	private Cache<Link> cache;
//	private Recurso current;

	
	public LinkRequester() {
		super();
		parser = new LinkParser();
		cache = new Cache<Link>();
		generateTestData();
	}

	private void generateTestData() {
		Link link = new Link(11002,-1,"un link a google copado");
		link.setNombre("www.google.com.ar");
		current = link;
		updateCache();
	}

	public OperationResponse save(Link link) {
		
		current = link;
		String link_str = parser.serializeLink(link);
		
		return save(link_str);
//		try {
//			GuardarDatos guardar = new GuardarDatos();
//			guardar.setXml(link_str);
//			GuardarDatosResponse g_resp = stub.guardarDatos(guardar);
//			// TODO implementar método que chequee las respuestas
//			// if (xmlUtil.isResponseOk(g_resp.get_return())) {
//			// or.setSuccess(true);
//			// or.setReason("algo");
//			// }
//			System.out.println(g_resp.get_return());
//			
//			response = OperationResponse.createSuccess();
//			
//			// Actualizo el cache
//			if (cache.contains(link)) {
//				cache.remove(link);
//				cache.add(link);
//			}
//			
//		} catch (AxisFault e) {
//			String reason = "Error al guardar el Link, Id: " + link.getRecursoId();
//			System.out.println(reason);
//			response = OperationResponse.createFailed(reason);
//		} catch (RemoteException e) {
//			String reason = "Error de conexion remota";
//			System.out.println(reason);
//			response = OperationResponse.createFailed(reason);
//		}
//		
//		return response;
		
	}
	
//	public OperationResponse getFromCache(int recursoId) {
//		
//		OperationResponse response;
////		LinkResponse response = new LinkResponse();
////		Link target = new Link(recursoId, 0, "");
//
//		if (cacheContains(recursoId)) {
//			response = OperationResponse.createSuccess();
//			response.setRecurso(retrieveCached(recursoId));
//			return response;
////			response = new LinkResponse(cache.get(target));
//		}
//		
//		return OperationResponse.createFailed("no esta");
//		
//	}

	protected Recurso retrieveCached(int recursoId) {
		return cache.get(new Link(recursoId, 0, ""));
	}
	
	public boolean cacheContains(int recursoId) {
		return cache.contains(new Link(recursoId, 0, ""));
	}

	public OperationResponse get(Recurso recurso) {

//		OperationResponse response;
//		String reason;
		
		current = recurso;

		// Consulto el link guardado
		String xml = this.parser.serializeQueryByType(recurso.getRecursoId(), LinkParser.LINK_TAG);
		try {
			return get(xml);
		} catch (GetException e) {
			return OperationResponse.createFailed(e.getMessage());
	} catch (ParseException e) {
		return OperationResponse.createFailed(e.getMessage());
	}
//		try {
//			SeleccionarDatos seleccionar_e = new SeleccionarDatos();
//			seleccionar_e.setXml(xml);
//			SeleccionarDatosResponse s_resp_e = this.stub.seleccionarDatos(seleccionar_e);
//			String xml_resp_e = s_resp_e.get_return();
//			Link link = parser.deserializeLink(xml_resp_e);
//			link.setAmbitoId(recurso.getAmbitoId());
//			link.setRecursoId(recurso.getRecursoId());
//			link.setDescripcion(recurso.getDescripcion());
//
//			// Agrego al cache de links
//			cache.add(link);
//
////			response = new LinkResponse(link);
////			response.setSuccess(true);
//			response = OperationResponse.createSuccess();
//			response.setRecurso(link);
//			return response;
//
//		} catch (AxisFault e) {
//			reason = "Error al intentar obtener el Link, ID: " + recurso.getRecursoId();
//		} catch (RemoteException e) {
//			reason = "Error de conexion remota";
//		}
//
//		response = OperationResponse.createFailed(reason);
//		System.out.println(reason);
////		response = new LinkResponse();
////		response.setReason(reason);
//		
//		return response;
		
	}

	public void deleteFromCache(int recursoId) {
		cache.remove(new Link(recursoId, 0, ""));
	}

	@Override
	protected String getHandledType() {
		return "Link";
	}

//	@Override
//	protected void createCurrentObject(String xml_resp_e) {
//		Recurso aux = current;
//		current = deserialize(xml_resp_e);
//		current.setAmbitoId(aux.getAmbitoId());
//		current.setRecursoId(aux.getRecursoId());
//		current.setDescripcion(aux.getDescripcion());
//		
//	}

	@Override
	protected void deserialize(String xml_resp_e) throws ParseException {
		current = parser.deserializeLink(xml_resp_e);
	}

//	@Override
//	protected Recurso getCurrent() {
//		return current;
//	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Cache getCache() {
		return cache;
	}

	@Override
	protected Parser getParser() {
		return parser;
	}
	
//	@Override
//	protected void updateCache() {
//		if (cache.contains(currentLink)) {
//			cache.remove(currentLink);
//		}
//		cache.add(currentLink);
//	}

}
