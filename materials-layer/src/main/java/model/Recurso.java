package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Recurso {

	@XmlElement //(nillable = false, required = true)
	protected Integer recursoId;

	@XmlElement //(nillable = false, required = true)
	protected String tipo;

	@XmlElement //(nillable = false, required = true)
	protected Integer ambitoId;

	@XmlElement //(nillable = false, required = true)
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
}