package model;

public class Recurso {

	public Recurso(Integer idRecurso, Integer idAmbiente, String descripcion) {
		this.idRecurso = idRecurso;
		this.idAmbiente = idAmbiente;
		this.descripcion = descripcion;
	}

	public Recurso() {
		// TODO Auto-generated constructor stub
	}

	protected Integer idRecurso;
	
	protected String tipo;

	public String getTipo() {
		return tipo;
	}

	protected Integer idAmbiente;

	protected String descripcion;

	public Integer getIdRecurso() {
		return idRecurso;
	}

	public Integer getIdAmbiente() {
		return idAmbiente;
	}

	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void Guardar() {
	}

	// TODO:ver si es necesario
	// public Recurso Obtener() {
	// }

	public void Crear() {
	}

}