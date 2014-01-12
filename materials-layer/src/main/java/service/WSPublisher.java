package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPBinding;

import model.Encuesta;
import model.Pregunta;
import connection.EncuestaParameter;
import connection.Parameter;
import connection.Parser;
import service.MaterialsImpl;

public class WSPublisher {

	public static void main(String[] args) {
		Endpoint ep = Endpoint.publish("http://localhost:8082/WS2/Greeting2", new MaterialsImpl());
		SOAPBinding binding = (SOAPBinding) ep.getBinding();

		binding.setMTOMEnabled(true);

		System.out.println("Capa de Negocio --- Materiales--- Web Services ");

		System.out.println("Enter para salir : ");

		try {
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			bufferRead.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ep.stop();

		System.out.println("Prueba marshal de parametros: ");
		Parameter p = new Parameter();
		p.setAmbitoId(15);
		p.setUsuarioId(23);
		Parser parser = new Parser();
		String xml = parser.convertToXml(p, p.getClass());
		System.out.println(xml);

		Parameter p2 = (Parameter) parser.unmarshal(xml, Parameter.class);
		System.out.println("Ambito: " + p2.getAmbitoId());
		System.out.println("Usuario: " + p2.getUsuarioId());
		System.out.println("Recurso: " + p2.getRecursoId());

		xml = "<parametro><ambitoId>15</ambitoId><usuarioId>23</usuarioId></parametro>";
		p2 = (Parameter) parser.unmarshal(xml, Parameter.class);
		System.out.println("Ambito: " + p2.getAmbitoId());
		System.out.println("Usuario: " + p2.getUsuarioId());
		System.out.println("Recurso: " + p2.getRecursoId());
		
		Parameter p3 = (Parameter) parser.unmarshal("hola", Parameter.class);
		System.out.println("Parameter: " + p3);
		
		System.out.println("Prueba marshal de encuesta: ");

		Encuesta encuesta = new Encuesta();
		encuesta.setDescripcion("Esto es un ejemplo");
		Pregunta pr1 = new Pregunta();
		pr1.setEnunciado("Cual es un color primario?");
		pr1.getOpciones().add("azul");
		pr1.getOpciones().add("verde");
		pr1.getOpciones().add("magenta");
		pr1.getCorrectas().add("azul");

		Pregunta pr2 = new Pregunta();
		pr2.setEnunciado("Campeon copa sudamericana 2013?");
		pr2.getOpciones().add("River");
		pr2.getOpciones().add("Boca");
		pr2.getOpciones().add("Lanus");
		pr2.getCorrectas().add("Lanus");

		encuesta.getPreguntas().add(pr1);
		encuesta.getPreguntas().add(pr2);
		
		xml = parser.convertToXml(encuesta, Encuesta.class);
		
		System.out.println(xml);
		
		Encuesta enc2 = (Encuesta)parser.unmarshal(xml, Encuesta.class);
		
		System.out.println(enc2.getDescripcion());
		
		EncuestaParameter ep1 = new EncuestaParameter();
		ep1.setAmbitoId(15);
		ep1.setUsuarioId(23);
		ep1.setEncuesta(encuesta);
		xml = parser.convertToXml(ep1, ep1.getClass());
		System.out.println(xml);
		EncuestaParameter ep2 = (EncuestaParameter) parser.unmarshal(xml, EncuestaParameter.class);
		
		enc2 = ep2.getEncuesta();
		System.out.println(enc2.getDescripcion());
		

		System.out.println("Programa terminado. ");

	}

}
