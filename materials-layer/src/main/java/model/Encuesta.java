package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Encuesta extends Recurso {

	@XmlTransient
	private int countOptions = 0;

	@XmlAttribute
	private boolean evaluada;
	
	@XmlElementWrapper
	@XmlElementRefs({
		   @XmlElementRef(type=PreguntaRespuestaFija.class),
		   @XmlElementRef(type=PreguntaRespuestaACompletar.class)
		})
	private List<Pregunta> preguntas = new ArrayList<Pregunta>();

	public Encuesta(Integer idRecurso, Integer idAmbito, String descripcion, boolean evaluada) {
		super(idRecurso, idAmbito, descripcion);
		this.tipo = "Encuesta";
		this.evaluada = evaluada;
	}

	public Encuesta() {
		super();
		this.tipo = "Encuesta";
	}
	
	public Encuesta(Recurso recurso) {
		super(recurso.getRecursoId(), null, null);
	}

	public boolean isEvaluada() {
		return evaluada;
	}

	public List<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}

	public void addPregunta(Pregunta pregunta) {
		countOptions++;
		pregunta.setIdPregunta(countOptions);
		preguntas.add(pregunta);
	}

	public String marshallPreguntas() {
		if (preguntas.isEmpty()) {
			return "null";
		}
		StringBuilder sb = new StringBuilder("");
		for (Pregunta p : preguntas) {
			sb.append(p.marshall());
			sb.append("|");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	public void unmarshallPreguntas(String field) {
		preguntas = Pregunta.unmarshallAll(field);
	}
}