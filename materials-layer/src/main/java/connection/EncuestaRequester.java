package connection;

import java.rmi.RemoteException;

import model.Encuesta;
import model.EncuestaRespondida;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionStub;
import com.ws.services.IntegracionStub.GuardarDatos;
import com.ws.services.IntegracionStub.GuardarDatosResponse;
import com.ws.services.IntegracionStub.SeleccionarDatos;
import com.ws.services.IntegracionStub.SeleccionarDatosResponse;

import connection.cache.Cache;

public class EncuestaRequester {

	private IntegracionStub stub;
	private EncuestaParser parser;
	private Cache<Encuesta> cacheEncuestas;
	private Cache<EncuestaRespondida> cacheEncuestasRespondidas;

	public EncuestaRequester() {
		
		parser = new EncuestaParser();
		cacheEncuestas = new Cache<Encuesta>();
		cacheEncuestasRespondidas = new Cache<EncuestaRespondida>();
		
		try {
			stub = new IntegracionStub();
		} catch (AxisFault e) {
			System.out.println("Error al intentar contectarse con Integracion");
		}
		
	}
	
	public void save(Encuesta encuesta) {
		
		if (encuesta != null) {
			// Guardo la encuesta
			String encuesta_str = parser.serializeEncuesta(encuesta);
			try {
				GuardarDatos guardar = new GuardarDatos();
				guardar.setXml(encuesta_str);
				GuardarDatosResponse g_resp = stub.guardarDatos(guardar);
				System.out.println(g_resp.get_return());
			} catch (AxisFault e) {
				System.out.println("Error al intentar guardar la siguiente Encuesta:");
				System.out.println(encuesta.getDescripcion());
			} catch (RemoteException e) {
				System.out.println("Error de conexion remota");
			}
		}
		
	}

	public void saveRespondida(EncuestaRespondida respondida) {
		
		if (respondida != null) {
			// Guardo la encuesta respondida
			String encuesta_str = parser.serializeEncuestaRespondida(respondida);
			try {
				GuardarDatos guardar = new GuardarDatos();
				guardar.setXml(encuesta_str);
				GuardarDatosResponse g_resp = this.stub.guardarDatos(guardar);
				System.out.println(g_resp.get_return());
			} catch (AxisFault e) {
				System.out.println("Error al intentar guardar la siguiente Encuesta Respondida:");
				System.out.println("Usuario: " + respondida.getIdUsuario());
			} catch (RemoteException e) {
				System.out.println("Error de conexion remota");
			}
		}
		
	}

	public Encuesta get(int IDAmbiente, int IDEncuesta) {
		
		// Busco en el cache de encuestas
		Encuesta target = new Encuesta(IDEncuesta, IDAmbiente, "", false);
		
		if (cacheEncuestas.contains(target)) {
			return cacheEncuestas.get(target);
		} else {
			try {
				
				// Consulto la encuesta guardada
				String xml = parser.serializeEncuestaQuery(IDEncuesta);
				SeleccionarDatos seleccionar_e = new SeleccionarDatos();
				seleccionar_e.setXml(xml);
				SeleccionarDatosResponse s_resp_e = stub.seleccionarDatos(seleccionar_e);
				String xml_resp_e = s_resp_e.get_return();
				Encuesta encuesta = parser.deserializeEncuesta(xml_resp_e);
				
				// Agrego al cache de encuestas
				cacheEncuestas.add(encuesta);
				
				return encuesta;
				
			} catch (AxisFault e) {
				System.out.println("Error al intentar obtener la siguiente Encuesta:");
				System.out.println("IDEncuesta: " + IDEncuesta);
			} catch (RemoteException e) {
				System.out.println("Error de conexion remota");
			}
			
			return null;
		}
		
	}
	
	public EncuestaRespondida getRespondida(int IDAmbiente, int IDUsuario, int IDEncuesta) {
		
		// Busco en el cache de encuestas respondidas
		EncuestaRespondida target = new EncuestaRespondida(IDEncuesta, IDUsuario, 0);
		if (cacheEncuestasRespondidas.contains(target)) {
			return cacheEncuestasRespondidas.get(target);
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
				cacheEncuestasRespondidas.add(encuesta);

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

}