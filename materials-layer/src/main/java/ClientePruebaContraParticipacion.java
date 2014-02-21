import connection.ParticipacionProxy;
import connection.exceptions.ConnectionException;



public class ClientePruebaContraParticipacion {
	
	public static void main(String[] args) {
		ParticipacionProxy ip = new ParticipacionProxy();
		boolean puede  =  true;
		
		System.out.println("Error");

//		try {
//			puede = ip.puedeEditar(0, 0);
			if (puede) {
				System.out.println("si puede");
			} else {
				System.out.println("no puede");
			}
//		} catch (ConnectionException e) {
//			System.out.println("Error");
//		}
	}

}
