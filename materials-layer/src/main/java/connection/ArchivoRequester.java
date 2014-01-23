package connection;


import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.activation.DataHandler;



import model.Archivo;
import model.Encuesta;
import model.Recurso;

import org.apache.axis2.AxisFault;



//import com.sun.xml.internal.ws.util.ByteArrayDataSource;
import com.ws.services.IntegracionStub;


import connection.cache.Cache;

import connection.responses.OperationResponse;

public class ArchivoRequester extends HandlerRequester {
	// private IntegracionStub stub;
	private ArchivoParser parser;
	private Cache<Archivo> cache;

	public ArchivoRequester() {
		super();
		parser = new ArchivoParser();
		cache = new Cache<Archivo>();
	}

	public OperationResponse save(Archivo archivo) {
		current = archivo; 
		String archivo_str = parser.serializeArchivo(archivo);
		return saveFile(archivo_str);
	}

	//Este metodo es el que consulta a integración y trae el archivo necesario.
	public OperationResponse get(Recurso recurso) {
		
		OperationResponse response;
		String reason;
		String xml = this.parser.serializeQueryByType(recurso.getRecursoId(), ArchivoParser.ARCHIVO_TAG);
		response = getFile ( xml ); 
		if(response.getSuccess()==false)
				return harcodeoDeArchivo();
		return response;
	}
	//Metodo privado para testear.
	private OperationResponse harcodeoDeArchivo(){
		OperationResponse response;
		Archivo archivo = new Archivo();
		archivo.setAmbitoId(14);
		archivo.setNombreArchivo("teofilo");
		archivo.setDescripcion("Campeon clausura 2014");
		archivo.setTipoArchivo("jpg");
		try {
			String path;
			path = "file:/home/damian/voucherAbuela.pdf";
			DataHandler arch = new DataHandler(new URL(path));
			archivo.setRawFile(arch);
		} catch (MalformedURLException e) {
			System.out.println("no existe el URL asigando");
			e.printStackTrace();
		}
		response = new OperationResponse();
		response.setRecurso(archivo);
		response.setSuccess(true);
		return response;
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
