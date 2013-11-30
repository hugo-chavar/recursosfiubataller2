

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
	    	System.out.println(g_resp.get_return());
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
