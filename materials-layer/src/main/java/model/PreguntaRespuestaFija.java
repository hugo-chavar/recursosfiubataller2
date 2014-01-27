package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import connection.Parser;

@XmlRootElement(name = "preguntaConOpciones")
public class PreguntaRespuestaFija extends Pregunta {

	@XmlTransient
	private List<String> respuestasPosibles = new ArrayList<String>();

	@XmlTransient
	private List<Integer> respuestasCorrectas = new ArrayList<Integer>();

	@XmlAttribute(name = "multiplesCorrectas")
	private boolean multiplesCorrectas = false;

	public PreguntaRespuestaFija() {
		type = FIXED_ANSWER_TYPE;
	}

	public PreguntaRespuestaFija(String s) {
		unmarshall(s);
		type = FIXED_ANSWER_TYPE;
	}

	public List<String> getRespuestasPosibles() {
		return respuestasPosibles;
	}

	public void setRespuestasPosibles(List<String> respuestasPosibles) {
		this.respuestasPosibles = respuestasPosibles;
	}

	public List<Integer> getRespuestasCorrectas() {
		return respuestasCorrectas;
	}

	@Override
	public Integer evaluar(PreguntaRespondida respondida) {
		return respondida.evaluar(this);
	}
	
	@Override
	public String marshall() {
		StringBuilder sb = new StringBuilder("");
		sb.append(marshallRespuestasPosibles());
		sb.append(";");
		sb.append(marshallRespuestasCorrectas());
		return super.marshall() + sb.toString();

	}

	private String marshallRespuestasPosibles() {
		if (respuestasPosibles.isEmpty()) {
			return "null";
		}
		StringBuilder sb = new StringBuilder("");
		for (String rta : respuestasPosibles) {
			sb.append(escapeSpecialCharacters(rta));
			sb.append(",");
		}
		sb.setLength(sb.length() - 1);

		return sb.toString();
	}

	private String marshallRespuestasCorrectas() {
		if (respuestasCorrectas.isEmpty()) {
			return "null";
		}
		StringBuilder sb = new StringBuilder("");
		for (Integer rta : respuestasCorrectas) {
			sb.append(rta);
			sb.append(",");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	public void unmarshall(String s) {
		super.unmarshall(s);
		String[] splited = s.split(";");
		splited = ignoreSpecialCharactersInSplit(splited, ";");
		unmarshallRespuestasPosibles(splited[3]);
		unmarshallRespuestasCorrectas(splited[4]);

	}

	private void unmarshallRespuestasPosibles(String rtas) {
		if ("null".equals(rtas)) {
			return;
		}
		String[] splited = rtas.split(",");
		splited = ignoreSpecialCharactersInSplit(splited, ",");
		respuestasPosibles = new ArrayList<String>();
		for (String s : splited) {
			respuestasPosibles.add(s);
		}

	}

	private void unmarshallRespuestasCorrectas(String rtas) {
		if ("null".equals(rtas)) {
			return;
		}
		String[] splited = rtas.split(",");
		splited = ignoreSpecialCharactersInSplit(splited, ",");
		respuestasCorrectas = new ArrayList<Integer>();
		for (String s : splited) {
			respuestasCorrectas.add(Integer.valueOf(s));
		}
	}

	@Override
	public void addRespuestaCorrecta(String string) {
		int i = 0;
		for (String s : respuestasPosibles) {
			if (s.equals(string)) {
				addRespuestaCorrecta(new Integer(i));
				return;
			}
			i++;
		}

	}

	private void addRespuestaCorrecta(Integer integer) {
		respuestasCorrectas.add(integer);
		if (respuestasCorrectas.size() > 1) {
			setMultiplesCorrectas(true);
		}

	}

	@Override
	public boolean isCorrect(Integer respuesta) {

		for (Integer res : this.respuestasCorrectas) {
			if (res.equals(respuesta))
				return true;
		}
		return false;
	}

	@Override
	public boolean isCorrect(String respuesta) {
		return false;
	}

	@Override
	public Integer getNroCorrectas() {
		return this.respuestasCorrectas.size();
	}

	@Override
	public Integer getIndiceRespuesta(String respuesta) {
		Integer i = 0;
		for (String res : respuestasPosibles) {
			if (res.equalsIgnoreCase(respuesta)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	@XmlAttribute(name = "correctas")
	public String getRtasCorrectas() {
		return marshallRespuestasCorrectas() != "null" ? marshallRespuestasCorrectas() : null;
	}
	
	public void setRtasCorrectas(String rtas) {
		unmarshallRespuestasCorrectas(rtas);
	}

	@XmlElement(name = "respuestas")
	public String getRespuestas() {
		return marshallRespuestasPosibles() != "null" ? marshallRespuestasPosibles() : null;
	}
	
	public void setRespuestas(String rtas) {
		unmarshallRespuestasPosibles(rtas);
	}
	
	public boolean getMultiplesCorrectas() {
		return multiplesCorrectas;
	}
	
	public void setMultiplesCorrectas(boolean multiple) {
		multiplesCorrectas = multiple;
	}

}