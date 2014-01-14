package connection;

import java.rmi.RemoteException;

import model.Link;
import model.Recurso;

import org.apache.axis2.AxisFault;

import connection.cache.Cache;
import connection.responses.LinkResponse;
import connection.responses.OperationResponse;

import com.ws.services.IntegracionStub;
import com.ws.services.IntegracionStub.GuardarDatos;
import com.ws.services.IntegracionStub.GuardarDatosResponse;
import com.ws.services.IntegracionStub.SeleccionarDatos;
import com.ws.services.IntegracionStub.SeleccionarDatosResponse;


public class LinkRequester {

	private IntegracionStub stub;
	private LinkParser parser;
	private Cache<Link> cache;

	public LinkRequester() {

		parser = new LinkParser();
		cache = new Cache<Link>();
		Link link = new Link(1002,-1,"un link a google copado");
		link.setNombre("www.google.com.ar");
		cache.add(link);

		try {
			this.stub = new IntegracionStub();
		} catch (AxisFault e) {
			System.out.println("Error al intentar contectarse con Integracion");
		}

	}

	public OperationResponse save(Link link) {
		
		OperationResponse response;
		// Guardo el link
		String link_str = parser.serializeLink(link);
		try {
			GuardarDatos guardar = new GuardarDatos();
			guardar.setXml(link_str);
			GuardarDatosResponse g_resp = stub.guardarDatos(guardar);
			// TODO implementar método que chequee las respuestas
			// if (xmlUtil.isResponseOk(g_resp.get_return())) {
			// or.setSuccess(true);
			// or.setReason("algo");
			// }
			response = OperationResponse.createSuccess();
			System.out.println(g_resp.get_return());
		} catch (AxisFault e) {
			String reason = "Error al intentar guardar la siguiente Link: " + link.getDescripcion();
			System.out.println(reason);
			response = OperationResponse.createFailed(reason);
		} catch (RemoteException e) {
			String reason = "Error de conexion remota";
			System.out.println(reason);
			response = OperationResponse.createFailed(reason);
		}
		return response;
		
	}
	
	public OperationResponse getFromCache(int recursoId) {
		
		LinkResponse response = new LinkResponse();
		Link target = new Link(recursoId, 0, "");

		if (cache.contains(target)) {
			response = new LinkResponse(cache.get(target));
			response.setSuccess(true);
		}
		
		return response;
		
	}

	public OperationResponse get(Recurso recurso) {

		LinkResponse response;
		String reason;
		
		// Busco en el cache de links
		OperationResponse cacheResponse = this.getFromCache(recurso.getRecursoId());
		if (cacheResponse.getSuccess()) {
			return cacheResponse;
		} else {
			try {

				// Consulto el link guardado
				String xml = this.parser.serializeQueryByType(recurso.getRecursoId(), LinkParser.LINK_TAG);
				SeleccionarDatos seleccionar_e = new SeleccionarDatos();
				seleccionar_e.setXml(xml);
				SeleccionarDatosResponse s_resp_e = this.stub.seleccionarDatos(seleccionar_e);
				String xml_resp_e = s_resp_e.get_return();
				Link link = this.parser.deserializeLink(xml_resp_e);
				link.setAmbitoId(recurso.getAmbitoId());
				link.setRecursoId(recurso.getRecursoId());
				link.setDescripcion(recurso.getDescripcion());

				// Agrego al cache de links
				cache.add(link);

				response = new LinkResponse(link);
				response.setSuccess(true);
				return response;

			} catch (AxisFault e) {
				reason = "Error al intentar obtener el Link, ID: " + recurso.getRecursoId();
			} catch (RemoteException e) {
				reason = "Error de conexion remota";
			}

			System.out.println(reason);
			response = new LinkResponse();
			response.setReason(reason);
			
			return response;
		}

	}

	public void deleteFromCache(int recursoId) {
		cache.remove(new Link(recursoId, 0, ""));
	}

}
