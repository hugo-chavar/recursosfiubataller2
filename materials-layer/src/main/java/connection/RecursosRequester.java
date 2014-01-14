package connection;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import model.Recurso;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionStub;
import com.ws.services.IntegracionStub.EliminarDatos;
import com.ws.services.IntegracionStub.EliminarDatosResponse;
import com.ws.services.IntegracionStub.SeleccionarDatos;
import com.ws.services.IntegracionStub.SeleccionarDatosResponse;

import connection.cache.Cache;
import connection.exceptions.GetException;
import connection.responses.OperationResponse;


public class RecursosRequester {

	private IntegracionStub stub;
	private RecursosParser parser;
	private Cache<Recurso> cache;
//	private Boolean statusOk;
//	private String statusMessage; 
	private List<Recurso> recursosEjemplo;

	public RecursosRequester() {

		parser = new RecursosParser();
		cache = new Cache<Recurso>();
		cache.changeSize(1000);

		try {
			stub = new IntegracionStub();
			recursosEjemplo = new ArrayList<Recurso>();
			Recurso r = new Recurso(1002,-1,"un link a google copado","Link");
			recursosEjemplo.add(r);
			r = new Recurso(1003,-1,"una encuesta chica","Encuesta");
			recursosEjemplo.add(r);
			r = new Recurso(1004,-1,"una encuesta grande","Encuesta");
			recursosEjemplo.add(r);
//			statusOk = true;
		} catch (AxisFault e) {
//			statusMessage = "Error al intentar contectarse con Integracion";
			System.out.println("Error al intentar contectarse con Integracion");
			System.out.println(e.toString());
		}

	}
	
	public Recurso get(Recurso target) {
		
		// Busco en el cache de recursos.
//		Recurso target = new Recurso(recursoId, 0, "");
		if (cache.contains(target)) {
			return cache.get(target);
		} else {
			try {

				// Consulto el recurso guardado
				String xml = parser.serializeRecursoQuery(target.getRecursoId());

				////////////// PRUEBAS //////////////
				String xml_resp_e;
				
				if (xml.equals("<WS><recurso><recursoId>15</recursoId></recurso></WS>")) {
					xml_resp_e = "<WS><recurso><recursoId>15</recursoId><ambitoId>2</ambitoId><descripcion>Encuesta con preguntas a completar</descripcion><tipo>Encuesta</tipo></recurso></WS>";
				} else if (xml.equals("<WS><recurso><recursoId>10</recursoId></recurso></WS>")) {
					xml_resp_e = "<WS><recurso><recursoId>10</recursoId><ambitoId>3</ambitoId><descripcion>Encuesta con preguntas fijas</descripcion><tipo>Encuesta</tipo></recurso></WS>";
				} else {
					SeleccionarDatos seleccionar_e = new SeleccionarDatos();
					seleccionar_e.setXml(xml);
					SeleccionarDatosResponse s_resp_e = stub.seleccionarDatos(seleccionar_e);
					xml_resp_e = s_resp_e.get_return();
				}
				////////////// PRUEBAS //////////////
				
				Recurso recurso = parser.deserializeRecurso(xml_resp_e);
				
				return recurso;
	
			} catch (AxisFault e) {
				String message = "Error al intentar obtener el recurso con IDRecurso: " + target.getRecursoId();
				System.out.println(message);
			} catch (RemoteException e) {
				String message = "Error de conexion remota";
				System.out.println(message);
			}
		}

		return null;

	}

	public List<Recurso> getAll(int IDAmbito) throws GetException {

		List<Recurso> recursos = new ArrayList<Recurso>();

		try {

			// Consulto los recursos guardados
			String xml = parser.serializeRecursosQuery(IDAmbito);
			SeleccionarDatos seleccionar_e = new SeleccionarDatos();
			seleccionar_e.setXml(xml);
			SeleccionarDatosResponse s_resp_e = stub.seleccionarDatos(seleccionar_e);
			String xml_resp_e = s_resp_e.get_return();
			System.out.println(xml_resp_e);
			recursos = parser.deserializeRecursos(xml_resp_e);
			if (recursos == null) {
				// TODO : devuelvo datos de ejemplo, mientras no funcione integracion
				return recursosEjemplo;
//				String message = "Integracion dice: " + xml_resp_e.substring(0, xml_resp_e.indexOf('<') -1);
//				System.out.println(message);
//				throw new GetException(message);
			}
			cache.addAll(recursos);
			return recursos;

		} catch (AxisFault e) {
			String message = "Error al intentar obtener los recursos del IDAmbito: " + IDAmbito;
			System.out.println(message);
			throw new GetException(message);
		} catch (RemoteException e) {
			String message = "Error de conexion remota";
			System.out.println(message);
			throw new GetException(message);
		}

//		return null;

	}

	public OperationResponse delete(int recursoId) {
		
		OperationResponse response = new OperationResponse();

		// Borro el recurso
		String xml = parser.serializeDeleteQuery(recursoId);
		try {
			
			EliminarDatos eliminar = new EliminarDatos();
			eliminar.setXml(xml);
			EliminarDatosResponse e_resp = stub.eliminarDatos(eliminar);
			System.out.println(e_resp.get_return());
			
			// TODO implementar método que chequee las respuestas
			// if (xmlUtil.isResponseOk(g_resp.get_return())) {
			// or.setSuccess(true);
			// or.setReason("algo");
			// }
			
			response.setSuccess(true);
			
		} catch (AxisFault e) {
			String reason = "Error al intentar eliminar el Recurso, Id: " + recursoId;
			System.out.println(reason);
			response.setReason(reason);
		} catch (RemoteException e) {
			String reason = "Error de conexion remota";
			System.out.println(reason);
			response.setReason(reason);
		}
		
		return response;

	}

}
