package model;

import java.util.ArrayList;
import java.util.List;

public class PreguntaRespuestaFija extends Pregunta {

	private List<String> respuestasPosibles = new ArrayList<String>();
	private List<Integer> respuestasCorrectas = new ArrayList<Integer>();

	public boolean isMultiplesRespuestasCorrectas() {
		return respuestasCorrectas.size() > 1;
	}

	public PreguntaRespuestaFija() {
		type = FIXED_ANSWER_TYPE;
	}

	public PreguntaRespuestaFija(String s) {
		unmarshall(s);
		type = FIXED_ANSWER_TYPE;
	}

	public List<String> getRespuestasPosibles() {
		return respuestasPosibles;
	}

	public void setRespuestasPosibles(List<String> respuestasPosibles) {
		this.respuestasPosibles = respuestasPosibles;
	}

	public List<Integer> getRespuestasCorrectas() {
		return respuestasCorrectas;
	}

	@Override
	public Integer evaluar(PreguntaRespondida respondida) {
		return respondida.evaluar(this);
	}

	@Override
	public String marshall() {
		StringBuilder sb = new StringBuilder("");
		sb.append(marshallRespuestasPosibles());
		sb.append(marshallRespuestasCorrectas());
		return super.marshall() + sb.toString();

	}

	private String marshallRespuestasPosibles() {
		StringBuilder sb = new StringBuilder("");
		for (String rta : respuestasPosibles) {
			sb.append(rta);
			sb.append(",");
		}
		sb.setLength(sb.length() - 1);
		sb.append(";");
		return sb.toString();
	}

	private String marshallRespuestasCorrectas() {
		StringBuilder sb = new StringBuilder("");
		for (Integer rta : respuestasCorrectas) {
			sb.append(rta);
			sb.append(",");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	public void unmarshall(String s) {
		super.unmarshall(s);
		String[] splited = s.split(";");
		unmarshallRespuestasPosibles(splited[3]);
		unmarshallRespuestasCorrectas(splited[4]);

	}

	private void unmarshallRespuestasPosibles(String rtas) {
		String[] splited = rtas.split(",");
		respuestasPosibles = new ArrayList<String>();
		for (String s : splited) {
			respuestasPosibles.add(s);
		}

	}

	private void unmarshallRespuestasCorrectas(String rtas) {
		String[] splited = rtas.split(",");
		respuestasCorrectas = new ArrayList<Integer>();
		for (String s : splited) {
			respuestasCorrectas.add(Integer.valueOf(s));
		}
	}

	@Override
	public void addRespuestaCorrecta(String string) {
		int i = 0;
		for (String s : respuestasPosibles) {
			if (s.equals(string)) {
				addRespuestaCorrecta(new Integer(i));
				return;
			}
			i++;
		}

	}

	private void addRespuestaCorrecta(Integer integer) {
		respuestasCorrectas.add(integer);

	}

	@Override
	public boolean isCorrect(Integer respuesta) {

		for (Integer res : this.respuestasCorrectas) {
			if (res.equals(respuesta))
				return true;
		}
		return false;
	}

	@Override
	public boolean isCorrect(String respuesta) {
		return false;
	}

	@Override
	public Integer getNroCorrectas() {
		return this.respuestasCorrectas.size();
	}
	
	@Override
	public Integer getIndiceRespuesta(String respuesta){
		Integer i=0;
		for(String res : respuestasPosibles ){
			if(res==respuesta)
				return i;
			i++;
		}
		return -1;
	}

	@Override
	public void completarDatosVisibles() {
		opciones=respuestasPosibles;
		for(Integer n : this.respuestasCorrectas)
			correctas.add(respuestasPosibles.get(n));
	}
	
}