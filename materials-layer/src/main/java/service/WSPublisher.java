package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPBinding;

import model.Encuesta;
import model.Pregunta;
import model.PreguntaRespuestaFija;
import model.Recurso;
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
		Recurso r = new Recurso();
		p.setRecurso(r);
		r.setAmbitoId(15);
		p.setUsuarioId(23);
		Parser parser = new Parser();
		String xml = parser.convertToXml(p, p.getClass());
		System.out.println(xml);

		Parameter p2 = (Parameter) parser.unmarshal(xml, Parameter.class);
		System.out.println("Ambito: " + p2.getRecurso().getAmbitoId());
		System.out.println("Usuario: " + p2.getUsuarioId());
		System.out.println("Recurso: " + p2.getRecurso().getRecursoId());

		xml = "<parametro><recurso><ambitoId>15</ambitoId></recurso><usuarioId>23</usuarioId></parametro>";
		p2 = (Parameter) parser.unmarshal(xml, Parameter.class);
		System.out.println("Ambito: " + p2.getRecurso().getAmbitoId());
		System.out.println("Usuario: " + p2.getUsuarioId());
		System.out.println("Recurso: " + p2.getRecurso().getRecursoId());
		
		Parameter p3 = (Parameter) parser.unmarshal("hola", Parameter.class);
		System.out.println("Parameter: " + p3);
		
		System.out.println("Prueba marshal de encuesta: ");

		Encuesta encuesta = new Encuesta(1003, -1, "una encuesta chica", false);
		
		Pregunta pr2, pr3;
		pr2 = new PreguntaRespuestaFija();
		List<String> opciones = new ArrayList<String>();
		pr2.setEnunciado("cuantas materias te faltan para recibirte?");
		opciones.add("1");
		opciones.add("menos de 5");
		opciones.add("entre 5 y 10");
		opciones.add("mas de 10");
		((PreguntaRespuestaFija) pr2).setRespuestasPosibles(opciones);
		encuesta.addPregunta(pr2);

		pr3 = new PreguntaRespuestaFija();
		opciones = new ArrayList<String>();
		pr3.setEnunciado("que materia fue la mas dificil?");
		opciones.add("analisis 2");
		opciones.add("algebra 2");
		opciones.add("taller 1");
		opciones.add("org de datos");
		((PreguntaRespuestaFija) pr3).setRespuestasPosibles(opciones);
		encuesta.addPregunta(pr3);

		
		xml = parser.convertToXml(encuesta, Encuesta.class);
		
		System.out.println(xml);
		
		Encuesta enc2 = (Encuesta)parser.unmarshal(xml, Encuesta.class);
		
		System.out.println(enc2.getDescripcion());
		
//		EncuestaParameter ep1 = new EncuestaParameter();
//		ep1.setAmbitoId(15);
//		ep1.setUsuarioId(23);
//		ep1.setEncuesta(encuesta);
//		xml = parser.convertToXml(ep1, ep1.getClass());
//		System.out.println(xml);
//		EncuestaParameter ep2 = (EncuestaParameter) parser.unmarshal(xml, EncuestaParameter.class);
//		
//		enc2 = ep2.getEncuesta();
//		System.out.println(enc2.getDescripcion());
		
		Parameter ep1 = new Parameter();
		ep1.setUsuarioId(23);
		ep1.setRecurso(encuesta);
		xml = parser.convertToXml(ep1, ep1.getClass());
		System.out.println(xml);
		Parameter ep2 = (Parameter) parser.unmarshal(xml, Parameter.class);
		
		enc2 = (Encuesta)ep2.getRecurso();
		System.out.println(enc2.getDescripcion());
		
		xml = parser.convertToXml(enc2, Encuesta.class);
		System.out.println(xml);
		
		xml = "<parametro><recurso><recursoId>1003</recursoId></recurso></parametro>";
		ep2 = (Parameter) parser.unmarshal(xml, Parameter.class);
		System.out.println(ep2.getRecurso().getRecursoId());

		System.out.println("Programa terminado. ");

	}

}
