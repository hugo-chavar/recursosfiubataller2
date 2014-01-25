package connection;

import java.util.ArrayList;
import java.util.List;

import model.Encuesta;
import model.Pregunta;
import model.PreguntaRespuestaACompletar;
import model.PreguntaRespuestaFija;
import model.Recurso;
import connection.cache.Cache;
import connection.exceptions.GetException;
import connection.exceptions.ParseException;
import connection.responses.OperationResponse;


public class EncuestaRequester extends HandlerRequester {

	private EncuestaParser parser;
	private Cache<Encuesta> cache;
	
	public EncuestaRequester() {

		super();
		parser = new EncuestaParser();
		cache = new Cache<Encuesta>();

		generateTestData();

	}

	private void generateTestData() {
		Encuesta enc = new Encuesta(11003, -1, "una encuesta chica", false);
		
		Pregunta p1, p2, p3, p4, p5;
		p2 = new PreguntaRespuestaFija();
		List<String> opciones = new ArrayList<String>();
		p2.setEnunciado("cuantas materias| te, faltan; para recibirte?");
		opciones.add("1");
		opciones.add("menos de 5");
		opciones.add("entre 5 y 10");
		opciones.add("mas de 10");
		((PreguntaRespuestaFija) p2).setRespuestasPosibles(opciones);
		enc.addPregunta(p2);

		p3 = new PreguntaRespuestaFija();
		opciones = new ArrayList<String>();
		p3.setEnunciado("que materia fue la mas dificil?");
		opciones.add("analisis 2");
		opciones.add("algebra 2");
		opciones.add("taller 1");
		opciones.add("org de datos");
		((PreguntaRespuestaFija) p3).setRespuestasPosibles(opciones);
		enc.addPregunta(p3);
		current = enc;
		updateCache();

		enc = new Encuesta(11004, -1, "una encuesta grande", false);
		p1 = new PreguntaRespuestaFija();
		p1.setEnunciado("de que color es el caballo blanco de san martin?");
		opciones = new ArrayList<String>();
		opciones.add("rojo");
		opciones.add("verde");
		opciones.add("azul");
		opciones.add("blanco");
		((PreguntaRespuestaFija) p1).setRespuestasPosibles(opciones);
		p1.addRespuestaCorrecta("blanco");

		enc.addPregunta(p1);

		p2 = new PreguntaRespuestaFija();
		opciones = new ArrayList<String>();
		p2.setEnunciado("a que equipo del futbol argentino le denominan Millo");
		opciones.add("velez");
		opciones.add("River Plate");
		opciones.add("crucero del norte");
		opciones.add("estudiantes");
		((PreguntaRespuestaFija) p2).setRespuestasPosibles(opciones);

		enc.addPregunta(p2);
		p2.addRespuestaCorrecta("River Plate");

		p3 = new PreguntaRespuestaFija();
		opciones = new ArrayList<String>();
		p3.setEnunciado("cual es un patron de diseno creacional");
		opciones.add("command");
		opciones.add("mediator");
		opciones.add("builder");
		opciones.add("facade");

		((PreguntaRespuestaFija) p3).setRespuestasPosibles(opciones);
		p3.addRespuestaCorrecta("builder");

		enc.addPregunta(p3);

		p4 = new PreguntaRespuestaFija();
		opciones = new ArrayList<String>();
		p4.setEnunciado("Un test unitario debe presentar las siguientes características");
		opciones.add("Rapido");
		opciones.add("Moldeable");
		opciones.add("Configurable");
		opciones.add("Acoplable");
		opciones.add("Lento");
		opciones.add("Extensible");
		opciones.add("Repetible");
		opciones.add("Profesional");
		opciones.add("Maduro");
		opciones.add("Amplio");
		opciones.add("Simple");
		opciones.add("Independiente");
		opciones.add("Automatizable");

		((PreguntaRespuestaFija) p4).setRespuestasPosibles(opciones);

		p4.addRespuestaCorrecta("Rapido");
		p4.addRespuestaCorrecta("Profesional");
		p4.addRespuestaCorrecta("Simple");
		p4.addRespuestaCorrecta("Independiente");
		p4.addRespuestaCorrecta("Automatizable");
		p4.addRespuestaCorrecta("Repetible");

		enc.addPregunta(p4);

		p5 = new PreguntaRespuestaACompletar();
		p5.setEnunciado("cuantas patas tiene un gato?");
		p5.addRespuestaCorrecta("4");

		enc.addPregunta(p5);
		current = enc;
		updateCache();
	}

	public OperationResponse save(Encuesta encuesta) {

		current = encuesta;
		String encuesta_str = parser.serializeEncuesta(encuesta);
		
		return save(encuesta_str);
	}

	public OperationResponse get(Recurso recurso){
		current = recurso;
		// Consulto la encuesta guardada
		String xml = parser.serializeQueryByType(recurso.getRecursoId(), EncuestaParser.ENCUESTA_TAG);
		try {
			return get(xml);
		} catch (GetException e) {
			return OperationResponse.createFailed(e.getMessage());
		} catch (ParseException e) {
			return OperationResponse.createFailed(e.getMessage());
		}
	}

	@Override
	protected String getHandledType() {
		return "Encuesta";
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Cache getCache() {
		return cache;
	}

	@Override
	protected boolean cacheContains(int recursoId) {
		return cache.contains(new Encuesta(recursoId, 0, "", false));
	}

	@Override
	protected Recurso retrieveCached(int recursoId) {
		return cache.get(new Encuesta(recursoId, 0, "", false));
	}

	@Override
	protected Parser getParser() {
		return parser;
	}

	@Override
	protected void deserialize(String xml_resp_e) throws ParseException {
		current = parser.deserializeEncuesta(xml_resp_e);
	}

}