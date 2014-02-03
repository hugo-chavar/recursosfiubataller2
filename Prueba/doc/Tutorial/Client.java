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

//-------------
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
//-------------		
//		try {
//			//prueba con parametros invalidos
//			String xmlRecursos = port.getRecursos("hola");
//			System.out.println(xmlRecursos);
//		} catch (Exception e) {
//			System.out.println("Error al traer recursos!");
//			System.out.println(e.toString());
//		}
//		
//		try {
//			String xml = "<parametro><usuarioId>23</usuarioId><recurso><ambitoId>15</ambitoId></recurso></parametro>";
//			String response = port.getRecursos(xml);
//			System.out.println(response);
//		} catch (Exception e) {
//			System.out.println("Error al traer recursos!");
//			System.out.println(e.toString());
//		}
//		
//		try {
//			// prueba q da error por no definir tipo de recurso
//			String xml = "<parametro><recurso><recursoId>1003</recursoId></recurso></parametro>";
//			String response = port.getRecurso(xml);
//			System.out.println(response);
//		} catch (Exception e) {
//			System.out.println("Error al traer la encuesta!");
//			System.out.println(e.toString());
//		}
//		
//		
//		try {
//			// prueba q da error por no definir id de recurso
//			String xml = "<parametro><recurso><tipo>Link</tipo></recurso></parametro>";
//			String response = port.getRecurso(xml);
//			System.out.println(response);
//		} catch (Exception e) {
//			System.out.println("Error al traer la encuesta!");
//			System.out.println(e.toString());
//		}
//		
//		try {
//			// prueba q da error por definir tipo inexistente
//			String xml = "<parametro><recurso><recursoId>1003</recursoId><tipo>abcd</tipo></recurso></parametro>";
//			String response = port.getRecurso(xml);
//			System.out.println(response);
//		} catch (Exception e) {
//			System.out.println("Error al traer la encuesta!");
//			System.out.println(e.toString());
//		}
//		
//		
//		try {
//			// prueba Ok de cache
//			String xml = "<parametro><recurso><recursoId>1003</recursoId><tipo>Encuesta</tipo></recurso></parametro>";
//			String response = port.getRecurso(xml);//ese id de encuesta esta harcodeado en el cache.. mira
//			System.out.println(response);
//		} catch (Exception e) {
//			System.out.println("Error al traer la encuesta!");
//			System.out.println(e.toString());
//		}
//		
//		try {
//			// prueba Ok de cache de nuevo
//			String xml = "<parametro><recurso><recursoId>1004</recursoId><tipo>Encuesta</tipo></recurso></parametro>";
//			String response = port.getRecurso(xml);
//			System.out.println(response);
//		} catch (Exception e) {
//			System.out.println("Error al traer la encuesta!");
//			System.out.println(e.toString());
//		}
//		
//		try {
////		 prueba con datos mockeados de integracion
//			String xml = "<parametro><recurso><recursoId>15</recursoId><tipo>Encuesta</tipo></recurso></parametro>";
//			String response = port.getRecurso(xml);
//			System.out.println(response);
//		} catch (Exception e) {
//			System.out.println("Error al traer la encuesta!");
//			System.out.println(e.toString());
//		}
//		
//		try {
//			//prueba encuesta inexistente
//			String xml = "<parametro><recurso><recursoId>16</recursoId><tipo>Encuesta</tipo></recurso></parametro>";
//			String response = port.getRecurso(xml);
//			System.out.println(response);
//		} catch (Exception e) {
//			System.out.println("Error al traer la encuesta!");
//			System.out.println(e.toString());
//		}
//		
//		try {
//			//prueba encuesta inexistente
//			String xml = "<parametro><recurso><recursoId>16</recursoId><tipo>Encuesta</tipo></recurso></parametro>";
//			String response = port.getRecurso(xml);
//			System.out.println(response);
//		} catch (Exception e) {
//			System.out.println("Error al traer la encuesta!");
//			System.out.println(e.toString());
//		} 

//-------------
//		try {
//			String xmlEncuesta = "<encuesta evaluada='true'><recursoId>1004</recursoId><tipo>Encuesta</tipo><ambitoId>-1</ambitoId><descripcion>una encuesta grande</descripcion><preguntas><pregunta correctas='3' idPregunta='1' enunciado='de que color es el caballo blanco de san martin?'><respuestas>rojo,verde,azul,blanco</respuestas></pregunta><pregunta correctas='1' idPregunta='2' enunciado='a que equipo del futbol argentino le denominan Millo'><respuestas>velez,River Plate,crucero del norte,estudiantes</respuestas></pregunta><pregunta correctas='2' idPregunta='3' enunciado='cual es un patron de diseno creacional'><respuestas>command,mediator,builder,facade</respuestas></pregunta><pregunta correctas='0,7,10,11,12,6' idPregunta='4' enunciado='Un test unitario debe presentar las siguientes caracter�sticas'><respuestas>Rapido,Moldeable,Configurable,Acoplable,Lento,Extensible,Repetible,Profesional,Maduro,Amplio,Simple,Independiente,Automatizable</respuestas></pregunta><pregunta correcta='4' idPregunta='5' enunciado='cuantas patas tiene un gato?'/></preguntas></encuesta>";
//			String xmlParametros = "<parametro><ambitoId>15</ambitoId><usuarioId>23</usuarioId>" + xmlEncuesta	+ "</parametro>";
//			String response = port.agregarEncuesta(xmlParametros);
//			System.out.println(response);
//		} catch (Exception e) {
//			System.out.println("Error al crear la encuesta");
//		}

//-------------
		try {
			//Prueba get Respondida con parametros validos de cache
			String xmlParametro ="<parametro><recurso><recursoId>15</recursoId></recurso><usuarioId>4</usuarioId></parametro>";
			String response = port.getEncuestaRespondida(xmlParametro);
			System.out.println(response);
		}catch (Exception e){
			System.out.println("Error al traer la encuesta Respondida!");
			System.out.println(e.toString());
		}
		
		try {
			//Prueba get otra Respondida con parametros validos de cache
			String xmlParametro ="<parametro><recurso><recursoId>10</recursoId></recurso><usuarioId>5</usuarioId></parametro>";
			String response = port.getEncuestaRespondida(xmlParametro);
			System.out.println(response);
		}catch (Exception e){
			System.out.println("Error al traer la encuesta Respondida!");
			System.out.println(e.toString());
		}
		
		try {
			//Prueba get Respondida con parametros inexistentes
			String xmlParametro ="<parametro><recurso><recursoId>20</recursoId></recurso><usuarioId>4</usuarioId></parametro>";
			String response = port.getEncuestaRespondida(xmlParametro);
			System.out.println(response);
		}catch (Exception e){
			System.out.println("Error al traer la encuesta Respondida!");
			System.out.println(e.toString());
		}
		
		try {
			//Prueba get Respondida sin especificar recursoId
			String xmlParametro ="<parametro><usuarioId>4</usuarioId></parametro>";
			String response = port.getEncuestaRespondida(xmlParametro);
			System.out.println(response);
		}catch (Exception e){
			System.out.println("Error al traer la encuesta Respondida!");
			System.out.println(e.toString());
		}
		
		try {
			//Prueba get Respondida sin especificar usuarioId
			String xmlParametro ="<parametro><recurso><recursoId>15</recursoId></recurso></parametro>";
			String response = port.getEncuestaRespondida(xmlParametro);
			System.out.println(response);
		}catch (Exception e){
			System.out.println("Error al traer la encuesta Respondida!");
			System.out.println(e.toString());
		}
	}

}
