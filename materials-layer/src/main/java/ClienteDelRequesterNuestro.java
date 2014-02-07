import java.util.List;

import model.Encuesta;
import model.EncuestaRespondida;
import model.Link;
import model.Recurso;
import connection.Parameter;
import connection.RecursosRequester;
import connection.Requester;
import connection.exceptions.GetException;
import connection.responses.OperationResponse;
import connection.responses.RecursosResponse;


public class ClienteDelRequesterNuestro {

	public static void main(String[] args) {
		
		OperationResponse or;
		String xml;
		Parameter p = new Parameter();
		Encuesta encuesta;
		// prueba link q existe
		xml = "<parametro><recurso><recursoId>997</recursoId><tipo>Link</tipo></recurso></parametro>";
		p = Parameter.createParameter(xml);
		or = Requester.INSTANCE.getRecurso(p.getRecurso());
		if (!or.getSuccess()) {
			System.out.println(or.getReason());
		}


		System.out.println("Borrar recurso");
		RecursosRequester rr = new RecursosRequester();
		or = rr.delete(new Recurso(1002,1,""));
		if (!or.getSuccess()) {
			System.out.println(or.getReason());
		}
		// prueba Archivo que existe
		xml = "<parametro><recurso><recursoId>-18</recursoId><tipo>Archivo</tipo></recurso></parametro>";
		p = Parameter.createParameter(xml);
		System.out.println(p.getRecurso().getRecursoId());
		or = Requester.INSTANCE.getRecurso(p.getRecurso());
		if (!or.getSuccess()) {
			System.out.println(or.getReason());
		}
//		 prueba encuesta que existe en cache de recursos pero no de encuestas
		xml = "<parametro><recurso><recursoId>996</recursoId><tipo>Encuesta</tipo></recurso></parametro>";
		p = Parameter.createParameter(xml);
		System.out.println(p.getRecurso().getRecursoId());
		or = Requester.INSTANCE.getRecurso(p.getRecurso());
		if (!or.getSuccess()) {
			System.out.println(or.getReason());
		}
		// prueba encuesta que no existe
		xml = "<parametro><recurso><recursoId>997</recursoId><tipo>Link</tipo></recurso></parametro>";
		p = Parameter.createParameter(xml);
		or = Requester.INSTANCE.getRecurso(p.getRecurso());
		if (!or.getSuccess()) {
			System.out.println(or.getReason());
		}
		xml = "<parametro><usuarioId>23</usuarioId><recurso><ambitoId>15</ambitoId></recurso></parametro>";
		p = Parameter.createParameter(xml);
		try {
			or = Requester.INSTANCE.getRecursosAmbito(1);
			if (!or.getSuccess()) {
				System.out.println(or.getReason());
			} else {
				RecursosResponse rec = (RecursosResponse)or;
				List<Recurso> lis = rec.getRecursos();
				for(Recurso recu : lis){
					System.out.println(recu.getDescripcion());
				}
			}
		} catch (GetException e) {
			System.out.println(e.getMessage());
		}
		String absolute = ClienteDelRequesterNuestro.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
		System.out.println(absolute);
		
		
		OperationResponse response;
		
		System.out.println("existe");
		encuesta = new Encuesta(15, 2, "Encuesta con preguntas a completar", true);
		response = Requester.INSTANCE.getRecurso(encuesta);
		if (response.getSuccess()) {
			Encuesta encuesta_rtn = (Encuesta) response.getSerializable();
			System.out.println(encuesta_rtn.getDescripcion());
		} else {
			System.out.println(response.getReason());
		}
//		
//		System.out.println("no existe");
//		encuesta = new Encuesta(16, 2, "Encuesta con preguntas a completar", true);
//		response = Requester.INSTANCE.getRecurso(encuesta);
//		if (response.getSuccess()) {
//			Encuesta encuesta_rtn = (Encuesta) response.getSerializable();
//			System.out.println(encuesta_rtn.getDescripcion());
//		} else {
//			System.out.println(response.getReason());
//		}
		
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
//		
//		encuesta = new Encuesta(10, 3, "Encuesta con preguntas fijas", false);

//		response = Requester.INSTANCE.getRecurso(encuesta);
		
//		EncuestaRespondida encuesta_rtn = (EncuestaRespondida) Requester.INSTANCE.getEncuestaRespondida(15, 4);
		System.out.println("prueba encuesta respondida");
		EncuestaRespondida encuesta_rtn = (EncuestaRespondida) Requester.INSTANCE.getEncuestaRespondida(10, 5).getSerializable();
		System.out.println(encuesta_rtn.getInfo());
		
		System.out.println("prueba encuesta respondida que no existe");
		encuesta_rtn = (EncuestaRespondida) Requester.INSTANCE.getEncuestaRespondida(20, 4).getSerializable();
		System.out.println(encuesta_rtn.getInfo());
		
		
	}

}
