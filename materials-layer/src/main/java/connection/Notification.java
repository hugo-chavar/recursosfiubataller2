package connection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "notificacion")
@XmlAccessorType(XmlAccessType.FIELD)
public class Notification implements Serializable {
	@XmlElement(name = "numero")
	private Integer number;
	
	@XmlElement(name = "mensaje")
	private String message;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getMessage() {
		return number + ":" + message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean success() {
		return number.equals(2);
	}

	@Override
	public String getInfo() {
		return getMessage();
	}

	@Override
	public void updateFields(Serializable s) {
		
	}

}
