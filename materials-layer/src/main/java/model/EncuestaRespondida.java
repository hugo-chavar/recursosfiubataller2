package model;

import java.util.ArrayList;
import java.util.List;

public class EncuestaRespondida {

	private Integer evaluacion;

	private List<Integer> preguntasCorrectas = new ArrayList<Integer>();

	private Integer idUsuario;

	private Integer idRecurso;

	private List<PreguntaRespondida> preguntasRespondidas;

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

	public void setPreguntasRespondidas(
			List<PreguntaRespondida> preguntasRespondidas) {
		this.preguntasRespondidas = preguntasRespondidas;
	}

	public void evaluar(Encuesta encuesta) {
		Integer resultado = 0;
		List<Pregunta> preguntas = encuesta.getPreguntas();
		if (preguntas.size() == this.getPreguntasRespondidas().size()) {
			for (int i = 0; i < preguntas.size(); i++) {
				Integer respuesta = this.getPreguntasRespondidas().get(i)
						.evaluar(preguntas.get(i));
				if (respuesta > 0) {
					this.preguntasCorrectas.add(i);
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

}

