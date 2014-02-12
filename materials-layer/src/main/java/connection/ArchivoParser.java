package connection;

import java.util.HashMap;

import model.Archivo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ArchivoParser extends Parser {
	public static String ARCHIVO_TAG = "ArchivoMetadata";
	public static String NOMBRE_TAG = "nombre";
	public static String EXTENSION_TAG = "tipo";
	public static String FILE_TAG = "file";
	
	public String serializeArchivo(Archivo archivo) {
		Document doc = this.buildXMLDocument();
		//Element xmlTag = doc.createElement(Parser.XML_TAG);
		//doc.appendChild(xmlTag);
		
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(ARCHIVO_TAG);
		rootElement.appendChild(nodeElement);
		
		Element nombre = doc.createElement(NOMBRE_TAG);
		nombre.appendChild(doc.createTextNode(archivo.getNombreArchivo()));
		nodeElement.appendChild(nombre);
		
		Element IDAmbiente = doc.createElement(Parser.TAMANIO);
		IDAmbiente.appendChild(doc.createTextNode(String.valueOf(archivo.getSize())));
		nodeElement.appendChild(IDAmbiente);
		
		
		Element extension = doc.createElement(EXTENSION_TAG);
		extension.appendChild(doc.createTextNode(archivo.getTipoArchivo()));
		nodeElement.appendChild(extension);
		
		Element IDArchivo = doc.createElement(Parser.RECURSOID_TAG);
		IDArchivo.appendChild(doc.createTextNode(String.valueOf(archivo.getRecursoId())));
		nodeElement.appendChild(IDArchivo);
		
	
	/*	Element file = doc.createElement(FILE_TAG);
		try {
			file.appendChild(doc.createTextNode(String.valueOf(archivo.getStringFile())));
		} catch (DOMException e) {
			System.out.println(e.getMessage());
			System.out.println("error al agregar file tag");
		}
		nodeElement.appendChild(file);*/
		
		String r = convertDocumentToXml(doc);
//		if(r!="error")
//			r=Parser.XML_TAG+r;
		return r;
	}

	@Override
	protected Serializable createSerializable(HashMap<String, String> fields) {
		
		int idAmbito = Integer.parseInt(fields.get(AMBITOID_TAG));
		int IDArchivo = Integer.parseInt(fields.get(Parser.ID_TAG));
		String descripcion = fields.get(DESCRIPCION_TAG);
		String nombre = fields.get(NOMBRE_TAG);		
		String extension = fields.get(EXTENSION_TAG);
//		String file = fields.get(FILE_TAG);
		
		Archivo archivo = new Archivo();
		archivo.setAmbitoId(idAmbito);
		archivo.setDescripcion(descripcion);
		archivo.setRecursoId(IDArchivo);
		archivo.setNombreArchivo(nombre);
		archivo.setTipoArchivo(extension);
//		archivo.setStringFile(file);
		
		return archivo;
	}
}
