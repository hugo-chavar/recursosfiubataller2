import java.net.URL;

import javax.activation.DataHandler;

import service.Materials;
import service.MaterialsImplService;

public class Client {

	private static String xml, path;
	private static Materials port;

	public static void main(String[] args) {
		MaterialsImplService service = new MaterialsImplService();
		port = service.getMaterialsImplPort();
		System.out.println("------->>  Probando el servicio");
		System.out.println(port.sayHello("Programador"));
		System.out.println("------->>  ESTA TODO OK!");

//		System.out.println("Ingrese algo para continuar y probar las encuestas : ");
//
//		try {
//			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
//			bufferRead.readLine();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
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
		xml = "hola";
		pruebaGetRecusos();

		xml = "<usuarioId>23</usuarioId><recurso><ambitoId>15</ambitoId></recurso>";
		pruebaGetRecusos();

		// prueba q da error por no definir tipo de recurso
		xml = "<recurso><recursoId>1003</recursoId></recurso>";
		pruebaGetRecurso();

		// prueba q da error por no definir id de recurso
		xml = "<recurso><tipo>Link</tipo></recurso>";
		pruebaGetRecurso();
		// prueba q da error por definir tipo inexistente
		xml = "<recurso><recursoId>1003</recursoId><tipo>abcd</tipo></recurso>";
		pruebaGetRecurso();

		// prueba Ok de cache
		xml = "<recurso><recursoId>1003</recursoId><tipo>Encuesta</tipo></recurso>";
		pruebaGetRecurso();
		
		// prueba Ok de cache de nuevo
		xml = "<recurso><recursoId>1004</recursoId><tipo>Encuesta</tipo></recurso>";
		pruebaGetRecurso();
		
		// prueba con datos mockeados de integracion
		xml = "<recurso><recursoId>15</recursoId><tipo>Encuesta</tipo></recurso>";
		pruebaGetRecurso();
		
		// prueba encuesta inexistente
		xml = "<recurso><recursoId>16</recursoId><tipo>Encuesta</tipo></recurso>";
		pruebaGetRecurso();

		// Prueba get Respondida con parametros validos de cache
		xml = "<recurso><recursoId>15</recursoId></recurso><usuarioId>4</usuarioId>";
		pruebaGetRespondida();
		
		// Prueba get otra Respondida con parametros validos de cache
		xml = "<recurso><recursoId>10</recursoId></recurso><usuarioId>5</usuarioId>";
		pruebaGetRespondida();
		
		// Prueba get otra Respondida con parametros validos de cache
		xml = "<recurso><recursoId>10</recursoId></recurso><usuarioId>5</usuarioId>";
		pruebaGetRespondida();
		
		// Prueba get Respondida con parametros inexistentes
		xml = "<recurso><recursoId>20</recursoId></recurso><usuarioId>4</usuarioId>";
		pruebaGetRespondida();
		
		// Prueba get otra Respondida con parametros validos de cache
		// conteniendo respuestas multiples
		xml = "<recurso><recursoId>18</recursoId></recurso><usuarioId>5</usuarioId>";
		pruebaGetRespondida();
		
		// Prueba get otra Respondida con parametros validos de cache
		// conteniendo respuestas vacias
		xml = "<recurso><recursoId>19</recursoId></recurso><usuarioId>5</usuarioId>";
		pruebaGetRespondida();
		
		// Prueba get Respondida sin especificar recursoId
		xml = "<usuarioId>4</usuarioId>";
		pruebaGetRespondida();
		
		// Prueba get Respondida sin especificar usuarioId
		xml = "<recurso><recursoId>15</recursoId></recurso>";
		pruebaGetRespondida();
		
		// Prueba guardar archivo

		xml = "<archivo><tipo>Archivo</tipo><ambitoId>14</ambitoId><descripcion>informacion sobre el equipo mas grande del universo</descripcion><tipo>jpg</tipo><nombre>River Plate</nombre></archivo>";
		path = Client.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
		path = path.substring(0, path.lastIndexOf("classes") + 8);
		path = path + "Client.class";
		pruebaGuardarArchivo();
		
//		String xmlEncuesta = "<encuesta evaluada='true'><recursoId>1004</recursoId><tipo>Encuesta</tipo><ambitoId>-1</ambitoId><descripcion>una encuesta grande</descripcion><preguntas><pregunta correctas='3' idPregunta='1' enunciado='de que color es el caballo blanco de san martin?'><respuestas>rojo,verde,azul,blanco</respuestas></pregunta><pregunta correctas='1' idPregunta='2' enunciado='a que equipo del futbol argentino le denominan Millo'><respuestas>velez,River Plate,crucero del norte,estudiantes</respuestas></pregunta><pregunta correctas='2' idPregunta='3' enunciado='cual es un patron de diseno creacional'><respuestas>command,mediator,builder,facade</respuestas></pregunta><pregunta correctas='0,7,10,11,12,6' idPregunta='4' enunciado='Un test unitario debe presentar las siguientes caracter�sticas'><respuestas>Rapido,Moldeable,Configurable,Acoplable,Lento,Extensible,Repetible,Profesional,Maduro,Amplio,Simple,Independiente,Automatizable</respuestas></pregunta><pregunta correcta='4' idPregunta='5' enunciado='cuantas patas tiene un gato?'/></preguntas></encuesta>";
//		xml = "<parametro><ambitoId>15</ambitoId><usuarioId>23</usuarioId>" + xmlEncuesta + "";
//		
//		pruebaGuardarEncuesta();

//		try {
//			System.out.println("Prueba set Archivo");
//			String path, nombre, desc, extension;
//			int idRec;
//			String xml = " <WS><Archivo><ambitoId>-1</ambitoId><id>1003</id><descripcion>la foto del siglo</descripcion><nombre>teofilo</nombre><tipo>jpg</tipo></Archivo></WS>";
//			// path = getClass().getProtectionDomain().getCodeSource().getLocation().toExternalForm();
//			// path = path.substring(0, path.lastIndexOf("classes") + 8);
//			path = "file:/home/damian/aux.txt";
//			DataHandler arch = new DataHandler(new URL(path));
//			String response = port.setArchivo(xml, arch);
//			System.out.println(response);
//		} catch (Exception e) {
//			System.out.println("Se produjo un error");
//			System.out.println(e.toString());
//		}



	}

	private static void addTagParametros() {
		xml = "<parametro>" + xml + "</parametro>";
	}

	private static void pruebaGuardarRecurso() {
		try {
			String response = port.agregarRecurso(xml);
			System.out.println(response);
		} catch (Exception e) {
			System.out.println("Error al crear la encuesta");
			System.out.println(e.toString());
		}
	}

	private static void pruebaGetRecurso() {
		addTagParametros();
		try {
			String response = port.getRecurso(xml);
			System.out.println(response);
		} catch (Exception e) {
			System.out.println("Error al traer el recurso: " + xml);
			System.out.println(e.toString());
		}
	}

	private static void pruebaGetRecusos() {
		addTagParametros();
		try {
			// prueba con parametros invalidos
			String xmlRecursos = port.getRecursos(xml);
			System.out.println(xmlRecursos);
		} catch (Exception e) {
			System.out.println("Error al traer recursos!");
			System.out.println(e.toString());
		}
	}

	private static void pruebaGetRespondida() {
		addTagParametros();
		try {
			String response = port.getEncuestaRespondida(xml);
			System.out.println(response);
		} catch (Exception e) {
			System.out.println("Error al traer encuesta respondida: " + xml);
			System.out.println(e.toString());
		}
	}

	private static void pruebaGuardarArchivo() {
		addTagParametros();
		try {
			DataHandler dataHandler = new DataHandler(new URL(path));
			String response = port.agregarArchivo(xml, dataHandler);
			System.out.println(response);
		} catch (Exception e) {
			System.out.println("Error al traer encuesta respondida: " + xml);
			System.out.println(e.toString());
		}
	}
}
