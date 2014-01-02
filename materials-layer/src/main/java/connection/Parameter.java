package connection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "parametro")
@XmlAccessorType(XmlAccessType.FIELD)
public class Parameter {

	public static Parameter createParameter(String xml) {
		Parser parser = new Parser();
		Parameter p = (Parameter)parser.unmarshal(xml, Parameter.class);
		return p;
	}
	
	public Parameter() {
		
	}

	@XmlElement (nillable = true)
	private Integer ambitoId;
	
	@XmlElement (nillable = true)
	private Integer usuarioId;
	
	@XmlElement (nillable = true)
	private Integer recursoId;
	
	public Integer getAmbitoId() {
		return ambitoId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public Integer getRecursoId() {
		return recursoId;
	}

	
}
