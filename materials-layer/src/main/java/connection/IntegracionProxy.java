package connection;

import java.rmi.RemoteException;

import javax.activation.DataHandler;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionStub;

import connection.exceptions.ConnectionException;

public class IntegracionProxy {
	private IntegracionStub stub;

	public IntegracionProxy() {
		try {
			stub = new IntegracionStub();
		} catch (AxisFault e) {
			System.out.println("Error al intentar contectarse con Integracion");
		}
	}
	
	public String seleccionar(String xml) throws ConnectionException {
		//TODO: pruebas de Yami
		if (xml.equals("<WS><Encuesta><id>15</id></Encuesta></WS>")) {
			return "<WS><encuesta><evaluada>true</evaluada><preguntas>C;1;De que color es el caballo blanco de San Martin?;blanco|" +
					"C;2;Cuantas patas tiene un gato?;4</preguntas></encuesta></WS>";
		} else if (xml.equals("<WS><Encuesta><id>10</id></Encuesta></WS>")) {
			return "<WS><encuesta><evaluada>true</evaluada><preguntas>F;1;De que color es el caballo blanco de San Martin?;negro,blanco,marron;1|" +
					"F;2;Cuantas patas tiene un gato?;3,2,4;2</preguntas></encuesta></WS>";
		} else if (xml.equals("<WS><Encuesta><id>11</id></Encuesta></WS>")) {
			return "<WS><encuesta><evaluada>true</evaluada><preguntas>F;1;De que color\\; es\\, el\\| caballo blanco de San Martin?;negro,blanco,marron;1|" +
					"F;2;Cuantas patas tiene un gato?;3,2,4;2</preguntas></encuesta></WS>";
		} else if (xml.equals("<WS><Recurso><id>15</id></Recurso></WS>")) {
			return "<WS><Recurso><id>15</id><ambitoId>2</ambitoId><descripcion>Encuesta con preguntas a completar</descripcion><tipo>Encuesta</tipo></Recurso></WS>";
		} else if (xml.equals("<WS><Recurso><id>10</id></Recurso></WS>")) {
			return "<WS><Recurso><id>10</id><ambitoId>3</ambitoId><descripcion>Encuesta con preguntas fijas</descripcion><tipo>Encuesta</tipo></Recurso></WS>";
		} else if (xml.equals("<WS><Recurso><id>11</id></Recurso></WS>")) {
			return "<WS><Recurso><id>11</id><ambitoId>3</ambitoId><descripcion>Encuesta con preguntas fijas</descripcion><tipo>Encuesta</tipo></Recurso></WS>";
		} else if (xml.equals("<WS><Recurso><id>1003</id></Recurso></WS>")) {//TODO: sacar harcodeo para testear los archivos.
			return "<WS><recurso><recursoId>1003</recursoId><ambitoId>3</ambitoId><descripcion>Es un Archivo</descripcion><tipo>Archivo</tipo></recurso></WS>";
		}
		//fin pruebas
		IntegracionStub.SeleccionarDatos seleccionar_e = new IntegracionStub.SeleccionarDatos();
		seleccionar_e.setXml(xml);
		IntegracionStub.SeleccionarDatosResponse s_resp_e;
		try {
			s_resp_e = stub.seleccionarDatos(seleccionar_e);
			return s_resp_e.get_return();
		} catch (AxisFault e) {
			throw new ConnectionException("No se pudo seleccionar. ");
		} catch (RemoteException e) {
			throw new ConnectionException("No se pudo conectar al servicio Integracion. ");
		}
	}

	public String eliminar(String xml) throws ConnectionException {
		IntegracionStub.EliminarDatos eliminar = new IntegracionStub.EliminarDatos();
		eliminar.setXml(xml);
		IntegracionStub.EliminarDatosResponse e_resp;
		try {
			e_resp = stub.eliminarDatos(eliminar);
			return e_resp.get_return();
		} catch (AxisFault e) {
			throw new ConnectionException("No se pudo eliminar. ");
		} catch (RemoteException e) {
			throw new ConnectionException("No se pudo conectar al servicio Integracion. ");
		}
		
	}

	public String guardar(String xml) throws ConnectionException {
		IntegracionStub.GuardarDatos guardar = new IntegracionStub.GuardarDatos();
		guardar.setXml(xml);
		
		try {
			IntegracionStub.GuardarDatosResponse g_resp = stub.guardarDatos(guardar);
			return g_resp.get_return();
		} catch (AxisFault e) {
			throw new ConnectionException("No se pudo guardar. ");
		} catch (RemoteException e) {
			throw new ConnectionException("No se pudo conectar al servicio Integracion. ");
		}
	}

	public String guardarArchivo(String xml, DataHandler archivo) throws ConnectionException {
		IntegracionStub.GuardarArchivoResponse responseArchivo;
		IntegracionStub.GuardarArchivo requestArchivo = new IntegracionStub.GuardarArchivo();
		requestArchivo.setArchivo(archivo);
		requestArchivo.setXml(xml);
		try {
			responseArchivo = stub.guardarArchivo(requestArchivo);
			return responseArchivo.get_return();
		} catch (AxisFault e) {
			throw new ConnectionException("No se pudo guardar archivo. ");
		} catch (RemoteException e) {
			throw new ConnectionException("No se pudo conectar al servicio Integracion. ");
		}
	}

	public String eliminarArchivo(String xml) throws ConnectionException {
		//TODO Dami, revisa la especificacion en la doc de integracion.. para ver como se manda este xml
		IntegracionStub.EliminarArchivoResponse responseArchivo;
		IntegracionStub.EliminarArchivo requestArchivo = new IntegracionStub.EliminarArchivo();
		requestArchivo.setXml(xml);
		try {
			responseArchivo = stub.eliminarArchivo(requestArchivo);
			return responseArchivo.get_return();
		} catch (AxisFault e) {
			throw new ConnectionException("No se pudo eliminar archivo. ");
		} catch (RemoteException e) {
			throw new ConnectionException("No se pudo conectar al servicio Integracion. ");
		}
	}
}