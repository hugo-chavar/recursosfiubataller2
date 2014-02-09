import java.net.MalformedURLException;
import java.net.URL;

import javax.activation.DataHandler;

import connection.IntegracionProxy;
import connection.exceptions.ConnectionException;

public class ClientePruebaContraIntegracion {

	private static IntegracionProxy ip = new IntegracionProxy();
	private static String xml;

	public static void main(String[] args) {
		
//		seleccionar("<WS><Usuario><username>javier</username></Usuario></WS>");
//
//		seleccionar("<WS><Usuario><id>117</id></Usuario></WS>");
//
		seleccionar("<WS><Link><id>1002</id></Link></WS>");
//
//		seleccionar("<WS><Recurso><id>997</id></Recurso></WS>");
//
//		seleccionar("<WS><Recurso><ambitoId>1</ambitoId></Recurso></WS>");
//
//		seleccionar("<WS><Link><recursoId>997</recursoId></Link></WS>");

		seleccionar("<WS><Encuesta><id>1003</id></Encuesta></WS>");

//		seleccionar("<WS><EncuestaRespondida><id>1003</id></EncuestaRespondida></WS>");
//
//		seleccionar("<WS><Recurso><descripcion>prueba</descripcion></Recurso></WS>");
//
		guardar("<WS><Link><id>1019</id><nombre>www.hola.com</nombre></Link></WS>");
//
//		guardar("<WS><Recurso><descripcion>prueba</descripcion><tipo>L</tipo></Recurso></WS>");
//
//		guardar("<?xml version=\"1.0\"?><WS><Link><nombre>prueba</nombre></Link></WS>");
//
		eliminar("<WS><Recurso><id>1010</id></Recurso></WS>");
		
		String path, nombre, extension;
		
		nombre = "teofilo";
		extension = "jpg";
		path = ClientePruebaContraIntegracion.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
		path = path.substring(0, path.lastIndexOf("classes") + 8);
		path = path + nombre + "." + extension;
		
//		xml = "<?xml version=\"1.0\"?><WS><ArchivoMetadata><nombre>teofilo</nombre><tamanio>1</tamanio><tipo>txt</tipo><recursoId>1010</recursoId></ArchivoMetadata></WS>";
//		xml = "<WS><ArchivoMetadata><nombre>teofilo</nombre><recursoId>1012</recursoId></ArchivoMetadata></WS>";
		xml = "<WS><ArchivoMetadata><nombre>teofilo</nombre><id>1012</id></ArchivoMetadata></WS>";

		guardarArchivo(xml, path);

	}

	private static void eliminar(String xmlInput) {
		System.out.println("Eliminando " + xmlInput.substring(0, Math.min(xmlInput.length(), 45)) + "..");
		try {
			xml = ip.eliminar(xmlInput);
			System.out.println(xml);
		} catch (ConnectionException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void guardar(String xmlInput) {
		System.out.println("Guardando " + xmlInput.substring(0, Math.min(xmlInput.length(), 45)) + "..");
		try {
			xml = ip.guardar(xmlInput);
			System.out.println(xml);
		} catch (ConnectionException e1) {
			System.out.println(e1.getMessage());
		}
	}

	private static void seleccionar(String xmlInput) {
		System.out.println("Seleccionando " + xmlInput.substring(0, Math.min(xmlInput.length(), 45)) + "..");
		try {
			xml = ip.seleccionar(xmlInput);
			System.out.println(xml);
		} catch (ConnectionException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void guardarArchivo(String xmlInput, String path) {
		System.out.println("Guardando archivo " + xmlInput.substring(0, Math.min(xmlInput.length(), 45)) + "..");
		try {
			DataHandler file = new DataHandler(new URL(path));
			xml = ip.guardarArchivo(xmlInput, file);
			System.out.println(xml);
		} catch (ConnectionException e) {
			System.out.println(e.getMessage());
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
	}

}
