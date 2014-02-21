import java.util.List;

import model.Encuesta;
import model.EncuestaRespondida;
import model.Link;
import model.Recurso;
import connection.Parameter;
import connection.RecursosRequester;
import connection.Requester;
import connection.responses.OperationResponse;
import connection.responses.RecursosResponse;

@SuppressWarnings("unused")
public class ClienteDelRequesterNuestro {

	private static OperationResponse or, response;
	private static String xml;
	private static Parameter p;
	private static Encuesta encuesta;
	private static EncuestaRespondida encuesta_rtn;

	public static void main(String[] args) {

//		probarLinkQueNoExiste();
//		borrarRecurso();
//		getArchivoQueExiste();
//		getEncuestaQExisteEnCacheRecursosPeroNoEnEncuestas();
//		getEncuestaQueNoExiste();
//		getListaRecursos();
//		getEncuestaHarcodeada();
//		getEncuestaQNoExisteEnNingunNivel();
//		getEncuesta11004();
//		guardarLink();
//		getRespondidaQueExiste();
//		getRespondidaInexistente();
		agregarEncuesta();

		// String absolute =
		// ClienteDelRequesterNuestro.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
		// System.out.println(absolute);
	}

	private static void getRespondidaInexistente() {
		System.out.println("prueba encuesta respondida que no existe");
		response = Requester.INSTANCE.getEncuestaRespondida(20, 4);
		if (response.getSuccess()) {
			encuesta_rtn = (EncuestaRespondida) response.getSerializable();
			System.out.println(encuesta_rtn.getInfo());
		} else {
			System.out.println();
			System.out.println(response.getReason());
		}
	}

	private static void getRespondidaQueExiste() {
		// encuesta = new Encuesta(10, 3, "Encuesta con preguntas fijas",
		// false);
		// response = Requester.INSTANCE.getRecurso(encuesta);
		// EncuestaRespondida encuesta_rtn = (EncuestaRespondida)
		// Requester.INSTANCE.getEncuestaRespondida(15, 4);
		System.out.println("prueba encuesta respondida");
		response = Requester.INSTANCE.getEncuestaRespondida(10, 5);
		if (response.getSuccess()) {
			encuesta_rtn = (EncuestaRespondida) response.getSerializable();
			System.out.println(encuesta_rtn.getInfo());
		} else {
			System.out.println();
			System.out.println(response.getReason());
		}
		
	}

	private static void guardarLink() {
		System.out.println("save link");
		Link link = new Link(16, 2, "Encuesta con preguntas a completar");
		link.setNombre("www.sport.es");
		response = Requester.INSTANCE.agregarRecurso(link);
		if (response.getSuccess()) {
			Link link_rtn = (Link) response.getSerializable();
			System.out.println(link_rtn.getNombre());
		} else {
			System.out.println();
			System.out.println(response.getReason());
		}
	}

	private static void getEncuestaQNoExisteEnNingunNivel() {
		System.out.println("no existe");
		encuesta = new Encuesta(16, 2, "Encuesta con preguntas a completar", true);
		response = Requester.INSTANCE.getRecurso(encuesta);
		if (response.getSuccess()) {
			Encuesta encuesta_rtn = (Encuesta) response.getSerializable();
			System.out.println(encuesta_rtn.getDescripcion());
		} else {
			System.out.println(response.getReason());
		}
	}

	private static void getEncuestaHarcodeada() {
		System.out.println("existe");
		encuesta = new Encuesta(15, 2, "Encuesta con preguntas a completar", true);
		response = Requester.INSTANCE.getRecurso(encuesta);
		if (response.getSuccess()) {
			Encuesta encuesta_rtn = (Encuesta) response.getSerializable();
			System.out.println(encuesta_rtn.getDescripcion());
		} else {
			System.out.println(response.getReason());
		}
	}
	

	private static void getEncuesta11004() {
		System.out.println("11004");

		encuesta = new Encuesta(11, null, null, null);
		response = Requester.INSTANCE.getRecurso(encuesta);
		
//		xml = "<parametro><recurso><recursoId>11</recursoId><tipo>Encuesta</tipo></recurso></parametro>";
//		p = Parameter.createParameter(xml);
//		response = Requester.INSTANCE.getRecurso(p.getRecurso());
		
		if (response.getSuccess()) {
			Encuesta encuesta_rtn = (Encuesta) response.getSerializable();
			System.out.println(encuesta_rtn.getDescripcion());
		} else {
			System.out.println(response.getReason());
		}
	}

	private static void getListaRecursos() {
		xml = "<parametro><usuarioId>23</usuarioId><recurso><ambitoId>15</ambitoId></recurso></parametro>";
		p = Parameter.createParameter(xml);
		or = Requester.INSTANCE.getRecursosAmbito(1);
		if (!or.getSuccess()) {
			System.out.println(or.getReason());
		} else {
			RecursosResponse rec = (RecursosResponse) or;
			List<Recurso> lis = rec.getRecursos();
			for (Recurso recu : lis) {
				System.out.println(recu.getDescripcion());
			}
		}
	}

	private static void getEncuestaQueNoExiste() {
		// prueba encuesta que no existe
		xml = "<parametro><recurso><recursoId>997</recursoId><tipo>Link</tipo></recurso></parametro>";
		p = Parameter.createParameter(xml);
		or = Requester.INSTANCE.getRecurso(p.getRecurso());
		if (!or.getSuccess()) {
			System.out.println(or.getReason());
		}
	}

	private static void getEncuestaQExisteEnCacheRecursosPeroNoEnEncuestas() {
		// prueba encuesta que existe en cache de recursos pero no de encuestas
		xml = "<parametro><recurso><recursoId>996</recursoId><tipo>Encuesta</tipo></recurso></parametro>";
		p = Parameter.createParameter(xml);
		System.out.println(p.getRecurso().getRecursoId());
		or = Requester.INSTANCE.getRecurso(p.getRecurso());
		if (!or.getSuccess()) {
			System.out.println(or.getReason());
		}
	}

	private static void getArchivoQueExiste() {
		// prueba Archivo que existe
		xml = "<parametro><recurso><recursoId>-18</recursoId><tipo>Archivo</tipo></recurso></parametro>";
		p = Parameter.createParameter(xml);
		System.out.println(p.getRecurso().getRecursoId());
		or = Requester.INSTANCE.getRecurso(p.getRecurso());
		if (!or.getSuccess()) {
			System.out.println(or.getReason());
		}
	}

	private static void borrarRecurso() {
		System.out.println("Borrar recurso");
		RecursosRequester rr = new RecursosRequester();
		or = rr.delete(new Recurso(1002, 1, ""));
		if (!or.getSuccess()) {
			System.out.println(or.getReason());
		}
	}

	protected static void probarLinkQueNoExiste() {
		// prueba link q existe
		System.out.println("probarLinkQueNoExiste");
		xml = "<parametro><recurso><recursoId>997</recursoId><tipo>Link</tipo></recurso></parametro>";
		p = Parameter.createParameter(xml);
		or = Requester.INSTANCE.getRecurso(p.getRecurso());
		if (!or.getSuccess()) {
			System.out.println(or.getReason());
		}
	}
	
	protected static void agregarEncuesta() {
		// prueba link q existe
		System.out.println("agregarEncuesta");
		xml = "<parametro><encuesta evaluada=\"true\"><tipo>Encuesta</tipo><ambitoId>-1</ambitoId><descripcion>una encuesta grande</descripcion><preguntas><pregunta correctas=\"4\" idPregunta=\"1\" enunciado=\"de que color es el caballo blanco de san martin?\"><respuestas>rojo,verde,azul,blanco</respuestas></pregunta><pregunta correctas=\"2\" idPregunta=\"2\" enunciado=\"a que equipo del futbol argentino le denominan Millo\"><respuestas>velez,River Plate,crucero del norte,estudiantes</respuestas></pregunta><pregunta correctas=\"3\" idPregunta=\"3\" enunciado=\"cual es un patron de diseno creacional\"><respuestas>command,mediator,builder,facade</respuestas></pregunta><pregunta correctas=\"1,8,11,12,13,7\" idPregunta=\"4\" enunciado=\"Untest unitario debe presentar las siguientes caractersticas\"><respuestas>Rapido,Moldeable,Configurable,Acoplable,Lento,Extensible,Repetible,Profesional,Maduro,Amplio,Simple,Independiente,Automatizable</respuestas></pregunta><pregunta correcta=\"4\" idPregunta=\"5\" enunciado=\"cuantas patas tiene un gato?\" /></preguntas></encuesta><usuarioId>5</usuarioId></parametro>";
		p = Parameter.createParameter(xml);
		or = Requester.INSTANCE.agregarRecurso(p.getRecurso());
		if (!or.getSuccess()) {
			System.out.println(or.getReason());
		}
	}

}
