package connection;

import java.util.HashMap;

import model.Archivo;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import connection.exceptions.ParseException;

public class ArchivoParser extends Parser {
	public static String ARCHIVO_TAG = "Archivo";
	public static String NOMBRE_TAG = "nombre";
	public static String EXTENSION_TAG = "tipo";
	public static String FILE_TAG = "file";
	
	public String serializeArchivo(Archivo archivo) {
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(ARCHIVO_TAG);
		rootElement.appendChild(nodeElement);
		
		Element IDAmbiente = doc.createElement(Parser.AMBITOID_TAG);
		IDAmbiente.appendChild(doc.createTextNode(String.valueOf(archivo.getAmbitoId())));
		nodeElement.appendChild(IDAmbiente);
		
		Element IDArchivo = doc.createElement(Parser.ID_TAG);
		IDArchivo.appendChild(doc.createTextNode(String.valueOf(archivo.getRecursoId())));
		nodeElement.appendChild(IDArchivo);
		
		Element descripcion = doc.createElement(Parser.DESCRIPCION_TAG);
		descripcion.appendChild(doc.createTextNode(archivo.getDescripcion()));
		nodeElement.appendChild(descripcion);
		
		Element nombre = doc.createElement(NOMBRE_TAG);
		nombre.appendChild(doc.createTextNode(archivo.getNombreArchivo()));
		nodeElement.appendChild(nombre);
		
		Element extension = doc.createElement(EXTENSION_TAG);
		extension.appendChild(doc.createTextNode(archivo.getTipoArchivo()));
		nodeElement.appendChild(extension);
		
	
	/*	Element file = doc.createElement(FILE_TAG);
		try {
			file.appendChild(doc.createTextNode(String.valueOf(archivo.getStringFile())));
		} catch (DOMException e) {
			System.out.println(e.getMessage());
			System.out.println("error al agregar file tag");
		}
		nodeElement.appendChild(file);*/
		
		String r = convertDocumentToXml(doc);
	
		return r;
	}

	public Archivo deserializeArchivo(String xml) throws ParseException {
		baseTag = ARCHIVO_TAG;
		Document doc = this.convertXmlToDocument(xml);
		if (doc == null) {
			return null;
		}
		HashMap<String, String> fields = new HashMap<String, String>();
		
		NodeList nodes = doc.getElementsByTagName(baseTag);
		if (nodes.getLength() == 0) {
			throw new ParseException("No existe tag " + baseTag);
		}
		NodeList childNodes = nodes.item(0).getChildNodes(); 
		
	    if (childNodes != null) {
	        for (int i = 0; i < childNodes.getLength(); i++) {
        	   Element element = (Element) childNodes.item(i);
        	   fields.put(element.getNodeName(), element.getTextContent());
	        }
	    }
	    
		int IDAmbiente = Integer.parseInt(fields.get(AMBITOID_TAG));
		int IDArchivo = Integer.parseInt(fields.get(Parser.ID_TAG));
		String descripcion = fields.get(DESCRIPCION_TAG);
		String nombre = fields.get(NOMBRE_TAG);		
		String extension = fields.get(EXTENSION_TAG);
		
		Archivo archivo = new Archivo();
		archivo.setAmbitoId(IDAmbiente);
		archivo.setDescripcion(descripcion);
		archivo.setRecursoId(IDArchivo);
		archivo.setNombreArchivo(nombre);
		archivo.setTipoArchivo(extension);
//		archivo.setStringFile(file);
		
		return archivo;
	}
	
	@Override
	protected Serializable createSerializable(HashMap<String, String> fields) {
		
		int IDAmbiente = Integer.parseInt(fields.get(AMBITOID_TAG));
		int IDArchivo = Integer.parseInt(fields.get(Parser.ID_TAG));
		String descripcion = fields.get(DESCRIPCION_TAG);
		String nombre = fields.get(NOMBRE_TAG);		
		String extension = fields.get(EXTENSION_TAG);
		String file = fields.get(FILE_TAG);
		
		Archivo archivo = new Archivo();
		archivo.setAmbitoId(IDAmbiente);
		archivo.setDescripcion(descripcion);
		archivo.setRecursoId(IDArchivo);
		archivo.setNombreArchivo(nombre);
		archivo.setTipoArchivo(extension);
		archivo.setStringFile(file);
		
		return archivo;
	}
}
