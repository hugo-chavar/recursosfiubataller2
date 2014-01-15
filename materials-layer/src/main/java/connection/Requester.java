package connection;

import java.util.List;

import connection.exceptions.GetException;
import connection.responses.OperationResponse;
import model.Archivo;
import model.Encuesta;
import model.EncuestaRespondida;
import model.Link;
import model.Recurso;


public enum Requester {
	
	INSTANCE;
	
	private EncuestaRequester encuestaReq;
	private ArchivoRequester archivoReq;
	private LinkRequester linkReq;
	private RecursosRequester recursosReq;
	
	
	private Requester() {
		encuestaReq = new EncuestaRequester();
		archivoReq = new ArchivoRequester();
		recursosReq = new RecursosRequester();
		linkReq = new LinkRequester();
	}
	
	public OperationResponse saveEncuesta(Encuesta encuesta) {
		return encuestaReq.save(encuesta);
	}

	public OperationResponse saveEncuestaRespondida(EncuestaRespondida respondida) {
		return encuestaReq.saveRespondida(respondida);
	}
	
	public void saveFile(Archivo archivo){
		archivoReq.save(archivo);
	}
	
	public OperationResponse saveLink(Link link) {
		return linkReq.save(link);
	}
	
	public OperationResponse getRecurso(Recurso target) {

		OperationResponse response;
		if (notValidInput(target)) {
			return informFailReason(target);
		}
		
		// Busco en el cache de especifico del recurso.
		response = getRecursoFromCache(target);
		if (response.getSuccess())
			return response;
		
		// Si no se encuentra en el cache.
		
		// Busco el recurso.
		Recurso recurso = recursosReq.get(target);
		if (recurso == null) {
			String reason = "Error al intentar obtener el recurso, ID: " + target.getRecursoId();
			System.out.println(reason);
			response = OperationResponse.createFailed(reason);
			return response;		
		}
		
		// Consulto la tabla especifica del recurso.
		response = makeQueryRecurso(recurso);
		
		return response;
		
	}

	private OperationResponse informFailReason(Recurso target) {
		OperationResponse response;
		String reason;
		if (target == null) {
			reason = "falta el elemento 'recurso'";
		} else if (target.getRecursoId() == null) {
			reason = "falta elemento recusoId en el elemento 'recurso'";
		} else if (target.getTipo() == null) {
			reason = "falta elemento tipo en el elemento 'recurso'";
		} else {
			reason = "tipo de recurso inexistente";
		}
		response = OperationResponse.createFailed("Parametros invalidos: " + reason);
		return response;
	}

	private boolean notValidInput(Recurso target) {
		return target == null || target.getRecursoId() == null || target.getTipo() == null || !isvalidType(target.getTipo());
	}

	public List<Recurso> getRecursosAmbito(int ambitoId) throws GetException {
		return recursosReq.getAll(ambitoId);
	}

	public EncuestaRespondida getEncuestaRespondida(int idAmbito, int idEncuesta, int idUsuario) {
		return encuestaReq.getRespondida(idAmbito, idUsuario, idEncuesta);
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

	public boolean getPermisoUsuario(Integer recursoId, int usuarioId) {
		// TODO: Yami, falta implementar este metodo
		return true;
	}
	
	private OperationResponse getRecursoFromCache(Recurso recurso) {
		
		OperationResponse response;
		
		if (recurso.getTipo().equalsIgnoreCase("Encuesta")) {
			response = encuestaReq.getFromCache(recurso.getRecursoId());
		} else if (recurso.getTipo().equalsIgnoreCase("Link")) {
			response = linkReq.getFromCache(recurso.getRecursoId());
		} else {
			// TODO: Falta para archivo
			//response = archivoReq.getFromCache(recursoId);
			response = null; // Sacar esto
			
		}
		
		return response;
		
	}
	
	private OperationResponse makeQueryRecurso(Recurso recurso) {
		
		OperationResponse response;
		
		if (recurso.getTipo().equalsIgnoreCase("Encuesta")) {
			response = encuestaReq.get(recurso);
		} else if (recurso.getTipo().equalsIgnoreCase("Link")) {
			response = linkReq.get(recurso);
		} else {
			// TODO: Falta para archivo
			//response = archivoReq.get(recurso);
//			response = null; // Sacar esto
			response = OperationResponse.createFailed("Tipo de recurso inexistente");
		}
		
		return response;
		
	}
	
	private boolean isvalidType(String type) {
		return type.equalsIgnoreCase("Link")||type.equalsIgnoreCase("Encuesta")||type.equalsIgnoreCase("Archivo");
	}
	
}