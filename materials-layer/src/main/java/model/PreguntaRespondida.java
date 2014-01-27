package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import connection.Parser;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class PreguntaRespondida {

	protected static String ANSWERED_QUESTION_FIXED_TYPE = "F";
	protected static String ANSWERED_QUESTION_TO_COMPLETE_TYPE = "C";

	@XmlTransient
	private Integer idPregunta;
	
	@XmlTransient
	protected String type;

	// Si no es evaluado siempre estara en null este atributo
	@XmlAttribute(required = false)
	protected Boolean isCorrecta = null;

	public void setIsCorrecta(Boolean isCorrecta) {
		this.isCorrecta = isCorrecta;
	}

	public Boolean getIsCorrecta() {
		return isCorrecta;
	}

	@XmlAttribute(required = true)
	protected List<String> respuestas = new ArrayList<String>();

	public List<String> getRespuestas() {
		return respuestas;
	}

	public static List<PreguntaRespondida> unmarshallAll(String field) {
		String[] splited = field.split("\\|");
		splited = ignoreSpecialCharactersInSplit(splited, "|");
		List<PreguntaRespondida> result = new ArrayList<PreguntaRespondida>();

		for (String s : splited) {
			if (unmarshallType(s).equals(ANSWERED_QUESTION_FIXED_TYPE)) {
				result.add(new PreguntaRespuestaFijaRespondida(s));
			} else {
				result.add(new PreguntaRespuestaACompletarRespondida(s));
			}
		}
		return result;
	}

	private static String unmarshallType(String marshalled) {
		return marshalled.substring(0, marshalled.indexOf(";"));
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

	public PreguntaRespondida() {
	}

	public PreguntaRespondida(Integer idPregunta) {
		this.idPregunta = idPregunta;
	}

	public Integer getIdPregunta() {
		return idPregunta;
	}

	// --------------------- metodos "abstractos" -------------

	public Integer evaluar(Pregunta pregunta) {
		return null;
	}

	public void completarDatosVisibles(Pregunta pregunta) {
	}

	protected String marshall() {
		StringBuilder sb = new StringBuilder("");
		sb.append(type);
		sb.append(";");
		sb.append(idPregunta);
		sb.append(";");
		sb.append(isCorrecta);
		sb.append(";");
		return sb.toString();
	}

	protected void unmarshall(String s) {
		String[] splited = s.split(";");
		type = splited[0];
		idPregunta = Integer.valueOf(splited[1]);
		isCorrecta = splited[2] != "null" ? Boolean.valueOf(splited[2]) : null;
	}

}