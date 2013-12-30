package connection.responses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import model.Link;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class LinkResponse extends OperationResponse {
	
	@XmlElement
	private Link link;
	
	public LinkResponse() {
		setSuccess(false);
	}
	
	public LinkResponse(Link link) {
		super();
		this.link = link;
		setSuccess(true);
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}
	

}
