package model;

import java.util.ArrayList;
import java.util.List;

public class PreguntaRespuestaFija extends Pregunta{

  private List<String> respuestasPosibles;

  private boolean multiplesRespuestas;

  private List<Integer>  respuestasCorrectas;
  
  
  public PreguntaRespuestaFija(String s) {
	  unmarshall(s);
	  
  }

public PreguntaRespuestaFija(boolean multiplesRespuestas,String enunciado,Integer idPregunta){
  	super(enunciado,idPregunta);
  	this.multiplesRespuestas=multiplesRespuestas;
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

//retorna false si no puede agregar esas respuestas
//Andy: en los setters no se debe validar nada y deben ser void siempre. Hugo
public boolean setRespuestasCorrectas(List<Integer> respuestasCorrectas) {
	if(!this.isMultiplesRespuestas() && respuestasCorrectas.size()!=1)
		return false;
	this.respuestasCorrectas = respuestasCorrectas;
	return true;
}

@Override
public Integer evaluar(PreguntaRespondida respondida) {
	return respondida.evaluar(this);
}

public boolean isMultiplesRespuestas() {
	return multiplesRespuestas;
}

@Override
public String marshall() {
	StringBuilder sb = new StringBuilder("");
	sb.append(multiplesRespuestas);
	sb.append(";");
	sb.append(marshallRespuestasPosibles());
	sb.append(marshallRespuestasCorrectas());
	return super.marshall() + sb.toString();
	
}

private String marshallRespuestasPosibles() {
	StringBuilder sb = new StringBuilder("");
	for (String rta: respuestasPosibles) {
		sb.append(rta);
		sb.append(",");
	}
	sb.setLength(sb.length() - 1);
	sb.append(";");
	return sb.toString();
}

private String marshallRespuestasCorrectas() {
	StringBuilder sb = new StringBuilder("");
	for (Integer rta: respuestasCorrectas) {
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
	for (String s: splited) {
		respuestasPosibles.add(s);
	}
	
}

private void unmarshallRespuestasCorrectas(String rtas) {
	String[] splited = rtas.split(",");
	respuestasCorrectas = new ArrayList<Integer>();
	for (String s: splited) {
		respuestasCorrectas.add(Integer.valueOf(s));
	}
}

}