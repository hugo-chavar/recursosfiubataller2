package connection;

import model.Encuesta;
import model.EncuestaRespondida;
import model.Link;


public enum Requester {
	
	INSTANCE;
	
	private EncuestaRequester encuestaReq;
	private LinkRequester linkReq;
	
	
	private Requester() {
		System.out.println("Creando EncuestaRequester");
		this.encuestaReq = new EncuestaRequester();
		System.out.println("EncuestaRequester listo");
	}
	
	public void saveEncuesta(Encuesta encuesta) {
		System.out.println("Guardando una encuesta..");
		this.encuestaReq.save(encuesta);
		System.out.println("Fin del proceso Grabar Encuesta..");
	}
	
	public Encuesta getEncuesta(int IDAmbiente, int IDEncuesta) {
		return this.encuestaReq.get(IDAmbiente, IDEncuesta);
	}

	public void saveEncuestaRespondida(EncuestaRespondida respondida) {
		this.encuestaReq.saveRespondida(respondida);
	}

	public EncuestaRespondida getEncuestaRespondida(int idAmbiente,int idRecurso, int idUsuario) {
		return null;
	}
	
	public void saveLink(Link link) {
		this.linkReq.save(link);
	}
	
	public Link getLink(int IDAmbiente, int IDLink) {
		return this.linkReq.get(IDAmbiente, IDLink);
	}
	
}