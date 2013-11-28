package connection;

import model.Encuesta;


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
	
}