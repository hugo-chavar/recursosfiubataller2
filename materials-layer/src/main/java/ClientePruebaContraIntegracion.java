//import model.Encuesta;
//import model.PreguntaRespuestaACompletar;

//import connection.Requester;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionStub.GuardarDatos;
import com.ws.services.IntegracionStub.GuardarDatosResponse;
//import com.ws.services.IntegracionWSStub.SeleccionarDatos;
//import com.ws.services.IntegracionWSStub.SeleccionarDatosResponse;
import com.ws.services.IntegracionStub;

public class ClientePruebaContraIntegracion {

	public static void main(String[] args) {

//		Encuesta encuesta = new Encuesta(0, 1, "Encuesta de prueba", false);
//		PreguntaRespuestaACompletar pregunta1 = new PreguntaRespuestaACompletar("De que color es el caballo blanco de San Martin?", 0);
//		encuesta.agregarPregunta(pregunta1);
//		PreguntaRespuestaACompletar pregunta2 = new PreguntaRespuestaACompletar("A cuantos km se encuentra Bariloche de Buenos Aires?", 1);
//		encuesta.agregarPregunta(pregunta2);
//		Requester.INSTANCE.saveEncuesta(encuesta);
//		encuesta = Requester.INSTANCE.getEncuesta(1, 0);
		
		IntegracionStub stub;

		try {
			stub = new IntegracionStub();
			
	    	GuardarDatos guardar = new GuardarDatos();
	    	guardar.setXml("<WS><hola><id>IdAmbiente</id><id>IdRecurso</id></hola></WS>");
	    	GuardarDatosResponse g_resp = stub.guardarDatos(guardar);
	    	
	    	System.out.println("Respuesta recibida de Integracion:");
	    	System.out.println(g_resp.get_return());
	    	System.out.println("Todo anda OK!");
	    	
		} catch (AxisFault e) {
			System.out.println(e.getMessage());
			System.out.println("Hay errores de AxisFault.. tal vez falta una libreria.. avisa a HUGO ");
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
			System.out.println("Hay errores de RemoteException.. levantaste el servicio? ");
		}


	}

}
