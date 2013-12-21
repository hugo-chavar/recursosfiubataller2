package service;



import java.util.List;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import model.Archivo;
import model.Encuesta;
import model.EncuestaRespondida;
import model.Link;
import model.Recurso;

import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.BindingType;


//@SOAPBinding(style = Style.RPC)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
@WebService
public interface Materials {

	@WebMethod String sayHello(String name);
	@WebMethod @WebResult(name="recursos") List<Recurso> obtenerRecursos(@WebParam(name="idAmbiente") int idAmbiente,@WebParam(name="idRecurso")int idUsuario);
	//@WebMethod Encuesta getEncuesta(@WebParam(name="idAmbiente")int idAmbiente, @WebParam(name="idRecurso")int idRecurso);
	@WebMethod @WebResult(name="Encuesta") String getEncuesta(@WebParam(name="idAmbiente")int idAmbiente, @WebParam(name="idRecurso")int idRecurso);
	//@WebMethod @WebResult(name="EncuestaRespondida")EncuestaRespondida getEncuestaRespondida(@WebParam(name="idAmbiente")int IdAmbiente,@WebParam(name="idRecurso") int idRecurso,@WebParam(name="idUsuario") int idUsuario);
	@WebMethod @WebResult(name="EncuestaRespondida")String getEncuestaRespondida(@WebParam(name="idAmbiente")int IdAmbiente,@WebParam(name="idRecurso") int idRecurso,@WebParam(name="idUsuario") int idUsuario);
	@WebMethod void agregarEncuestaRespondida(@WebParam(name="EncuestaRespondida")EncuestaRespondida respondida,@WebParam(name="idAmbiente")int idAmbiente);
	@WebMethod void agregarLink(@WebParam(name="Link")Link link,@WebParam(name="idUsuario")int idUsuario);
	@WebMethod void agregarEncuesta(@WebParam(name="Encuesta")Encuesta encuesta,@WebParam(name="idUsuario")int idUsuario);
	@WebMethod @WebResult(name="SuccessString")String setArchivo(@WebParam(name="idAmbiente")int idAmbiente,@WebParam(name="Nombre")String name,@WebParam(name="Extension")String ext, @WebParam(name="Archivo") @XmlMimeType("application/octet-stream") DataHandler data);
	@WebMethod @WebResult(name="Borrado")boolean borrarRecurso(@WebParam(name="idAmbiente")int idAmbiente, @WebParam(name="idRecurso")int idRecurso,@WebParam(name="idUsuario")int idUsuario);
	@WebMethod @WebResult(name="Archivo")String getArchivo(@WebParam(name="idAmbiente")int idAmbiente,@WebParam(name="idRecurso")int idRecurso ); 
}