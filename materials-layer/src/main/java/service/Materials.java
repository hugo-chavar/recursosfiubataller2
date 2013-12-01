package service;



import java.io.File;

import javax.jws.WebMethod;
import javax.jws.WebService;

import model.Archivo;
import model.Encuesta;
import model.EncuestaRespondida;
import model.Link;
import model.ListaDeRecursos;


@WebService
public interface Materials {

	@WebMethod String sayHello(String name);
	//@WebMethod Node getTree();
	//@WebMethod Encuesta2 getEncuesta();
	@WebMethod ListaDeRecursos obtenerRecursos(int idAmbiente,int idUsuario);
	@WebMethod Encuesta getEncuesta(int idAmbiente, int idRecurso);
	@WebMethod EncuestaRespondida getEncuestaRespondida(int IdAmbiente, int idRecurso, int idUsuario);
	@WebMethod void agregarEncuestaRespondida(EncuestaRespondida respondida,int idAmbiente);
	@WebMethod void agregarLink(Link link);
	@WebMethod void agregarEncuesta(Encuesta encuesta);
	@WebMethod void setArchivo(int idAmbiente,String name,String ext, File file );
	@WebMethod Archivo getArchivo(int idAmbiente,int idRecurso );
}