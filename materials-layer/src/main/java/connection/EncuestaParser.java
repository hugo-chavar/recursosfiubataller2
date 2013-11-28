package connection;

import java.util.List;

import model.Encuesta;
import model.Pregunta;


public class EncuestaParser extends Parser {

	public String serializeEncuesta(Encuesta encuesta) {
		String xml = "";
		xml = this.addField(xml, "IDAmbiente", String.valueOf(encuesta.getIdAmbiente()));
		xml = this.addField(xml, "IDEncuesta", String.valueOf(encuesta.getIdRecurso()));
		xml = this.addField(xml, "Descripcion", encuesta.getDescripcion());
		xml = this.addField(xml, "Evaluada", String.valueOf(encuesta.getEvaluada()));
		xml = this.addTag(xml, "Encuesta");
		xml = this.addTag(xml, "WS");
		return xml;
	}
	
	public Encuesta deserializeEncuesta(String xml) {
		xml = this.removeTag(xml);
		xml = this.removeTag(xml);
		int IDAmbiente = Integer.parseInt(this.getFieldValue(xml));
		xml = this.removeField(xml);
		int IDEncuesta = Integer.parseInt(this.getFieldValue(xml));
		xml = this.removeField(xml);
		String descripcion = this.getFieldValue(xml);
		xml = this.removeField(xml);
		Boolean evaluada = Boolean.parseBoolean(this.getFieldValue(xml));
		Encuesta encuesta = new Encuesta(IDEncuesta, IDAmbiente, descripcion, evaluada);
		return encuesta;
	}
	
	public String serializePreguntas(Encuesta encuesta) {
		List<Pregunta> preguntas = encuesta.getPreguntas();
		String xml = "";
		for (int i=0; i<preguntas.size(); i++) {
			xml = this.addField(xml, "IDAmbiente", String.valueOf(encuesta.getIdAmbiente()));
			xml = this.addField(xml, "IDEncuesta", String.valueOf(encuesta.getIdRecurso()));
			xml = this.addField(xml, "IDPregunta", String.valueOf(preguntas.get(i).getIdPregunta()));
			xml = this.addField(xml, "Enunciado", preguntas.get(i).getEnunciado());
			// TODO: Agregar opciones de pregunta.
			xml = this.addTag(xml, "Pregunta");
		}
		xml = this.addTag(xml, "WS");
		return xml;
	}
	
//	public List<Pregunta> deserializePreguntas(String xml) {
//		List<Pregunta> preguntas;
//		
//		
//		return preguntas;
//	}
	
}
