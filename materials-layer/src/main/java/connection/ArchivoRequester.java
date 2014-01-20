package connection;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.activation.DataHandler;

import model.Archivo;
import model.Link;
import model.Recurso;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionStub;
import com.ws.services.IntegracionStub.SeleccionarDatos;
import com.ws.services.IntegracionStub.SeleccionarDatosResponse;

import connection.responses.ArchivoResponse;
import connection.responses.LinkResponse;
import connection.responses.OperationResponse;


public class ArchivoRequester {
	private IntegracionStub stub;
	private ArchivoParser parser;

	public ArchivoRequester() {
		parser = new ArchivoParser();
		
		try {
			stub = new IntegracionStub();
		} catch (AxisFault e) {
			// e.printStackTrace();
			System.out.println("Error al intentar contectarse con Integracion");
		}
	}

	public void save(Archivo archivo) {
		String archivo_str = parser.serializeArchivo(archivo);
		IntegracionStub.GuardarArchivoResponse responseArchivo = null;
		try {
			
			IntegracionStub.GuardarArchivo requestArchivo = new IntegracionStub.GuardarArchivo();
			
//			requestArchivo.setArchivo(archivo.getByteArray()); 
			requestArchivo.setArchivo(archivo.getRawFile()); 
			requestArchivo.setXml(archivo_str);
			
			responseArchivo = this.stub.guardarArchivo(requestArchivo);
			System.out.println("La salida es : " + responseArchivo.get_return());		
		} catch (AxisFault e) {
			// e.printStackTrace();
			System.out.println("Error al intentar guardar la siguiente Archivo:");
			System.out.println(archivo.getDescripcion());
		} catch (RemoteException e) {
			 e.printStackTrace();
			System.out.println("Error de conexion remota");
		}
	}
	public Archivo getArchivo(int idAmbiente, int idRecurso){
		Archivo adevolver = new Archivo();
		String archivoXml = "asc";
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
	
	public OperationResponse get(Recurso recurso) {

		ArchivoResponse response;
		String reason;
		try{
			String xml = this.parser.serializeQueryByType(recurso.getRecursoId(), ArchivoParser.ARCHIVO_TAG);
			SeleccionarDatos seleccionar_e = new SeleccionarDatos();
			seleccionar_e.setXml(xml);
			SeleccionarDatosResponse s_resp_e = this.stub.seleccionarDatos(seleccionar_e);
			String xml_resp_e = s_resp_e.get_return();
			Archivo archivo = this.parser.deserializeArchivo(xml_resp_e);
			archivo.setAmbitoId(recurso.getAmbitoId());
			archivo.setRecursoId(recurso.getRecursoId());
			archivo.setDescripcion(recurso.getDescripcion());


			response = new ArchivoResponse(archivo);
			response.setSuccess(true);
			return response;

			} catch (AxisFault e) {
				reason = "Error al intentar obtener el Archivo, ID: " + recurso.getRecursoId();
			} catch (RemoteException e) {
				reason = "Error de conexion remota";
			}

			System.out.println(reason);
			response = new ArchivoResponse();
			response.setReason(reason);
			
			return response;
		}

	}
