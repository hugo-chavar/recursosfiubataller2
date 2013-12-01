package connection;

import java.rmi.RemoteException;
import java.util.ArrayList;

import model.Encuesta;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionWSStub;
import com.ws.services.IntegracionWSStub.GuardarDatos;
import com.ws.services.IntegracionWSStub.GuardarDatosResponse;
import com.ws.services.IntegracionWSStub.SeleccionarDatos;
import com.ws.services.IntegracionWSStub.SeleccionarDatosResponse;


public class EncuestaRequester {
	
	public static int MAX_ITEMS_LIST = 10;
	
	IntegracionWSStub stub;
	private EncuestaParser parser;
	private ArrayList<Encuesta> cacheEncuestas;
	private int indexToReplace;
	
	
	public EncuestaRequester() {
		this.parser = new EncuestaParser();
		this.cacheEncuestas = new ArrayList<Encuesta>();
		this.indexToReplace = 0;
    	try {
	    	this.stub = new IntegracionWSStub();
    	} catch (AxisFault e) {
            e.printStackTrace();
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
            e.printStackTrace();
    	} catch (RemoteException e) {
            e.printStackTrace();
    	}
    	
    	// Guardo las preguntas
    	String preguntas_str = this.parser.serializePreguntas(encuesta);
    	try {
	    	GuardarDatos guardar = new GuardarDatos();
	    	guardar.setXml(preguntas_str);
	    	GuardarDatosResponse g_resp = this.stub.guardarDatos(guardar);
	    	System.out.println(g_resp.get_return());
    	} catch (AxisFault e) {
            e.printStackTrace();
    	} catch (RemoteException e) {
            e.printStackTrace();
    	}
    	
    	// Agrego al cache de encuestas
    	this.addToCacheEncuestas(encuesta);
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
		    	
				// Consulto las preguntas guardadas
	    		xml = this.parser.serializePreguntasQuery(IDAmbiente, IDEncuesta);
		    	SeleccionarDatos seleccionar_p = new SeleccionarDatos();
		    	seleccionar_p.setXml(xml);
		    	SeleccionarDatosResponse s_resp_p = this.stub.seleccionarDatos(seleccionar_p);
		    	String xml_resp_p = s_resp_p.get_return();
		    	encuesta.setPreguntas(this.parser.deserializePreguntas(xml_resp_p));
		    	
		    	// Agrego al cache de encuestas
		    	this.addToCacheEncuestas(encuesta);
		    	
		    	return encuesta;
	    	} catch (AxisFault e) {
	            e.printStackTrace();
	    	} catch (RemoteException e) {
	            e.printStackTrace();
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
	
	private Encuesta searchCachedEncuesta(int IDAmbiente, int IDEncuesta) {
		Encuesta encuesta = null;
		int i = 0;
		Boolean found = false;
		while ((!found) && (i<this.cacheEncuestas.size())) {
			if ((this.cacheEncuestas.get(i).getIdAmbiente() == IDAmbiente) && (this.cacheEncuestas.get(i).getIdRecurso() == IDEncuesta)) {
				encuesta = this.cacheEncuestas.get(i);
				found = true;
			}
			i++;
		}
		return encuesta;
	}
	
}