package connection;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionStub;
import com.ws.services.IntegracionStub.GuardarDatos;
import com.ws.services.IntegracionStub.GuardarDatosResponse;

import connection.responses.OperationResponse;

public abstract class HandlerRequester {

	protected IntegracionStub stub;

	protected HandlerRequester() {
		try {
			this.stub = new IntegracionStub();
		} catch (AxisFault e) {
			System.out.println("Error al intentar contectarse con Integracion");
		}
	}

	protected OperationResponse save(String xml_str, int recursoId) {
		OperationResponse response;

		try {
			GuardarDatos guardar = new GuardarDatos();
			guardar.setXml(xml_str);
			GuardarDatosResponse g_resp = stub.guardarDatos(guardar);
			System.out.println(g_resp.get_return());

			response = OperationResponse.createSuccess();

			udpateCache();

		} catch (AxisFault e) {
			String reason = "Error al guardar " + getHandledType() + ", Id: " + recursoId;
			System.out.println(reason);
			response = OperationResponse.createFailed(reason);
		} catch (RemoteException e) {
			String reason = "Error de conexion remota";
			System.out.println(reason);
			response = OperationResponse.createFailed(reason);
		}

		return response;
	}

	protected abstract String getHandledType();

	public abstract void udpateCache();

}
