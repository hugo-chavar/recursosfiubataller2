package connection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import model.Encuesta;

@XmlRootElement(name = "parametros")
@XmlAccessorType(XmlAccessType.FIELD)
public class EncuestaParameter extends Parameter  {
	public static EncuestaParameter createParameter(String xml) {
		Parser parser = new Parser();
		EncuestaParameter p = (EncuestaParameter)parser.unmarshal(xml, EncuestaParameter.class);
		if (p != null) {
			return p;
		}
		return new EncuestaParameter();
		
	}
	
	public EncuestaParameter() {
		
	}

	@XmlElement (nillable = true)
	private Encuesta encuesta;
	
	public Encuesta getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(Encuesta encuesta) {
		this.encuesta = encuesta;
	}

}
