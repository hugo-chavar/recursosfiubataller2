package connection;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import model.Encuesta;
import model.EncuestaRespondida;


public class EncuestaParser extends Parser {

	public static String ENCUESTA_TAG = "Encuesta";
	public static String IDENCUESTA_TAG = "IDEncuesta";
	public static String IDAMBIENTE_TAG = "IDAmbiente";
	public static String DESCRIPCION_TAG = "Descripcion";
	public static String EVALUADA_TAG = "Evaluada";
	public static String PREGUNTAS_TAG = "Preguntas";
	
	public static String ENCUESTA_RESPONDIDA_TAG = "EncuestaRespondida";
	public static String IDUSUARIO_TAG = "IDUsuario";
	public static String EVALUACION_TAG = "Evaluacion";
	
	
	public String serializeEncuesta(Encuesta encuesta) {
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(EncuestaParser.ENCUESTA_TAG);
		rootElement.appendChild(nodeElement);
		
		Element IDAmbiente = doc.createElement(EncuestaParser.IDAMBIENTE_TAG);
		IDAmbiente.appendChild(doc.createTextNode(String.valueOf(encuesta.getIdAmbiente())));
		nodeElement.appendChild(IDAmbiente);
		Element IDEncuesta = doc.createElement(EncuestaParser.IDENCUESTA_TAG);
		IDEncuesta.appendChild(doc.createTextNode(String.valueOf(encuesta.getIdRecurso())));
		nodeElement.appendChild(IDEncuesta);
		Element descripcion = doc.createElement(EncuestaParser.DESCRIPCION_TAG);
		descripcion.appendChild(doc.createTextNode(encuesta.getDescripcion()));
		nodeElement.appendChild(descripcion);
		Element evaluada = doc.createElement(EncuestaParser.EVALUADA_TAG);
		evaluada.appendChild(doc.createTextNode(String.valueOf(encuesta.esEvaluada())));
		nodeElement.appendChild(evaluada);
		
		String preguntas_str = encuesta.marshallPreguntas();
		Element preguntas = doc.createElement(EncuestaParser.PREGUNTAS_TAG);
		preguntas.appendChild(doc.createTextNode(preguntas_str));
		nodeElement.appendChild(preguntas);
		
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		String xml = serializer.writeToString(doc);		
		
		return xml;
	}
	
	public String serializeEncuestaRespondida(EncuestaRespondida resondida) {
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(EncuestaParser.ENCUESTA_RESPONDIDA_TAG);
		rootElement.appendChild(nodeElement);
		
		Element IDEncuesta = doc.createElement(EncuestaParser.IDENCUESTA_TAG);
		IDEncuesta.appendChild(doc.createTextNode(String.valueOf(resondida.getIdRecurso())));
		nodeElement.appendChild(IDEncuesta);
		Element IDUsuario = doc.createElement(EncuestaParser.IDUSUARIO_TAG);
		IDUsuario.appendChild(doc.createTextNode(String.valueOf(resondida.getIdUsuario())));
		nodeElement.appendChild(IDUsuario);
		Element evaluacion = doc.createElement(EncuestaParser.EVALUACION_TAG);
		evaluacion.appendChild(doc.createTextNode(String.valueOf(resondida.getEvaluacion())));
		nodeElement.appendChild(evaluacion);
		
		// TODO: Hacer un marshall de las preguntas correctas y las respondidas.
		//private List<Integer> preguntasCorrectas;
		//private ArrayList<PreguntaRespondida> preguntasRespondidas;
		
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		String xml = serializer.writeToString(doc);		
		
		return xml;
	}
	
	public Encuesta deserializeEncuesta(String xml) {
		Document doc = this.convertToXMLDocument(xml);
		NodeList nodes = doc.getElementsByTagName(EncuestaParser.ENCUESTA_TAG);
		NodeList childNodes = nodes.item(0).getChildNodes(); 
		HashMap<String, String> fields = new HashMap<String, String>();
	    if (childNodes != null) {
	        for (int i = 0; i < childNodes.getLength(); i++) {
        	   Element element = (Element) childNodes.item(i);
        	   fields.put(element.getNodeName(), element.getTextContent());
	        }
	    }
	    
		int IDAmbiente = Integer.parseInt(fields.get(EncuestaParser.IDAMBIENTE_TAG));
		int IDEncuesta = Integer.parseInt(fields.get(EncuestaParser.IDENCUESTA_TAG));
		String descripcion = fields.get(EncuestaParser.DESCRIPCION_TAG);
		Boolean evaluada = Boolean.parseBoolean(fields.get(EncuestaParser.EVALUADA_TAG));		
		
		Encuesta encuesta = new Encuesta(IDEncuesta, IDAmbiente, descripcion, evaluada);
		encuesta.unmarshallPreguntas(fields.get(EncuestaParser.PREGUNTAS_TAG));
		return encuesta;
	}
	
	public String serializeEncuestaQuery(int IDAmbiente, int IDEncuesta) {
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(EncuestaParser.ENCUESTA_TAG);
		rootElement.appendChild(nodeElement);
		
		Element IDAmbiente_el = doc.createElement(EncuestaParser.IDAMBIENTE_TAG);
		IDAmbiente_el.appendChild(doc.createTextNode(String.valueOf(IDAmbiente)));
		nodeElement.appendChild(IDAmbiente_el);
		Element IDEncuesta_el = doc.createElement(EncuestaParser.IDENCUESTA_TAG);
		IDEncuesta_el.appendChild(doc.createTextNode(String.valueOf(IDEncuesta)));
		nodeElement.appendChild(IDEncuesta_el);
		
		DOMImplementationLS domImplLS = (DOMImplementationLS) doc.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		serializer.getDomConfig().setParameter("xml-declaration", false);
		String xml = serializer.writeToString(doc);		
		
		return xml;
	}
	
}
