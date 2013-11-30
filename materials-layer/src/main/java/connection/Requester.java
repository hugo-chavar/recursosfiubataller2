package connection;

import model.Encuesta;
import model.EncuestaRespondida;


public enum Requester {
	
	INSTANCE;
	
	private EncuestaRequester encuestaReq;
	
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
		// TODO Auto-generated method stub
		
	}

	public EncuestaRespondida getEncuestaRespondida(int idAmbiente,int idRecurso, int idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}
	
}