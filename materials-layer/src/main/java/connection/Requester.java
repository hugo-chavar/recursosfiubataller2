package connection;

import java.util.List;

import model.Encuesta;
import model.EncuestaRespondida;
import model.Link;
import model.Recurso;


public enum Requester {
	
	INSTANCE;
	
	private EncuestaRequester encuestaReq;
	private LinkRequester linkReq;
	
	
	private Requester() {
		System.out.println("Creando EncuestaRequester");
		encuestaReq = new EncuestaRequester();
		//archivoReq = new Ar
		System.out.println("EncuestaRequester listo");
	}
	
	public void saveEncuesta(Encuesta encuesta) {
		System.out.println("Guardando una encuesta..");
		encuestaReq.save(encuesta);
		System.out.println("Fin del proceso Grabar Encuesta..");
	}
	
	public Encuesta getEncuesta(int IDAmbiente, int IDEncuesta) {
		return encuestaReq.get(IDAmbiente, IDEncuesta);
	}

	public void saveEncuestaRespondida(EncuestaRespondida respondida) {
		encuestaReq.saveRespondida(respondida);
	}

	public EncuestaRespondida getEncuestaRespondida(int idAmbiente,int idRecurso, int idUsuario) {
		return null;
	}
	
	public void saveLink(Link link) {
		linkReq.save(link);
	}
	
	public Link getLink(int IDAmbiente, int IDLink) {
		return linkReq.get(IDAmbiente, IDLink);
	}

	public List<Recurso> getRecursosAmbiente(int idAmbiente) {
		// TODO:Yami, falta implementar este metodo
		return null;
	}

	public boolean getPermisoUsuario(Integer idRecurso, int idUsuario) {
		// TODO:Yami, falta implementar este metodo
		return true;
	}
	
}