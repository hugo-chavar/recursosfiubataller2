package connection;

import model.Archivo;
import model.EncuestaRespondida;
import model.Recurso;
import connection.responses.OperationResponse;


public enum Requester {
	
	INSTANCE;
	
	private EncuestaRequester encuestaRequester;
	private EncuestaRespondidaRequester respondidaRequester;
	private ArchivoRequester archivoRequester;
	private LinkRequester linkRequester;
	private RecursosRequester recursosRequester;
	
	
	private Requester() {
		encuestaRequester = new EncuestaRequester();
		archivoRequester = new ArchivoRequester();
		recursosRequester = new RecursosRequester();
		linkRequester = new LinkRequester();
		respondidaRequester = new EncuestaRespondidaRequester();
	}

	public OperationResponse saveEncuestaRespondida(EncuestaRespondida respondida) {
		return respondidaRequester.save(respondida,"E");
	}
	
	public OperationResponse agregarRecurso(Recurso target) {
		
		OperationResponse response;
		
		if (notValidInput(target)) {
			return informFailReason(target);
		}
		
		// Agrego el recurso segun su tipo
		response = makeQueryAddRecurso(target);
		
		// Actualizo el cache de recursos
		recursosRequester.updateCache(target);
		
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
		response = recursosRequester.get(target);
		
		if (!response.getSuccess()) {
			return response;		
		}
		
		// Consulto la tabla especifica del recurso
		response = makeQueryGetRecurso((Recurso)response.getSerializable());
		return response;
		
	}
	
	public OperationResponse getRecursosAmbito(int ambitoId) {
		return recursosRequester.getAll(ambitoId);
	}
	
	public OperationResponse getEncuestaRespondida(int idEncuesta, int idUsuario) {
		return respondidaRequester.get(idEncuesta, idUsuario);
	}
	
	public OperationResponse deleteRecurso(Recurso recurso) {
		
		if (notValidInput(recurso)) {
			return informFailReason(recurso);
		}
		// Borro el recurso de todos los caches
		if (recurso.getTipo().equalsIgnoreCase("Encuesta")) {
			encuestaRequester.deleteRecurso(recurso);
		} else if (recurso.getTipo().equalsIgnoreCase("Link")) {
			linkRequester.deleteRecurso(recurso);
		} else {
			archivoRequester.deleteRecurso(recurso);
		}
		
		return recursosRequester.delete(recurso);

	}

	public boolean getPermisoUsuario(Integer ambitoId, Integer usuarioId,String action) {
		return true;
	}
	
	private OperationResponse getRecursoFromCache(Recurso recurso) {
		
		OperationResponse response;
		if (recurso.getTipo().equalsIgnoreCase("Encuesta")) {
			response = encuestaRequester.getFromCache(recurso);
		} else if (recurso.getTipo().equalsIgnoreCase("Link")) {
			response = linkRequester.getFromCache(recurso);
		} else if (recurso.getTipo().equalsIgnoreCase("Archivo")) {
			response = archivoRequester.getFromCache(recurso);
		} else {
			response = OperationResponse.createFailed("No existe cache");
		}
		
		return response;
		
	}
	
	private OperationResponse makeQueryAddRecurso(Recurso recurso) {
		
		OperationResponse response;
		
		if (recurso.getTipo().equalsIgnoreCase("Encuesta")) {
			response = encuestaRequester.save(recurso,"E");
		} else if (recurso.getTipo().equalsIgnoreCase("Link")) {
			response = linkRequester.save(recurso,"L");
		} else {
			response = archivoRequester.save((Archivo)recurso);
		//	response = OperationResponse.createFailed("Tipo de recurso inexistente");
		}
		
		return response;
		
	}
	
	private OperationResponse makeQueryGetRecurso(Recurso recurso) {
		
		OperationResponse response;
		
		if (recurso.getTipo().equalsIgnoreCase("Encuesta")) {
			response = encuestaRequester.get(recurso);
		} else if (recurso.getTipo().equalsIgnoreCase("Link")) {
			response = linkRequester.get(recurso);
		} else if (recurso.getTipo().equalsIgnoreCase("Archivo")) {
			//System.out.println("Entra a makeQuery de archivo");
			response = archivoRequester.get(recurso);
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
		
		response = archivoRequester.save(file);
		return response;
	}
	
}