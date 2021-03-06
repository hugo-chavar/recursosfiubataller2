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
import connection.parsers.ArchivoParser;
import connection.parsers.Parser;
import connection.responses.OperationResponse;

public class ArchivoRequester extends HandlerRequester {
	private Parser parser;
	private Cache<Archivo> cache;

	public ArchivoRequester() {
		super();
		parser = new ArchivoParser();
		cache = new Cache<Archivo>();
		cache.changeSize(5);
		generateTestData();
	}
	
	protected void generateTestData() {
		String path, nombre, desc, extension;
		int idRec;
		path = getClass().getProtectionDomain().getCodeSource().getLocation().toExternalForm();
		path = path.substring(0, path.lastIndexOf("classes") + 8);
//		//System.out.println("Se trae de "+path);
		nombre = "teofilo";
		desc = "la foto del siglo";
		extension = "jpg";
		idRec = 1003;
		
		addFileToCache(path, nombre, desc, extension, idRec);
		
////		path = "file:/opt/apache-tomcat-7.0.47/webapps/axis2/WEB-INF/services/";
//		nombre = "head";
//		desc = "x";
//		extension = "pdf";
//		idRec = -18;
//		
//		addFileToCache(path, nombre, desc, extension, idRec);
		
//		path = "file:/opt/apache-tomcat-7.0.47/webapps/axis2/WEB-INF/services/";
//		nombre = "integracion";
//		desc = "x";
//		extension = "aar";
//		idRec = -18;
//		
//		addFileToCache(path, nombre, desc, extension, idRec);
	}

	protected void addFileToCache(String path, String nombre, String desc, String extension, int idRec) {
		Archivo archivo = new Archivo();
		archivo.setAmbitoId(-1);
		archivo.setRecursoId(idRec);
		archivo.setNombreArchivo(nombre);
		archivo.setDescripcion(desc);
		archivo.setTipoArchivo(extension);
		try {			
			path = path + nombre + "." + extension;
			DataHandler arch = new DataHandler(new URL(path));
			archivo.setRawFile(arch);
			current = archivo;
			updateCache();
		} catch (MalformedURLException e) {
			//System.out.println("no existe el URL: " + path);
			//System.out.println(e.getMessage());
		}
	}

	public OperationResponse save(Archivo archivo) {
		current = archivo; 		
		getParser().setSaveMode();
		int id = createRecurso(archivo.getDescripcion(), "A", archivo.getAmbitoId());
		if(id==-1)
			return OperationResponse.createFailed("MATERIALS: No se ha podido crear un Nuevo Recurso");
		archivo.setRecursoId(id);
		String archivo_str = parser.serialize(archivo);
		try {
			return saveFile(archivo_str, archivo.getRawFile());
		} catch (GetException e) {
			return OperationResponse.createFailed(e.getMessage());
		}
	}

	// Este metodo es el que consulta a integración y trae el archivo necesario.
	public OperationResponse get(Serializable serializable) {
		OperationResponse response;
		getParser().setGetMode();
		String xml = parser.serialize(new Archivo(((Recurso) serializable).getRecursoId()));
		try {
			response = getFile(xml);
		} catch (GetException e) {
			response = OperationResponse.createFailed(e.getMessage());
		} catch (ParseException e) {
			response = OperationResponse.createFailed(e.getMessage());
		}
		return response;
	}
	
//	Metodo privado para testear. lo meti en el cache .. ver generateTestData()
//	public OperationResponse harcodeoDeArchivo(){
//		OperationResponse response;
//		String path, nombre, desc, extension;
//		int idRec;
////		Archivo archivo = new Archivo();
////		archivo.setAmbitoId(14);
////		archivo.setNombreArchivo("teofilo");
////		archivo.setDescripcion("la foto del siglo");
////		String extension = "jpg";
////		archivo.setTipoArchivo("jpg");
////		try {
////			String path;
////			path = getClass().getProtectionDomain().getCodeSource().getLocation().toExternalForm();
////			path = path.substring(0, path.lastIndexOf("classes") + 8);
////			path = path + "teofilo." + extension;
////			DataHandler arch = new DataHandler(new URL(path));
////			archivo.setRawFile(arch);
////		} catch (MalformedURLException e) {
////			//System.out.println("no existe el URL asigando");
////			//System.out.println(e.getMessage());
////		}
//		path = "file:/opt/apache-tomcat-7.0.47/webapps/axis2/WEB-INF/services/";
//		nombre = "integracion";
//		desc = "x";
//		extension = "aar";
//		idRec = -18;
//		Archivo archivo = new Archivo();
//		archivo.setAmbitoId(-1);
//		archivo.setRecursoId(idRec);
//		archivo.setNombreArchivo(nombre);
//		archivo.setDescripcion(desc);
//		archivo.setTipoArchivo(extension);
//		try {			
//			path = path + nombre + "." + extension;
//			DataHandler arch = new DataHandler(new URL(path));
//			archivo.setRawFile(arch);
//			current = archivo;
//			updateCache();
//		} catch (MalformedURLException e) {
//			//System.out.println("no existe el URL: " + path);
//			//System.out.println(e.getMessage());
//		}
//		response = OperationResponse.createSuccess();
//		response.setRecurso(archivo);
//		return response;
//	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Cache getCache() {
		return cache;
	}

	@Override
	protected Parser getParser() {
		return parser;
	}
	
	@Override
	protected void updateCache() {
		if (currentHasValidSize()) {
//			//System.out.println("Se va a cachear");
			super.updateCache();
		}
//		else{
//			//System.out.println("No se cachea");
//		}
	}

	private boolean currentHasValidSize() {
		// aca chequea si x el tamanio del archivo se puede cachear (limite 50k)
		Archivo archivo = (Archivo) current;
		return (archivo.getSize() <= 50000);
	}

//	@Override
//	public void deleteRecurso(Serializable s) {
//		Recurso aux = (Recurso)s;
//		super.deleteRecurso(aux);
//		//TODO parece que esto no hace falta
//		String xml = parser.serializeXmlQuery(aux.getRecursoId());
//		try {
//			String xml_resp_e = proxy.eliminarArchivo(xml);
//			//System.out.println(xml_resp_e);
//		} catch (ConnectionException e) {
//			String message = "Intentando eliminar archivo id: " + aux.getRecursoId();
//			message = e.getMessage() + message;
//			//System.out.println(message);
//		}
//		
//	}

	private OperationResponse saveFile(String xml, DataHandler dataHandler) throws GetException {
//		DataHandler archivo = ((Archivo) current).getRawFile();
		try {
//			Integer id =  createRecurso("ARCHIVO", "A", 5);
			String xml_resp_e = proxy.guardarArchivo(xml, dataHandler);
			return validateOneWayOperation(xml_resp_e);
//			updateCache(); al guardar no se actualiza el cache ?
		} catch (ConnectionException e) {
			String reason = e.getMessage() + getCurrent().getInfo();
			return OperationResponse.createFailed(reason);

		}	
	}

}
