package service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import model.Encuesta;

@WebService
public interface Materials {

	@WebMethod String sayHello(String name);
	//@WebMethod Node getTree();
	@WebMethod Encuesta getEncuesta();
}