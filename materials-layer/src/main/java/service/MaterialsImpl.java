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
	public String getArchivo(int ambitoId, int recursoId){
		OperationResponse response = Requester.INSTANCE.getRecurso(recursoId, "Archivo");
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
		if (Requester.INSTANCE.getPermisoUsuario(encuesta.getAmbitoId(), parameter.getUsuarioId())) {
			response= Requester.INSTANCE.saveEncuesta(encuesta);
		}
		else{
			return createFailedResponse("Permisos insuficientes");
		}
		return toXml(response);
	}
	
	@Override
	public String setArchivo(int ambitoId, String name, String ext,	@XmlMimeType("application/octet-stream") DataHandler data) {
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
	public String agregarLink(Link link, int usuarioId) {
		OperationResponse response;
		if (Requester.INSTANCE.getPermisoUsuario(link.getAmbitoId(), usuarioId)) {
			response = Requester.INSTANCE.saveLink(link);
		} else {
			return createFailedResponse("Permisos insuficientes");
		}
	//  TODO dejo este ejemplo hagan igual en todos los demas
		return toXml(response);
	}
	
	@Override
	public String getEncuesta(int ambitoId, int recursoId) {
		//try {
			OperationResponse response = Requester.INSTANCE.getRecurso(recursoId, "Encuesta");
		//} catch (GetException e) {
//			return createFailedResponse(e.getMessage());
		//}
		return toXml(response);
	}
	
	
	@Override
	public String agregarEncuestaRespondida(String respondidaParam) {	
		Parameter parameter = Parameter.createParameter(respondidaParam);
		if (parameter.getRespondida() == null || parameter.getAmbitoId() == null){
			return createFailedResponse("Parametros invalidos");
		}
		EncuestaRespondida respondida = parameter.getRespondida();
		OperationResponse response;
		EncuestaResponse encuestaResponse = (EncuestaResponse) Requester.INSTANCE.getRecurso(respondida.getIdRecurso(), "Encuesta");
		Encuesta encuesta = encuestaResponse.getEncuesta();
		if (encuesta.isEvaluada()) {
			respondida.evaluar(encuesta);
		}
		response = Requester.INSTANCE.saveEncuestaRespondida(respondida);
		return toXml(response);
	}
	
	@Override
	public String getEncuestaRespondida(int IdAmbiente, int recursoId, int usuarioId) {
		EncuestaRespondidaResponse response = new EncuestaRespondidaResponse();
		response.setSuccess(true);	
		//try {
		EncuestaRespondida respondida = Requester.INSTANCE.getEncuestaRespondida(IdAmbiente, recursoId, usuarioId);
		response.setRespondida(respondida);
		
		//} catch (GetException e) {
		//	return createFailedResponse(e.getMessage());
		//}
		return toXml(response);
	}
	
	@Override
	public String getRecursos(int ambitoId, int usuarioId) {
		List<Recurso> recursos = new ArrayList<Recurso>();
		RecursosResponse recursosPermitidos = new RecursosResponse();
		recursosPermitidos.setSuccess(true);

		// Obtengo los recursos
		try {
			recursos = Requester.INSTANCE.getRecursosAmbito(ambitoId);
		} catch (GetException e) {
			return createFailedResponse(e.getMessage());
		}
		// Chequeo Recursos permitidos
		// TODO el chequeo tiene q ser a nivel ambito.. no recurso por recurso
		for (Recurso r:  recursos) {
			if (Requester.INSTANCE.getPermisoUsuario(r.getRecursoId(), usuarioId)) {
				recursosPermitidos.add(r);
			}
		}
		return toXml(recursosPermitidos);
	}
	
	@Override
	public String borrarRecurso(int ambitoId, int recursoId, int usuarioId) {
		OperationResponse response;
		if (Requester.INSTANCE.getPermisoUsuario(recursoId, usuarioId)) {
			response = Requester.INSTANCE.deleteRecurso(recursoId,"Link");
		} else {
			return createFailedResponse("Permisos insuficientes");
		}
		return toXml(response);
	}

	private String createFailedResponse(String reason) {
		OperationResponse response;
		response = OperationResponse.createFailed(reason);;
		return toXml(response);
	}
	
	private String toXml(OperationResponse response) {
		return parser.convertToXml(response, response.getClass());
	}

	@Override
	public String getEncuestaRespondida2(String parametros) {
		Parameter parameter = Parameter.createParameter(parametros);
		if (parameter.getAmbitoId() == null || parameter.getUsuarioId() == null || parameter.getRecursoId() == null){
			return createFailedResponse("Parametros invalidos");
		}
		return getEncuestaRespondida(parameter.getAmbitoId(), parameter.getRecursoId(), parameter.getUsuarioId());
	}

	@Override
	public String getRecursos2(String parametros) {
		Parameter parameter = Parameter.createParameter(parametros);
		if (parameter.getAmbitoId() == null || parameter.getUsuarioId() == null){
			return createFailedResponse("Parametros invalidos");
		}
		return getRecursos(parameter.getAmbitoId(),parameter.getUsuarioId());
	}
	
	@Override
	public String getEncuesta2(String parametros) {
		Parameter parameter = Parameter.createParameter(parametros);
		if (parameter.getAmbitoId() == null || parameter.getRecursoId() == null){
			return createFailedResponse("Parametros invalidos");
		}
		return getEncuesta(parameter.getAmbitoId(),parameter.getRecursoId());
	}

}
