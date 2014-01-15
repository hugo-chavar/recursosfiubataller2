package connection.responses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import model.Archivo;
import model.Link;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArchivoResponse extends OperationResponse {
	
	@XmlElement
	private Archivo archivo;
	
	public ArchivoResponse() {
		setSuccess(false);
	}
	
	public ArchivoResponse(Archivo archivo) {
		super();
		this.archivo = archivo;
		setSuccess(true);
	}

	public Archivo getArchivo() {
		return archivo;
	}

	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}
	

}
