package service;



import java.io.File;

import javax.jws.WebMethod;
import javax.jws.WebService;

import model.Archivo;
import model.Encuesta;
import model.EncuestaRespondida;
import model.Link;
import model.ListaDeRecursos;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


//@SOAPBinding(style = Style.RPC)
@WebService
public interface Materials {

	@WebMethod String sayHello(String name);
	@WebMethod ListaDeRecursos obtenerRecursos(int idAmbiente,int idUsuario);
	@WebMethod Encuesta getEncuesta(int idAmbiente, int idRecurso);
	@WebMethod EncuestaRespondida getEncuestaRespondida(int IdAmbiente, int idRecurso, int idUsuario);
	@WebMethod void agregarEncuestaRespondida(EncuestaRespondida respondida,int idAmbiente);
	@WebMethod void agregarLink(Link link);
	@WebMethod void agregarEncuesta(Encuesta encuesta);
	//@WebMethod String setArchivo(int idAmbiente,String name,String ext, File file );
	//@WebMethod Archivo getArchivo(int idAmbiente,int idRecurso );
}