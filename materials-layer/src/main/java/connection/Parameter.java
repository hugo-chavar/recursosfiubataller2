package connection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;

import connection.parsers.Parser;
import model.Archivo;
import model.Encuesta;
import model.EncuestaRespondida;
import model.Link;
import model.Recurso;
import model.Usuario;

@XmlRootElement(name = "parametro")
@XmlAccessorType(XmlAccessType.FIELD)
public class Parameter {

	public static Parameter createParameter(String xml) {
		Parser parser = new Parser();
		Parameter p = (Parameter) parser.unmarshal(xml, Parameter.class);
		if (p != null) {
			return p;
		}
		return new Parameter();

	}

	public Parameter() {
	}

	@XmlElement
	private Usuario usuario;

	@XmlElement
	private EncuestaRespondida respondida;

	@XmlElementRefs({ 
		@XmlElementRef(type = Recurso.class), 
		@XmlElementRef(type = Encuesta.class), 
		@XmlElementRef(type = Link.class),
		@XmlElementRef(type = Archivo.class),
		})
	private Recurso recurso;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Recurso getRecurso() {
		return recurso;
	}

	public void setRecurso(Recurso object) {
		this.recurso = object;
	}
	
	public EncuestaRespondida getRespondida() {
		return respondida;
	}

	public void setRespondida(EncuestaRespondida respondida) {
		this.respondida = respondida;
	}

}
