package connection;

import java.rmi.RemoteException;

import model.Recurso;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionStub;
import com.ws.services.IntegracionStub.GuardarDatos;
import com.ws.services.IntegracionStub.GuardarDatosResponse;
import com.ws.services.IntegracionStub.SeleccionarDatos;
import com.ws.services.IntegracionStub.SeleccionarDatosResponse;

import connection.cache.Cache;
import connection.responses.OperationResponse;

public abstract class HandlerRequester {

	protected IntegracionStub stub;
	

	protected HandlerRequester() {
		try {
			this.stub = new IntegracionStub();
		} catch (AxisFault e) {
			System.out.println("Error al intentar contectarse con Integracion");
		}
	}

	protected OperationResponse save(String xml_str) {
		OperationResponse response;

		try {
			GuardarDatos guardar = new GuardarDatos();
			guardar.setXml(xml_str);
			GuardarDatosResponse g_resp = stub.guardarDatos(guardar);
			System.out.println(g_resp.get_return());

			response = OperationResponse.createSuccess();

			updateCache();

		} catch (AxisFault e) {
			String reason = "Error al guardar " + getHandledType() + ", Id: " + getCurrent().getRecursoId();
			System.out.println(reason);
			response = OperationResponse.createFailed(reason);
		} catch (RemoteException e) {
			String reason = "Error de conexion remota";
			System.out.println(reason);
			response = OperationResponse.createFailed(reason);
		}

		return response;
	}

	public OperationResponse get(String xml) {
		String reason;

		try {
			SeleccionarDatos seleccionar_e = new SeleccionarDatos();
			seleccionar_e.setXml(xml);
			SeleccionarDatosResponse s_resp_e = this.stub.seleccionarDatos(seleccionar_e);
			String xml_resp_e = s_resp_e.get_return();
			createCurrentObject(xml_resp_e);

			updateCache();

			return currentObjetToResponse();

		} catch (AxisFault e) {
			reason = "Error al intentar obtener " + getHandledType() + ", ID: " + getCurrent().getRecursoId();
		} catch (RemoteException e) {
			reason = "Error de conexion remota";
		}

		return OperationResponse.createFailed(reason);

	}

	protected OperationResponse currentObjetToResponse() {
		OperationResponse response;
		if (getCurrent() == null) {
			response = OperationResponse.createFailed("NullPointer: Error al obtener " + getHandledType());
		} else {
			response = OperationResponse.createSuccess();
			response.setRecurso(getCurrent());
		}
		return response;
	}
	
	//si no anda.. hacerlo abstracto y codificar en los requesters
	@SuppressWarnings("unchecked")
	protected void updateCache() {
		if (getCache().contains(getCurrent())) {
			getCache().remove(getCurrent());
		}
		getCache().add(getCurrent());
	}
	
	public OperationResponse getFromCache(int recursoId) {

		OperationResponse response;
		// LinkResponse response = new LinkResponse();
		// Link target = new Link(recursoId, 0, "");

		if (cacheContains(recursoId)) {
			response = OperationResponse.createSuccess();
			response.setRecurso(retrieveCached(recursoId));
			return response;
			// response = new LinkResponse(cache.get(target));
		}

		return OperationResponse.createFailed("no esta");

	}

	public abstract void deleteFromCache(int recursoId);
	
	protected abstract boolean cacheContains(int recursoId);
	
	protected abstract Recurso retrieveCached(int recursoId);

	protected abstract void createCurrentObject(String xml_resp_e);

//	protected abstract OperationResponse currentObjetToResponse();
	
	protected abstract String getHandledType();

//	protected abstract void updateCache();
	
	protected abstract Recurso getCurrent();
	
	protected abstract Parser getParser();
	
	@SuppressWarnings("rawtypes")
	protected abstract Cache getCache();

}
