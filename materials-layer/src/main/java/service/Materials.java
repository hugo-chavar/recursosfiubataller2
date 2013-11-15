package service;



import javax.jws.WebMethod;
import javax.jws.WebService;

import model.Encuesta2;
import model.ListaDeRecursos;


@WebService
public interface Materials {

	@WebMethod String sayHello(String name);
	//@WebMethod Node getTree();
	@WebMethod Encuesta2 getEncuesta();
	@WebMethod ListaDeRecursos obtenerRecursos(int idAmbiente,int idUsuario);
	//@WebMethod void setArchivo();
	
}