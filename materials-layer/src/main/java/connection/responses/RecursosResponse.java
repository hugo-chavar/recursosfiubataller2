package connection.responses;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import model.Recurso;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecursosResponse extends OperationResponse {

	@XmlElementWrapper(name = "recursos")
	private List<Recurso> recursos;
	
	public RecursosResponse() {
		recursos = new ArrayList<Recurso>();
	}
	
	public void add(Recurso recurso) {
		recursos.add(recurso);
	}
}
