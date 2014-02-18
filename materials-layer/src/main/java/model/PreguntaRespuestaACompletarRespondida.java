package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "preguntaRespondidaSinOpciones")
public class PreguntaRespuestaACompletarRespondida extends PreguntaRespondida {

	private String respuesta;

	public PreguntaRespuestaACompletarRespondida(Integer idPregunta) {
		super(idPregunta);
		type = ANSWERED_QUESTION_TO_COMPLETE_TYPE;
	}
	
	public PreguntaRespuestaACompletarRespondida() {
	}

	public PreguntaRespuestaACompletarRespondida(String s) {
		unmarshall(s);
		type = ANSWERED_QUESTION_TO_COMPLETE_TYPE;
	}

	public void responder(String respuesta) {
		this.respuesta = respuesta;
	}

	@XmlAttribute(name = "respuesta")
	public String getRespuesta() {
		return respuesta;
	}

	// 1 si respuesta correcta, 0 en caso contrario
	@Override
	public Integer evaluar(Pregunta pregunta) {
		if (pregunta.isCorrect(respuesta)) {
			correcta = true;
			return 1;
		}
		correcta = false;
		return 0;
	}

	@Override
	public String marshall() {
		return super.marshall() + StringEscapeUtils.escapeSpecialCharacters(respuesta);
	}

	public void unmarshall(String s) {
		super.unmarshall(s);
		String[] splited = StringEscapeUtils.splitIgnoringEscaped(s, ';');
		if (splited.length==4)
			respuesta = StringEscapeUtils.removeEscapers(splited[3]);
		else
			respuesta = "";
	}

}