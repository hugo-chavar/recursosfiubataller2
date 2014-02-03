package service;

import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.soap.MTOM;

import model.Archivo;
import model.Encuesta;
import model.EncuestaRespondida;
import model.Recurso;
import connection.Parameter;
import connection.Parser;
import connection.Requester;
import connection.exceptions.GetException;
import connection.responses.OperationResponse;

@MTOM 
@WebService(endpointInterface = "service.Materials")
public class MaterialsImpl implements Materials {

	private Parser parser = new Parser();
	
	@Override
	public String sayHello(String name) {
		return "Hello, Welcom to jax-ws " + name + '\n';
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
			response = Requester.INSTANCE.agregarRecurso(encuesta);
		}
		else{
			return createFailedResponse("Permisos insuficientes");
		}
		return toXml(response);
	}
	
	@Override
	//EN el xml debe venir el ambitoId, nombre, extension.
	public String setArchivo(String archivoParam,	@XmlMimeType("application/octet-stream") DataHandler data) {

		Parameter parameter = Parameter.createParameter(archivoParam);
		if (parameter.getRecurso() == null || parameter.getRecurso().getClass() != Archivo.class){
			return createFailedResponse("Parametros invalidos: debe especificar 'archivo' en xml");
		}
		
		if (data != null) {
//			ArchivoParser parser = new ArchivoParser();
			Archivo file = (Archivo) parameter.getRecurso();
//			try {
//				file = parser.deserializeArchivo(archivoParam);
//			} catch (ParseException e) {
//				e.printStackTrace();
//				return createFailedResponse("Parametros invalidos");
//			} 
			file.setRawFile(data);
			OperationResponse response =  Requester.INSTANCE.saveArchivo(file); 
			return toXml(response);

		}
		return createFailedResponse("Archivo sin datos recibido.");
	}
	
//	@Override
//	public String agregarLink(Link link, int usuarioId) {
//		OperationResponse response;
//		if (Requester.INSTANCE.getPermisoUsuario(link.getAmbitoId(), usuarioId,"agregarLink")) {
//			response = Requester.INSTANCE.agregarRecurso(link);
//		} else {
//			return createFailedResponse("Permisos insuficientes");
//		}
//		return toXml(response);
//	}
	
	@Override
	public String agregarEncuestaRespondida(String respondidaParam) {
		Parameter parameter = Parameter.createParameter(respondidaParam);
		if (parameter.getRespondida() == null) {
			return createFailedResponse("Parametros invalidos");
		}
		EncuestaRespondida respondida = parameter.getRespondida();
		OperationResponse response;
		OperationResponse encuestaResponse;
		encuestaResponse = Requester.INSTANCE.getRecurso(new Encuesta(respondida.getIdRecurso(), 0, "", false));
		if (!encuestaResponse.getSuccess()) {
			return createFailedResponse("Encuesta inexistente");
		}
		Encuesta encuesta = (Encuesta) encuestaResponse.getSerializable();
		if (encuesta.isEvaluada()) {
			respondida.evaluar(encuesta);
		}
		response = Requester.INSTANCE.saveEncuestaRespondida(respondida);
		return toXml(response);

	}
	
	private String getEncuestaRespondida(Recurso recurso, int usuarioId) {
		OperationResponse response = new OperationResponse();
		response.setSuccess(true);	
		//try {
		EncuestaRespondida respondida = Requester.INSTANCE.getEncuestaRespondida( recurso.getRecursoId(), usuarioId);
//		response.setRespondida(respondida);
		response.setSerializable(respondida);
		
		//} catch (GetException e) {
		//	return createFailedResponse(e.getMessage());
		//}
		return toXml(response);
	}
	
	private String getRecursos(int ambitoId, int usuarioId) {
		OperationResponse recursosPermitidos;
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
	
	@Override
	public String getRecurso(String parametros) {
		Parameter parameter = Parameter.createParameter(parametros);
		OperationResponse response = Requester.INSTANCE.getRecurso(parameter.getRecurso());
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
