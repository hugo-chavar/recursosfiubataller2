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
		
		try {
			String l = port.getEncuesta(-1, 1004);
			System.out.println(l);
		} catch (Exception e) {
			System.out.println("Error al traer la encuesta!");
			System.out.println(e.toString());
		}
		
		try {
			String xmlEncuesta = "<encuesta evaluada='true'><recursoId>1004</recursoId><tipo>Encuesta</tipo><ambitoId>-1</ambitoId><descripcion>una encuesta grande</descripcion><preguntas><pregunta correctas='3' idPregunta='1' enunciado='de que color es el caballo blanco de san martin?'><respuestas>rojo,verde,azul,blanco</respuestas></pregunta><pregunta correctas='1' idPregunta='2' enunciado='a que equipo del futbol argentino le denominan Millo'><respuestas>velez,River Plate,crucero del norte,estudiantes</respuestas></pregunta><pregunta correctas='2' idPregunta='3' enunciado='cual es un patron de diseno creacional'><respuestas>command,mediator,builder,facade</respuestas></pregunta><pregunta correctas='0,7,10,11,12,6' idPregunta='4' enunciado='Un test unitario debe presentar las siguientes características'><respuestas>Rapido,Moldeable,Configurable,Acoplable,Lento,Extensible,Repetible,Profesional,Maduro,Amplio,Simple,Independiente,Automatizable</respuestas></pregunta><pregunta correcta='4' idPregunta='5' enunciado='cuantas patas tiene un gato?'/></preguntas></encuesta>";
			String xmlParametros = "<parametro><ambitoId>15</ambitoId><usuarioId>23</usuarioId>" + xmlEncuesta	+ "</parametro>";
			String response = port.agregarEncuesta(xmlParametros);
			System.out.println(response);
		} catch (Exception e) {
			System.out.println("Error al crear la encuesta");
		}

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
