package connection;

import java.util.ArrayList;
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
		encuestaReq = new EncuestaRequester();
		//archivoReq = new Ar
	}
	
	public void saveEncuesta(Encuesta encuesta) {
		encuestaReq.save(encuesta);
	}
	
	public Encuesta getEncuesta(int IDAmbiente, int IDEncuesta) {
		return encuestaReq.get(IDAmbiente, IDEncuesta);
	}

	public void saveEncuestaRespondida(EncuestaRespondida respondida) {
		encuestaReq.saveRespondida(respondida);
	}

	public EncuestaRespondida getEncuestaRespondida(int IDAmbiente, int IDEncuesta, int IDUsuario) {
		return encuestaReq.getRespondida(IDAmbiente, IDUsuario, IDEncuesta);
	}
	
	public void saveLink(Link link) {
		linkReq.save(link);
	}
	
	public Link getLink(int IDAmbiente, int IDLink) {
		return linkReq.get(IDAmbiente, IDLink);
	}

	public List<Recurso> getRecursosAmbiente(int IDAmbiente) {
		List<Recurso> recursos = new ArrayList<Recurso>();
		// TODO Yami.. tiene q ser solo de recursos
		recursos.addAll(encuestaReq.getAll(IDAmbiente));
		recursos.addAll(linkReq.getAll(IDAmbiente));
		// TODO: Falta agregar archivos.
		return null;
	}

	public boolean getPermisoUsuario(Integer idRecurso, int idUsuario) {
		// TODO: Yami, falta implementar este metodo
		return true;
	}

	public void borrarRecurso(int idAmbiente, int idRecurso) {
		// TODO: Yami, falta implementar este metodo
	}
	
}