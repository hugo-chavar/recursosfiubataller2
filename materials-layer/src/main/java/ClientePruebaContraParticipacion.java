import connection.ParticipacionProxy;
import connection.exceptions.ConnectionException;



public class ClientePruebaContraParticipacion {
	
	private static ParticipacionProxy ip = new ParticipacionProxy();

	public static void main(String[] args) {

		try {
			boolean puede = ip.puedeEditar(0, 0);
			if (puede) {
				System.out.println("si puede");
			} else {
				System.out.println("no puede");
			}
		} catch (ConnectionException e) {
			System.out.println("Error");
		}
	}

}
