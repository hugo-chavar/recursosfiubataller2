package connection;

import java.io.IOException;
import java.util.HashMap;

import model.Archivo;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ArchivoParser extends Parser {
	public static String ARCHIVO_TAG = "archivo";
	public static String IDARCHIVO_TAG = "archivoId";
	public static String DESCRIPCION_TAG = "descripcion";
	public static String NOMBRE_TAG = "nombre";
	public static String EXTENSION_TAG = "tipo";
	public static String FILE_TAG = "file";
	
	public String serializeArchivo(Archivo archivo) {
		
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(ArchivoParser.ARCHIVO_TAG);
		rootElement.appendChild(nodeElement);
		
		Element IDAmbiente = doc.createElement(Parser.AMBITOID_TAG);
		IDAmbiente.appendChild(doc.createTextNode(String.valueOf(archivo.getAmbitoId())));
		nodeElement.appendChild(IDAmbiente);
		Element IDArchivo = doc.createElement(ArchivoParser.IDARCHIVO_TAG);
		IDArchivo.appendChild(doc.createTextNode(String.valueOf(archivo.getRecursoId())));
		nodeElement.appendChild(IDArchivo);
		Element descripcion = doc.createElement(ArchivoParser.DESCRIPCION_TAG);
		descripcion.appendChild(doc.createTextNode(archivo.getDescripcion()));
		nodeElement.appendChild(descripcion);
		
		Element nombre = doc.createElement(ArchivoParser.NOMBRE_TAG);
		nombre.appendChild(doc.createTextNode(String.valueOf(archivo.getNombreArchivo())));
		nodeElement.appendChild(nombre);
		
		Element extension = doc.createElement(ArchivoParser.EXTENSION_TAG);
		extension.appendChild(doc.createTextNode(String.valueOf(archivo.getTipoArchivo())));
		nodeElement.appendChild(extension);
		
	
		Element file = doc.createElement(ArchivoParser.FILE_TAG);
		try {
			file.appendChild(doc.createTextNode(String.valueOf(archivo.getStringFile())));
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nodeElement.appendChild(file);
		
//		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
//		LSSerializer serializer = domImplLS.createLSSerializer();
//		serializer.getDomConfig().setParameter("xml-declaration", false);
//		String xml = serializer.writeToString(doc);		
//		
//		return xml;
		
		return convertDocumentToXml(doc);
	}

	public Archivo deserializeArchivo(String xml) {
		Document doc = this.convertXmlToDocument(xml);
		if (doc == null) {
			return null;
		}
		NodeList nodes = doc.getElementsByTagName(ArchivoParser.ARCHIVO_TAG);
		NodeList childNodes = nodes.item(0).getChildNodes(); 
		HashMap<String, String> fields = new HashMap<String, String>();
	    if (childNodes != null) {
	        for (int i = 0; i < childNodes.getLength(); i++) {
        	   Element element = (Element) childNodes.item(i);
        	   fields.put(element.getNodeName(), element.getTextContent());
	        }
	    }
	    
		int IDAmbiente = Integer.parseInt(fields.get(ArchivoParser.AMBITOID_TAG));
		int IDArchivo = Integer.parseInt(fields.get(ArchivoParser.IDARCHIVO_TAG));
		String descripcion = fields.get(ArchivoParser.DESCRIPCION_TAG);
		String nombre = fields.get(ArchivoParser.NOMBRE_TAG);		
		String extension = fields.get(ArchivoParser.EXTENSION_TAG);
		String file = fields.get(ArchivoParser.FILE_TAG);
		
		Archivo archivo = new Archivo();
		archivo.setAmbitoId(IDAmbiente);
		archivo.setDescripcion(descripcion);
		archivo.setRecursoId(IDArchivo);
		archivo.setNombreArchivo(nombre);
		archivo.setTipoArchivo(extension);
		System.out.println("entra aca");
		archivo.setStringFile(file);
		
		return archivo;
	}
}
