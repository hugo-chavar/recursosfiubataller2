package connection.responses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import model.Encuesta;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class EncuestaResponse extends OperationResponse {
	
	@XmlElement
	private Encuesta encuesta;
	
	public EncuestaResponse() {
		setSuccess(false);
	}
	
	public EncuestaResponse(Encuesta encuesta) {
		super();
		this.encuesta = encuesta;
		setSuccess(true);
	}

	public Encuesta getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(Encuesta encuesta) {
		this.encuesta = encuesta;
	}

}
