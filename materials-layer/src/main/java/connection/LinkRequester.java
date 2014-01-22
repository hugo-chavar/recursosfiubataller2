package connection;

import java.rmi.RemoteException;

import model.Link;
import model.Recurso;

import org.apache.axis2.AxisFault;

import connection.cache.Cache;
import connection.responses.OperationResponse;

import com.ws.services.IntegracionStub.SeleccionarDatos;
import com.ws.services.IntegracionStub.SeleccionarDatosResponse;


public class LinkRequester extends HandlerRequester {

//	private IntegracionStub stub;
//	private LinkParser parser;
	private Cache<Link> cache;
	private Link currentLink;

	
	public LinkRequester() {
		super();

		parser = new LinkParser();
		cache = new Cache<Link>();
//		Link link = new Link(11002,-1,"un link a google copado");
//		link.setNombre("www.google.com.ar");
//		cache.add(link);
		currentLink = new Link(11002,-1,"un link a google copado");
		currentLink.setNombre("www.google.com.ar");
		updateCache();

//		try {
//			this.stub = new IntegracionStub();
//		} catch (AxisFault e) {
//			System.out.println("Error al intentar contectarse con Integracion");
//		}

	}

	public OperationResponse save(Link link) {
		
		currentLink = link;
		// Guardo el link
		String link_str = ((LinkParser) parser).serializeLink(link);
		
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
		Link target = new Link(recursoId, 0, "");
		return cache.get(target);
	}
	
	public boolean cacheContains(int recursoId) {
		Link target = new Link(recursoId, 0, "");
		return cache.contains(target);
	}

	public OperationResponse get(Recurso recurso) {

		OperationResponse response;
		String reason;

		// Consulto el link guardado
		String xml = this.parser.serializeQueryByType(recurso.getRecursoId(), LinkParser.LINK_TAG);
		try {
			SeleccionarDatos seleccionar_e = new SeleccionarDatos();
			seleccionar_e.setXml(xml);
			SeleccionarDatosResponse s_resp_e = this.stub.seleccionarDatos(seleccionar_e);
			String xml_resp_e = s_resp_e.get_return();
			Link link = ((LinkParser) parser).deserializeLink(xml_resp_e);
			link.setAmbitoId(recurso.getAmbitoId());
			link.setRecursoId(recurso.getRecursoId());
			link.setDescripcion(recurso.getDescripcion());

			// Agrego al cache de links
			cache.add(link);

//			response = new LinkResponse(link);
//			response.setSuccess(true);
			response = OperationResponse.createSuccess();
			response.setRecurso(link);
			return response;

		} catch (AxisFault e) {
			reason = "Error al intentar obtener el Link, ID: " + recurso.getRecursoId();
		} catch (RemoteException e) {
			reason = "Error de conexion remota";
		}

		response = OperationResponse.createFailed(reason);
		System.out.println(reason);
//		response = new LinkResponse();
//		response.setReason(reason);
		
		return response;
		
	}

	public void deleteFromCache(int recursoId) {
		cache.remove(new Link(recursoId, 0, ""));
	}

	@Override
	protected String getHandledType() {
		return "Link";
	}

//	@Override
//	protected void updateCache() {
//		if (cache.contains(currentLink)) {
//			cache.remove(currentLink);
//		}
//		cache.add(currentLink);
//	}

	@Override
	protected void createCurrentObject(String xml_resp_e) {
		currentLink = ((LinkParser) parser).deserializeLink(xml_resp_e);
	}

	@Override
	protected Recurso getCurrent() {
		return currentLink;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Cache getCache() {
		return cache;
	}

}
