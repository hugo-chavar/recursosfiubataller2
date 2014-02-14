package connection;

import java.util.HashMap;

import model.Encuesta;

import org.w3c.dom.Element;


public class EncuestaParser extends Parser {
	
	public static String ENCUESTA_TAG = "Encuesta";
	public static String EVALUADA_TAG = "evaluada";
	public static String PREGUNTAS_TAG = "preguntas";
	
	public EncuestaParser() {
		baseTag = ENCUESTA_TAG;
	}
	
//	public String serialize(Serializable serializable) {
//		
//		Encuesta encuesta = (Encuesta) serializable;
//		Document document = buildXMLDocument();
//		Element rootElement = document.createElement(Parser.INITIAL_TAG);
//		document.appendChild(rootElement);
//		
//		Element baseNode = document.createElement(EncuestaParser.ENCUESTA_TAG);
//		rootElement.appendChild(baseNode);
//		
//		Element IDRecurso = document.createElement(Parser.ID_TAG);
//		IDRecurso.appendChild(document.createTextNode(String.valueOf(encuesta.getRecursoId())));
//		baseNode.appendChild(IDRecurso);
//		
//		Element evaluada = document.createElement(EncuestaParser.EVALUADA_TAG);
//		evaluada.appendChild(document.createTextNode(String.valueOf(encuesta.isEvaluada())));
//		baseNode.appendChild(evaluada);
//		
//		String preguntas_str = encuesta.marshallPreguntas();
//		Element preguntas = document.createElement(EncuestaParser.PREGUNTAS_TAG);
//		preguntas.appendChild(document.createTextNode(preguntas_str));
//		baseNode.appendChild(preguntas);
//		
//		return convertDocumentToXml(document);
//	}
	
	@Override
	protected Serializable createSerializable(HashMap<String, String> fields) {
		Boolean evaluada = Boolean.parseBoolean(fields.get(EVALUADA_TAG));
		Encuesta encuesta = new Encuesta(0, 0, "", evaluada);
		encuesta.unmarshallPreguntas(fields.get(PREGUNTAS_TAG));
		return encuesta;
	}
	
	@Override
	protected void addElements(Serializable serializable, Element baseNode) {
		Encuesta encuesta = (Encuesta) serializable;
		
		addTextElement(baseNode, Parser.ID_TAG, String.valueOf(encuesta.getRecursoId()));
		addTextElement(baseNode, EVALUADA_TAG, String.valueOf(encuesta.isEvaluada()));
		addTextElement(baseNode, PREGUNTAS_TAG, encuesta.marshallPreguntas());

	}
}