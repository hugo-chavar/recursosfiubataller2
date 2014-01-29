package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPBinding;

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
			System.out.println(e.getMessage());
		}

		ep.stop();


		System.out.println("Programa terminado. ");
	}

}
