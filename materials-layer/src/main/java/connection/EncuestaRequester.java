package connection;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import model.Encuesta;
import model.EncuestaRespondida;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionWSStub;
import com.ws.services.IntegracionWSStub.GuardarDatos;
import com.ws.services.IntegracionWSStub.GuardarDatosResponse;
import com.ws.services.IntegracionWSStub.SeleccionarDatos;
import com.ws.services.IntegracionWSStub.SeleccionarDatosResponse;

public class EncuestaRequester {

	public static int MAX_ITEMS_LIST = 10;

	private IntegracionWSStub stub;
	private EncuestaParser parser;
	private List<Encuesta> cacheEncuestas;
	private int indexToReplace;
	private List<EncuestaRespondida> cacheEncuestasRespondidas;
	private int indexToReplaceRespondidas;

	public EncuestaRequester() {
		this.parser = new EncuestaParser();
		this.cacheEncuestas = new ArrayList<Encuesta>();
		this.cacheEncuestasRespondidas = new ArrayList<EncuestaRespondida>();
		this.indexToReplace = 0;
		this.indexToReplaceRespondidas = 0;
		try {
			this.stub = new IntegracionWSStub();
		} catch (AxisFault e) {
			// e.printStackTrace();
			System.out.println("Error al intentar contectarse con Integracion");
		}
	}

	public void save(Encuesta encuesta) {
		// Guardo la encuesta
		String encuesta_str = this.parser.serializeEncuesta(encuesta);
		try {
			GuardarDatos guardar = new GuardarDatos();
			guardar.setXml(encuesta_str);
			GuardarDatosResponse g_resp = this.stub.guardarDatos(guardar);
			System.out.println(g_resp.get_return());
		} catch (AxisFault e) {
//			 e.printStackTrace();
			System.out.println("Error al intentar guardar la siguiente Encuesta:");
			System.out.println(encuesta.getDescripcion());
		} catch (RemoteException e) {
//			 e.printStackTrace();
			System.out.println("Error de conexion remota");
		}

		// Agrego al cache de encuestas
		// Yami, la primera vez que viene no tiene el Id, no podes grabarlo aca
		// solo ponelo en el cache cuando lo recuperas de integracion
		this.addToCacheEncuestas(encuesta);
	}

	public void saveRespondida(EncuestaRespondida respondida) {
		// Guardo la encuesta respondida
		String encuesta_str = this.parser.serializeEncuestaRespondida(respondida);
		try {
			GuardarDatos guardar = new GuardarDatos();
			guardar.setXml(encuesta_str);
			GuardarDatosResponse g_resp = this.stub.guardarDatos(guardar);
			System.out.println(g_resp.get_return());
		} catch (AxisFault e) {
//			 e.printStackTrace();
			System.out.println("Error al intentar guardar la siguiente Encuesta Respondida:");
			System.out.println("Usuario: " + respondida.getIdUsuario());
		} catch (RemoteException e) {
			// e.printStackTrace();
			System.out.println("Error de conexion remota");
		}

		// Agrego al cache de encuestas respondidas
		this.addToCacheEncuestasRespondidas(respondida);
	}

	public Encuesta get(int IDAmbiente, int IDEncuesta) {
		// Busco en el cache de encuestas
		Encuesta encuesta = this.searchCachedEncuesta(IDAmbiente, IDEncuesta);
		if (encuesta != null) {
			return encuesta;
		} else {
			try {
				// Consulto la encuesta guardada
				String xml = this.parser.serializeEncuestaQuery(IDAmbiente, IDEncuesta);
				SeleccionarDatos seleccionar_e = new SeleccionarDatos();
				seleccionar_e.setXml(xml);
				SeleccionarDatosResponse s_resp_e = this.stub.seleccionarDatos(seleccionar_e);
				String xml_resp_e = s_resp_e.get_return();
				encuesta = this.parser.deserializeEncuesta(xml_resp_e);

				// Agrego al cache de encuestas
				this.addToCacheEncuestas(encuesta);

				return encuesta;
			} catch (AxisFault e) {
//				e.printStackTrace();
				System.out.println("Error al intentar obtener la siguiente Encuesta:");
				System.out.println("IDEncuesta: " + IDEncuesta);
			} catch (RemoteException e) {
//				 e.printStackTrace();
				System.out.println("Error de conexion remota");
			}
		}
		return null;
	}

	private void addToCacheEncuestas(Encuesta encuesta) {
		if (this.cacheEncuestas.size() < EncuestaRequester.MAX_ITEMS_LIST) {
			this.cacheEncuestas.add(encuesta);
		} else {
			this.cacheEncuestas.set(this.indexToReplace, encuesta);
			if (this.indexToReplace < (EncuestaRequester.MAX_ITEMS_LIST - 1)) {
				this.indexToReplace++;
			} else {
				this.indexToReplace = 0;
			}
		}
	}

	private void addToCacheEncuestasRespondidas(EncuestaRespondida respondida) {
		if (this.cacheEncuestasRespondidas.size() < EncuestaRequester.MAX_ITEMS_LIST) {
			this.cacheEncuestasRespondidas.add(respondida);
		} else {
			this.cacheEncuestasRespondidas.set(this.indexToReplaceRespondidas, respondida);
			if (this.indexToReplaceRespondidas < (EncuestaRequester.MAX_ITEMS_LIST - 1)) {
				this.indexToReplaceRespondidas++;
			} else {
				this.indexToReplaceRespondidas = 0;
			}
		}
	}

	private Encuesta searchCachedEncuesta(int IDAmbiente, int IDEncuesta) {
		Encuesta encuesta = null;
		int i = 0;
		Boolean found = false;
		while ((!found) && (i < this.cacheEncuestas.size())) {
			if ((this.cacheEncuestas.get(i).getIdAmbiente() == IDAmbiente)
					&& (this.cacheEncuestas.get(i).getIdRecurso() == IDEncuesta)) {
				encuesta = this.cacheEncuestas.get(i);
				found = true;
			}
			i++;
		}
		return encuesta;

		// reemplazar por este codigo es
		// "mas orientado a objetos y mas orientado a java"
		// Yami, los this. en java solo se usan para desambiguar .. en general
		// no se ponen
		// Encuesta target = new Encuesta(IDEncuesta, IDAmbiente,"" , false);
		// for (Encuesta e : cacheEncuestas) {
		// if (e.equals(target)) {
		// return e;
		// }
		// }
		// return null;
	}

}