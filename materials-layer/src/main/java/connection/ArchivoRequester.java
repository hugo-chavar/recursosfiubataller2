package connection;

import java.rmi.RemoteException;

import model.Archivo;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionWSStub;
import com.ws.services.IntegracionWSStub.GuardarDatos;
import com.ws.services.IntegracionWSStub.GuardarDatosResponse;

import connection.cache.Cache;

public class ArchivoRequester {
	private IntegracionWSStub stub;
	private ArchivoParser parser;

	public ArchivoRequester() {
		parser = new ArchivoParser();
		
		try {
			stub = new IntegracionWSStub();
		} catch (AxisFault e) {
			// e.printStackTrace();
			System.out.println("Error al intentar contectarse con Integracion");
		}
	}

	public void save(Archivo encuesta) {
	/*	String encuesta_str = parser.serializeArchivo(encuesta);
		try {
			GuardarDatos guardar = new GuardarArchivo();
			guardar.setXml(encuesta_str);
			GuardarDatosResponse g_resp = stub.guardarDatos(guardar);
			System.out.println(g_resp.get_return());
		} catch (AxisFault e) {
			// e.printStackTrace();
			System.out.println("Error al intentar guardar la siguiente Archivo:");
			System.out.println(encuesta.getDescripcion());
		} catch (RemoteException e) {
			// e.printStackTrace();
			System.out.println("Error de conexion remota");
		}
*/
		// Agrego al cache de encuestas
		// Yami, la primera vez que viene no tiene el Id, no podes grabarlo aca
		// solo ponelo en el cache cuando lo recuperas de integracion
		// this.addToCacheArchivos(encuesta);
	}
}
