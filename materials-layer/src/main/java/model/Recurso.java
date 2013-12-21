package model;

public class Recurso {

	protected Integer recursoId;
	protected String tipo;
	protected Integer ambitoId;
	protected String descripcion;

	public Recurso(Integer idRecurso, Integer idAmbiente, String descripcion) {
		this.recursoId = idRecurso;
		this.ambitoId = idAmbiente;
		this.descripcion = descripcion;
	}

	public Recurso(Integer idRecurso, Integer idAmbiente, String descripcion, String tipo) {
		this.recursoId = idRecurso;
		this.ambitoId = idAmbiente;
		this.descripcion = descripcion;
		this.tipo = tipo;
	}
	
	public Recurso() {
	}

	public String getTipo() {
		return tipo;
	}

	public Integer getRecursoId() {
		return recursoId;
	}

	public Integer getAmbitoId() {
		return ambitoId;
	}

	public void setRecursoId(Integer idRecurso) {
		this.recursoId = idRecurso;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setAmbitoId(int id){
		this.ambitoId = id;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass().equals(this.getClass())) {
			Recurso r = (Recurso) o;
			return r.getRecursoId().equals(getRecursoId()) ;
//				   && r.getIdAmbiente().equals(getIdAmbiente());
		}
		return false;
	}
}