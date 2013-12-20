package connection;

import java.util.List;

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
	
	public Encuesta getEncuesta(int IDAmbito, int IDEncuesta) {
		return encuestaReq.get(IDAmbito, IDEncuesta);
	}

	public void saveEncuestaRespondida(EncuestaRespondida respondida) {
		encuestaReq.saveRespondida(respondida);
	}

	public EncuestaRespondida getEncuestaRespondida(int IDAmbito, int IDEncuesta, int IDUsuario) {
		return encuestaReq.getRespondida(IDAmbito, IDUsuario, IDEncuesta);
	}
	
	public void saveFile(Archivo archivo){
		archivoReq.save(archivo);
	}
	
	public Archivo getArchivo(int IDAmbito, int IDArchivo){
		return archivoReq.getArchivo(IDAmbito, IDArchivo);
	}
	
	public void saveLink(Link link) {
		linkReq.save(link);
	}
	
	public Link getLink(int IDAmbito, int IDLink) {
		return linkReq.get(IDAmbito, IDLink);
	}
	
	public void deleteRecurso(int IDRecurso) {
		recursosReq.delete(IDRecurso);
	}

	public List<Recurso> getRecursosAmbiente(int IDAmbito) {
		return recursosReq.get(IDAmbito);
	}

	public boolean getPermisoUsuario(Integer idRecurso, int idUsuario) {
		// TODO: Yami, falta implementar este metodo
		return true;
	}
	
}