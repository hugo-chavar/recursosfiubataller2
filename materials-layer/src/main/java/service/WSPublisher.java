package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPBinding;

import connection.Parameter;
import connection.Parser;
import service.MaterialsImpl;

public class WSPublisher {

	public static void main(String[] args) {
		Endpoint ep = Endpoint.publish("http://localhost:8081/WS2/Greeting2", new MaterialsImpl());
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
		
		Parameter p2 = (Parameter)parser.unmarshal(xml, Parameter.class);
		System.out.println("Ambito: " + p2.getAmbitoId());
		System.out.println("Usuario: " + p2.getUsuarioId());
		System.out.println("Recurso: " + p2.getRecursoId());
		
		System.out.println("Programa terminado. ");

	}

}
