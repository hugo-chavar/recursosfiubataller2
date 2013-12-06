package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.ws.Endpoint;

import service.MaterialsImpl;

public class WSPublisher {

	public static void main(String[] args) {
		Endpoint ep = Endpoint.publish("http://localhost:8095/WS2/Greeting2",
				new MaterialsImpl());
		System.out.println("Capa de Negocio --- Materiales--- Web Services : ");
		
	
		
	
		
		System.out.println("Ingrese \"hola\" para salir : ");

		try {
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			String s = bufferRead.readLine();
			if (s.equalsIgnoreCase("hola")) {
				ep.stop();
				System.out.println("Saliendo bien...");
			} else { //esto es una boludez .. hace lo mismo
				ep.stop();
				System.out.println("Saliendo mal...");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
