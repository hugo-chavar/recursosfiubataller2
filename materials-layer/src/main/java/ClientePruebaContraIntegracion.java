

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionWSStub.GuardarDatos;
import com.ws.services.IntegracionWSStub.GuardarDatosResponse;
//import com.ws.services.IntegracionWSStub.SeleccionarDatos;
//import com.ws.services.IntegracionWSStub.SeleccionarDatosResponse;
import com.ws.services.IntegracionWSStub;

public class ClientePruebaContraIntegracion {

	public static void main(String[] args) {

		IntegracionWSStub stub;

		try {
			stub = new IntegracionWSStub();
			
	    	GuardarDatos guardar = new GuardarDatos();
	    	guardar.setXml("<WS><hola><id>IdAmbiente</id><id>IdRecurso</id></hola></WS>");
	    	GuardarDatosResponse g_resp = stub.guardarDatos(guardar);
	    	
	    	System.out.println("Respuesta recibida de Integracion:");
	    	System.out.println(g_resp.get_return());
	    	System.out.println("Todo anda OK!");
	    	
		} catch (AxisFault e) {
			e.printStackTrace();
			System.out.println("Hay errores de AxisFault.. tal vez falta una libreria.. avisa a HUGO ");
		} catch (RemoteException e) {
			e.printStackTrace();
			System.out.println("Hay errores de RemoteException.. levantaste el servicio? ");
		}


	}

}
