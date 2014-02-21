package connection.responses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;

import connection.Serializable;
import model.Archivo;
import model.Encuesta;
import model.EncuestaRespondida;
import model.Link;
import model.Recurso;
import model.Usuario;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class OperationResponse {
	
	public static OperationResponse createFailed(String reason) {
		OperationResponse response;
		response = new OperationResponse();
		response.setSuccess(false);
		response.setReason(reason);
		return response;
	}
	
	public static OperationResponse createSuccess() {
		OperationResponse response;
		response = new OperationResponse();
		response.setSuccess(true);
		return response;
	}

	@XmlElement
	private String reason;
	
	@XmlElement (nillable = false, required = true)
	private Boolean success;
	
	@XmlElementRefs({ 
		@XmlElementRef(type = Recurso.class), 
		@XmlElementRef(type = Encuesta.class), 
		@XmlElementRef(type = Link.class),
		@XmlElementRef(type = Archivo.class),
		@XmlElementRef(type = EncuestaRespondida.class),
		@XmlElementRef(type = Usuario.class)
		})
	private Serializable serializable;

	public OperationResponse() {
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	public Boolean getSuccess() {
		return this.success;
	}

	public Serializable getSerializable() {
		return serializable;
	}

	public void setSerializable(Serializable object) {
		this.serializable = object;
	}
	
	public String getReason() {
		return this.reason;
	}


}
