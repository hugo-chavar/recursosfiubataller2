package model;

import java.util.Vector;

public class Encuesta extends Recurso {

  public Encuesta(Integer idRecurso, Integer idAmbiente, String descripcion) {
		super(idRecurso, idAmbiente, descripcion);
		this.tipo="Encuesta";
		// TODO Auto-generated constructor stub
	}

public Encuesta() {
	super();
	this.tipo="Archivo";
	// TODO Auto-generated constructor stub
}

private Boolean evaluada;

  private Vector<Pregunta>  preguntas;
  
  private Vector<EncuestaRespondida>  encuestasRespondidas;

  public void guardar() {
	  //TODO:Escribir en la base de datos la info actualizada de la encuesta
  }

  public void agregarPregunta( Pregunta pregunta) {
	  preguntas.add(pregunta);
	  //TODO:Escribir cambio en la base de datos
  }

  public void eliminarPregunta(int nPregunta) {
	//TODO:Escribir cambio en la base de datos
	  preguntas.remove(nPregunta);
  }

  //-1:Encuesta no evaluada -2:Usuario no respondio la encuesta
  public Integer evaluar(int idUsuario) {
	  if(!this.evaluada)
		  return -1;
	  encuestasRespondidas=obtenerEncuestasRespondidasBd();
	  boolean encontrado= false;
	  Integer resultado=0;
	  int i=0;
	  EncuestaRespondida respondida=null;
	  while(!encontrado && i<encuestasRespondidas.size())
	  {
		  if(encuestasRespondidas.elementAt(i).getIdUsuario()==idUsuario)
		  {
			  encontrado=true;
			  respondida=encuestasRespondidas.elementAt(i);
		  }
	  }
	  if(encontrado){
		  for(int j=0;j<preguntas.size();j++){
			  resultado+=10*preguntas.elementAt(j).evaluar(respondida.getPreguntasRespondidas().elementAt(j));
		  }
		  resultado=resultado/preguntas.size();
		  return resultado;
	  }
	  else
	  return -2;
  }

private Vector<EncuestaRespondida> obtenerEncuestasRespondidasBd() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void Guardar() {
	//Guardar en la base de Datos
	
}

@Override
public void Crear() {
	// TODO Auto-generated method stub
	
}

}