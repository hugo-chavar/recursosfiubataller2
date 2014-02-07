package connection;

import connection.exceptions.GetException;
import connection.exceptions.ParseException;
import connection.responses.OperationResponse;
import model.Archivo;
import model.Encuesta;
import model.EncuestaRespondida;
import model.Link;
import model.Recurso;


public enum Requester {
	
	INSTANCE;
	
	private EncuestaRequester encuestaReq;
	private EncuestaRespondidaRequester respondidaReq;
	private ArchivoRequester archivoReq;
	private LinkRequester linkReq;
	private RecursosRequester recursosReq;
	
	
	private Requester() {
		encuestaReq = new EncuestaRequester();
		archivoReq = new ArchivoRequester();
		recursosReq = new RecursosRequester();
		linkReq = new LinkRequester();
		respondidaReq = new EncuestaRespondidaRequester();
	}

	public OperationResponse saveEncuestaRespondida(EncuestaRespondida respondida) {
		return respondidaReq.save(respondida);
	}
	
	public OperationResponse agregarRecurso(Recurso target) {
		
		OperationResponse response;
		
		if (notValidInput(target)) {
			return informFailReason(target);
		}
		
		// Agrego el recurso segun su tipo
		response = makeQueryAddRecurso(target);
		
		// Actualizo el cache de recursos
		recursosReq.updateCache(target);
		
		return response;
		
	}
	
	public OperationResponse getRecurso(Recurso target) {
		OperationResponse response;
		
		if (notValidInput(target)) {
			return informFailReason(target);
		}
	
		// Busco en el cache de especifico del recurso
		response = getRecursoFromCache(target);
		if (response.getSuccess()) {
			return response;
		}
		// Si no se encuentra en el cache
		
		// Busco el recurso
		try {
			response = recursosReq.get(target);
		} catch (GetException e) {
			response = OperationResponse.createFailed(e.getMessage());
		} catch (ParseException e) {
			response = OperationResponse.createFailed(e.getMessage());
		}
		
		if (!response.getSuccess()) {
			return response;		
		}
		
		// Consulto la tabla especifica del recurso
		response = makeQueryGetRecurso((Recurso)response.getSerializable());
		
		return response;
		
	}
	
	public OperationResponse getRecursosAmbito(int ambitoId) {
		//TODO Hugo atrapar aca la excepcion
		return recursosReq.getAll(ambitoId);
	}
	
	public OperationResponse getEncuestaRespondida(int idEncuesta, int idUsuario) {
		return respondidaReq.getRespondida(idEncuesta, idUsuario);
	}
	
	public OperationResponse deleteRecurso(Recurso recurso) {
		
		if (notValidInput(recurso)) {
			return informFailReason(recurso);
		}
		// Borro el recurso de todos los caches
		if (recurso.getTipo().equalsIgnoreCase("Encuesta")) {
			encuestaReq.deleteRecurso(recurso);
		} else if (recurso.getTipo().equalsIgnoreCase("Link")) {
			linkReq.deleteRecurso(recurso);
		} else {
			archivoReq.deleteRecurso(recurso);
		}
		
		return recursosReq.delete(recurso);

	}

	public boolean getPermisoUsuario(Integer ambitoId, Integer usuarioId,String action) {
		return true;
	}
	
	private OperationResponse getRecursoFromCache(Recurso recurso) {
		
		OperationResponse response;
		if (recurso.getTipo().equalsIgnoreCase("Encuesta")) {
			response = encuestaReq.getFromCache(recurso);
		} else if (recurso.getTipo().equalsIgnoreCase("Link")) {
			response = linkReq.getFromCache(recurso);
		} else if (recurso.getTipo().equalsIgnoreCase("Archivo")) {
			response = archivoReq.getFromCache(recurso);
		} else {
			response = OperationResponse.createFailed("No existe cache");
		}
		
		return response;
		
	}
	
	private OperationResponse makeQueryAddRecurso(Recurso recurso) {
		
		OperationResponse response;
		
		if (recurso.getTipo().equalsIgnoreCase("Encuesta")) {
			response = encuestaReq.save((Encuesta)recurso);
		} else if (recurso.getTipo().equalsIgnoreCase("Link")) {
			response = linkReq.save((Link)recurso);
		} else {
			response = archivoReq.save((Archivo)recurso);
		//	response = OperationResponse.createFailed("Tipo de recurso inexistente");
		}
		
		return response;
		
	}
	
	private OperationResponse makeQueryGetRecurso(Recurso recurso) {
		
		OperationResponse response;
		
		if (recurso.getTipo().equalsIgnoreCase("Encuesta")) {
			response = encuestaReq.get(recurso);
		} else if (recurso.getTipo().equalsIgnoreCase("Link")) {
			response = linkReq.get(recurso);
		} else if (recurso.getTipo().equalsIgnoreCase("Archivo")) {
			//System.out.println("Entra a makeQuery de archivo");
			response = archivoReq.get(recurso);
		} else {
			response = OperationResponse.createFailed("Tipo de recurso inexistente");
		}
		
		return response;
		
	}

	private OperationResponse informFailReason(Recurso target) {
		OperationResponse response;
		String reason;
		if (target == null) {
			reason = "falta el elemento 'recurso'";
		} else if (target.getRecursoId() == null) {
			reason = "falta elemento 'recusoId' en el elemento 'recurso'";
		} else if (target.getTipo() == null) {
			reason = "falta elemento 'tipo' en el elemento 'recurso'";
		} else {
			reason = "tipo de recurso inexistente";
		}
		response = OperationResponse.createFailed("Parametros invalidos: " + reason);
		return response;
	}

	private boolean notValidInput(Recurso target) {
		return target == null || target.getRecursoId() == null || target.getTipo() == null || !isvalidType(target.getTipo());
	}
	
	private boolean isvalidType(String type) {
		return type.equalsIgnoreCase("Link")||type.equalsIgnoreCase("Encuesta")||type.equalsIgnoreCase("Archivo");
	}

	public OperationResponse saveArchivo(Archivo file) {
		OperationResponse response;
		response = archivoReq.save(file);
		return response;
	}
	
}