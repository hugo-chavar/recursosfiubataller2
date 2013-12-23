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

	public List<Recurso> get(int IDAmbito) {

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
			System.out.println("Error al intentar obtener los recursos del");
			System.out.println("IDAmbito: " + IDAmbito);
		} catch (RemoteException e) {
			System.out.println("Error de conexion remota");
		}

		return null;

	}

	public void delete(int IDRecurso) {

		// Borro la encuesta respondida
		String xml = parser.serializeDeleteQuery(IDRecurso);
		try {
			EliminarDatos eliminar = new EliminarDatos();
			eliminar.setXml(xml);
			EliminarDatosResponse e_resp = this.stub.eliminarDatos(eliminar);
			System.out.println(e_resp.get_return());
		} catch (AxisFault e) {
			System.out.println("Error al intentar eliminar el siguiente Recurso:");
			System.out.println("IDRecurso: " + IDRecurso);
		} catch (RemoteException e) {
			System.out.println("Error de conexion remota");
		}

	}

}
