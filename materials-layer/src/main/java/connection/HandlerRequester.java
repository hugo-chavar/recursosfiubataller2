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
	public Recurso current;

	protected HandlerRequester() {
		
	}
	
	protected Recurso getCurrent() {
		return current;
	}


	protected OperationResponse save(String xml) {
		OperationResponse response;

		try {
			String xml_resp_e = proxy.guardar(xml);
			System.out.println(xml_resp_e);
			response = OperationResponse.createSuccess();
//			updateCache(); al guardar no se actualiza el cache

		} catch (ConnectionException e) {
			String reason = "Intentando guardar " + getHandledType() + ", Id: " + getCurrent().getRecursoId();
			reason = e.getMessage() + reason;
			System.out.println(reason);
			response = OperationResponse.createFailed(reason);
		}
		return response;
	}

	public OperationResponse getFile(String xml) throws GetException, ParseException {
		try {
			String xml_resp_e = proxy.seleccionar(xml);
			System.out.println("Integracion me esta contestando : " + xml_resp_e);
			createCurrentObject(xml_resp_e);
			updateCache();
		} catch (ConnectionException e) {
			String reason = "Intentando guardar " + getHandledType() + ", Id: " + getCurrent().getRecursoId() +". ";
			reason = reason + e.getMessage();
			System.out.println(reason);
			return OperationResponse.createFailed(reason);
		}
		return currentObjetToResponse();
	}
	
	protected OperationResponse saveFile(String xml){
		DataHandler archivo = ((Archivo) current).getRawFile();
		OperationResponse response;
		try {

			String xml_resp_e = proxy.guardarArchivo(xml, archivo);
			System.out.println("Integracion me esta contestando : " + xml_resp_e);
			//TODO: Dami Falta ver si realmente se guardo bien.. hay que chequear lo que devuelve integracion
			response = OperationResponse.createSuccess();
			
//			updateCache(); al guardar no se actualiza el cache
		} catch (ConnectionException e) {
			String reason = e.getMessage() + ((Archivo) current).getNombreArchivo();
			System.out.println(reason);
			return OperationResponse.createFailed(reason);

		}	
		return response;
	}

	public OperationResponse get(String xml) throws ParseException, GetException {

		try {
			String xml_resp_e =  proxy.seleccionar(xml);
			createCurrentObject(xml_resp_e);

			updateCache();

			return currentObjetToResponse();

		} catch (ConnectionException e) {
			String reason = e.getMessage() + "Intentando obtener " + getHandledType() + ", ID: " + getCurrent().getRecursoId();
			System.out.println(reason);
			return OperationResponse.createFailed(reason);

		}

	}
	

	public OperationResponse delete(String xml) {
		try {

			String xml_resp_e = proxy.eliminar(xml);
			System.out.println(xml_resp_e);

			// TODO (Yami) implementar metodo que chequee las respuestas
			// if (xmlUtil.isResponseOk(g_resp.get_return())) {
			// or.setSuccess(true);
			// or.setReason("algo");
			// }

			deleteIfExists();

			return OperationResponse.createSuccess();
			
		} catch (ConnectionException e) {
			String reason = e.getMessage() + "Intentando obtener " + getHandledType() + ", ID: " + getCurrent().getRecursoId();
			System.out.println(reason);
			return OperationResponse.createFailed(reason);

		}
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
		deleteIfExists();
		getCache().add(getCurrent());
	}

	@SuppressWarnings("unchecked")
	protected void deleteIfExists() {
		if (getCache().contains(getCurrent())) {
			getCache().remove(getCurrent());
		}
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
			String message = "Error desconocido: current is null ";
			System.out.println(message);
			throw new GetException(message);
		}
		
	}
	
	public void deleteRecurso(Recurso recurso) {
		current = recurso;
		deleteIfExists();
		
	}

//	public abstract void deleteFromCache(int recursoId);
	
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
