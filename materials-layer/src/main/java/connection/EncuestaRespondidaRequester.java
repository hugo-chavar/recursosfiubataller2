package connection;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import model.EncuestaRespondida;

import com.ws.services.IntegracionStub;
import com.ws.services.IntegracionStub.GuardarDatos;
import com.ws.services.IntegracionStub.GuardarDatosResponse;
import com.ws.services.IntegracionStub.SeleccionarDatos;
import com.ws.services.IntegracionStub.SeleccionarDatosResponse;

import connection.cache.Cache;
//import connection.responses.EncuestaRespondidaResponse;
import connection.responses.OperationResponse;

public class EncuestaRespondidaRequester {
	private IntegracionStub stub;
	private EncuestaRespondidaParser parser;
	private Cache<EncuestaRespondida> cacheRespondidas;
	
	public EncuestaRespondidaRequester(){
		parser = new EncuestaRespondidaParser();
		cacheRespondidas = new Cache<EncuestaRespondida>();

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
	
	public EncuestaRespondida getRespondida(int IDAmbiente, int IDUsuario, int IDEncuesta) {

		// Busco en el cache de encuestas respondidas
		EncuestaRespondida target = new EncuestaRespondida(IDEncuesta, IDUsuario, 0);
		if (cacheRespondidas.contains(target)) {
			return cacheRespondidas.get(target);
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
