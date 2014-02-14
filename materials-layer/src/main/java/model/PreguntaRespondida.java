package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import model.StringEscapeUtils;

@XmlRootElement(name="preguntaRespondida")
@XmlSeeAlso({PreguntaRespuestaFijaRespondida.class,  PreguntaRespuestaACompletarRespondida.class})
@XmlAccessorType(XmlAccessType.NONE)
public class PreguntaRespondida {

	protected static String ANSWERED_QUESTION_FIXED_TYPE = "F";
	protected static String ANSWERED_QUESTION_TO_COMPLETE_TYPE = "C";

	@XmlTransient
	private Integer idPregunta;
	
	@XmlTransient
	protected String type;

	// Si no es evaluado siempre estara en null este atributo
	@XmlAttribute
	protected Boolean correcta = null;

	public void setCorrecta(Boolean isCorrecta) {
		this.correcta = isCorrecta;
	}

	public Boolean getCorrecta() {
		return correcta;
	}

	@XmlTransient
	protected List<String> respuestas = new ArrayList<String>();

	public List<String> getRespuestas() {
		return respuestas;
	}

	public static List<PreguntaRespondida> unmarshallAll(String field) {
//		String[] splited = field.split("\\|");
//		splited = StringEscapeUtils.ignoreSpecialCharactersInSplit(splited, "|");
		String[] splited = StringEscapeUtils.splitIgnoringEscaped(field, '|');
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

	protected String marshall() {
		StringBuilder sb = new StringBuilder("");
		sb.append(type);
		sb.append(";");
		sb.append(idPregunta);
		sb.append(";");
		sb.append(correcta);
		sb.append(";");
		return sb.toString();
	}

	protected void unmarshall(String s) {
		String[] splited = s.split(";");
		type = splited[0];
		idPregunta = Integer.valueOf(splited[1]);
		correcta = (!splited[2].equals("null"))? Boolean.valueOf(splited[2]) : null;
	}

}