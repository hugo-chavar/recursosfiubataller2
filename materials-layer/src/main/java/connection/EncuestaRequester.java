package connection;

import java.rmi.RemoteException;

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
	
	public EncuestaRequester() {
		this.parser = new EncuestaParser();
    	try {
	    	this.stub = new IntegracionWSStub();
    	} catch (AxisFault e) {
            e.printStackTrace();
    	}
	}
	
	public void save(Encuesta encuesta) {
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
	}
	
	public Encuesta get(int IDAmbiente, int IDEncuesta) {
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
	    	Encuesta encuesta = this.parser.deserializeEncuesta(xml_resp);
	    	// TODO: deserializePreguntas
	    	return encuesta;
    	} catch (AxisFault e) {
            e.printStackTrace();
    	} catch (RemoteException e) {
            e.printStackTrace();
    	}
    	return null;
	}
	
}