package connection;

import javax.activation.DataHandler;

import model.Archivo;

import com.ws.services.IntegracionStub;
import com.ws.services.IntegracionStub.SeleccionarArchivoMetadata;

import connection.cache.Cache;
import connection.exceptions.ConnectionException;
import connection.exceptions.GetException;
import connection.exceptions.ParseException;
import connection.parsers.Parser;
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
	protected int createRecurso(String descripcion,String tipo, int id){
		
		try {
			String aGuardar  = "<WS><Recurso><descripcion>"+descripcion+"</descripcion><tipo>"+tipo+"</tipo><ambitoId>"+id+"</ambitoId></Recurso></WS>";
			System.out.println("aGuardar "+aGuardar);
			String xml_recurso = proxy.guardar(aGuardar);
			System.out.println(xml_recurso);
			notification = (Notification) getParser().unmarshal(xml_recurso, Notification.class);
			if(notification.success())
				return notification.getDatos();
			return -1;
		} catch (ConnectionException e) {
			return -1;
		}
	}
	protected OperationResponse save(Serializable serializable, String tipo) {
		getParser().setSaveMode();
		current = serializable;
		int id = createRecurso(((model.Recurso)current).getDescripcion(), tipo, (int) ((model.Recurso)current).getAmbitoId());
		if(id==-1)
			return OperationResponse.createFailed("MATERIALS: No se ha podido crear un Nuevo Recurso");
		((model.Recurso)serializable).setRecursoId(id); //TODO: SI ACA MANDO EL ID A ESE RECURSO PODEMOS GUARDAR CUALQUIER COSA
		
		String xml = getParser().serialize(serializable);

		try {
			System.out.println("DAMI: Se va a mandar a integracion: "+xml);
			String xml_resp_e = proxy.guardar(xml);
			return validateOneWayOperation(xml_resp_e);
//			updateCache(); al guardar no se actualiza el cache?

		} catch (GetException e) {
			return OperationResponse.createFailed(e.getMessage());
		} catch (ConnectionException e) {
			String reason = "Intentando guardar " + getInfo();
			reason = e.getMessage() + reason;
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
			throw new GetException(message + " para " + getInfo());
		} 
		return notification == null;
	}

	protected String getInfo() {
		return getCurrent().getInfo();
	}

	public OperationResponse getFile(String xml) throws GetException, ParseException {
		try {
			System.out.println("se intenta traer"+xml);
			String file = proxy.seleccionarArchivoMetadata(xml);
			System.out.println("Eso me devuelve"+file);
			DataHandler contenido  = proxy.seleccionarArchivo(xml);
//		
			createCurrentObject(file);
			((Archivo)current).setRawFile(contenido);
			return currentObjetToResponse(); 
			
		} catch (ConnectionException e) {
			String reason = "Intentando obtener " +  getInfo() +". ";
			reason = reason + e.getMessage();
			return OperationResponse.createFailed(reason);
		}
		
	}


	public OperationResponse get(String xml) {

		try {
			String xml_resp_e =  proxy.seleccionar(xml);
			if (validateTwoWayOperation(xml_resp_e)) {
				createCurrentObject(xml_resp_e);
				updateCache();
				return currentObjetToResponse();
			}
			return OperationResponse.createFailed("Respuesta inesperada: " + notification.getMessage());
			
		} catch (GetException e) {
			return OperationResponse.createFailed(e.getMessage());
		} catch (ParseException e) {
			return OperationResponse.createFailed(e.getMessage());
		} catch (ConnectionException e) {
			String reason = e.getMessage() + "Intentando obtener " + getInfo();
			return OperationResponse.createFailed(reason);

		}
	}

	public OperationResponse delete(String xml) {
		try {
			String xml_resp_e = proxy.eliminar(xml);
			deleteFromCache();
			return validateOneWayOperation(xml_resp_e);
		} catch (GetException e) {
			return OperationResponse.createFailed(e.getMessage());
		} catch (ConnectionException e) {
			String reason = e.getMessage() + "Intentando borrar " + getInfo();
			return OperationResponse.createFailed(reason);
		}
	}

	protected OperationResponse currentObjetToResponse() {
		OperationResponse response;
		if (currentIsInvalid()) {
			response = OperationResponse.createFailed("NullPointer: Error al obtener " + getInfo());
		} else {
			response = createResponse();
		}
		return response;
	}

	protected boolean currentIsInvalid() {
		return getCurrent() == null;
	}

	protected OperationResponse createResponse() {
		OperationResponse response;
		response = OperationResponse.createSuccess();
		response.setSerializable(getCurrent());
		return response;
	}
	
	@SuppressWarnings("unchecked")
	protected void updateCache() {
		getCache().add(getCurrent());
	}

	@SuppressWarnings("unchecked")
	protected void deleteFromCache() {
		getCache().remove(getCurrent());
	}
	
	@SuppressWarnings("unchecked")
	public OperationResponse getFromCache() {

		OperationResponse response;
		if (getCache().contains(getCurrent())) {
			response = OperationResponse.createSuccess();
			response.setSerializable((Serializable) getCache().get(getCurrent()));
			return response;
		}

		return OperationResponse.createFailed("No existe en cache:" + getInfo());

	}
	
	protected void createCurrentObject(String xml_resp_e) throws ParseException, GetException {
		Serializable aux = current;
		deserialize(xml_resp_e);
		verifyCurrentObject();
		current.updateFields(aux);
		
	}
	
	protected void verifyCurrentObject() throws GetException {
		if (currentIsInvalid()) {	
			String message = "Error desconocido: current is null ";
			throw new GetException(message);
		}
		
	}
	
	public void deleteRecurso(Serializable serializable) {
		current = serializable;
		deleteFromCache();
		
	}
	
	public OperationResponse getFromCache(Serializable serializable) {
		current = serializable;
		return getFromCache();
	}
	
	protected void deserialize(String xml_resp_e) throws ParseException {
		current = getParser().deserialize(xml_resp_e);
	}
	
	protected abstract Parser getParser();
	
	@SuppressWarnings("rawtypes")
	protected abstract Cache getCache();

}
