package connection;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionStub;

public abstract class HandlerRequester {
	
	protected IntegracionStub stub;

	protected HandlerRequester() {
		try {
			this.stub = new IntegracionStub();
		} catch (AxisFault e) {
			System.out.println("Error al intentar contectarse con Integracion");
		}
	}
	

}
