package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import connection.Parser;

@XmlRootElement (name = "preguntaSinOpciones")
public class PreguntaRespuestaACompletar extends Pregunta {

	public String respuesta = null;

	public PreguntaRespuestaACompletar() {
		super();
		type = ANSWER_TO_COMPLETE_TYPE;
	}

	public PreguntaRespuestaACompletar(String s) {
		unmarshall(s);
		type = ANSWER_TO_COMPLETE_TYPE;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	@Override
	public Integer evaluar(PreguntaRespondida respondida) {
		return respondida.evaluar(this);
	}

	@Override
	public String marshall() {
		return super.marshall() + escapeSpecialCharacters(respuesta);
	}

	public void unmarshall(String s) {
		super.unmarshall(s);
		String[] splited = s.split(";");
		splited = ignoreSpecialCharactersInSplit(splited);
		respuesta = splited[3];
	}

	@Override
	public void addRespuestaCorrecta(String string) {
		respuesta = string;
	}

	@Override
	public boolean isCorrect(String respuesta) {
		return this.respuesta.equalsIgnoreCase(respuesta);
	}

	@Override
	public boolean isCorrect(Integer respuesta) {
		return false;
	}

	@Override
	public Integer getNroCorrectas() {
		return 1;
	}
	
	@XmlAttribute(name = "correcta")
	public String getRtasCorrectas() {
		return respuesta;
	}
	
	public void setRtasCorrectas(String rts) {
		setRespuesta(rts);
	}
	
	@XmlElement(name = "respuestas")
	public String getRespuestas() {
		return null;
	}

}