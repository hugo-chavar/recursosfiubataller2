import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.activation.DataHandler;

import model.Archivo;
import model.Encuesta;
import model.Link;
import model.Recurso;
import model.StringEscapeUtils;
import connection.EncuestaRequester;
import connection.LinkRequester;
import connection.Parameter;
import connection.Parser;
import connection.responses.OperationResponse;


public class PruebasParserParameter {

	public static void main(String[] args) {
		
		String prueba = "informacion sobre| el; equipo,, mas grande del universo,:,||";
		prueba = StringEscapeUtils.escapeSpecialCharacters(prueba);
		System.out.println(prueba);
		String prueba2 = StringEscapeUtils.removeEscapers(prueba);
		System.out.println(prueba2);
		prueba += "," + StringEscapeUtils.escapeSpecialCharacters("ho|la");
		prueba += "," + StringEscapeUtils.escapeSpecialCharacters("xx;tt;");
		prueba += "," + StringEscapeUtils.escapeSpecialCharacters("mi,sio;nes|");
		System.out.println(prueba);
		String[] splited = StringEscapeUtils.splitIgnoringEscaped(prueba, ',');
		for (String s : splited){
			System.out.println(s);
		}
		
		splited = StringEscapeUtils.splitIgnoringEscaped("hola mundo xxxx", ',');
		for (String s : splited){
			System.out.println(s);
		}
		
		prueba  = "C;1;De que color es el caballo blanco de San Martin?;blanco|C;2;Cuantas patas tiene un gato?;4";
		splited = StringEscapeUtils.splitIgnoringEscaped(prueba, '|');
		for (String s : splited){
			System.out.println(s);
		}

		

		Parser parser = new Parser();
		Recurso r = new Recurso();
		OperationResponse or;
		String xml;
		Parameter p = new Parameter();
		System.out.println("Prueba marshal de parametros: ");
		p.setRecurso(r);
		r.setAmbitoId(15);
		p.setUsuarioId(23);
		xml = parser.convertToXml(p, p.getClass());
		System.out.println(xml);
		xml = "hola munco: " + xml;

		Parameter p2 = (Parameter) parser.unmarshal(xml, Parameter.class);
		if (p2 == null) {
			System.out.println("p2 null ");
		} else {
			System.out.println("Ambito: " + p2.getRecurso().getAmbitoId());
			System.out.println("Usuario: " + p2.getUsuarioId());
			System.out.println("Recurso: " + p2.getRecurso().getRecursoId());
		}

		xml = "<parametro><recurso><ambitoId>15</ambitoId></recurso><usuarioId>23</usuarioId></parametro>";
		p2 = (Parameter) parser.unmarshal(xml, Parameter.class);
		System.out.println("Ambito: " + p2.getRecurso().getAmbitoId());
		System.out.println("Usuario: " + p2.getUsuarioId());
		System.out.println("Recurso: " + p2.getRecurso().getRecursoId());
		
		Parameter p3 = (Parameter) parser.unmarshal("hola", Parameter.class);
		System.out.println("Parameter: " + p3);
		
		System.out.println("Prueba marshal de encuesta: ");	
//		or = (new EncuestaRequester()).getFromCache(11004);
		or = (new EncuestaRequester()).getFromCache(new Encuesta(11004, null, null, false));

		Encuesta encuesta = (Encuesta)or.getSerializable();
//		
		xml = parser.convertToXml(encuesta, Encuesta.class);
		
		System.out.println(xml);
		
		Encuesta enc2 = (Encuesta)parser.unmarshal(xml, Encuesta.class);
		
		or = (new LinkRequester()).getFromCache(new Link(11002, null, null));

		Link link = (Link)or.getSerializable();
		
		xml = parser.convertToXml(link, Link.class);
		
		System.out.println(xml);
		
		
		
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
			path = PruebasParserParameter.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
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
		

	}

}
