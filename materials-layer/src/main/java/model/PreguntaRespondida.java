package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
	protected Boolean esCorrecta = null;

	@XmlAttribute(required = true)
	protected List<String> respuestasVisibles = new ArrayList<String>();

	public static List<PreguntaRespondida> unmarshallAll(String field) {
		String[] splited = field.split("\\|");
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

	public void completarDatosVisibles(Pregunta pregunta) {
	}

	protected String marshall() {
		StringBuilder sb = new StringBuilder("");
		sb.append(type);
		sb.append(";");
		sb.append(idPregunta);
		sb.append(";");
		sb.append(esCorrecta);
		sb.append(";");
		return sb.toString();
	}

	protected void unmarshall(String s) {
		String[] splited = s.split(";");
		type = splited[0];
		idPregunta = Integer.valueOf(splited[1]);
		esCorrecta = splited[2] != "null" ? Boolean.valueOf(splited[2]) : null;
	}

}