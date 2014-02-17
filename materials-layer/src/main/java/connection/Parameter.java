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
	private Integer usuarioId;

	@XmlElement
	private EncuestaRespondida respondida;

	@XmlElementRefs({ 
		@XmlElementRef(type = Recurso.class), 
		@XmlElementRef(type = Encuesta.class), 
		@XmlElementRef(type = Link.class),
		@XmlElementRef(type = Archivo.class),
		})
	private Recurso recurso;

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
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
