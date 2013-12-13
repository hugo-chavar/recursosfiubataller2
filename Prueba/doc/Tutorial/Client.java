import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import service.Encuesta;
import service.Materials;
import service.MaterialsImplService;
import service.Pregunta;
import service.Recurso;

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

		// A partir de aca se pueden descomentar los try-catch que se quieren testear
		try {
			List<Recurso> l = port.obtenerRecursos(0, 0);
			for (Recurso r : l) {
				System.out.println("Un recurso: " + r.getDescripcion());
			}
			System.out.println("Ok, recursos obtenidos.");
		} catch (Exception e) {
			System.out.println("Error al traer recursos!");
		}
//		try {
//			Encuesta enc = port.getEncuesta(0, 0);
//			System.out.println("TITULO ENCUESTA: " + enc.getDescripcion());
//
//			List<Pregunta> preguntas = enc.getPreguntas();
//			for (Pregunta p : preguntas) {
//				System.out.println("\t" + p.getEnunciado());
//			}
//		} catch (Exception e) {
//			System.out.println("Encuesta inexistente!");
//		}

		try {
			Encuesta enc2 = new Encuesta();
			enc2.setDescripcion("Esto es un ejemplo");
			Pregunta p1 = new Pregunta();
			p1.setEnunciado("Cual es un color primario?");
			p1.getOpciones().add("azul");
			p1.getOpciones().add("verde");
			p1.getOpciones().add("magenta");
			p1.getCorrectas().add("azul");

			Pregunta p2 = new Pregunta();
			p1.setEnunciado("Campeon copa sudamericana 2013?");
			p1.getOpciones().add("River");
			p1.getOpciones().add("Boca");
			p1.getOpciones().add("Lanus");
			p1.getCorrectas().add("Lanus");

			enc2.getPreguntas().add(p1);
			enc2.getPreguntas().add(p2);

			System.out.println("Creando una encuesta..");
			
			port.agregarEncuesta(enc2);

		} catch (Exception e) {
			System.out.println("Error al crear una encuesta");
		}

	}

}
