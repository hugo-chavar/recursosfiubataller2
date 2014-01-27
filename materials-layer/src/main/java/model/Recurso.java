package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import connection.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Recurso implements Serializable {

	@XmlElement
	protected Integer recursoId;

	@XmlElement
	protected String tipo;

	@XmlElement
	protected Integer ambitoId;

	@XmlElement
	protected String descripcion;

	public Recurso(Integer idRecurso, Integer idAmbito, String descripcion) {
		this.recursoId = idRecurso;
		this.ambitoId = idAmbito;
		this.descripcion = descripcion;
	}

	public Recurso(Integer idRecurso, Integer idAmbito, String descripcion, String tipo) {
		this.recursoId = idRecurso;
		this.ambitoId = idAmbito;
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
		if (idRecurso != null) {
			this.recursoId = idRecurso;
		}
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setAmbitoId(Integer id) {
		if (id != null) {
			this.ambitoId = id;
		}
	}

	public void setDescripcion(String descrip) {
		if (descrip != null) {
			this.descripcion = descrip;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass().equals(this.getClass())) {
			Recurso r = (Recurso) o;
			return r.getRecursoId().equals(getRecursoId());
		}
		return false;
	}

	@Override
	public String getInfo() {
		return "Recurso ID " +  getRecursoId();
	}

	@Override
	public void updateFields(Serializable s) {
		Recurso aux = (Recurso)s; 
		setRecursoId(aux.getRecursoId());
		setAmbitoId(aux.getAmbitoId());
		setDescripcion(aux.getDescripcion());
		
	}
}