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
		Archivo file = Requester.INSTANCE.getArchivo(ambitoId, recursoId);
		String xmlArchivo = parser.convertToXml(file, Archivo.class);
		return xmlArchivo;
	}
	
	@Override
	public void agregarEncuesta(Encuesta encuesta, int usuarioId) {
		if (Requester.INSTANCE.getPermisoUsuario(encuesta.getAmbitoId(), usuarioId)) {
			encuesta.recuperarDatosVisibles();
			Requester.INSTANCE.saveEncuesta(encuesta);
			System.out.println("Guardando encuesta: " + encuesta.getDescripcion());
		}
		
	//  TODO retornar confirmaciones en xml, copiar de borrarRecurso
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
			response = new OperationResponse();
			response.setReason("Permisos insuficientes");
		}
	//  TODO dejo este ejemplo hagan igual en todos los demas
		return parser.convertToXml(response, OperationResponse.class);
	}
	
	@Override
	public String getEncuesta(int ambitoId, int recursoId) {
		Encuesta encuesta = Requester.INSTANCE.getEncuesta(ambitoId, recursoId);
		encuesta.completarDatosVisibles();
		//usar algo que herede de OperationResponse, ver RecursosResponse
		String xmlEncuesta = parser.convertToXml(encuesta, Encuesta.class);
		return xmlEncuesta;
	//  TODO retornar una response, en lugar del objeto en xml
//		copiar de getRecursos
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
		//  TODO retornar confirmaciones en xml, copiar de borrarRecurso
	}
	
	@Override
	public String getEncuestaRespondida(int IdAmbiente, int recursoId, int usuarioId) {
		EncuestaRespondida respondida = Requester.INSTANCE.getEncuestaRespondida(IdAmbiente, recursoId, usuarioId);
		Encuesta encuesta = Requester.INSTANCE.getEncuesta(IdAmbiente, recursoId);
		respondida.completarDatosVisibles(encuesta.getPreguntas());
		String xmlRespondida = parser.convertToXml(respondida, EncuestaRespondida.class);
		return xmlRespondida;
	}
	
	@Override
	public String getRecursos(int ambitoId, int usuarioId) {
		List<Recurso> recursos = new ArrayList<Recurso>();
		RecursosResponse recursosPermitidos = new RecursosResponse();
		recursosPermitidos.setSuccess(true);

		// Obtengo los recursos
		recursos = Requester.INSTANCE.getRecursosAmbito(ambitoId);
		// Chequeo Recursos permitidos
		// TODO el chequeo tiene q ser a nivel ambito.. no recurso por recurso
		for (Recurso r:  recursos) {
			if (Requester.INSTANCE.getPermisoUsuario(r.getRecursoId(), usuarioId)) {
				recursosPermitidos.add(r);
			}
		}
//		return recursosPermitidos;
		return parser.convertToXml(recursosPermitidos, recursosPermitidos.getClass());
	}
	
	@Override
	public String borrarRecurso(int ambitoId, int recursoId, int usuarioId) {
		OperationResponse response;
		if (Requester.INSTANCE.getPermisoUsuario(recursoId, usuarioId)) {
			response = Requester.INSTANCE.deleteRecurso(recursoId);
		} else {
			response = createFailedResponse("Permisos insuficientes");
		}
		return parser.convertToXml(response, response.getClass());
	}

	private OperationResponse createFailedResponse(String reason) {
		OperationResponse response;
		response = new OperationResponse();
		response.setSuccess(false);
		response.setReason(reason);
		return response;
	}

	@Override
	public String getEncuestaRespondida2(String parametros) {
		Parameter parameter = Parameter.createParameter(parametros);
		return getEncuestaRespondida(parameter.getAmbitoId(), parameter.getRecursoId(), parameter.getUsuarioId());
	}

	@Override
	public String getRecursos2(String parametros) {
		Parameter parameter = Parameter.createParameter(parametros);
		if (parameter.getAmbitoId() == null || parameter.getUsuarioId() == null){
			return parser.convertToXml(createFailedResponse("Parametros invalidos"), OperationResponse.class);
		}
		return getRecursos(parameter.getAmbitoId(),parameter.getUsuarioId());
	}

}
