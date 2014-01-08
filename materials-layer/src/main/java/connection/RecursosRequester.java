package connection;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import model.Recurso;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionWSStub;
import com.ws.services.IntegracionWSStub.EliminarDatos;
import com.ws.services.IntegracionWSStub.EliminarDatosResponse;
import com.ws.services.IntegracionWSStub.SeleccionarDatos;
import com.ws.services.IntegracionWSStub.SeleccionarDatosResponse;

import connection.cache.Cache;
import connection.exceptions.GetException;
import connection.responses.OperationResponse;

public class RecursosRequester {

	private IntegracionWSStub stub;
	private RecursosParser parser;
	private Cache<Recurso> cache;

	public RecursosRequester() {

		parser = new RecursosParser();
		cache = new Cache<Recurso>();
		cache.changeSize(1000);

		try {
			stub = new IntegracionWSStub();
		} catch (AxisFault e) {
			System.out.println("Error al intentar contectarse con Integracion");
		}

	}

	public List<Recurso> get(int IDAmbito) throws GetException {

		List<Recurso> recursos = new ArrayList<Recurso>();

		try {

			// Consulto los recursos guardados
			String xml = parser.serializeRecursosQuery(IDAmbito);
			SeleccionarDatos seleccionar_e = new SeleccionarDatos();
			seleccionar_e.setXml(xml);
			SeleccionarDatosResponse s_resp_e = stub.seleccionarDatos(seleccionar_e);
			String xml_resp_e = s_resp_e.get_return();
			recursos = parser.deserializeRecursos(xml_resp_e);
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

	public OperationResponse delete(int IDRecurso) {
		OperationResponse response = new OperationResponse();
		response.setSuccess(false);

		// Borro la encuesta respondida
		String xml = parser.serializeDeleteQuery(IDRecurso);
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
			String reason = "Error al intentar eliminar el siguiente Recurso con Id "  + IDRecurso;
			System.out.println(reason);
			response.setReason(reason);
		} catch (RemoteException e) {
			String reason = "Error de conexion remota";
			System.out.println(reason);
			response.setReason(reason);
		}
		return response;

	}

	public Recurso get(Recurso target) {
		return cache.get(target);
	}

	public Recurso getCached(int recursoId) {
		return get(new Recurso(recursoId, 0, ""));
	}
}
