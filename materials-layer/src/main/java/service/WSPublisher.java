package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPBinding;

import model.Encuesta;
import model.Recurso;
import connection.EncuestaRequester;
import connection.Parameter;
import connection.Parser;
import connection.responses.EncuestaResponse;

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
		EncuestaResponse er = (EncuestaResponse)((new EncuestaRequester()).getFromCache(1004));

		Encuesta encuesta = er.getEncuesta();
		
		xml = parser.convertToXml(encuesta, Encuesta.class);
		
		System.out.println(xml);
		
		Encuesta enc2 = (Encuesta)parser.unmarshal(xml, Encuesta.class);
		
		System.out.println(enc2.getDescripcion());
		
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
