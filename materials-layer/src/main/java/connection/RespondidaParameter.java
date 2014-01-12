package connection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import model.EncuestaRespondida;

@XmlRootElement(name = "parametro")
@XmlAccessorType(XmlAccessType.FIELD)
public class RespondidaParameter extends Parameter{
	public static RespondidaParameter createParameter(String xml) {
		Parser parser = new Parser();
		RespondidaParameter p = (RespondidaParameter)parser.unmarshal(xml, RespondidaParameter.class);
		if (p != null) {
			return p;
		}
		return new RespondidaParameter();
		
	}
	
	public RespondidaParameter() {
		
	}

	@XmlElement (nillable = true)
	private EncuestaRespondida respondida;
	
	public EncuestaRespondida getRespondida() {
		return respondida;
	}

	public void setRespondida(EncuestaRespondida respondida) {
		this.respondida = respondida;
	}
}
