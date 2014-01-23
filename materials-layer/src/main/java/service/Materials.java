package service;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.BindingType;

import model.Link;


@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
@WebService
public interface Materials {

	@WebMethod
	String sayHello(String name);
	
//	@WebMethod 
//	@WebResult(name = "recursos")
//	String getRecursos(@WebParam(name = "ambitoId") int ambitoId, @WebParam(name = "recursoId")int usuarioId);
	
	@WebMethod 
	@WebResult(name = "recursos")
	String getRecursos(@WebParam(name = "parametros")String parametros);
		
//	@WebMethod 
//	@WebResult(name = "encuesta")
//	String getEncuesta(@WebParam(name = "recursoId")int recursoId);
	
//	@WebMethod
//	@WebResult(name = "encuestaRespondida")
//	String getEncuestaRespondida(@WebParam(name = "ambitoId")int ambitoId, @WebParam(name = "recursoId") int recursoId,@WebParam(name="usuarioId") int usuarioId);
//	
	@WebMethod
	@WebResult(name = "encuestaRespondida")
	String getEncuestaRespondida(@WebParam(name = "parametros")String parametros);

	@WebMethod
	String agregarEncuestaRespondida(@WebParam(name = "parametros")String respondidaParam);

//	@WebMethod
//	@WebResult(name = "agregarLink")
//	String agregarLink(@WebParam(name = "link") Link link, @WebParam(name = "usuarioId") int usuarioId);

	@WebMethod
	String agregarEncuesta(@WebParam(name = "parametros")String encuestaParam);
	
	@WebMethod
	@WebResult(name = "successString")
	//String setArchivo(@WebParam(name = "ambitoId")int ambitoId, @WebParam(name = "nombre")String name, @WebParam(name = "extension")String ext, @WebParam(name = "archivo") @XmlMimeType("application/octet-stream") DataHandler data);
	String setArchivo(@WebParam(name = "parametros")String archivoParam, @WebParam(name = "archivo") @XmlMimeType("application/octet-stream") DataHandler data);
//	@WebMethod
//	@WebResult(name = "borrado")
//	String borrarRecurso(@WebParam(name = "recursoId") int recursoId, @WebParam(name = "usuarioId") int usuarioId);

//	@WebMethod
//	@WebResult(name = "archivo")
//	String getArchivo(@WebParam(name = "ParametrosXmlIdAmbitoIdRecurso") String xmlParam);

//	@WebMethod 
//	@WebResult(name = "encuesta")
//	String getEncuesta2(@WebParam(name = "parametros")String parametros);
	
	@WebMethod 
	@WebResult(name = "recurso")
	String getRecurso(@WebParam(name = "parametro")String parametros);
	
//	@WebMethod 
//	@WebResult(name = "String")
//	String transformDataHandlerToString(@WebParam(name = "File as DataHandler")@XmlMimeType("application/octet-stream") DataHandler data);
//	
//	@WebMethod 
//	@WebResult(name = "DataHandler")
//	@XmlMimeType("application/octet-stream") DataHandler transformStringtoDataHandler(@WebParam(name = "parametro")String parametros);

	
	@WebMethod
	@WebResult(name = "borrado")
	String borrarRecurso(@WebParam(name = "parametro")String parametros);
}