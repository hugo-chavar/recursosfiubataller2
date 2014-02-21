package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "WS/Usuario")
@XmlAccessorType(XmlAccessType.FIELD)

public class Usuario {
	
	@XmlElement
	private String id;
	
	@XmlElement
	private String username;
	
	@XmlElement
	private String password;
	
	@XmlElement
	private String nombre;
	
	@XmlElement
	private String apellido;
	
	@XmlElement
	private String padron;
	
	@XmlElement
	private String email;
	
	@XmlElement
	private String fechaNac;
	
	@XmlElement
	private Boolean activado;
	
	@XmlElement
	private Boolean habilitado;
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getPadron() {
		return padron;
	}
	
	public void setPadron(String padron) {
		this.padron = padron;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFechaNac() {
		return fechaNac;
	}
	
	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}
	
	public Boolean getActivado() {
		return activado;
	}
	
	public void setActivado(Boolean activado) {
		this.activado = activado;
	}
	
	public Boolean getHabilitado() {
		return habilitado;
	}
	
	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}
	
} 