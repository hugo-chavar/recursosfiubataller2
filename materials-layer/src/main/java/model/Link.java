package model;

public class Link extends Recurso {

	private String nombre;
	
	public Link(Integer idRecurso, Integer idAmbiente, String descripcion) {
		super(idRecurso, idAmbiente, descripcion);
		this.tipo = "Link";
	}

	public Link() {
		this.tipo = "Link";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}