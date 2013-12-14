package service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import model.Archivo;
import model.Encuesta;
import model.EncuestaRespondida;
import model.Link;
import model.Recurso;
import connection.Requester;

// @MTOM
@WebService(endpointInterface = "service.Materials")
public class MaterialsImpl implements Materials {

	@Override
	public String sayHello(String name) {
		return "Hello, Welcom to jax-ws " + name;
	}

//	@Override
//	public Archivo getArchivo(int idAmbiente, int idRecurso){
//		Archivo file = new Archivo();
//		//TODO: llamar al web service de persistencia que me devuelva los datos de los archivos
//		return file;
//	}
	
	@Override
	public void agregarEncuesta(Encuesta encuesta,int idUsuario) {
		if(Requester.INSTANCE.getPermisoUsuario(encuesta.getIdRecurso(),idUsuario)){
			Requester.INSTANCE.saveEncuesta(encuesta);
			System.out.println("Guardando encuesta: " + encuesta.getDescripcion());
		}		
	}
	
//	@Override
//	public String setArchivo(int idAmbiente, String name,String ext, File file){
//		if(file != null){
//			Archivo File = new Archivo();
//			File.setNombreArchivo(name);
//			File.setTipoArchivo(ext);
//			File.setRawFile(new DataHandler(new FileDataSource(file)));
//	        //TODO: llamar a la capa de integracion para que guarde el archivo
//			return "Archivo subido Correctamente";
//			
//		}
//		else{
//			return "ERROR al subir el archivo";
//		}
//	}
	
	@Override
	public void agregarLink(Link link,int idUsuario){
		if(Requester.INSTANCE.getPermisoUsuario(link.getIdRecurso(),idUsuario)){
			Requester.INSTANCE.saveLink(link);
		}
		//TODO:link.setId(pedirIdLinkBd());
	}
	
	@Override 
	public Encuesta getEncuesta(int idAmbiente, int idRecurso){
		Encuesta encuesta= Requester.INSTANCE.getEncuesta(idAmbiente,idRecurso);
		return encuesta;
	}
	
	@Override
	public void agregarEncuestaRespondida(EncuestaRespondida respondida,int idAmbiente){
		Encuesta encuesta= Requester.INSTANCE.getEncuesta(idAmbiente,respondida.getIdRecurso());
		if(encuesta.esEvaluada()){
			respondida.evaluar(encuesta);
		}
		 Requester.INSTANCE.saveEncuestaRespondida(respondida);
	}
	
	@Override
	public EncuestaRespondida getEncuestaRespondida(int IdAmbiente, int idRecurso, int idUsuario){
		EncuestaRespondida respondida= Requester.INSTANCE.getEncuestaRespondida(IdAmbiente,idRecurso,idUsuario);
		return respondida;
	}
	
	@Override
	public List<Recurso> obtenerRecursos(int idAmbiente, int idUsuario) {
		List<Recurso> recursos = new ArrayList<Recurso>();
		List<Recurso> recursosPermitidos = new ArrayList<Recurso>();

		// Obtengo los recursos
		recursos=Requester.INSTANCE.getRecursosAmbiente(idAmbiente);
		// Chequeo Recursos permitidos
		for (int i = 0; i < recursos.size(); i++) {
			if (Requester.INSTANCE.getPermisoUsuario(recursos.get(i).getIdRecurso(),
					idUsuario)) {
				recursosPermitidos.add(recursos.get(i));
			}
		}
		return recursosPermitidos;
	}

	// TODO: consultarPermisoUsuario NO DEBE EXISTIR, SE USA EL REQUESTER
	// PARA CONSULTAR LOS PERMISOS
	// TODO:METODOS QUE DEBEN COMPLETARSE CON LLAMADOS A PARTICIPACION
//	private boolean consultarPermisoUsuario(int idRecurso, int idUsuario) {
//		// Harcodeo
//		if (idRecurso < idUsuario)
//			return true;
//		return false;
//	}

	// TODO:METODOS QUE DEBEN COMPLETARSE CON LLAMADAS A BASE DE DATOS
	// Aca va la logica de la solicitud de un Recurso a la base de datos
	// TODO: obtenerRecursoBd NO DEBE EXISTIR, SE USA EL REQUESTER
	// PARA OBTENER LOS RECURSOS
//	private Recurso obtenerRecursoBd(int idAmbiente, int idRecurso) {
//		// Harcodeo segun id de Recurso el recurso a crear para prueba
//		Recurso recurso;
//		if (idRecurso <= 2) {
//			recurso = new Archivo();
//			recurso.setDescripcion("Archivo resumen del Tp");
//		} else if (idRecurso > 2 && idRecurso <= 4) {
//			recurso = new Link();
//			recurso.setDescripcion("Link a pagina web");
//		} else {
//			recurso = new Encuesta();
//			recurso.setDescripcion("Encuesta a pagina web");
//		}
//		recurso.setIdRecurso(idRecurso);
//
//		return recurso;
//	}
//
//	// TODO: solicitarBdRecursosAmbiente NO DEBE EXISTIR, SE USA EL REQUESTER
//	private List<Recurso> solicitarBdRecursosAmbiente(int idAmbiente) {
//		// Harcodeo recursos del ambiente.
//		List<Recurso> recursos = new ArrayList<Recurso>();
//		Recurso recurso = new Archivo();
//		recurso.setDescripcion("Archivo resumen del Tp");
//		recurso.setIdRecurso(1);
//		recursos.add(recurso);
//		recurso = new Link();
//		recurso.setDescripcion("Link a pagina web");
//		recurso.setIdRecurso(3);
//		recursos.add(recurso);
//		recurso = new Encuesta();
//		recurso.setDescripcion("Encuesta a pagina web");
//		recurso.setIdRecurso(6);
//		recursos.add(recurso);
//
//		return recursos;
//	}

	// TODO: agregarRecursoBD NO DEBE EXISTIR, SE USA EL REQUESTER
//	private boolean agregarRecursoBD(int idAmbiente, Recurso recurso, int idUsuario) {
//		// Harcodeo
//		if (idUsuario > 5)
//			return false;
//		return true;
//	}

	// TODO: eliminarRecursoBD NO DEBE EXISTIR, SE USA EL REQUESTER
//	private boolean eliminarRecursoBD(int idAmbiente, int idRecurso, int idUsuario) {
//		// Harcodeo
//		if (idUsuario > 5)
//			return false;
//		return true;
//	}

}
