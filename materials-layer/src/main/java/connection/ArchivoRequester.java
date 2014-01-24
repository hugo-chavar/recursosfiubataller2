package connection;


import java.net.MalformedURLException;
import java.net.URL;

import javax.activation.DataHandler;

import model.Archivo;
import model.Recurso;
import connection.cache.Cache;
import connection.exceptions.ConnectionException;
import connection.exceptions.GetException;
import connection.exceptions.ParseException;
import connection.responses.OperationResponse;

public class ArchivoRequester extends HandlerRequester {
	private ArchivoParser parser;
	private Cache<Archivo> cache;

	public ArchivoRequester() {
		super();
		parser = new ArchivoParser();
		cache = new Cache<Archivo>();
		generateTestData();
	}
	
	protected void generateTestData() {
		Archivo archivo = new Archivo();
		archivo.setAmbitoId(-1);
		archivo.setRecursoId(1003);
		archivo.setNombreArchivo("teofilo");
		archivo.setDescripcion("la foto del siglo");
		String extension = "jpg";
		archivo.setTipoArchivo("jpg");
		try {
			String path;
			path = getClass().getProtectionDomain().getCodeSource().getLocation().toExternalForm();
			path = path.substring(0, path.lastIndexOf("classes") + 8);
			path = path + "teofilo." + extension;
			DataHandler arch = new DataHandler(new URL(path));
			archivo.setRawFile(arch);
			current = archivo;
			updateCache();
		} catch (MalformedURLException e) {
			System.out.println("no existe el URL asigando");
			System.out.println(e.getMessage());
		}
	}

	public OperationResponse save(Archivo archivo) {
		current = archivo; 
		String archivo_str = parser.serializeArchivo(archivo);
		return saveFile(archivo_str);
	}

	// Este metodo es el que consulta a integración y trae el archivo necesario.
	public OperationResponse get(Recurso recurso) {
		OperationResponse response;
		String xml = parser.serializeQueryByType(recurso.getRecursoId(), ArchivoParser.ARCHIVO_TAG);
		try {
			response = getFile(xml);
		} catch (GetException e) {
			response = OperationResponse.createFailed(e.getMessage());
		} catch (ParseException e) {
			response = OperationResponse.createFailed(e.getMessage());
		}
		
//		if (!response.getSuccess()) {
//			return harcodeoDeArchivo();
//		} //esto lo mete al cache
		
		return response;
	}
	
	//Metodo privado para testear. lo meti en el cache .. ver generateTestData()
//	private OperationResponse harcodeoDeArchivo(){
//		OperationResponse response;
//		Archivo archivo = new Archivo();
//		archivo.setAmbitoId(14);
//		archivo.setNombreArchivo("teofilo");
//		archivo.setDescripcion("la foto del siglo");
//		String extension = "jpg";
//		archivo.setTipoArchivo("jpg");
//		try {
//			String path;
//			path = getClass().getProtectionDomain().getCodeSource().getLocation().toExternalForm();
//			path = path.substring(0, path.lastIndexOf("classes") + 8);
//			path = path + "teofilo." + extension;
//			DataHandler arch = new DataHandler(new URL(path));
//			archivo.setRawFile(arch);
//		} catch (MalformedURLException e) {
//			System.out.println("no existe el URL asigando");
//			System.out.println(e.getMessage());
//		}
//		response = OperationResponse.createSuccess();
//		response.setRecurso(archivo);
//		return response;
//	}
	
	@Override
	protected String getHandledType() {
		return "Archivo";
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Cache getCache() {
		return cache;
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
		return true;
	}

	@Override
	protected void deserialize(String xml_resp_e) throws ParseException {
		current = parser.deserializeArchivo(xml_resp_e);
	}

	@Override
	public void deleteRecurso(Recurso recurso) {
		super.deleteRecurso(recurso);
		//TODO Dami esto hay que probarlo
		String xml = parser.serializeQueryByType(recurso.getRecursoId(), ArchivoParser.ARCHIVO_TAG);
		try {
			String xml_resp_e = proxy.eliminarArchivo(xml);
			System.out.println(xml_resp_e);
		} catch (ConnectionException e) {
			String message = "Intentando eliminar archivo id: " + recurso.getRecursoId();
			message = e.getMessage() + message;
			System.out.println(message);
		}
		
	}

}
