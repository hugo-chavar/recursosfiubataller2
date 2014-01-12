package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

//@XmlRootElement(name = "pregunta")
@XmlSeeAlso({PreguntaRespuestaFija.class,  PreguntaRespuestaACompletar.class})
@XmlAccessorType(XmlAccessType.NONE)
public class Pregunta {

	protected static String FIXED_ANSWER_TYPE = "F";
	protected static String ANSWER_TO_COMPLETE_TYPE = "C";

	@XmlAttribute
	protected Integer idPregunta = 0;
	
	@XmlTransient
	protected String type;

	@XmlAttribute
	protected String enunciado;
	
	//@XmlElement(required = false)
	protected List<String> opciones = new ArrayList<String>();
	
	//@XmlAttribute(required = true) 
	//esto tiene q ser una lista de ints, segun lo requerido x presentacion
	protected List<String> correctas = new ArrayList<String>();

	public static List<Pregunta> unmarshallAll(String field) {
		String[] splited = field.split("\\|");
		List<Pregunta> result = new ArrayList<Pregunta>();

		for (String s : splited) {
			if (unmarshallType(s).equals(FIXED_ANSWER_TYPE)) {
				result.add(new PreguntaRespuestaFija(s));
			} else {
				result.add(new PreguntaRespuestaACompletar(s));
			}
		}
		return result;
	}

	private static String unmarshallType(String marshalledPregunta) {
		return marshalledPregunta.substring(0, marshalledPregunta.indexOf(";"));
	}

	public Pregunta() {
	}

	public String getEnunciado() {
		return enunciado;
	}

	public Integer getIdPregunta() {
		return idPregunta;
	}

	public void setIdPregunta(Integer idPregunta) {
		this.idPregunta = idPregunta;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public void addRespuestaCorrecta(String rta) {

	}

	protected String marshall() {
		StringBuilder sb = new StringBuilder("");
		sb.append(type);
		sb.append(";");
		sb.append(idPregunta);
		sb.append(";");
		sb.append(enunciado);
		sb.append(";");
		return sb.toString();
	}

	protected void unmarshall(String s) {
		String[] splited = s.split(";");
		type = splited[0];
		idPregunta = Integer.valueOf(splited[1]);
		enunciado = splited[2];
	}

	// --------------------- metodos "abstractos" -------------

	public boolean isCorrect(Integer respuesta) {
		return false;
	}

	public boolean isCorrect(String respuesta) {
		return false;
	}

	public Integer getNroCorrectas() {
		return -1;
	}

	public Integer evaluar(PreguntaRespondida respondida) {
		return null;
	}

	public List<String> getOpciones() {
		return this.opciones;
	}

	public List<String> getCorrectas() {
		return this.correctas;
	}

	public void completarDatosVisibles() {
	}

	public Integer getIndiceRespuesta(String Respuesta) {
		return 0;
	}
	

}
