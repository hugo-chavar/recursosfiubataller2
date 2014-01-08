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
	}
	
	public void saveEncuesta(Encuesta encuesta) {
		encuestaReq.save(encuesta);
	}
	
	public Encuesta getEncuesta(int idAmbito, int idEncuesta) {
		return encuestaReq.get(idAmbito, idEncuesta);
	}

	public void saveEncuestaRespondida(EncuestaRespondida respondida) {
		encuestaReq.saveRespondida(respondida);
	}

	public EncuestaRespondida getEncuestaRespondida(int idAmbito, int idEncuesta, int idUsuario) {
		return encuestaReq.getRespondida(idAmbito, idUsuario, idEncuesta);
	}
	
	public void saveFile(Archivo archivo){
		archivoReq.save(archivo);
	}
	
	public Archivo getArchivo(int idAmbito, int idArchivo){
		return archivoReq.getArchivo(idAmbito, idArchivo);
	}
	
	public OperationResponse saveLink(Link link) {
		return linkReq.save(link);
	}
	
	public OperationResponse getLink(int idAmbito, int IDLink) {
		Recurso recurso = recursosReq.getCached(IDLink);
		return linkReq.get(recurso);
//		return linkReq.get(idAmbito, IDLink);
	}
	
	public OperationResponse deleteRecurso(int idRecurso) {
		Recurso recurso = recursosReq.getCached(idRecurso);
		
		// borro el recurso de todos los caches
		if ("Link".equals(recurso.getTipo())) {
			linkReq.delete(idRecurso);
		} else if ("Encuesta".equals(recurso.getTipo())) {
			// encuestaReq.delete(IDRecurso);
		} else {
			// archivoReq.delete(IDRecurso);
		}
		return recursosReq.delete(idRecurso);

	}

	public List<Recurso> getRecursosAmbito(int idAmbito) throws GetException {
		return recursosReq.get(idAmbito);
	}

	public boolean getPermisoUsuario(Integer idRecurso, int idUsuario) {
		// TODO: Yami, falta implementar este metodo
		return true;
	}
	
}