package connection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.activation.DataHandler;
import javax.activation.DataSource;

import model.Archivo;
import model.Link;
import model.Recurso;

import org.apache.axis2.AxisFault;



//import com.sun.xml.internal.ws.util.ByteArrayDataSource;
import com.ws.services.IntegracionStub;
import com.ws.services.IntegracionStub.SeleccionarDatos;
import com.ws.services.IntegracionStub.SeleccionarDatosResponse;

import connection.cache.Cache;
import connection.responses.ArchivoResponse;
import connection.responses.OperationResponse;

public class ArchivoRequester extends HandlerRequester {
	// private IntegracionStub stub;
	private ArchivoParser parser;
	private Cache<Archivo> cache;
	private Recurso current;

	public ArchivoRequester() {
		super();
		parser = new ArchivoParser();
		cache = new Cache<Archivo>();
	}

	public String save(Archivo archivo) {
		current = archivo; //Dami.. esta linea es muy necesaria, revisa el LinkRequester y su clase padre HandlerRequester
		String archivo_str = parser.serializeArchivo(archivo);
		IntegracionStub.GuardarArchivoResponse responseArchivo = null;
		try {

			IntegracionStub.GuardarArchivo requestArchivo = new IntegracionStub.GuardarArchivo();

			// requestArchivo.setArchivo(archivo.getByteArray());
			requestArchivo.setArchivo(archivo.getRawFile());
			requestArchivo.setXml(archivo_str);

			responseArchivo = this.stub.guardarArchivo(requestArchivo);
			return "";
		} catch (AxisFault e) {
			// e.printStackTrace();
			// System.out.println("Error al intentar guardar la siguiente Archivo:");
			// System.out.println(archivo.getDescripcion());
			return "error al guardar archivo";
		} catch (RemoteException e) {
			// e.printStackTrace();
			// System.out.println("Error de conexion remota");
			return "error de conexion";
		}
	}

	public Archivo getArchivo(int idAmbiente, int idRecurso) {
		Archivo adevolver = new Archivo();
		String archivoXml = "asc";
		byte[] archivoRecuperado;
		// IntegracionStub. responseArchivo = null;
		// try {

		// requestArchivo = new IntegracionStub.ObtenerArchivo();

		// OP1 archivoRecuperado =
		// requestArchivo.getArchivo(idAmbiente,idRecurso); asi me dijeron por
		// mail
		// OP2 requestArchivo.setArchivo(archivo.getFile()); asi lo hacen por el
		// ejemplo q me mandaron
		// String archivoXml = requestArchivo.getXml(archivo_str);

		// Archivo adevolver = parser.deserializeArchivo(archivoXml);
		// adevolver.setByteArray(archivoRecuperado);
		// responseArchivo = this.stub.guardarArchivo(requestArchivo);
		// System.out.println("La salida es : " + responseArchivo.get_return());
		// } catch (AxisFault e) {
		// e.printStackTrace();

		try {
			System.out.println("Entra a pedir el archivo");
			String path;
			path = "file:" + System.getProperty("user.dir") + "\\webapps\\Materials\\index.jsp";
			DataHandler arch = new DataHandler(new URL(path));
			// DataHandler arch = new DataHandler(new
			// URL("file:/home/damian/aux"));
			adevolver.setRawFile(arch);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return adevolver;

	}

	public OperationResponse get(Recurso recurso) {
		// System.out.println("Entra al get de Archivo Request");
		ArchivoResponse response;
		String reason;
		// try{
		String xml = this.parser.serializeQueryByType(recurso.getRecursoId(), ArchivoParser.ARCHIVO_TAG);
		SeleccionarDatos seleccionar_e = new SeleccionarDatos();
		seleccionar_e.setXml(xml);
		/*
		 * SeleccionarDatosResponse s_resp_e =
		 * this.stub.seleccionarDatos(seleccionar_e);// Integrar con lo nuevo de
		 * integracion String xml_resp_e = s_resp_e.get_return(); Archivo
		 * archivo = this.parser.deserializeArchivo(xml_resp_e);
		 * archivo.setAmbitoId(recurso.getAmbitoId());
		 * archivo.setRecursoId(recurso.getRecursoId());
		 * archivo.setDescripcion(recurso.getDescripcion());
		 */

		/********* Pruebas **************/
		Archivo archivo = new Archivo();
		archivo.setAmbitoId(14);
		archivo.setNombreArchivo("River Plate");
		archivo.setDescripcion("este archivo contiene informacion sobre el equipo mas grande del universo");
		archivo.setTipoArchivo("png");
		try {
			String path;
			// path = "file:"+ System.getProperty("user.dir") +
			// "\\webapps\\Materials\\index.jsp";
			path = "file:C:\\Users\\HugoW7\\Desktop\\diagrama.png";
			DataHandler arch = new DataHandler(new URL(path));
			// DataHandler arch = new DataHandler(new
			// URL("file:/home/damian/aux.txt"));
			// System.out.println("abre aux.txt");
			// DataHandler arch = new DataHandler(new
			// URL("file:/home/damian/aux.txt"));
			// System.out.println("el data handler tiene como mime: "+arch.getContentType());

			archivo.setRawFile(arch);
			// byte[] fileBinary;
			// try {
			// fileBinary = archivo.getByteArray();
			// //System.out.println("como archivo deja "+archivo.getStringFile());
			// DataSource dataSource = new
			// ByteArrayDataSource(fileBinary,"text/plain");
			// DataHandler dh = new DataHandler(dataSource);
			// InputStream is;
			// try {
			// is = dh.getInputStream();
			// OutputStream os = new FileOutputStream(new
			// File("/home/damian/salidas/1RiverPlate.txt"));
			//
			//
			// byte[] buffer = new byte[1024];
			// int bytesRead = 0;
			// while ((bytesRead = is.read(buffer)) != -1) {
			// os.write(buffer,0,bytesRead);
			// }
			// dh.writeTo(os);
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			//
			//
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

		} catch (MalformedURLException e) {
			System.out.println("no existe el URL asigando");
			e.printStackTrace();
		}
		/***** FIN PRUEBAS *****/
		response = new ArchivoResponse(archivo);
		response.setSuccess(true);
		return response;

		/*
		 * } catch (AxisFault e) { reason =
		 * "Error al intentar obtener el Archivo, ID: " +
		 * recurso.getRecursoId(); } catch (RemoteException e) { reason =
		 * "Error de conexion remota"; }
		 */
		/*
		 * System.out.println(reason); response = new ArchivoResponse();
		 * response.setReason(reason);
		 * 
		 * return response;
		 */
	}

	@Override
	protected String getHandledType() {
		return "Archivo";
	}

	@Override
	protected void createCurrentObject(String xml_resp_e) {
		// TODO Auto-generated method stub

	}

//	@Override
//	protected Recurso getCurrent() {
//		return current;
//	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Cache getCache() {
		return cache;
	}

	@Override
	public void deleteFromCache(int recursoId) {
		cache.remove(new Archivo(recursoId, 0, ""));
	}

	@Override
	protected boolean cacheContains(int recursoId) {
		return cache.contains(new Archivo(recursoId, 0, ""));
	}

	@Override
	protected Recurso retrieveCached(int recursoId) {
		return cache.get(new Archivo(recursoId, 0, ""));
	}

	@Override
	protected Parser getParser() {
		return parser;
	}
	
	@Override
	protected void updateCache() {
		if (currentHasValidSize()) {
			super.updateCache();
		}
	}

	private boolean currentHasValidSize() {
		// TODO Dami aca chequea si x el tamanio del archivo se puede cachear (limite 50k)
		return false;
	}

	@Override
	protected Recurso deserialize(String xml_resp_e) {
		return parser.deserializeArchivo(xml_resp_e);
	}

}
