package connection;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.activation.DataHandler;

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

	public void save(Archivo archivo) {
		String archivo_str = parser.serializeArchivo(archivo);
		//IntegracionStub.GuardarArchivoResponse responseArchivo = null;
	//	try {
			
		//	requestArchivo = new IntegracionStub.GuardarArchivo();
			
		//OP1	requestArchivo.setArchivo(archivo.getByteArray()); asi me dijeron por mail
		//OP2	requestArchivo.setArchivo(archivo.getFile()); asi lo hacen por el ejemplo q me mandaron
		//	requestArchivo.setXml(archivo_str);
			
		//	responseArchivo = this.stub.guardarArchivo(requestArchivo);
		//	System.out.println("La salida es : " + responseArchivo.get_return());		
	//	} catch (AxisFault e) {
			// e.printStackTrace();
			System.out.println("Error al intentar guardar la siguiente Archivo:");
			System.out.println(archivo.getDescripcion());
	//	} catch (RemoteException e) {
			// e.printStackTrace();
			System.out.println("Error de conexion remota");
	//	}
	}
	public Archivo getArchivo(int idAmbiente, int idRecurso){
		Archivo adevolver =  new Archivo();
		String archivoXml="asc";
		byte[] archivoRecuperado;
		//IntegracionStub.ObtenerArchivoResponse responseArchivo = null;
		//	try {
				
			//	requestArchivo = new IntegracionStub.ObtenerArchivo();
				
			//OP1 	archivoRecuperado = requestArchivo.getArchivo(idAmbiente,idRecurso); asi me dijeron por mail
			//OP2	requestArchivo.setArchivo(archivo.getFile()); asi lo hacen por el ejemplo q me mandaron
			//	String archivoXml = requestArchivo.getXml(archivo_str);
				
			//	Archivo adevolver = parser.deserializeArchivo(archivoXml);
			//	adevolver.setByteArray(archivoRecuperado);
			//	responseArchivo = this.stub.guardarArchivo(requestArchivo);
			//	System.out.println("La salida es : " + responseArchivo.get_return());		
		//	} catch (AxisFault e) {
				// e.printStackTrace();
		
		try {
			System.out.println("Entra a pedir el archivo");
			DataHandler arch = new DataHandler(new URL("/home/damian/aux"));
			adevolver.setRawFile(arch);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return adevolver;
		
	}
}
