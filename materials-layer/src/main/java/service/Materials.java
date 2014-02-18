package service;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.BindingType;

@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
@WebService
public interface Materials {

	@WebMethod 
	@WebResult(name = "recursos")
	String getRecursos(@WebParam(name = "parametros")String parametros);
		
	@WebMethod
	@WebResult(name = "encuestaRespondida")
	String getEncuestaRespondida(@WebParam(name = "parametros")String parametros);

	@WebMethod
	String agregarEncuestaRespondida(@WebParam(name = "parametros")String respondidaParam);

	@WebMethod
	String agregarRecurso(@WebParam(name = "parametros")String encuestaParam);
	
	@WebMethod
	@WebResult(name = "successString")
	String agregarArchivo(@WebParam(name = "parametros")String archivoParam, @WebParam(name = "archivo") @XmlMimeType("application/octet-stream") DataHandler data);
	
	@WebMethod 
	@WebResult(name = "recurso")
	String getRecurso(@WebParam(name = "parametro")String parametros);
	
	@WebMethod
	@WebResult(name = "borrado")
	String borrarRecurso(@WebParam(name = "parametro")String parametros);
}