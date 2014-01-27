package connection;

import java.util.HashMap;

import model.Encuesta;
import model.Link;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import connection.exceptions.ParseException;


public class EncuestaParser extends Parser {
	
	public static String ENCUESTA_TAG = "Encuesta";
	public static String EVALUADA_TAG = "evaluada";
	public static String PREGUNTAS_TAG = "preguntas";
	
	public String serializeEncuesta(Encuesta encuesta) {
		
		Document doc = this.buildXMLDocument();
		Element rootElement = doc.createElement(Parser.INITIAL_TAG);
		doc.appendChild(rootElement);
		
		Element nodeElement = doc.createElement(Parser.RECURSO_TAG);
		rootElement.appendChild(nodeElement);
		
		if ((encuesta.getRecursoId() != null) && (!encuesta.getRecursoId().equals(0))) {
			Element IDRecurso = doc.createElement(Parser.ID_TAG);
			IDRecurso.appendChild(doc.createTextNode(String.valueOf(encuesta.getRecursoId())));
			nodeElement.appendChild(IDRecurso);
		}
		Element IDAmbito = doc.createElement(Parser.AMBITOID_TAG);
		IDAmbito.appendChild(doc.createTextNode(String.valueOf(encuesta.getAmbitoId())));
		nodeElement.appendChild(IDAmbito);
		Element descripcion = doc.createElement(Parser.DESCRIPCION_TAG);
		descripcion.appendChild(doc.createTextNode(encuesta.getDescripcion()));
		nodeElement.appendChild(descripcion);
		Element tipo = doc.createElement(Parser.TIPO_TAG);
		tipo.appendChild(doc.createTextNode(encuesta.getTipo()));
		nodeElement.appendChild(tipo);
		
		Element recursos = doc.createElement(Parser.RECURSOS_TAG);
		nodeElement.appendChild(recursos);
		
		Element encuesta_el = doc.createElement(EncuestaParser.ENCUESTA_TAG);
		recursos.appendChild(encuesta_el);
		
		if ((encuesta.getRecursoId() != null) && (!encuesta.getRecursoId().equals(0))) {
			Element IDRecurso = doc.createElement(Parser.ID_TAG);
			IDRecurso.appendChild(doc.createTextNode(String.valueOf(encuesta.getRecursoId())));
			encuesta_el.appendChild(IDRecurso);
		}
		Element evaluada = doc.createElement(EncuestaParser.EVALUADA_TAG);
		evaluada.appendChild(doc.createTextNode(String.valueOf(encuesta.isEvaluada())));
		encuesta_el.appendChild(evaluada);
		
		String preguntas_str = encuesta.marshallPreguntas();
		Element preguntas = doc.createElement(EncuestaParser.PREGUNTAS_TAG);
		preguntas.appendChild(doc.createTextNode(preguntas_str));
		encuesta_el.appendChild(preguntas);
		
		return convertDocumentToXml(doc);
		
	}
	
	public Encuesta deserializeEncuesta(String xml) throws ParseException {

		Encuesta encuesta = null;
		baseTag = ENCUESTA_TAG;

		Document doc = this.convertXmlToDocument(xml);
		if (doc == null) {
			return null;
		}
		HashMap<String, String> fields = new HashMap<String, String>();

		NodeList nodes = doc.getElementsByTagName(baseTag);
		if (nodes.getLength() == 0) {
			throw new ParseException("No existe tag " + baseTag);
		}
		NodeList encuestaChildNodes = nodes.item(0).getChildNodes();
		
		if (encuestaChildNodes != null) {

			for (int i = 0; i < encuestaChildNodes.getLength(); i++) {
				Element element = (Element) encuestaChildNodes.item(i);
				fields.put(element.getNodeName(), element.getTextContent());
			}

			Boolean evaluada = Boolean.parseBoolean(fields.get(EVALUADA_TAG));
			encuesta = new Encuesta(0, 0, "", evaluada);
			encuesta.unmarshallPreguntas(fields.get(PREGUNTAS_TAG));

		}

		return encuesta;

	}
	
	@Override
	protected Serializable createSerializable(HashMap<String, String> fields) {
		Boolean evaluada = Boolean.parseBoolean(fields.get(EVALUADA_TAG));
		Encuesta encuesta = new Encuesta(0, 0, "", evaluada);
		encuesta.unmarshallPreguntas(fields.get(PREGUNTAS_TAG));
		return encuesta;
	}
	
}