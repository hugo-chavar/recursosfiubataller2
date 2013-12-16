package service;



import java.io.File;
import java.util.List;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import model.Archivo;
import model.Encuesta;
import model.EncuestaRespondida;
import model.Link;
import model.Recurso;

import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.BindingType;


//@SOAPBinding(style = Style.RPC)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
@WebService
public interface Materials {

	@WebMethod String sayHello(String name);
	@WebMethod @WebResult(name="recursos") List<Recurso> obtenerRecursos(int idAmbiente,int idUsuario);
	@WebMethod Encuesta getEncuesta(int idAmbiente, int idRecurso);
	@WebMethod EncuestaRespondida getEncuestaRespondida(int IdAmbiente, int idRecurso, int idUsuario);
	@WebMethod void agregarEncuestaRespondida(EncuestaRespondida respondida,int idAmbiente);
	@WebMethod void agregarLink(Link link,int idUsuario);
	@WebMethod void agregarEncuesta(Encuesta encuesta,int idUsuario);
	@WebMethod String setArchivo(int idAmbiente,String name,String ext,  @XmlMimeType("application/octet-stream") DataHandler data);
	//@WebMethod Archivo getArchivo(int idAmbiente,int idRecurso ); esto no 
}