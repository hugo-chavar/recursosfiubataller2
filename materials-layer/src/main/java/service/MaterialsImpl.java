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
import connection.Requester;
import connection.parsers.Parser;
import connection.responses.OperationResponse;

@MTOM 
@WebService(endpointInterface = "service.Materials")
public class MaterialsImpl implements Materials {

	private Parser parser = new Parser();
	
	@Override
	public String agregarRecurso(String encuestaParam) {
		Parameter parameter = Parameter.createParameter(encuestaParam);
		if (parameter.getRecurso() == null )
			return createFailedResponse("Parametros invalidos");
		if(parameter.getUsuario() == null) {
			return createFailedResponse("usuario no creado");
		}
		if (parameter.getRecurso().getClass() == Archivo.class) {
			createFailedResponse("Para archivos utilice el WS agregarArchivo");
		}
		OperationResponse response;	
		Recurso recurso = parameter.getRecurso();
		if (Requester.INSTANCE.getPermisoUsuario(recurso.getAmbitoId(), parameter.getUsuario().getUsername(),"agregarEncuesta")) {
			response = Requester.INSTANCE.agregarRecurso(recurso);
		}
		else{
			return createFailedResponse("Permisos insuficientes");
		}
		return toXml(response);
	}
	
	@Override
	//EN el xml debe venir el ambitoId, nombre, extension.
	public String agregarArchivo(String archivoParam,	@XmlMimeType("application/octet-stream") DataHandler data) {

		Parameter parameter = Parameter.createParameter(archivoParam);
		if (parameter.getRecurso() == null ){
			return createFailedResponse("Parametros invalidos: debe especificar 'archivo' en xml");
		}
		
		if (data != null) {
			Archivo file = (Archivo) parameter.getRecurso();
			file.setRawFile(data);
			OperationResponse response =  Requester.INSTANCE.saveArchivo(file); 
			return toXml(response);
		}
		return createFailedResponse("Archivo sin datos recibido.");
	}

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
	
//	private String getEncuestaRespondida(Recurso recurso, Usia username) { // Cambio de usuarioId a username
//		OperationResponse response = Requester.INSTANCE.getEncuestaRespondida( recurso.getRecursoId(), username);
//		return toXml(response);
//	}
	
	private String getRecursos(int ambitoId, String username) { // Cambio de usuarioId a username
		OperationResponse recursosPermitidos;
		if (!Requester.INSTANCE.getPermisoUsuario(ambitoId, username, "getRecursos")){
			return createFailedResponse("El usuario " + username + " no tiene los permisos necesarios");
		}
		recursosPermitidos = Requester.INSTANCE.getRecursosAmbito(ambitoId);
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
		if (parameter.getUsuario() == null || parameter.getRecurso() == null){
			return createFailedResponse("Parametros invalidos");
		}
		
		OperationResponse response = Requester.INSTANCE.getEncuestaRespondida( parameter.getRecurso().getRecursoId(), parameter.getUsuario());
		return toXml(response);
//		return getEncuestaRespondida(parameter.getRecurso(), parameter.getUsuario());
	}

	@Override
	public String getRecursos(String parametros) {
		Parameter parameter = Parameter.createParameter(parametros);
		if (parameter.getRecurso() == null || parameter.getUsuario() == null) {
			return createFailedResponse("Parametros invalidos");
		}
		return getRecursos(parameter.getRecurso().getAmbitoId(), parameter.getUsuario().getUsername());
	}
	
	@Override
	public String getRecurso(String parametros) {
		//System.out.println(parametros);
		Parameter parameter = Parameter.createParameter(parametros);
		OperationResponse response = Requester.INSTANCE.getRecurso(parameter.getRecurso());
		return toXml(response);
	}

	@Override
	public String borrarRecurso(String parametros) {
		OperationResponse response;
		//System.out.println(parametros);
		Parameter parameter = Parameter.createParameter(parametros);
		if (Requester.INSTANCE.getPermisoUsuario(parameter.getRecurso().getAmbitoId(), parameter.getUsuario().getUsername(),"borrarRecurso")) {
			response = Requester.INSTANCE.deleteRecurso(parameter.getRecurso());
		} else {
			return createFailedResponse("Permisos insuficientes");
		}
		return toXml(response);
	}

}
