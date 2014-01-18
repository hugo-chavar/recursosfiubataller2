package connection;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import model.Encuesta;
import model.EncuestaRespondida;
import model.Pregunta;
import model.PreguntaRespuestaACompletar;
import model.PreguntaRespuestaFija;
import model.Recurso;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionStub;
import com.ws.services.IntegracionStub.GuardarDatos;
import com.ws.services.IntegracionStub.GuardarDatosResponse;
import com.ws.services.IntegracionStub.SeleccionarDatos;
import com.ws.services.IntegracionStub.SeleccionarDatosResponse;

import connection.cache.Cache;
import connection.responses.EncuestaResponse;
import connection.responses.OperationResponse;


public class EncuestaRequester {

	private IntegracionStub stub;
	private EncuestaParser parser;
	private Cache<Encuesta> cacheEncuestas;
	private Cache<EncuestaRespondida> cacheEncuestasRespondidas;

	
	public EncuestaRequester() {

		parser = new EncuestaParser();
		cacheEncuestas = new Cache<Encuesta>();
		// TODO cargo encuestas de ejemplo (sacar)
		Encuesta enc = new Encuesta(1003, -1, "una encuesta chica", false);
		
		Pregunta p1, p2, p3, p4, p5;
		p2 = new PreguntaRespuestaFija();
		List<String> opciones = new ArrayList<String>();
		p2.setEnunciado("cuantas materias te faltan para recibirte?");
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
		cacheEncuestas.add(enc);

		enc = new Encuesta(1004, -1, "una encuesta grande", false);
		cacheEncuestas.add(enc);
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

		cacheEncuestasRespondidas = new Cache<EncuestaRespondida>();

		try {
			stub = new IntegracionStub();
		} catch (AxisFault e) {
			System.out.println("Error al intentar contectarse con Integracion");
		}

	}

	public OperationResponse save(Encuesta encuesta) {

		OperationResponse response;
		
		// Guardo la encuesta
		String encuesta_str = parser.serializeEncuesta(encuesta);
		try {
			GuardarDatos guardar = new GuardarDatos();
			guardar.setXml(encuesta_str);
			GuardarDatosResponse g_resp = stub.guardarDatos(guardar);
			System.out.println(g_resp.get_return());
			
			response = OperationResponse.createSuccess();
			
			// Actualizo el cache
			if (cacheEncuestas.contains(encuesta)) {
				cacheEncuestas.remove(encuesta);
				cacheEncuestas.add(encuesta);
			}
			
		} catch (AxisFault e) {
			String reason = "Error al guardar la Encuesta, Id: " + encuesta.getRecursoId();
			System.out.println(reason);
			response = OperationResponse.createFailed(reason);
		} catch (RemoteException e) {
			String reason = "Error de conexion remota";
			System.out.println(reason);
			response = OperationResponse.createFailed(reason);
		}
		
		return response;
	}

	public OperationResponse saveRespondida(EncuestaRespondida respondida) {

		OperationResponse response;
		// Guardo la encuesta respondida
		String encuesta_str = parser.serializeEncuestaRespondida(respondida);
		try {
			GuardarDatos guardar = new GuardarDatos();
			guardar.setXml(encuesta_str);
			GuardarDatosResponse g_resp = this.stub.guardarDatos(guardar);
			response = OperationResponse.createSuccess();
			System.out.println(g_resp.get_return());
		} catch (AxisFault e) {
			String reason = "Error al intentar guardar EncuestaRespondida";
			System.out.println(reason);
			response = OperationResponse.createFailed(reason);
		} catch (RemoteException e) {
			String reason = "Error de conexion remota";
			System.out.println(reason);
			response = OperationResponse.createFailed(reason);
		}
		return response;
	}
	
	public OperationResponse getFromCache(int recursoId) {
		
		EncuestaResponse response = new EncuestaResponse();
		Encuesta target = new Encuesta(recursoId, 0, "", false);

		if (cacheEncuestas.contains(target)) {
			response = new EncuestaResponse(cacheEncuestas.get(target));
			response.setSuccess(true);
		}
		
		return response;
		
	}

	public OperationResponse get(Recurso recurso) {

		EncuestaResponse response;
		String reason;

		try {

			// Consulto la encuesta guardada
			String xml = parser.serializeQueryByType(recurso.getRecursoId(), EncuestaParser.ENCUESTA_TAG);
			
			////////////// PRUEBAS //////////////
			String xml_resp_e;
			
			if (xml.equals("<WS><encuesta><recursoId>15</recursoId></encuesta></WS>")) {
				xml_resp_e = "<WS><encuesta><evaluada>true</evaluada><preguntas>C;1;De que color es el caballo blanco de San Martin?;blanco|" +
						"C;2;Cuantas patas tiene un gato?;4</preguntas></encuesta></WS>";
			} else if (xml.equals("<WS><encuesta><recursoId>10</recursoId></encuesta></WS>")) {
				xml_resp_e = "<WS><encuesta><evaluada>true</evaluada><preguntas>F;1;De que color es el caballo blanco de San Martin?;negro,blanco,marron;1|" +
						"F;2;Cuantas patas tiene un gato?;3,2,4;2</preguntas></encuesta></WS>";
			} else {
				SeleccionarDatos seleccionar_e = new SeleccionarDatos();
				seleccionar_e.setXml(xml);
				SeleccionarDatosResponse s_resp_e = stub.seleccionarDatos(seleccionar_e);
				xml_resp_e = s_resp_e.get_return();
			}
			
			////////////// PRUEBAS //////////////
			
			Encuesta encuesta = parser.deserializeEncuesta(xml_resp_e);
			encuesta.setAmbitoId(recurso.getAmbitoId());
			encuesta.setRecursoId(recurso.getRecursoId());
			encuesta.setDescripcion(recurso.getDescripcion());

			// Agrego al cache de encuestas
			cacheEncuestas.add(encuesta);

			response = new EncuestaResponse(encuesta);
			response.setSuccess(true);
			return response;

		} catch (AxisFault e) {
			reason = "Error al intentar obtener la encuesta, ID: " + recurso.getRecursoId();
		} catch (RemoteException e) {
			reason = "Error de conexion remota";
		}

		System.out.println(reason);
		
		return OperationResponse.createFailed(reason);

	}

	public EncuestaRespondida getRespondida(int IDAmbiente, int IDUsuario, int IDEncuesta) {

		// Busco en el cache de encuestas respondidas
		EncuestaRespondida target = new EncuestaRespondida(IDEncuesta, IDUsuario, 0);
		if (cacheEncuestasRespondidas.contains(target)) {
			return cacheEncuestasRespondidas.get(target);
		} else {

			try {

				// Consulto la encuesta guardada
				String xml = parser.serializeEncuestaRespondidaQuery(IDAmbiente, IDUsuario, IDEncuesta);
				SeleccionarDatos seleccionar_e = new SeleccionarDatos();
				seleccionar_e.setXml(xml);
				SeleccionarDatosResponse s_resp_e = stub.seleccionarDatos(seleccionar_e);
				String xml_resp_e = s_resp_e.get_return();
				EncuestaRespondida encuesta = parser.deserializeEncuestaRespondida(xml_resp_e);

				// Agrego al cache de encuestas respondidas
				cacheEncuestasRespondidas.add(encuesta);

				return encuesta;

			} catch (AxisFault e) {
				System.out.println("Error al intentar obtener la siguiente Encuesta:");
				System.out.println("IDEncuesta: " + IDEncuesta);
			} catch (RemoteException e) {
				System.out.println("Error de conexion remota");
			}

		}

		return null;

	}
	
	public void deleteFromCache(int recursoId) {
		cacheEncuestas.remove(new Encuesta(recursoId, 0, "", false));
	}

}