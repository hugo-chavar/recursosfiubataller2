import connection.IntegracionProxy;
import connection.exceptions.ConnectionException;

public class ClientePruebaContraIntegracion {

	private static IntegracionProxy ip = new IntegracionProxy();
	private static String xml;

	public static void main(String[] args) {

		seleccionar("<WS><Usuario><username>javier</username></Usuario></WS>");

		seleccionar("<WS><Usuario><id>117</id></Usuario></WS>");

		seleccionar("<WS><Link><id>1002</id></Link></WS>");

		seleccionar("<WS><Recurso><id>997</id></Recurso></WS>");

		seleccionar("<WS><Recurso><ambitoId>1</ambitoId></Recurso></WS>");

		seleccionar("<WS><Link><recursoId>997</recursoId></Link></WS>");

		seleccionar("<WS><Encuesta><id>1003</id></Encuesta></WS>");

		seleccionar("<WS><EncuestaRespondida><id>1003</id></EncuestaRespondida></WS>");

		seleccionar("<WS><Recurso><descripcion>prueba</descripcion></Recurso></WS>");

		guardar("<WS><Link><id>400</id><nombre>www.hola.com</nombre></Link></WS>");

		guardar("<WS><Recurso><descripcion>prueba</descripcion><tipo>L</tipo></Recurso></WS>");

		guardar("<?xml version=\"1.0\"?><WS><Link><nombre>prueba</nombre></Link></WS>");

		eliminar("<WS><Recurso><id>1006</id></Recurso></WS>");

	}

	private static void eliminar(String xmlInput) {
		try {
			xml = ip.eliminar(xmlInput);
			System.out.println(xml);
		} catch (ConnectionException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void guardar(String xmlInput) {
		try {
			xml = ip.guardar(xmlInput);
			System.out.println(xml);
		} catch (ConnectionException e1) {
			System.out.println(e1.getMessage());
		}
	}

	private static void seleccionar(String xmlInput) {
		try {
			xml = ip.seleccionar(xmlInput);
			System.out.println(xml);
		} catch (ConnectionException e) {
			System.out.println(e.getMessage());
		}
	}

}
