package connection;

import java.rmi.RemoteException;
import java.util.HashMap;

import javax.activation.DataHandler;

import org.apache.axis2.AxisFault;


import com.ws.services.IntegracionStub;
import com.ws.services.IntegracionStub.SeleccionarArchivoMetadata;
import com.ws.services.IntegracionStub.SeleccionarBytesArchivo;
import com.ws.services.IntegracionStub.SeleccionarBytesArchivoResponse;

import connection.exceptions.ConnectionException;

public class IntegracionProxy {
	private IntegracionStub stub;
	private HashMap<String, String> harcodeos;

	public IntegracionProxy() {
		try {
			stub = new IntegracionStub();
		} catch (AxisFault e) {
			//System.out.println("Error al intentar contectarse con Integracion");
		}
		generateTestData();
	}

	private void generateTestData() {
		harcodeos = new HashMap<String, String>();
		String el1, el2;
		el1 = "<WS><Encuesta><id>15</id></Encuesta></WS>";
		el2 = "<WS><Encuesta><evaluada>true</evaluada><preguntas>C;1;De que color es el caballo blanco de San Martin?;blanco|" +
				"C;2;Cuantas patas tiene un gato?;4</preguntas></Encuesta></WS>";
		harcodeos.put(el1, el2);
		el1 = "<WS><Encuesta><id>10</id></Encuesta></WS>";
		el2 = "<WS><Encuesta><evaluada>true</evaluada><preguntas>F;1;De que color es el caballo blanco de San Martin?;negro,blanco,marron;2|" +
				"F;2;Cuantas patas tiene un gato?;3,2,4;3</preguntas></Encuesta></WS>";
		harcodeos.put(el1, el2);
		el1 = "<WS><Encuesta><id>11</id></Encuesta></WS>";
		el2 = "<WS><Encuesta><evaluada>true</evaluada><preguntas>F;1;De que color\\; es\\, el\\| caballo blanco de San Martin?;negro,blanco,marron;2|" +
				"F;2;Cuantas patas tiene un gato?;3,2,4;3</preguntas></Encuesta></WS>";
		harcodeos.put(el1, el2);
		el1 = "<WS><Recurso><id>15</id></Recurso></WS>";
		el2 = "<WS><Recurso><id>15</id><ambitoId>2</ambitoId><descripcion>Encuesta con preguntas a completar</descripcion><tipo>Encuesta</tipo></Recurso></WS>";
		harcodeos.put(el1, el2);
		el1 = "<WS><Recurso><id>10</id></Recurso></WS>";
		el2 = "<WS><Recurso><id>10</id><ambitoId>3</ambitoId><descripcion>Encuesta con preguntas fijas</descripcion><tipo>Encuesta</tipo></Recurso></WS>";
		harcodeos.put(el1, el2);
		el1 = "<WS><Recurso><id>10</id></Recurso></WS>";
		el2 = "<WS><Recurso><id>10</id><ambitoId>3</ambitoId><descripcion>Encuesta con preguntas fijas</descripcion><tipo>Encuesta</tipo></Recurso></WS>";
		harcodeos.put(el1, el2);
		el1 = "<WS><Recurso><id>11</id></Recurso></WS>";
		el2 = "<WS><Recurso><id>11</id><ambitoId>3</ambitoId><descripcion>Encuesta con preguntas fijas</descripcion><tipo>Encuesta</tipo></Recurso></WS>"; 
		harcodeos.put(el1, el2);
		el1 = "<WS><Recurso><id>1003</id></Recurso></WS>";
		el2 = "<WS><Recurso><recursoId>1003</recursoId><ambitoId>3</ambitoId><descripcion>Es un Archivo</descripcion><tipo>Archivo</tipo></Recurso></WS>"; 
		harcodeos.put(el1, el2);
		el1 = "<WS><Link><id>999</id></Link></WS>";
		el2 = "<WS><Link><id>999</id><nombre>www.lomejordelomejor.es</nombre></Link></WS>"; 
		harcodeos.put(el1, el2);
		//--------------
		el1 = "<WS><EncuestaRespondida><recursoId>15</recursoId><usuarioId>4</usuarioId></EncuestaRespondida></WS>";
		el2 = "<WS><EncuestaRespondida><recursoId>15</recursoId><usuarioId>4</usuarioId><evaluacion>50</evaluacion><preguntasRespondidas>C;1;false;Azul|" +
				"C;2;true;4</preguntasRespondidas></EncuestaRespondida></WS>"; 
		harcodeos.put(el1, el2);
		el1 = "<WS><EncuestaRespondida><recursoId>10</recursoId><usuarioId>5</usuarioId></EncuestaRespondida></WS>";
		el2 = "<WS><EncuestaRespondida><recursoId>10</recursoId><usuarioId>5</usuarioId><preguntasRespondidas>F;1;null;1|" +
				"F;2;null;2</preguntasRespondidas></EncuestaRespondida></WS>";
		harcodeos.put(el1, el2);
		el1 = "<WS><EncuestaRespondida><recursoId>18</recursoId><usuarioId>5</usuarioId></EncuestaRespondida></WS>";
		el2 = "<WS><EncuestaRespondida><recursoId>18</recursoId><usuarioId>5</usuarioId><evaluacion>67</evaluacion><preguntasRespondidas>F;1;true;1,5,7|C;2;false;cientifico|" +
				"F;3;true;1</preguntasRespondidas></EncuestaRespondida></WS>";
		harcodeos.put(el1, el2);
		el1 = "<WS><EncuestaRespondida><recursoId>19</recursoId><usuarioId>5</usuarioId></EncuestaRespondida></WS>";
		el2 = "<WS><EncuestaRespondida><recursoId>19</recursoId><usuarioId>5</usuarioId><evaluacion>0</evaluacion><preguntasRespondidas>F;1;false;|C;2;false;</preguntasRespondidas></EncuestaRespondida></WS>";
		harcodeos.put(el1, el2);
		el1 = "<WS><Usuario><username>5</username></Usuario></WS>";
		el2 = "<WS><Usuario><id>5</id></Usuario></WS>";
		harcodeos.put(el1, el2);
		el1 = "<WS><Usuario><username>4</username></Usuario></WS>";
		el2 = "<WS><Usuario><id>4</id></Usuario></WS>";
		harcodeos.put(el1, el2);
		el1 = "<WS><Usuario><username>15</username></Usuario></WS>";
		el2 = "<WS><Usuario><id>15</id></Usuario></WS>";
		harcodeos.put(el1, el2);
		
	}
	
	public String seleccionar(String xml) throws ConnectionException {
		//TODO: harcodeos de "respuestas" de integracion
		String x = xml.substring(xml.indexOf("<WS>"));
		if (harcodeos.containsKey(x)) {
			return harcodeos.get(x);
		}
		//fin pruebas
		IntegracionStub.SeleccionarDatos seleccionar_e = new IntegracionStub.SeleccionarDatos();
		seleccionar_e.setXml(xml);
		IntegracionStub.SeleccionarDatosResponse s_resp_e;

		try {
			s_resp_e = stub.seleccionarDatos(seleccionar_e);
			return s_resp_e.get_return();
		} catch (AxisFault e) {
			throw new ConnectionException("Fallo en la conexion con Integracion, reintente luego. ");
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
		IntegracionStub.GuardarDatosResponse g_resp;

		try {
			g_resp = stub.guardarDatos(guardar);
			return g_resp.get_return();
		} catch (AxisFault e) {
			throw new ConnectionException("No se pudo guardar. ");
		} catch (RemoteException e) {
			throw new ConnectionException("No se pudo conectar al servicio Integracion. ");
		}
	}

	public String guardarArchivo(String xml, DataHandler archivo) throws ConnectionException {
		IntegracionStub.GuardarArchivo requestArchivo = new IntegracionStub.GuardarArchivo();
		requestArchivo.setArchivo(archivo);
		requestArchivo.setXml(xml);
		IntegracionStub.GuardarArchivoResponse ga_resp;

		try {
			ga_resp = stub.guardarArchivo(requestArchivo);
			return ga_resp.get_return();
		} catch (AxisFault e) {
			throw new ConnectionException("No se pudo guardar archivo. ");
		} catch (RemoteException e) {
			throw new ConnectionException("No se pudo conectar al servicio Integracion. ");
		}
	}

	public String eliminarArchivo(String xml) throws ConnectionException {
		//TODO Dami, revisa la especificacion en la doc de integracion.. para ver como se manda este xml
		//TODO (Hugo) me parece que esto no es necesario--> eliminar archivos con el otro metodo
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

	public String seleccionarArchivoMetadata(String xml) throws ConnectionException {
		SeleccionarArchivoMetadata fileSelected = new SeleccionarArchivoMetadata();
		IntegracionStub.SeleccionarArchivoMetadataResponse responseArchivo;
		try {
			fileSelected.setXml(xml);
			responseArchivo = stub.seleccionarArchivoMetadata(fileSelected);
			return responseArchivo.get_return();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConnectionException("No se pudo obtener la metadata  archivo. ");
		}
	}
	public DataHandler seleccionarArchivo(String xml) throws ConnectionException{
		SeleccionarBytesArchivo fileBytes = new SeleccionarBytesArchivo();
		SeleccionarBytesArchivoResponse response;
		fileBytes.setXml(xml);
		try {
			response = stub.seleccionarBytesArchivo(fileBytes);
			return response.get_return();
		} catch (RemoteException e) {
			throw new ConnectionException("No se pudo obtener el  Byte Array del archivo. ");
		}
		
	}
}