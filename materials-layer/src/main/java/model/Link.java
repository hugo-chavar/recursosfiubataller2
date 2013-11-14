package model;

public class Link extends Recurso {

  public Link(	String nombre) {
		this.descripcion = nombre;
	}

public String nombre;

  public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public void Guardar() {
	  //guardar objeto en base de datos
  }

  public String Obtener() {
	  return nombre;
  }

@Override
public void Crear() {
	// TODO Auto-generated method stub
	
}

}