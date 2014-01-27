package connection;

import javax.activation.DataHandler;

import model.Archivo;
import model.Recurso;
import connection.cache.Cache;
import connection.exceptions.ConnectionException;
import connection.exceptions.GetException;
import connection.exceptions.ParseException;
import connection.responses.OperationResponse;

public abstract class HandlerRequester {

	protected IntegracionProxy proxy = new IntegracionProxy();
	protected Serializable current;
	private Notification notification;

	protected HandlerRequester() {
		
	}
	
	protected Serializable getCurrent() {
		return current;
	}

	protected OperationResponse save(String xml) throws GetException {

		try {
			String xml_resp_e = proxy.guardar(xml);
//			System.out.println(xml_resp_e);
			return validateOneWayOperation(xml_resp_e);
//			updateCache(); al guardar no se actualiza el cache

		} catch (ConnectionException e) {
			String reason = "Intentando guardar " + getCurrent().getInfo();
			reason = e.getMessage() + reason;
			System.out.println(reason);
			return OperationResponse.createFailed(reason);
		}
	}

	protected OperationResponse validateOneWayOperation(String xml_resp) throws GetException {
		notification = (Notification) getParser().unmarshal(xml_resp, Notification.class);
		if (notification != null && notification.success()) {
			return OperationResponse.createSuccess();
		} else {
			String[] lines = xml_resp.split("\n");
			String message = notification != null ? notification.getMessage() : "notif error " + lines[0];
			return OperationResponse.createFailed(message);
		}
	}
	
	protected boolean validateTwoWayOperation(String xml_resp) throws GetException {
		notification = (Notification) getParser().unmarshal(xml_resp, Notification.class);
		if (notification != null && !notification.success()) {
			String[] lines = xml_resp.split(System.getProperty("line.separator"));
			String message = notification != null ? notification.getMessage() : "Notif error: " + lines[0];
			throw new GetException(message + " para " + getCurrent().getInfo());
		} 
		return notification == null;
	}

	public OperationResponse getFile(String xml) throws GetException, ParseException {
		try {
			String xml_resp_e = proxy.seleccionar(xml);
//			System.out.println("Integracion me esta contestando : " + xml_resp_e);
			if (validateTwoWayOperation(xml_resp_e)) {
				createCurrentObject(xml_resp_e);
				updateCache();
				return currentObjetToResponse();
			}
			return OperationResponse.createFailed("Respuesta inesperada: " + notification.getInfo()); 
			
		} catch (ConnectionException e) {
			String reason = "Intentando guardar " +  getCurrent().getInfo() +". ";
			reason = reason + e.getMessage();
			return OperationResponse.createFailed(reason);
		}
		
	}
	
	protected OperationResponse saveFile(String xml) throws GetException {
		DataHandler archivo = ((Archivo) current).getRawFile();
		try {

			String xml_resp_e = proxy.guardarArchivo(xml, archivo);
//			System.out.println("Integracion me esta contestando : " + xml_resp_e);
			return validateOneWayOperation(xml_resp_e);
			
//			updateCache(); al guardar no se actualiza el cache
		} catch (ConnectionException e) {
			String reason = e.getMessage() + getCurrent().getInfo();
			return OperationResponse.createFailed(reason);

		}	
	}

	public OperationResponse get(String xml) throws ParseException, GetException {

		try {
			String xml_resp_e =  proxy.seleccionar(xml);
			
			if (validateTwoWayOperation(xml_resp_e)) {
				createCurrentObject(xml_resp_e);
				updateCache();
				return currentObjetToResponse();
			}
			return OperationResponse.createFailed("Respuesta inesperada: " + notification.getInfo());
			
		} catch (ConnectionException e) {
			String reason = e.getMessage() + "Intentando obtener " + getCurrent().getInfo();
			return OperationResponse.createFailed(reason);

		}
	}

	public OperationResponse delete(String xml) throws GetException {
		try {

			String xml_resp_e = proxy.eliminar(xml);
//			System.out.println(xml_resp_e);
			deleteIfExists();

			return validateOneWayOperation(xml_resp_e);
			
		} catch (ConnectionException e) {
			String reason = e.getMessage() + "Intentando borrar " + getCurrent().getInfo();
			return OperationResponse.createFailed(reason);

		}
	}

	protected OperationResponse currentObjetToResponse() {
		OperationResponse response;
		if (getCurrent() == null) {
			response = OperationResponse.createFailed("NullPointer: Error al obtener " + getHandledType());
		} else {
			response = OperationResponse.createSuccess();
			response.setSerializable(getCurrent());
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	protected void updateCache() {
		deleteIfExists();
		getCache().add(getCurrent());
	}

	@SuppressWarnings("unchecked")
	protected void deleteIfExists() {
		if (getCache().contains(getCurrent())) {
			getCache().remove(getCurrent());
		}
	}
	
	public OperationResponse getFromCache(int id) {

		OperationResponse response;
		if (cacheContains(id)) {
			response = OperationResponse.createSuccess();
			response.setSerializable(retrieveCached(id));
			return response;
		}

		return OperationResponse.createFailed("No existe en cache, id:" + id);

	}
	
	private void createCurrentObject(String xml_resp_e) throws ParseException, GetException {
		Serializable aux = current;
		
		deserialize(xml_resp_e);
		verifyCurrentObject();
		current.updateFields(aux);
		
	}

	private void verifyCurrentObject() throws GetException {
		if (current == null) {	
			String message = "Error desconocido: current is null ";
			throw new GetException(message);
		}
		
	}
	
	public void deleteRecurso(Serializable s) {
		current = s;
		deleteIfExists();
		
	}
	
	protected abstract boolean cacheContains(int id);
	
	protected abstract Recurso retrieveCached(int id);

	protected abstract void deserialize(String xml_resp_e) throws ParseException;
	
	protected abstract String getHandledType();
	
	protected abstract Parser getParser();
	
	@SuppressWarnings("rawtypes")
	protected abstract Cache getCache();

}
