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
import model.XmlUtil;
import connection.Requester;
@MTOM 
@WebService(endpointInterface = "service.Materials")

public class MaterialsImpl implements Materials {

	private XmlUtil xmlutil = new XmlUtil();
	
	@Override
	public String sayHello(String name) {
		return "Hello, Welcom to jax-ws " + name;
	}

	@Override
	public String getArchivo(int idAmbiente, int idRecurso){
		//Archivo file = new Archivo();
		Archivo file = Requester.INSTANCE.getArchivo(idAmbiente, idRecurso);
		String xmlArchivo = xmlutil.convertToXml(file, Archivo.class);
		return xmlArchivo;
	}
	
	@Override
	public void agregarEncuesta(Encuesta encuesta, int idUsuario) {
		if (Requester.INSTANCE.getPermisoUsuario(encuesta.getAmbitoId(), idUsuario)) {
			encuesta.recuperarDatosVisibles();
			Requester.INSTANCE.saveEncuesta(encuesta);
			System.out.println("Guardando encuesta: " + encuesta.getDescripcion());
		}
	}
	
	@Override
	public String setArchivo(int idAmbiente, String name, String ext,
			@XmlMimeType("application/octet-stream") DataHandler data) {
		if (data != null) {
			Archivo File = new Archivo();
			File.setNombreArchivo(name);
			File.setTipoArchivo(ext);
			File.setRawFile(data);
			// Requester.INSTANCE.saveArchivo(File); TODO: ACA DEBERIA ANDAR EL
			// SAVE ARCHIVO
			return "Archivo subido Correctamente";

		}
		//  TODO retornar confirmaciones en xml, hablar con presentacion para ver como lo quieren
		return "ERROR al subir el archivo";
	}
	
	@Override
	public void agregarLink(Link link, int idUsuario) {
		if (Requester.INSTANCE.getPermisoUsuario(link.getRecursoId(), idUsuario)) {
			Requester.INSTANCE.saveLink(link);
		}
		//  TODO retornar confirmaciones en xml, hablar con presentacion para ver como lo quieren
	}
	
	@Override
	public String getEncuesta(int idAmbiente, int idRecurso) {
		Encuesta encuesta = Requester.INSTANCE.getEncuesta(idAmbiente, idRecurso);
		encuesta.completarDatosVisibles();
		String xmlEncuesta = xmlutil.convertToXml(encuesta, Encuesta.class);
		return xmlEncuesta;
	}
	
	@Override
	public void agregarEncuestaRespondida(EncuestaRespondida respondida, int idAmbiente) {
		Encuesta encuesta = Requester.INSTANCE.getEncuesta(idAmbiente, respondida.getIdRecurso());
		encuesta.completarDatosVisibles();
		respondida.recuperarDatosVisibles(encuesta);
		if (encuesta.isEvaluada()) {
			respondida.evaluar(encuesta);
		}
		Requester.INSTANCE.saveEncuestaRespondida(respondida);
		//  TODO retornar confirmaciones en xml, hablar con presentacion para ver como lo quieren
	}
	
	@Override
	public String  getEncuestaRespondida(int IdAmbiente, int idRecurso, int idUsuario) {
		EncuestaRespondida respondida = Requester.INSTANCE.getEncuestaRespondida(IdAmbiente, idRecurso, idUsuario);
		Encuesta encuesta= Requester.INSTANCE.getEncuesta(IdAmbiente, idRecurso);
		respondida.completarDatosVisibles(encuesta.getPreguntas());
		String xmlRespondida = xmlutil.convertToXml(respondida, EncuestaRespondida.class);
		return xmlRespondida;
	}
	
	@Override
	public List<Recurso> obtenerRecursos(int idAmbiente, int idUsuario) {
		List<Recurso> recursos = new ArrayList<Recurso>();
		List<Recurso> recursosPermitidos = new ArrayList<Recurso>();

		// Obtengo los recursos
		recursos = Requester.INSTANCE.getRecursosAmbiente(idAmbiente);
		// Chequeo Recursos permitidos
		for (Recurso r:  recursos) {
			if (Requester.INSTANCE.getPermisoUsuario(r.getRecursoId(), idUsuario)) {
				recursosPermitidos.add(r);
			}
		}
		return recursosPermitidos;
	}
	
	@Override
	public boolean borrarRecurso(int idAmbiente, int idRecurso, int idUsuario) {
		if (Requester.INSTANCE.getPermisoUsuario(idRecurso, idUsuario)) {
			Requester.INSTANCE.deleteRecurso(idRecurso);
			return true;
		}
		return false;
	}

}
