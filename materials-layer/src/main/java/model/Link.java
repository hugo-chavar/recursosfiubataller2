package model;

public class Link extends Recurso {

public Link(Integer idRecurso, Integer idAmbiente, String descripcion) {
		super(idRecurso, idAmbiente, descripcion);
		this.tipo="Link";
		// TODO Auto-generated constructor stub
		}

public Link(){
	this.tipo="Link";
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