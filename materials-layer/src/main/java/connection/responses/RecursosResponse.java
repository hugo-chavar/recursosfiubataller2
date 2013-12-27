package connection.responses;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;

import model.Recurso;

public class RecursosResponse extends OperationResponse {

	@XmlElementWrapper
	private List<Recurso> recursos;
	
	public RecursosResponse() {
		recursos = new ArrayList<Recurso>();
	}
	
	public void add(Recurso recurso) {
		recursos.add(recurso);
	}
}
