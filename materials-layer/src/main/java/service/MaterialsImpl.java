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
	public String getArchivo(int ambitoId, int recursoId){
		//Archivo file = new Archivo();
		Archivo file = Requester.INSTANCE.getArchivo(ambitoId, recursoId);
		String xmlArchivo = xmlutil.convertToXml(file, Archivo.class);
		return xmlArchivo;
	}
	
	@Override
	public void agregarEncuesta(Encuesta encuesta, int usuarioId) {
		if (Requester.INSTANCE.getPermisoUsuario(encuesta.getAmbitoId(), usuarioId)) {
			encuesta.recuperarDatosVisibles();
			Requester.INSTANCE.saveEncuesta(encuesta);
			System.out.println("Guardando encuesta: " + encuesta.getDescripcion());
		}
		
	//  TODO retornar confirmaciones en xml, hablar con presentacion para ver como lo quieren
	}
	
	@Override
	public String setArchivo(int ambitoId, String name, String ext,
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
	public void agregarLink(Link link, int usuarioId) {
		if (Requester.INSTANCE.getPermisoUsuario(link.getRecursoId(), usuarioId)) {
			Requester.INSTANCE.saveLink(link);
		}
		//  TODO retornar confirmaciones en xml, hablar con presentacion para ver como lo quieren
	}
	
	@Override
	public String getEncuesta(int ambitoId, int recursoId) {
		Encuesta encuesta = Requester.INSTANCE.getEncuesta(ambitoId, recursoId);
		encuesta.completarDatosVisibles();
		String xmlEncuesta = xmlutil.convertToXml(encuesta, Encuesta.class);
		return xmlEncuesta;
	}
	
	@Override
	public void agregarEncuestaRespondida(EncuestaRespondida respondida, int ambitoId) {
		Encuesta encuesta = Requester.INSTANCE.getEncuesta(ambitoId, respondida.getIdRecurso());
		encuesta.completarDatosVisibles();
		respondida.recuperarDatosVisibles(encuesta);
		if (encuesta.isEvaluada()) {
			respondida.evaluar(encuesta);
		}
		Requester.INSTANCE.saveEncuestaRespondida(respondida);
		//  TODO retornar confirmaciones en xml, hablar con presentacion para ver como lo quieren
	}
	
	@Override
	public String  getEncuestaRespondida(int IdAmbiente, int recursoId, int usuarioId) {
		EncuestaRespondida respondida = Requester.INSTANCE.getEncuestaRespondida(IdAmbiente, recursoId, usuarioId);
		Encuesta encuesta= Requester.INSTANCE.getEncuesta(IdAmbiente, recursoId);
		respondida.completarDatosVisibles(encuesta.getPreguntas());
		String xmlRespondida = xmlutil.convertToXml(respondida, EncuestaRespondida.class);
		return xmlRespondida;
	}
	
	@Override
	public List<Recurso> obtenerRecursos(int ambitoId, int usuarioId) {
		List<Recurso> recursos = new ArrayList<Recurso>();
		List<Recurso> recursosPermitidos = new ArrayList<Recurso>();

		// Obtengo los recursos
		recursos = Requester.INSTANCE.getRecursosAmbiente(ambitoId);
		// Chequeo Recursos permitidos
		for (Recurso r:  recursos) {
			if (Requester.INSTANCE.getPermisoUsuario(r.getRecursoId(), usuarioId)) {
				recursosPermitidos.add(r);
			}
		}
		return recursosPermitidos;
	}
	
	@Override
	public boolean borrarRecurso(int ambitoId, int recursoId, int usuarioId) {
		if (Requester.INSTANCE.getPermisoUsuario(recursoId, usuarioId)) {
			Requester.INSTANCE.deleteRecurso(recursoId);
			return true;
		}
		return false;
	}

}
