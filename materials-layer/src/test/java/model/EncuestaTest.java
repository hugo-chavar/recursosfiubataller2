package model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EncuestaTest {

	Encuesta encuesta;
	Pregunta p1, p2, p3, p4, p5;

	@Before
	public void setUp() throws Exception {
		encuesta = new Encuesta();
		encuesta.setDescripcion("Encuesta general de no se que");
		p1 = new PreguntaRespuestaFija();
		p1.setEnunciado("de que color es el caballo blanco de san martin?");
		List<String> opciones = new ArrayList<String>();
		opciones.add("rojo");
		opciones.add("verde");
		opciones.add("azul");
		opciones.add("blanco");
		((PreguntaRespuestaFija) p1).setRespuestasPosibles(opciones);

		encuesta.agregarPregunta(p1);

		p2 = new PreguntaRespuestaFija();
		opciones = new ArrayList<String>();
		p2.setEnunciado("a que equipo del futbol argentino le denominan Millo");
		opciones.add("velez");
		opciones.add("River Plate");
		opciones.add("crucero del norte");
		opciones.add("estudiantes");
		((PreguntaRespuestaFija) p2).setRespuestasPosibles(opciones);

		encuesta.agregarPregunta(p2);

		p3 = new PreguntaRespuestaFija();
		p3.setEnunciado("cual es un patron de diseno creacional");
		opciones.add("command");
		opciones.add("mediator");
		opciones.add("builder");
		opciones.add("facade");

		((PreguntaRespuestaFija) p3).setRespuestasPosibles(opciones);
		((PreguntaRespuestaFija) p3).addRespuestaCorrecta("builder");

		encuesta.agregarPregunta(p3);
		
		p4 = new PreguntaRespuestaFija();
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

		encuesta.agregarPregunta(p4);
		
		p5 = new PreguntaRespuestaACompletar();
		p5.setEnunciado("cuantas patas tiene un gato?");
		p5.addRespuestaCorrecta("5");
	}

	@Test
	public void marshallPreguntaRespuestaACompletarWorksAsExpected() {
		Assert.assertEquals("C;0;cuantas patas tiene un gato?;5", p5.marshall());
	}

}
