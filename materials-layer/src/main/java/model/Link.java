package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Link extends Recurso {

	@XmlElement
	private String nombre;
	
	public Link(Integer idRecurso, Integer idAmbiente, String descripcion) {
		super(idRecurso, idAmbiente, descripcion);
		this.tipo = "Link";
	}

	public Link() {
		this.tipo = "Link";
	}

	public Link(Recurso recurso) {
		super(recurso.getRecursoId(), recurso.getAmbitoId(), recurso.getDescripcion());
		this.tipo = "Link";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}