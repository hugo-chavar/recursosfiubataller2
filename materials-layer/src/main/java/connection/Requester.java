package connection;

import connection.exceptions.GetException;
import connection.responses.OperationResponse;
import connection.responses.RecursosResponse;
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
		return respondidaReq.saveRespondida(respondida);
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
	
	public OperationResponse getRecurso(Recurso target) throws GetException {
		OperationResponse response;
		
		if (notValidInput(target)) {
			return informFailReason(target);
		}
	
		// Busco en el cache de especifico del recurso
		response = getRecursoFromCache(target);
		if (response.getSuccess())
			return response;
		// Si no se encuentra en el cache
		
		// Busco el recurso
		Recurso recurso = recursosReq.get(target);
		if (recurso == null) {
			String reason = "Error al intentar obtener el recurso, ID: " + target.getRecursoId();
			System.out.println(reason);
			response = OperationResponse.createFailed(reason);
			return response;		
		}
		
		// Consulto la tabla especifica del recurso
		response = makeQueryGetRecurso(recurso);
		
		return response;
		
	}
	
	public RecursosResponse getRecursosAmbito(int ambitoId) throws GetException {
		return recursosReq.getAll(ambitoId);
	}
	
	public EncuestaRespondida getEncuestaRespondida(int idEncuesta, int idUsuario) {
		return respondidaReq.getRespondida(idEncuesta, idUsuario);
	}
	
	public OperationResponse deleteRecurso(Recurso recurso) {
		
		if (notValidInput(recurso)) {
			return informFailReason(recurso);
		}
		// Borro el recurso de todos los caches
		if (recurso.getTipo().equalsIgnoreCase("Encuesta")) {
			encuestaReq.deleteFromCache(recurso.getRecursoId());
		} else if (recurso.getTipo().equalsIgnoreCase("Link")) {
			linkReq.deleteFromCache(recurso.getRecursoId());
		} else {
			// TODO: Falta para archivo
			//archivoReq.deleteFromCache(idRecurso);
		}
		
		return recursosReq.delete(recurso.getRecursoId());

	}

	public boolean getPermisoUsuario(Integer ambitoId, Integer usuarioId,String action) {
		// TODO: Yami, falta implementar este metodo
		return true;
	}
	
	private OperationResponse getRecursoFromCache(Recurso recurso) {
		
		OperationResponse response;
		
		if (recurso.getTipo().equalsIgnoreCase("Encuesta")) {
			response = encuestaReq.getFromCache(recurso.getRecursoId());
		} else if (recurso.getTipo().equalsIgnoreCase("Link")) {
			response = linkReq.getFromCache(recurso.getRecursoId());
		} else if (recurso.getTipo().equalsIgnoreCase("Link")) {
			response = archivoReq.getFromCache(recurso.getRecursoId());
		}else {
			
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
			// TODO: Falta para archivo
			
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