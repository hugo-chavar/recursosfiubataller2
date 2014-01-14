package connection.responses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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

	@XmlElement // (nillable = true)
	private String reason;
	
	@XmlElement (nillable = false, required = true)
	private Boolean success;

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


}
