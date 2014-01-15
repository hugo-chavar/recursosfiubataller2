package service;

import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.soap.MTOM;

import model.Archivo;
import model.Encuesta;
import model.EncuestaRespondida;
import model.Link;
import model.Recurso;
import connection.Parameter;
import connection.Parser;
import connection.Requester;
import connection.exceptions.GetException;
import connection.responses.EncuestaRespondidaResponse;
import connection.responses.EncuestaResponse;
import connection.responses.OperationResponse;
import connection.responses.RecursosResponse;

@MTOM 
@WebService(endpointInterface = "service.Materials")
public class MaterialsImpl implements Materials {

	private Parser parser = new Parser();
	
	@Override
	public String sayHello(String name) {
		return "Hello, Welcom to jax-ws " + name;
	}

	@Override
	public String getArchivo(String xmlParam){
		Parameter parameter = Parameter.createParameter(xmlParam);
		OperationResponse response = Requester.INSTANCE.getRecurso(parameter.getRecurso());
		return toXml(response);
	}
	
	@Override
	public String agregarEncuesta(String encuestaParam) {
		Parameter parameter = Parameter.createParameter(encuestaParam);
		if (parameter.getRecurso() == null || parameter.getUsuarioId() == null || parameter.getRecurso().getClass() != Encuesta.class){
			return createFailedResponse("Parametros invalidos");
		}
		OperationResponse response;	
		Encuesta encuesta = (Encuesta)parameter.getRecurso();
		if (Requester.INSTANCE.getPermisoUsuario(encuesta.getAmbitoId(), parameter.getUsuarioId(),"agregarEncuesta")) {
			response= Requester.INSTANCE.saveEncuesta(encuesta);
		}
		else{
			return createFailedResponse("Permisos insuficientes");
		}
		return toXml(response);
	}
	
	@Override
	//EN el xml debe venir el ambitoId, nombre, extension.
	public String setArchivo(String archivoParam,	@XmlMimeType("application/octet-stream") DataHandler data) {
		// TODO Dami, pasa al archivoRequester la mayoria de las validaciones
		// y que te devuelva un response con el resultado
		Parameter parameter = Parameter.createParameter(archivoParam);
		if (parameter.getRecurso() == null || parameter.getUsuarioId() == null || parameter.getRecurso().getClass() != Encuesta.class){
			return createFailedResponse("Parametros invalidos");
		}
		if (data != null) {
			Archivo file = (Archivo)parameter.getRecurso(); 
			file.setRawFile(data);
			// Requester.INSTANCE.saveArchivo(File); TODO: ACA DEBERIA ANDAR EL
			// SAVE ARCHIVO
			return "Archivo subido Correctamente";

		}
		//  TODO retornar confirmaciones en xml, hablar con presentacion para ver como lo quieren
		return "ERROR al subir el archivo";
	}
	
	@Override
	public String agregarLink(Link link, int usuarioId) {
		OperationResponse response;
		if (Requester.INSTANCE.getPermisoUsuario(link.getAmbitoId(), usuarioId,"agregarLink")) {
			response = Requester.INSTANCE.saveLink(link);
		} else {
			return createFailedResponse("Permisos insuficientes");
		}
		return toXml(response);
	}
	
	@Override
	public String agregarEncuestaRespondida(String respondidaParam) {	
		Parameter parameter = Parameter.createParameter(respondidaParam);
		if (parameter.getRespondida() == null){
			return createFailedResponse("Parametros invalidos");
		}
		EncuestaRespondida respondida = parameter.getRespondida();
		OperationResponse response;
		EncuestaResponse encuestaResponse = (EncuestaResponse) Requester.INSTANCE.getRecurso(new Recurso(respondida.getIdRecurso(),0,"","Encuesta"));
		if (!encuestaResponse.getSuccess()){
			return createFailedResponse("Encuesta inexistente");
		}
		Encuesta encuesta = encuestaResponse.getEncuesta();
		if (encuesta.isEvaluada()) {
			respondida.evaluar(encuesta);
		}
		response = Requester.INSTANCE.saveEncuestaRespondida(respondida);
		return toXml(response);
	}
	
	public String getEncuestaRespondida(Recurso recurso, int usuarioId) {
		EncuestaRespondidaResponse response = new EncuestaRespondidaResponse();
		response.setSuccess(true);	
		//try {
		EncuestaRespondida respondida = Requester.INSTANCE.getEncuestaRespondida(recurso.getAmbitoId(), recurso.getRecursoId(), usuarioId);
		response.setRespondida(respondida);
		
		//} catch (GetException e) {
		//	return createFailedResponse(e.getMessage());
		//}
		return toXml(response);
	}
	
	private String getRecursos(int ambitoId, int usuarioId) {
		RecursosResponse recursosPermitidos;
		// Obtengo los recursos
		try {
			if (!Requester.INSTANCE.getPermisoUsuario(ambitoId, usuarioId,"getRecursos")){
				return createFailedResponse("El usuario "+usuarioId+" no tiene los permisos necesarios");
			}
			recursosPermitidos = Requester.INSTANCE.getRecursosAmbito(ambitoId);
		} catch (GetException e) {
			return createFailedResponse(e.getMessage());
		}
		return toXml(recursosPermitidos);
	}
	
//	@Override
//	public String borrarRecurso(int recursoId, int usuarioId) {
//		OperationResponse response;
//		if (Requester.INSTANCE.getPermisoUsuario(recursoId, usuarioId)) {
//			response = Requester.INSTANCE.deleteRecurso(recursoId,"Link");
//		} else {
//			return createFailedResponse("Permisos insuficientes");
//		}
//		return toXml(response);
//	}

	private String createFailedResponse(String reason) {
		OperationResponse response;
		response = OperationResponse.createFailed(reason);
		return toXml(response);
	}
	
	private String toXml(OperationResponse response) {
		return parser.convertToXml(response, response.getClass());
	}

	@Override
	public String getEncuestaRespondida(String parametros) {
		Parameter parameter = Parameter.createParameter(parametros);
		if (parameter.getUsuarioId() == null || parameter.getRecurso() == null){
			return createFailedResponse("Parametros invalidos");
		}
		return getEncuestaRespondida(parameter.getRecurso(), parameter.getUsuarioId());
	}

	@Override
	public String getRecursos(String parametros) {
		Parameter parameter = Parameter.createParameter(parametros);
		if (parameter.getRecurso() == null || parameter.getUsuarioId() == null) {
			return createFailedResponse("Parametros invalidos");
		}
		return getRecursos(parameter.getRecurso().getAmbitoId(), parameter.getUsuarioId());
	}
	
//	@Override
//	public String getEncuesta2(String parametros) {
//		Parameter parameter = Parameter.createParameter(parametros);
//		if (parameter.getAmbitoId() == null || parameter.getRecursoId() == null){
//			return createFailedResponse("Parametros invalidos");
//		}
//		return getEncuesta(parameter.getRecurso());
//	}
	
	@Override
	public String getRecurso(String parametros) {
		System.out.println(parametros);
		Parameter parameter = Parameter.createParameter(parametros);
		
		// try {
		OperationResponse response = Requester.INSTANCE.getRecurso(parameter.getRecurso());
		// } catch (GetException e) {
		// return createFailedResponse(e.getMessage());
		// }
		return toXml(response);
	}

	@Override
	public String borrarRecurso(String parametros) {
		OperationResponse response;
		System.out.println(parametros);
		Parameter parameter = Parameter.createParameter(parametros);
		if (Requester.INSTANCE.getPermisoUsuario(parameter.getRecurso().getAmbitoId(), parameter.getUsuarioId(),"borrarRecurso")) {
			response = Requester.INSTANCE.deleteRecurso(parameter.getRecurso());
		} else {
			return createFailedResponse("Permisos insuficientes");
		}
		return toXml(response);
	}

}
