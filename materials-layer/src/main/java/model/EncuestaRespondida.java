package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class EncuestaRespondida {

  private Integer evaluacion;
  
  private ArrayList<Integer> preguntasCorrectas;
	
  private Integer idUsuario;

  private Integer idRecurso;

  private ArrayList<PreguntaRespondida> preguntasRespondidas;
  
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

public void setPreguntasRespondidas(ArrayList<PreguntaRespondida> preguntasRespondidas) {
	this.preguntasRespondidas = preguntasRespondidas;
}

public void evaluar(Encuesta encuesta) {
		Integer resultado=0;
		List<Pregunta> preguntas=encuesta.getPreguntas();
		if(preguntas.size()==this.getPreguntasRespondidas().size()){
			for(int i=0;i<preguntas.size();i++){
				Integer respuesta=preguntas.get(i).evaluar(this.getPreguntasRespondidas().get(i));
				if(respuesta>0){
					this.preguntasCorrectas.add(i);	
					resultado += 10 * preguntas.get(i).evaluar(this.getPreguntasRespondidas().get(i));
				}
			}
			resultado = resultado / preguntas.size();
			this.evaluacion=resultado;
		}
		else 
			this.evaluacion=-1;
}

public Integer getEvaluacion() {
	return evaluacion;
}

}

