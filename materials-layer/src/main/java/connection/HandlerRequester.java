package connection;

import java.rmi.RemoteException;

import model.Archivo;
import model.Recurso;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionStub;
import com.ws.services.IntegracionStub.EliminarDatos;
import com.ws.services.IntegracionStub.EliminarDatosResponse;
import com.ws.services.IntegracionStub.GuardarArchivoResponse;
import com.ws.services.IntegracionStub.GuardarDatos;
import com.ws.services.IntegracionStub.GuardarDatosResponse;
import com.ws.services.IntegracionStub.SeleccionarDatos;
import com.ws.services.IntegracionStub.SeleccionarDatosResponse;

import connection.cache.Cache;
import connection.exceptions.GetException;
import connection.exceptions.ParseException;
import connection.responses.OperationResponse;

public abstract class HandlerRequester {

	protected IntegracionStub stub;
	protected Recurso current;

	protected HandlerRequester() {
		try {
			stub = new IntegracionStub();
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

	public OperationResponse getFile (String xml) throws GetException, ParseException{
	
		SeleccionarDatos seleccionar_e = new SeleccionarDatos();
		seleccionar_e.setXml(xml);
	
	  SeleccionarDatosResponse s_resp_e;
	try {
		s_resp_e = this.stub.seleccionarDatos(seleccionar_e);
		String xml_resp_e = s_resp_e.get_return();
		System.out.println("Integracion me esta contestando : "+ xml_resp_e);
		createCurrentObject(xml_resp_e);
		updateCache();
	} catch (RemoteException e) {
		
		e.printStackTrace();
	}
	 
	  return currentObjetToResponse();
	}
	
	protected OperationResponse saveFile(String xml){
		GuardarArchivoResponse responseArchivo;
		Archivo archivo = (Archivo) current;
		OperationResponse response;
		try {

			IntegracionStub.GuardarArchivo requestArchivo = new IntegracionStub.GuardarArchivo();
			requestArchivo.setArchivo(archivo.getRawFile());
			requestArchivo.setXml(xml);

			responseArchivo = this.stub.guardarArchivo(requestArchivo);
			//TODO: Dami Falta ver si realmente se guardo bien.. hay que chequear lo que devuelve integracion
			response = OperationResponse.createSuccess();
			
			updateCache();
			
		} catch (AxisFault e) {
			 e.printStackTrace();
			 response = OperationResponse.createFailed("No se pudo guardar el archivo: "+archivo.getNombreArchivo());
		
		} catch (RemoteException e) {
			response = OperationResponse.createFailed("Error de conexion");
		}
		return response;
	}

	public OperationResponse get(String xml) throws ParseException, GetException {
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
			} else if (xml.equals("<WS><recurso><recursoId>15</recursoId></recurso></WS>")) {
				xml_resp_e = "<WS><recurso><recursoId>15</recursoId><ambitoId>2</ambitoId><descripcion>Encuesta con preguntas a completar</descripcion><tipo>Encuesta</tipo></recurso></WS>";
			} else if (xml.equals("<WS><recurso><recursoId>10</recursoId></recurso></WS>")) {
				xml_resp_e = "<WS><recurso><recursoId>10</recursoId><ambitoId>3</ambitoId><descripcion>Encuesta con preguntas fijas</descripcion><tipo>Encuesta</tipo></recurso></WS>";
			} else if (xml.equals("<WS><recurso><recursoId>1003</recursoId></recurso></WS>")) {//TODO: sacar harcodeo para testear los archivos.
				xml_resp_e = "<WS><recurso><recursoId>1003</recursoId><ambitoId>3</ambitoId><descripcion>Es un Archivo</descripcion><tipo>Archivo</tipo></recurso></WS>";
			}
			System.out.println("Esto es lo que me devuelve:");
			System.out.println(xml_resp_e);
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

		return OperationResponse.createFailed("No existe en cache, id:" + recursoId);

	}
	
	protected void createCurrentObject(String xml_resp_e) throws ParseException, GetException {
		Recurso aux = current;
		deserialize(xml_resp_e);
		verifyCurrentObject();
		current.setAmbitoId(aux.getAmbitoId());
		current.setRecursoId(aux.getRecursoId());
		current.setDescripcion(aux.getDescripcion());
		
	}
	
	private void verifyCurrentObject() throws GetException {
		if (current == null) {	
			String message = "Error desconocido current is null ";
			System.out.println(message);
			throw new GetException(message);
		}
		
	}

	public OperationResponse delete(String xml) {
		String reason;
		try {

			EliminarDatos eliminar = new EliminarDatos();
			eliminar.setXml(xml);
			EliminarDatosResponse e_resp = stub.eliminarDatos(eliminar);
			System.out.println(e_resp.get_return());

			// TODO implementar m�todo que chequee las respuestas
			// if (xmlUtil.isResponseOk(g_resp.get_return())) {
			// or.setSuccess(true);
			// or.setReason("algo");
			// }

			deleteFromCache(getCurrent().getRecursoId());

			return OperationResponse.createSuccess();

		} catch (AxisFault e) {
			reason = "Error al intentar eliminar el Recurso, Id: " + getCurrent().getRecursoId();
		} catch (RemoteException e) {
			reason = "Error de conexion remota";
		}
		System.out.println(reason);
		return OperationResponse.createFailed(reason);

	}

	public abstract void deleteFromCache(int recursoId);
	
	protected abstract boolean cacheContains(int recursoId);
	
	protected abstract Recurso retrieveCached(int recursoId);

//	protected abstract void createCurrentObject(String xml_resp_e);
	
	protected abstract void deserialize(String xml_resp_e) throws ParseException;
	

//	protected abstract OperationResponse currentObjetToResponse();
	
	protected abstract String getHandledType();

//	protected abstract void updateCache();
	
//	protected abstract Recurso getCurrent();
	
	protected abstract Parser getParser();
	
	@SuppressWarnings("rawtypes")
	protected abstract Cache getCache();

}
