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
	protected Recurso current;

	protected HandlerRequester() {
		try {
			this.stub = new IntegracionStub();
		} catch (AxisFault e) {
			System.out.println("Error al intentar contectarse con Integracion");
		}
	}
	
	protected Recurso getCurrent() {
		return current;
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
			//TODO: pruebas de Yami
			if (xml.equals("<WS><encuesta><recursoId>15</recursoId></encuesta></WS>")) {
				xml_resp_e = "<WS><encuesta><evaluada>true</evaluada><preguntas>C;1;De que color es el caballo blanco de San Martin?;blanco|" +
						"C;2;Cuantas patas tiene un gato?;4</preguntas></encuesta></WS>";
			} else if (xml.equals("<WS><encuesta><recursoId>10</recursoId></encuesta></WS>")) {
				xml_resp_e = "<WS><encuesta><evaluada>true</evaluada><preguntas>F;1;De que color es el caballo blanco de San Martin?;negro,blanco,marron;1|" +
						"F;2;Cuantas patas tiene un gato?;3,2,4;2</preguntas></encuesta></WS>";
			}
			//fin pruebas
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
	
	@SuppressWarnings("unchecked")
	protected void updateCache() {
		if (getCache().contains(getCurrent())) {
			getCache().remove(getCurrent());
		}
		getCache().add(getCurrent());
	}
	
	public OperationResponse getFromCache(int recursoId) {

		OperationResponse response;

		if (cacheContains(recursoId)) {
			response = OperationResponse.createSuccess();
			response.setRecurso(retrieveCached(recursoId));
			return response;
		}

		return OperationResponse.createFailed("no esta");

	}
	
	protected void createCurrentObject(String xml_resp_e) {
		Recurso aux = current;
		current = deserialize(xml_resp_e);
		current.setAmbitoId(aux.getAmbitoId());
		current.setRecursoId(aux.getRecursoId());
		current.setDescripcion(aux.getDescripcion());
		
	}

	public abstract void deleteFromCache(int recursoId);
	
	protected abstract boolean cacheContains(int recursoId);
	
	protected abstract Recurso retrieveCached(int recursoId);

//	protected abstract void createCurrentObject(String xml_resp_e);
	
	protected abstract Recurso deserialize(String xml_resp_e);
	

//	protected abstract OperationResponse currentObjetToResponse();
	
	protected abstract String getHandledType();

//	protected abstract void updateCache();
	
//	protected abstract Recurso getCurrent();
	
	protected abstract Parser getParser();
	
	@SuppressWarnings("rawtypes")
	protected abstract Cache getCache();

}
