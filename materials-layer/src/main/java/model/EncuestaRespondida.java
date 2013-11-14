package model;

import java.util.Vector;

public class EncuestaRespondida {

  private Integer idUsuario;

  private Integer idRecurso;

  private Vector<PreguntaRespondida> preguntasRespondidas;
  
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

public Vector<PreguntaRespondida> getPreguntasRespondidas() {
	return preguntasRespondidas;
}

public void setPreguntasRespondidas(Vector<PreguntaRespondida> preguntasRespondidas) {
	this.preguntasRespondidas = preguntasRespondidas;
}

}