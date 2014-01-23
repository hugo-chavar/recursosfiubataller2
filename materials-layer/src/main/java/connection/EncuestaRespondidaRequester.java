package connection;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.AxisFault;

import model.Encuesta;
import model.EncuestaRespondida;
import model.Pregunta;
import model.PreguntaRespondida;
import model.PreguntaRespuestaACompletarRespondida;
import model.PreguntaRespuestaFija;
import model.PreguntaRespuestaFijaRespondida;

import com.ws.services.IntegracionStub;
import com.ws.services.IntegracionStub.GuardarDatos;
import com.ws.services.IntegracionStub.GuardarDatosResponse;
import com.ws.services.IntegracionStub.SeleccionarDatos;
import com.ws.services.IntegracionStub.SeleccionarDatosResponse;

import connection.cache.Cache;
import connection.exceptions.ParseException;
//import connection.responses.EncuestaRespondidaResponse;
import connection.responses.OperationResponse;

public class EncuestaRespondidaRequester {
	private IntegracionStub stub;
	private EncuestaRespondidaParser parser;
	private Cache<EncuestaRespondida> cacheRespondidas;
	
	public EncuestaRespondidaRequester(){
		parser = new EncuestaRespondidaParser();
		cacheRespondidas = new Cache<EncuestaRespondida>();
		
		// TODO cargo encuestasRespondida de ejemplo (sacar)
		EncuestaRespondida resp = new EncuestaRespondida(2,15);
		
		PreguntaRespuestaFijaRespondida p1 = new PreguntaRespuestaFijaRespondida(1);
		PreguntaRespuestaACompletarRespondida p2 = new PreguntaRespuestaACompletarRespondida(2);

		List<PreguntaRespondida> preguntasRespondidas = new ArrayList<PreguntaRespondida>();
		p1.addRespuesta(1);
		p1.addRespuesta(4);
		p2.responder("Buenos Aires");
		p1.setIsCorrecta(true);
		p2.setIsCorrecta(false);
        preguntasRespondidas.add(p1);
        preguntasRespondidas.add(p2);
        resp.setEvaluacion(50);
		resp.setPreguntasRespondidas(preguntasRespondidas);
		cacheRespondidas.add(resp);

		try {
			stub = new IntegracionStub();
		} catch (AxisFault e) {
			System.out.println("Error al intentar contectarse con Integracion");
		}
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
	
	public EncuestaRespondida getRespondida(int IDEncuesta,int IDUsuario) {

		// Busco en el cache de encuestas respondidas
		EncuestaRespondida target = new EncuestaRespondida(IDEncuesta, IDUsuario);
		if (cacheRespondidas.contains(target)) {
			return cacheRespondidas.get(target);
		} else {

			try {
				// Consulto la encuesta guardada
				String xml = parser.serializeEncuestaRespondidaQuery(IDUsuario, IDEncuesta);
//				SeleccionarDatos seleccionar_e = new SeleccionarDatos();
//				seleccionar_e.setXml(xml);
//				SeleccionarDatosResponse s_resp_e = stub.seleccionarDatos(seleccionar_e);
//				String xml_resp_e = s_resp_e.get_return();

				////////////// PRUEBAS //////////////
				String xml_resp_e;
				
				if (xml.equals("<WS><encuestaRespondida><recursoId>15</recursoId><usuarioId>4</usuarioId></encuestaRespondida></WS>")) {
					xml_resp_e = "<WS><encuestaRespondida><recursoId>15</recursoId><usuarioId>4</usuarioId><evaluacion>50</evaluacion><preguntasRespondidas>C;1;false;Azul|" +
							"C;2;true;4</preguntasRespondidas></encuestaRespondida></WS>";
				} else if (xml.equals("<WS><encuestaRespondida><recursoId>10</recursoId><usuarioId>5</usuarioId></encuestaRespondida></WS>")) {
					xml_resp_e = "<WS><encuestaRespondida><recursoId>10</recursoId><usuarioId>5</usuarioId><evaluacion>100</evaluacion><preguntasRespondidas>F;1;true;1|" +
							"F;2;true;2</preguntasRespondidas></encuestaRespondida></WS>";
				} else {
					SeleccionarDatos seleccionar_e = new SeleccionarDatos();
					seleccionar_e.setXml(xml);
					SeleccionarDatosResponse s_resp_e = stub.seleccionarDatos(seleccionar_e);
					xml_resp_e = s_resp_e.get_return();
				}
				
				////////////// PRUEBAS //////////////
				EncuestaRespondida encuesta;
				try {
					encuesta = parser.deserializeEncuestaRespondida(xml_resp_e);
				} catch (ParseException e) {
					// TODO Andy: Esto hay que arreglar, este metodo tiene q devolver un OperationResponse
					//igual que el resto de los requesters
					encuesta = null;
					e.printStackTrace();
				}				
				
				// Agrego al cache de encuestas respondidas
				cacheRespondidas.add(encuesta);

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
	
//	public OperationResponse getFromCache(int recursoId,int idUsuario) {
//		
//		EncuestaRespondidaResponse response = new EncuestaRespondidaResponse();
//		EncuestaRespondida target = new EncuestaRespondida(recursoId,idUsuario,0);
//
//		if (cacheRespondidas.contains(target)) {
//			response = new EncuestaRespondidaResponse(cacheRespondidas.get(target));
//		}
//		
//		return response;
//		
//	}
//	
//	public void deleteFromCache(int recursoId , int idUsuario) {
//		cacheRespondidas.remove(new EncuestaRespondida(recursoId, idUsuario,0));
//	}

}
