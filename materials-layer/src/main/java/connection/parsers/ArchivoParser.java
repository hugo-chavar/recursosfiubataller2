package connection.parsers;

import java.util.HashMap;

import model.Archivo;

import org.w3c.dom.Element;

import connection.Serializable;

public class ArchivoParser extends Parser {
	public static String ARCHIVO_TAG = "ArchivoMetadata";
	public static String NOMBRE_TAG = "nombre";
	public static String EXTENSION_TAG = "tipo";
	public static String FILE_TAG = "file";
	
	public ArchivoParser() {
		baseTag = ARCHIVO_TAG;
	}
	
//	public String serialize(Archivo archivo) {
//		Document document = buildXMLDocument();
//		
//		Element rootElement = document.createElement(Parser.INITIAL_TAG);
//		document.appendChild(rootElement);
//		
//		Element baseNode = document.createElement(baseTag);
//		rootElement.appendChild(baseNode);
//		
//		Element nombre = document.createElement(NOMBRE_TAG);
//		nombre.appendChild(document.createTextNode(archivo.getNombreArchivo()));
//		baseNode.appendChild(nombre);
//		
//		Element IDAmbiente = document.createElement(Parser.TAMANIO);
//		IDAmbiente.appendChild(document.createTextNode(String.valueOf(archivo.getSize())));
//		baseNode.appendChild(IDAmbiente);
//		
//		
//		Element extension = document.createElement(EXTENSION_TAG);
//		extension.appendChild(document.createTextNode(archivo.getTipoArchivo()));
//		baseNode.appendChild(extension);
//		
//		Element IDArchivo = document.createElement(Parser.RECURSOID_TAG);
//		IDArchivo.appendChild(document.createTextNode(String.valueOf(archivo.getRecursoId())));
//		baseNode.appendChild(IDArchivo);
//		
//	
//	/*	Element file = doc.createElement(FILE_TAG);
//		try {
//			file.appendChild(doc.createTextNode(String.valueOf(archivo.getStringFile())));
//		} catch (DOMException e) {
//			//System.out.println(e.getMessage());
//			//System.out.println("error al agregar file tag");
//		}
//		nodeElement.appendChild(file);*/
//		
//		String r = convertDocumentToXml(document);
//		return r;
//	}

	@Override
	protected Serializable createSerializable(HashMap<String, String> fields) {
		
//		int idAmbito = Integer.parseInt(fields.get(AMBITOID_TAG));
		int IDArchivo = Integer.parseInt(fields.get(Parser.ID_TAG));
		String descripcion = fields.get(DESCRIPCION_TAG);
		String nombre = fields.get(NOMBRE_TAG);		
		String extension = fields.get(EXTENSION_TAG);
//		String file = fields.get(FILE_TAG);
		
		Archivo archivo = new Archivo();
//		archivo.setAmbitoId(idAmbito);
		archivo.setDescripcion(descripcion);
		archivo.setRecursoId(IDArchivo);
		archivo.setNombreArchivo(nombre);
		archivo.setTipoArchivo(extension);
//		archivo.setStringFile(file);
		
		return archivo;
	}
	
	@Override
	protected void addElements(Serializable serializable, Element baseNode) {
		Archivo archivo = (Archivo) serializable;

		addTextElement(baseNode, NOMBRE_TAG, archivo.getNombreArchivo());
		addTextElement(baseNode, Parser.TAMANIO, String.valueOf(archivo.getSize()));
		addTextElement(baseNode, EXTENSION_TAG, archivo.getTipoArchivo());
		addTextElement(baseNode,isSaveMode?RECURSOID_TAG:ID_TAG, String.valueOf(archivo.getRecursoId()));
		
	}

}
