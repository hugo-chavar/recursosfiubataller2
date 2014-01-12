package connection.responses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import model.EncuestaRespondida;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class EncuestaRespondidaResponse extends OperationResponse {
	
	@XmlElement
	private EncuestaRespondida respondida;
	
	public EncuestaRespondidaResponse() {
		setSuccess(false);
	}
	
	public EncuestaRespondidaResponse(EncuestaRespondida respondida) {
		super();
		this.respondida = respondida;
		setSuccess(true);
	}

	public EncuestaRespondida getRespondida() {
		return respondida;
	}

	public void setRespondida(EncuestaRespondida respondida) {
		this.respondida = respondida;
	}
}
