package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "preguntaRespondidaConOpciones")
public class PreguntaRespuestaFijaRespondida extends PreguntaRespondida {

	@XmlTransient
	private List<Integer> respuestasfijas = new ArrayList<Integer>();

	public PreguntaRespuestaFijaRespondida(Integer idPregunta) {
		super(idPregunta);
		type = ANSWERED_QUESTION_FIXED_TYPE;
	}

	public PreguntaRespuestaFijaRespondida(String s) {
		unmarshall(s);
		type = ANSWERED_QUESTION_FIXED_TYPE;
	}

	public PreguntaRespuestaFijaRespondida() {
	}

	public void unmarshall(String s) {
		super.unmarshall(s);
		String[] splited = StringEscapeUtils.splitIgnoringEscaped(s, ';');
		if(splited.length==4)
			unmarshallRespuestas(splited[3]);
		else
			unmarshallRespuestas("null");
		
	}

	public void responder(List<Integer> respuestas) {
		this.respuestasfijas = respuestas;
	}

	@XmlAttribute(name = "respuestas")
	public String getRespuestasFijas() {
		return marshallRespuestas() != "null" ? marshallRespuestas() : "";
	}

	@Override
	public Integer evaluar(Pregunta pregunta) {
		// Chequeo que coincida cantidad de respuestas
		if (!pregunta.getNroCorrectas().equals(this.respuestasfijas.size())) {
			correcta = false;
			return 0;
		}
		// Chequeo cada una de las respuestas
		for (Integer respuesta : respuestasfijas) {
			if (!pregunta.isCorrect(respuesta)) {
				correcta = false;
				return 0;
			}
		}
		correcta = true;
		return 1;
	}

	public void addRespuesta(Integer indiceRespuesta) {
		this.respuestasfijas.add(indiceRespuesta);
	}
	
	@Override
	public String marshall() {
		StringBuilder sb = new StringBuilder("");
		sb.append(marshallRespuestas());
		return super.marshall() + sb.toString();

	}

	private String marshallRespuestas() {
		if (respuestasfijas.isEmpty()) {
			return "null";
		}
		StringBuilder sb = new StringBuilder("");
		for (Integer rta : respuestasfijas) {
			sb.append(rta + 1);
			sb.append(",");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}
	
	private void unmarshallRespuestas(String rtas) {
		respuestasfijas = new ArrayList<Integer>();
		if ("null".equals(rtas)) {
			return;
		}
		String[] splited = rtas.split(",");
		for (String s : splited) {
			respuestasfijas.add(Integer.valueOf(s)-1);
		}

	}
}