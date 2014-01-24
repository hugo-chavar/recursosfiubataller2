package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.activation.DataHandler;
import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPBinding;

import model.Archivo;
import model.Encuesta;
import model.Recurso;
import connection.EncuestaRequester;
import connection.Parameter;
import connection.Parser;
import connection.Requester;
import connection.responses.OperationResponse;

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

		Parser parser = new Parser();
		Recurso r = new Recurso();
		OperationResponse or;
		String xml;
		Parameter p = new Parameter();
//		System.out.println("Prueba marshal de parametros: ");
//		p.setRecurso(r);
//		r.setAmbitoId(15);
//		p.setUsuarioId(23);
//		xml = parser.convertToXml(p, p.getClass());
//		System.out.println(xml);
//
//		Parameter p2 = (Parameter) parser.unmarshal(xml, Parameter.class);
//		System.out.println("Ambito: " + p2.getRecurso().getAmbitoId());
//		System.out.println("Usuario: " + p2.getUsuarioId());
//		System.out.println("Recurso: " + p2.getRecurso().getRecursoId());
//
//		xml = "<parametro><recurso><ambitoId>15</ambitoId></recurso><usuarioId>23</usuarioId></parametro>";
//		p2 = (Parameter) parser.unmarshal(xml, Parameter.class);
//		System.out.println("Ambito: " + p2.getRecurso().getAmbitoId());
//		System.out.println("Usuario: " + p2.getUsuarioId());
//		System.out.println("Recurso: " + p2.getRecurso().getRecursoId());
//		
//		Parameter p3 = (Parameter) parser.unmarshal("hola", Parameter.class);
//		System.out.println("Parameter: " + p3);
//		
		System.out.println("Prueba marshal de encuesta: ");
		or = (new EncuestaRequester()).getFromCache(11004);

		Encuesta encuesta = (Encuesta)or.getRecurso();
		
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
		
		xml = "<parametro><recurso><recursoId>11003</recursoId></recurso></parametro>";
		ep2 = (Parameter) parser.unmarshal(xml, Parameter.class);
		System.out.println(ep2.getRecurso().getRecursoId());
		
		/*********Pruebas**************/
		Archivo archivo = new Archivo();
		archivo.setAmbitoId(14);
		archivo.setNombreArchivo("River Plate");
		archivo.setDescripcion("informacion sobre el equipo mas grande del universo");
		String extension = "jpg";
		archivo.setTipoArchivo(extension);
		try {
			String path;
			path = WSPublisher.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
			path = path.substring(0, path.lastIndexOf("classes") + 8);
			path = path + "teofilo."+ extension;
			DataHandler arch = new DataHandler(new URL(path));
			archivo.setRawFile(arch);
			String xml2 = parser.convertToXml(archivo, archivo.getClass());
			System.out.println("El archivo: ");
			System.out.println(xml2);
			System.out.println("Genero archivo a partir de xml: ");
			Archivo arch2 = (Archivo) parser.unmarshal(xml2, Archivo.class);
			if (arch2 == null){
				return;
			}
			
			DataHandler handler = arch2.getRawFile();
			path = System.getProperty("user.home")+ System.getProperty("file.separator")+"generadaXws.jpg";
			OutputStream os;
			try {
				os = new FileOutputStream(new File(path));
				handler.writeTo(os);
				os.close();
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

		} catch (MalformedURLException e) {
			System.out.println("no existe el URL asigando");
			System.out.println(e.getMessage());
			return;
		}
		
		// prueba encuesta que no existe
		xml = "<parametro><recurso><recursoId>16</recursoId><tipo>Encuesta</tipo></recurso></parametro>";
		p = Parameter.createParameter(xml);
		or = Requester.INSTANCE.getRecurso(p.getRecurso());
		if (!or.getSuccess()) {
			System.out.println(or.getReason());
		}

		// prueba Archivo que existe
		xml = "<parametro><recurso><recursoId>1003</recursoId><tipo>Archivo</tipo></recurso></parametro>";
		p = Parameter.createParameter(xml);
		System.out.println(p.getRecurso().getRecursoId());
		or = Requester.INSTANCE.getRecurso(p.getRecurso());
		if (!or.getSuccess()) {
			System.out.println(or.getReason());
		}
		
		// prueba encuesta que existe en cache de recursos pero no de encuestas
		xml = "<parametro><recurso><recursoId>996</recursoId><tipo>Encuesta</tipo></recurso></parametro>";
		p = Parameter.createParameter(xml);
		System.out.println(p.getRecurso().getRecursoId());
		or = Requester.INSTANCE.getRecurso(p.getRecurso());
		if (!or.getSuccess()) {
			System.out.println(or.getReason());
		}
		
		
//		String absolute = WSPublisher.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();

//		System.out.println(absolute);
		System.out.println("Programa terminado. ");

	}

}
