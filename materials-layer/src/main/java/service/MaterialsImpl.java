package service;

import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
//import javax.activation.FileDataSource;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.soap.MTOM;

import model.Archivo;
import model.Encuesta;
import model.EncuestaRespondida;
import model.Link;
import model.Recurso;
import connection.Requester;
@MTOM 
@WebService(endpointInterface = "service.Materials")

public class MaterialsImpl implements Materials {

	@Override
	public String sayHello(String name) {
		return "Hello, Welcom to jax-ws " + name;
	}

	@Override
	public Archivo getArchivo(int idAmbiente, int idRecurso){
	
		Archivo file =Requester.INSTANCE.getArchivo(idAmbiente, idRecurso);
		return file;
	}
	
	@Override
	public void agregarEncuesta(Encuesta encuesta, int idUsuario) {
		//TODO esto esta mal.. tiene q preguntar los permisos en el Ambiente y no en el recurso
		if (Requester.INSTANCE.getPermisoUsuario(encuesta.getIdRecurso(), idUsuario)) {
			encuesta.recuperarDatosVisibles();
			Requester.INSTANCE.saveEncuesta(encuesta);
			System.out.println("Guardando encuesta: " + encuesta.getDescripcion());
		}
	}
	
	@Override
	public String setArchivo(int idAmbiente, String name,String ext, @XmlMimeType("application/octet-stream") DataHandler data){
		if(data != null){
			Archivo File = new Archivo();
			File.setNombreArchivo(name);
			File.setTipoArchivo(ext);
			File.setRawFile(data);
		//	Requester.INSTANCE.saveArchivo(File); TODO: ACA DEBERIA ANDAR EL SAVE ARCHIVO
			return "Archivo subido Correctamente";
			
		}
//		else{
			return "ERROR al subir el archivo";
//		}
		//return "hola";
	}
	
	@Override
	public void agregarLink(Link link, int idUsuario) {
		if (Requester.INSTANCE.getPermisoUsuario(link.getIdRecurso(), idUsuario)) {
			Requester.INSTANCE.saveLink(link);
		}
	}
	
	@Override
	public Encuesta getEncuesta(int idAmbiente, int idRecurso) {
		Encuesta encuesta = Requester.INSTANCE.getEncuesta(idAmbiente, idRecurso);
		encuesta.completarDatosVisibles();
		return encuesta;
	}
	
	@Override
	public void agregarEncuestaRespondida(EncuestaRespondida respondida, int idAmbiente) {
		Encuesta encuesta = Requester.INSTANCE.getEncuesta(idAmbiente, respondida.getIdRecurso());
		encuesta.completarDatosVisibles();
		respondida.recuperarDatosVisibles(encuesta.getPreguntas());
		if (encuesta.esEvaluada()) {
			respondida.evaluar(encuesta);
		}
		Requester.INSTANCE.saveEncuestaRespondida(respondida);
	}
	
	@Override
	public EncuestaRespondida getEncuestaRespondida(int IdAmbiente, int idRecurso, int idUsuario) {
		EncuestaRespondida respondida = Requester.INSTANCE.getEncuestaRespondida(IdAmbiente, idRecurso, idUsuario);
		Encuesta encuesta= Requester.INSTANCE.getEncuesta(IdAmbiente, idRecurso);
		respondida.completarDatosVisibles(encuesta.getPreguntas());
		return respondida;
	}
	
	@Override
	public List<Recurso> obtenerRecursos(int idAmbiente, int idUsuario) {
		List<Recurso> recursos = new ArrayList<Recurso>();
		List<Recurso> recursosPermitidos = new ArrayList<Recurso>();

		// Obtengo los recursos
		recursos = Requester.INSTANCE.getRecursosAmbiente(idAmbiente);
		// Chequeo Recursos permitidos
		for (Recurso r:  recursos) {
			if (Requester.INSTANCE.getPermisoUsuario(r.getIdRecurso(), idUsuario)) {
				recursosPermitidos.add(r);
			}
		}
		return recursosPermitidos;
	}
	
	@Override 
	public boolean borrarRecurso(int idAmbiente, int idRecurso,int idUsuario){
		if (Requester.INSTANCE.getPermisoUsuario(idRecurso, idUsuario)){
			Requester.INSTANCE.borrarRecurso(idAmbiente,idRecurso);
			//En el caso de las encuestas habria que elimnar las respondidas tambien
			return true;
		}
		return false;
	}

}
