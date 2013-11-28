package connection;

import java.rmi.RemoteException;
import java.util.List;

import model.Encuesta;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionWSStub;
import com.ws.services.IntegracionWSStub.GuardarDatos;
import com.ws.services.IntegracionWSStub.GuardarDatosResponse;
import com.ws.services.IntegracionWSStub.SeleccionarDatos;
import com.ws.services.IntegracionWSStub.SeleccionarDatosResponse;


public class EncuestaRequester {
	
	IntegracionWSStub stub;
	private EncuestaParser parser;
	private List<Encuesta> cacheEncuestas;	
	
	public EncuestaRequester() {
		this.parser = new EncuestaParser();
    	try {
	    	this.stub = new IntegracionWSStub();
    	} catch (AxisFault e) {
            e.printStackTrace();
    	}
	}
	
	public void save(Encuesta encuesta) {
		// Guardo encuesta
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
    	
    	// Guardo preguntas
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
    	this.cacheEncuestas.add(encuesta);
	}
	
	public Encuesta get(int IDAmbiente, int IDEncuesta) {
		// Busco en el cache de encuestas
		Encuesta encuesta = this.searchCachedEncuesta(IDAmbiente, IDEncuesta);
		if (encuesta != null) {
			return encuesta;
		} else {
			// Pido la encuesta guardada
	    	try {
	    		String xml = "";
	    		xml = this.parser.addField(xml, "IDAmbiente", String.valueOf(IDAmbiente));
	    		xml = this.parser.addField(xml, "IDEncuesta", String.valueOf(IDEncuesta));
	    		xml = this.parser.addTag(xml, "Encuesta");
	    		xml = this.parser.addTag(xml, "WS");
		    	SeleccionarDatos seleccionar = new SeleccionarDatos();
		    	seleccionar.setXml(xml);
		    	SeleccionarDatosResponse s_resp = this.stub.seleccionarDatos(seleccionar);
		    	String xml_resp = s_resp.get_return();
		    	encuesta = this.parser.deserializeEncuesta(xml_resp);
		    	// TODO: deserializePreguntas
		    	return encuesta;
	    	} catch (AxisFault e) {
	            e.printStackTrace();
	    	} catch (RemoteException e) {
	            e.printStackTrace();
	    	}
		}
    	return null;
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