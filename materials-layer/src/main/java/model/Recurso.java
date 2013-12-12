package model;

public class Recurso {

	protected Integer idRecurso;
	protected String tipo;
	protected Integer idAmbiente;
	protected String descripcion;

	public Recurso(Integer idRecurso, Integer idAmbiente, String descripcion) {
		this.idRecurso = idRecurso;
		this.idAmbiente = idAmbiente;
		this.descripcion = descripcion;
	}

	public Recurso() {
	}

	public String getTipo() {
		return tipo;
	}

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

	@Override
	public boolean equals(Object o) {
		if (o.getClass().equals(this.getClass())) {
			Recurso r = (Recurso) o;
			return r.getIdRecurso().equals(getIdRecurso()) 
				   && r.getIdAmbiente().equals(getIdAmbiente());
		}
		return false;
	}
}