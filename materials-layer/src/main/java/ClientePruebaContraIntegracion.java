import connection.IntegracionProxy;
import connection.exceptions.ConnectionException;

public class ClientePruebaContraIntegracion {

	public static void main(String[] args) {

		IntegracionProxy ip = new IntegracionProxy();
		String xml;
		
//		try {
//			xml = ip.guardar("<WS><Link><id>400</id><nombre>www.hola.com</nombre></Link></WS>");
//			System.out.println(xml);
//		} catch (ConnectionException e1) {
//			System.out.println(e1.getMessage());
//		}
//
//		try {
//			xml = ip.seleccionar("<WS><Usuario><username>javier</username></Usuario></WS>");
//			System.out.println(xml);
//		} catch (ConnectionException e) {
//			System.out.println(e.getMessage());
//		}
//		try {
//			xml = ip.seleccionar("<WS><Usuario><id>117</id></Usuario></WS>");
//			System.out.println(xml);
//		} catch (ConnectionException e) {
//			System.out.println(e.getMessage());
//		}
//		try {
//			xml = ip.seleccionar("<WS><Link><id>1002</id></Link></WS>");
//			System.out.println(xml);
//		} catch (ConnectionException e) {
//			System.out.println(e.getMessage());
//		}
//
//		try {
//			xml = ip.seleccionar("<WS><Recurso><id>1009</id></Recurso></WS>");
//			System.out.println(xml);
//		} catch (ConnectionException e) {
//			System.out.println(e.getMessage());
//		}
//		try {
//			xml = ip.seleccionar("<WS><Recurso><descripcion>prueba</descripcion></Recurso></WS>");
//			System.out.println(xml);
//		} catch (ConnectionException e) {
//			System.out.println(e.getMessage());
//		}
//		try {
//			xml = ip.guardar("<WS><Recurso><descripcion>prueba</descripcion><tipo>L</tipo></Recurso></WS>");
//			System.out.println(xml);
//		} catch (ConnectionException e) {
//			System.out.println(e.getMessage());
//		}
//		try {
//			xml = ip.guardar("<?xml version=\"1.0\"?><WS><Link><nombre>prueba</nombre></Link></WS>");
//			System.out.println(xml);
//		} catch (ConnectionException e) {
//			System.out.println(e.getMessage());
//		}
		
		try {
			xml = ip.eliminar("<WS><Recurso><id>1002</id></Recurso></WS>");
			System.out.println(xml);
		} catch (ConnectionException e) {
			System.out.println(e.getMessage());
		}

	}

}
