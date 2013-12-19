package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class PreguntaRespondida {

	@XmlTransient
	private Integer idPregunta;
	
	//Si no es evaluado siempre estara en null este atributo
	@XmlAttribute(required=false)
	protected Boolean esCorrecta = null;
	
	@XmlAttribute(required=true)
	protected List<String> respuestasVisibles = new ArrayList<String>();
	
	public PreguntaRespondida() {
	}
	
	public PreguntaRespondida(Integer idPregunta) {
		this.idPregunta = idPregunta;
	}

	public Integer getIdPregunta() {
		return idPregunta;
	}
	
	// --------------------- metodos "abstractos" -------------
	
	public Integer evaluar(Pregunta pregunta) {
		return null;
	}
	
	public void completarDatosVisibles(Pregunta pregunta) {
	}

}