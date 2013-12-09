package connection;

import model.Encuesta;
import model.EncuestaRespondida;
import model.Link;


public enum Requester {
	
	INSTANCE;
	
	private EncuestaRequester encuestaReq;
	private LinkRequester linkReq;
	
	
	private Requester() {
		this.encuestaReq = new EncuestaRequester();
	}
	
	public void saveEncuesta(Encuesta encuesta) {
		this.encuestaReq.save(encuesta);
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