package model;

import java.util.ArrayList;
import java.util.List;


public class EncuestaRespondida {

	private Integer evaluacion;

	private Integer idUsuario;

	private Integer idRecurso;
	
	private Integer idAmbiente;

	private List<PreguntaRespondida> preguntasRespondidas;

	public EncuestaRespondida() { }
	
	public EncuestaRespondida(Integer idAmbiente, Integer idRecurso, Integer idUsuario, int evaluacion) {
		this.idAmbiente = idAmbiente;
		this.idRecurso = idRecurso;
		this.idUsuario = idUsuario;
		this.evaluacion = evaluacion;
	}
	
	public Integer getIdAmbiente() {
		return this.idAmbiente;
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
		StringBuilder sb = new StringBuilder("");
		for (PreguntaRespondida p : preguntasRespondidas) {
			// TODO: Hacer un marshal de PreguntaRespondida por cada tipo
			sb.append("|");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	public void unmarshallPreguntasRespondidas(String field) {
		String[] splited = field.split("\\|");
		List<PreguntaRespondida> result = new ArrayList<PreguntaRespondida>();

		for (String s : splited) {
			// TODO: Hacer un unmarshal de PreguntaRespondida por cada tipo
		}
		
		preguntasRespondidas = result;
	}

	public void completarDatosVisibles(List<Pregunta> preguntas) {
		for(int i=0;i<preguntas.size();i++){
			this.preguntasRespondidas.get(i).completarDatosVisibles(preguntas.get(i));
		}
	}

	public void recuperarDatosVisibles(List<Pregunta> preguntas) {
		List<PreguntaRespondida> preguntasRespondidasAux = this.preguntasRespondidas;
		this.preguntasRespondidas = new ArrayList<PreguntaRespondida>();
		for (Integer i=0;i<preguntas.size();i++){
			Pregunta pregunta = preguntas.get(i);
			if(pregunta.getOpciones().isEmpty()){
				PreguntaRespuestaACompletarRespondida respondida = new PreguntaRespuestaACompletarRespondida(pregunta.getIdPregunta());
				respondida.responder(preguntasRespondidasAux.get(i).respuestasVisibles.get(0));
				this.preguntasRespondidas.add(respondida);
			}
			else{
				PreguntaRespuestaFijaRespondida respondida = new PreguntaRespuestaFijaRespondida(pregunta.getIdPregunta());
				for(String res : preguntasRespondidasAux.get(i).respuestasVisibles){
					respondida.addRespuesta(pregunta.getIndiceRespuesta(res));
				}
				this.preguntasRespondidas.add(respondida);
			}
		}	
	}
	
	@Override
	public boolean equals(Object o) {
		if (o.getClass().equals(this.getClass())) {
			EncuestaRespondida r = (EncuestaRespondida) o;
			return r.getIdRecurso().equals(getIdRecurso()) 
				   && r.getIdAmbiente().equals(getIdAmbiente())
						   && r.getIdUsuario().equals(getIdUsuario());
		}
		return false;
	}
	
}

