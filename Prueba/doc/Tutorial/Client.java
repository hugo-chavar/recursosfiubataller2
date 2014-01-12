import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPBinding;

import service.Materials;
import service.MaterialsImplService;

public class Client {

	public static void main(String[] args) {
		MaterialsImplService service = new MaterialsImplService();
		Materials port = service.getMaterialsImplPort();
		System.out.println("------->>  Probando el servicio");
		System.out.println(port.sayHello("Programador"));
		System.out.println("------->>  ESTA TODO OK!");

		System.out.println("Ingrese algo para continuar y probar las encuestas : ");

		try {
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			bufferRead.readLine();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// A partir de aca se pueden descomentar los try-catch que se quieren
		// testear

//		try {
//			System.out.println("ingrese el path de su archivo");
//			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
//			String path = bufferRead.readLine();
//
//			File arch = new File(path);
//			DataHandler file = new DataHandler(new FileDataSource(arch));
//			// enable MTOM in client
//			BindingProvider bp = (BindingProvider) port;
//			SOAPBinding binding = (SOAPBinding) bp.getBinding();
//			binding.setMTOMEnabled(true);
//
//			String status = port.setArchivo(0, "aca iria su nombre", "aca su extension", file);
//			System.out.println("imageServer.uploadImage() : " + status);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		try {
//			String xmlRecursos = port.getRecursos(0, 0);
//			System.out.println(xmlRecursos);
//
//			System.out.println("Ok, recursos obtenidos.");
//		} catch (Exception e) {
//			System.out.println("Error al traer recursos!");
//		}
		
//		try {
//			String xmlRecursos = port.getRecursos2("hola");
//			System.out.println(xmlRecursos);
//
//			System.out.println("Ok, recursos obtenidos.");
//		} catch (Exception e) {
//			System.out.println("Error al traer recursos!");
//			System.out.println(e.toString());
//		}
		
		// A partir de aca se pueden descomentar los try-catch que se quieren testear
		try {
			String l = port.getRecursos(1, 0);
			System.out.println(l);
			System.out.println("Ok, recursos obtenidos.");
		} catch (Exception e) {
			System.out.println("Error al traer recursos!");
			System.out.println(e.toString());
		}
		
		try {
			String l = port.getEncuesta(-1, 1003);
			System.out.println(l);
		} catch (Exception e) {
			System.out.println("Error al traer la encuesta!");
			System.out.println(e.toString());
		}
		// try {
		// Encuesta enc = port.getEncuesta(0, 0);
		// System.out.println("TITULO ENCUESTA: " + enc.getDescripcion());
		//
		// List<Pregunta> preguntas = enc.getPreguntas();
		// for (Pregunta p : preguntas) {
		// System.out.println("\t" + p.getEnunciado());
		// }
		// } catch (Exception e) {
		// System.out.println("Encuesta inexistente!");
		// }

		// try {
		// Encuesta enc2 = new Encuesta();
		// enc2.setDescripcion("Esto es un ejemplo");
		// Pregunta p1 = new Pregunta();
		// p1.setEnunciado("Cual es un color primario?");
		// p1.getOpciones().add("azul");
		// p1.getOpciones().add("verde");
		// p1.getOpciones().add("magenta");
		// p1.getCorrectas().add("azul");
		//
		// Pregunta p2 = new Pregunta();
		// p1.setEnunciado("Campeon copa sudamericana 2013?");
		// p1.getOpciones().add("River");
		// p1.getOpciones().add("Boca");
		// p1.getOpciones().add("Lanus");
		// p1.getCorrectas().add("Lanus");
		//
		// enc2.getPreguntas().add(p1);
		// enc2.getPreguntas().add(p2);
		//
		// System.out.println("Creando una encuesta..");
		//
		// port.agregarEncuesta(enc2, 0);
		//
		// } catch (Exception e) {
		// System.out.println("Error al crear una encuesta");
		// }

	}

}
