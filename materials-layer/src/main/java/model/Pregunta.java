package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import connection.Parser;

@XmlRootElement(name = "pregunta")
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
//	protected List<String> opciones = new ArrayList<String>();
	
	//@XmlAttribute(required = true) 
	//esto tiene q ser una lista de ints, segun lo requerido x presentacion
//	protected List<String> correctas = new ArrayList<String>();

	public static List<Pregunta> unmarshallAll(String field) {
		String[] splited = field.split("\\|");
		splited = ignoreSpecialCharactersInSplit(splited, "|");
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
	
	public String escapeSpecialCharacters(String line) {
		
		if (line != null) {
			for (int index = 0; index < Parser.SPECIAL_CHARACTERS.length(); index++) {
				char specialChar = Parser.SPECIAL_CHARACTERS.charAt(index);
				int line_idx = line.indexOf(specialChar);
				while (line_idx > -1) {
					line = line.substring(0, line_idx) + "\\" + line.substring(line_idx, line.length());
					line_idx = line.indexOf(specialChar);
				}
			}
		}
		
		return line;
		
	}
	
	public String removeSpecialCharacters(String line) {
		
		if (line != null) {
			int line_idx = line.indexOf("\\");
			while (line_idx > -1) {
				line = line.substring(0, line_idx) + line.substring(line_idx + 1, line.length());
				line_idx = line.indexOf("\\");
			}
		}
		
		return line;
		
	}
	
	public static String[] ignoreSpecialCharactersInSplit(String[] splitted, String specialChar) {
		
		int i = 0;
		int j = 0;
		while (i < splitted.length) {
			if (splitted[i].endsWith("\\")) {
				splitted[i] = splitted[i].substring(0, splitted[i].length()-1);
				splitted[i] = splitted[i].concat(specialChar + splitted[i+1]);
				splitted[i+1] = null;
				i++;
				j++;
			}
			i++;
		}
		
		String[] newSplitted = new String[i-j];
		j = 0;
		for (i = 0; i < splitted.length; i++) {
			if (splitted[i] != null) {
				newSplitted[j] = splitted[i];
				j++;
			}
		}
		
		return newSplitted;
		
	}

	public String marshall() {
		StringBuilder sb = new StringBuilder("");
		sb.append(type);
		sb.append(";");
		sb.append(idPregunta);
		sb.append(";");
		sb.append(escapeSpecialCharacters(enunciado));
		sb.append(";");
		return sb.toString();
	}

	protected void unmarshall(String s) {
		String[] splited = s.split(";");
		splited = ignoreSpecialCharactersInSplit(splited, ";");
		type = splited[0];
		idPregunta = Integer.valueOf(splited[1]);
		enunciado = removeSpecialCharacters(splited[2]);
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

//	public List<String> getOpciones() {
//		return this.opciones;
//	}
//
//	public List<String> getCorrectas() {
//		return this.correctas;
//	}

//	public void completarDatosVisibles() {
//	}

	public Integer getIndiceRespuesta(String Respuesta) {
		return 0;
	}
	

}
