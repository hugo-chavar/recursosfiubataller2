package model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import connection.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EncuestaRespondida implements Serializable {

	@XmlElement
	private Integer evaluacion;

	@XmlTransient
	private Integer idUsuario;

	@XmlTransient
	private Integer idRecurso;

	@XmlElementWrapper
	@XmlElementRefs({
		   @XmlElementRef(type=PreguntaRespuestaFijaRespondida.class),
		   @XmlElementRef(type=PreguntaRespuestaACompletarRespondida.class)
		})
	private List<PreguntaRespondida> preguntasRespondidas;

	public EncuestaRespondida() { }
	
	public EncuestaRespondida(Integer idRecurso, Integer idUsuario) {
		this.idRecurso = idRecurso;
		this.idUsuario = idUsuario;
		this.evaluacion = -1;
	}
	
	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdRecurso() {
		return idRecurso;
	}

	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
	}

	public List<PreguntaRespondida> getPreguntasRespondidas() {
		return preguntasRespondidas;
	}

	public void setPreguntasRespondidas(List<PreguntaRespondida> preguntasRespondidas) {
		this.preguntasRespondidas = preguntasRespondidas;
	}

	public void setEvaluacion(Integer evaluacion) {
		this.evaluacion = evaluacion;
	}

	public void evaluar(Encuesta encuesta) {
		Integer resultado = 0;
		List<Pregunta> preguntas = encuesta.getPreguntas();
		if (preguntas.size() == this.getPreguntasRespondidas().size()) {
			for (int i = 0; i < preguntas.size(); i++) {
				Integer respuesta = this.getPreguntasRespondidas().get(i).evaluar(preguntas.get(i));
				if (respuesta > 0) {
					resultado += 100 * respuesta;
				}
			}
			resultado = resultado / preguntas.size();
			this.evaluacion = resultado;
		} else
			this.evaluacion = -1;
	}

	public Integer getEvaluacion() {
		return evaluacion;
	}
	
	public String marshallPreguntasRespondidas() {
		if (preguntasRespondidas.isEmpty()) {
			return "null";
		}
		StringBuilder sb = new StringBuilder("");
		for (PreguntaRespondida p : preguntasRespondidas) {
			sb.append(p.marshall());
			sb.append("|");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	public void unmarshallPreguntasRespondidas(String field) {
		preguntasRespondidas =  PreguntaRespondida.unmarshallAll(field);
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass().equals(this.getClass())) {
			EncuestaRespondida r = (EncuestaRespondida) o;
			return r.getIdRecurso().equals(getIdRecurso())
					&& r.getIdUsuario().equals(getIdUsuario());
		}
		return false;
	}

	@Override
	public String getInfo() {
		return "EncuestaRespondida recursoId: " + getIdRecurso() + " usuarioId: " + getIdUsuario();
	}

	@Override
	public void updateFields(Serializable s) {
//		EncuestaRespondida er = (EncuestaRespondida)s;
		
	}
}