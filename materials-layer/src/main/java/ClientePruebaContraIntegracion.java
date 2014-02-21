import java.net.MalformedURLException;
import java.net.URL;

import javax.activation.DataHandler;

import com.ws.services.IntegracionStub;

import connection.IntegracionProxy;
import connection.exceptions.ConnectionException;

@SuppressWarnings("unused")
public class ClientePruebaContraIntegracion {

	private static IntegracionProxy ip = new IntegracionProxy();
	private static String xml;

	public static void main(String[] args) {
		
//		seleccionar("<WS><Usuario><username>javier</username></Usuario></WS>");
//
//		seleccionar("<WS><Usuario><id>117</id></Usuario></WS>");
//
//		seleccionar("<WS><Link><id>997</id></Link></WS>");
//
//		seleccionar("<WS><Recurso><id>997</id></Recurso></WS>");
//
//		seleccionar("<WS><Recurso><ambitoId>1</ambitoId></Recurso></WS>");
//
//		seleccionar("<WS><Link><recursoId>997</recursoId></Link></WS>");
//
		seleccionar("<WS><Encuesta><id>1030</id></Encuesta></WS>");
//
//		seleccionar("<WS><EncuestaRespondida><recursoId>1003</recursoId><usuarioId>19</usuarioId></EncuestaRespondida></WS>");
		
//		guardar("<WS><EncuestaRespondida><recursoId>1003</recursoId><usuarioId>19</usuarioId><preguntasRespondidas>a,b,c</preguntasRespondidas></EncuestaRespondida></WS>");
//
		seleccionar("<WS><Recurso><id>1095</id></Recurso></WS>");
//
//		guardar("<WS><Link><recursoId>1030</recursoId><nombre>www.hola.com</nombre></Link></WS>");
//
//		guardar("<WS><Recurso><descripcion>prueba Dami</descripcion><tipo>L</tipo></Recurso></WS>");
//
//		guardar("<?xml version=\"1.0\"?><WS><Link><nombre>prueba</nombre></Link></WS>");
//
//		eliminar("<WS><Recurso><id>1016</id></Recurso></WS>");
		
//		guardar("<WS><Encuesta><recursoId>1011</recursoId><evaluada>false</evaluada><preguntas>jha</preguntas></Encuesta></WS>");
		
//		guardar("<WS><Encuesta><recursoId>1031</recursoId><evaluada>false</evaluada><preguntas>abc</preguntas></Encuesta></WS>");
//		String path, nombre, extension;
//		nombre = "hola";
//		extension = "txt";
//		guardar("<WS><Recurso><ambitoId>12</ambitoId><descripcion>Una clase java</descripcion><tipo>A</tipo></Recurso></WS>");
////		guardar("<WS><Recurso><ambitoId>5</ambitoId><descripcion>Archivo chico Prueba </descripcion><tipo>A</tipo></Recurso></WS>");
//		path = ClientePruebaContraIntegracion.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
//		path = path.substring(0, path.lastIndexOf("classes") + 8);
//		path = path + nombre + "." + extension;
//		
//		System.out.println(path);
//		path = "file:/home/damian/hola.txt";
//		xml = "<WS><ArchivoMetadata><nombre>hola</nombre><tamanio>14</tamanio><tipo>txt</tipo><recursoId>1036</recursoId></ArchivoMetadata></WS>";
////		xml = "<WS><ArchivoMetadata><nombre>teofilo</nombre><recursoId>1012</recursoId></ArchivoMetadata></WS>";
////		xml = "<WS><ArchivoMetadata><nombre>teofilo</nombre><id>1018</id></ArchivoMetadata></WS>";
////		guardarArchivo(xml, path);
////		
////		PruebasDeArchivo();
		
//		xml = "<WS><Archivo><recursoId>1037</recursoId></Archivo></WS>";
//		try {
//			IntegracionStub.ArchivoMetadata[] objects = ip.seleccionarArchivo(xml);
//		} catch (ConnectionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

	private static void PruebasDeArchivo(){
		String path, nombre, extension;
		
		System.out.println("COMIENZA TESTEO DE ARCHIVOS");
		nombre = "teofilo";
		extension = "jpg";
		path = ClientePruebaContraIntegracion.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
		path = path.substring(0, path.lastIndexOf("classes") + 8);
		path = path + nombre + "." + extension;
	
//		path = "/home/damian/hola.txt";
		path = "C:\\Users\\HugoW7\\Desktop\\proxyfacu2.txt";
//		xml = "<?xml version=\"1.0\"?><WS><ArchivoMetadata><nombre>teofilo2</nombre><tamanio>1</tamanio><tipo>jpg</tipo><recursoId>1012</recursoId></ArchivoMetadata></WS>";
		xml = "<WS><ArchivoMetadata><recursoId>1035</recursoId></ArchivoMetadata></WS>";
//		xml = "<WS><ArchivoMetadata><nombre>teofilo</nombre><recursoId>1012</recursoId></ArchivoMetadata></WS>";
//		xml = "<WS><ArchivoMetadata><nombre>teofilo</nombre><id>1012</id></ArchivoMetadata></WS>";
//		System.out.println("Va a guardar un archivo");
		System.out.println("TEST 1 TRAER ARCHIVO EXISTENTE");
//		try {
//			xml = "<?xml version=\"1.0\"?><WS><ArchivoMetadata><recursoId>1035</recursoId></ArchivoMetadata></WS>";
//			com.ws.services.IntegracionStub.ArchivoMetadata[] objects = ip.seleccionarArchivo(xml);
//			if(objects==null){
//				System.out.println("La consulta no trajo resultados");
//				
//			}else{
//				System.out.println("Algo Trajo");
//				System.out.println("Cantidad: " + objects.length);
//				com.ws.services.IntegracionStub.ArchivoMetadata supuestoArchivo = objects[0];
//				
//				System.out.println("Nombre de lo que trajo: "+supuestoArchivo.getNombre());
//			}
//		} catch (ConnectionException e) {
//			System.out.println("No lo pudo traer al archivo");
//			//e.printStackTrace();
//		}
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
