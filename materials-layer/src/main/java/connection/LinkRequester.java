package connection;

import java.rmi.RemoteException;
import java.util.ArrayList;

import model.Link;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionWSStub;
import com.ws.services.IntegracionWSStub.GuardarDatos;
import com.ws.services.IntegracionWSStub.GuardarDatosResponse;
import com.ws.services.IntegracionWSStub.SeleccionarDatos;
import com.ws.services.IntegracionWSStub.SeleccionarDatosResponse;


public class LinkRequester {

	public static int MAX_ITEMS_LIST = 10;
	
	private IntegracionWSStub stub;
	private LinkParser parser;
	private ArrayList<Link> cacheLinks;
	private int indexToReplace;
	
	
	public LinkRequester() {
    	try {
	    	this.stub = new IntegracionWSStub();
    	} catch (AxisFault e) {
            e.printStackTrace();
    	}
	}
	
	public void save(Link link) {
		// Guardo el link
		String link_str = this.parser.serializeLink(link);
    	try {
	    	GuardarDatos guardar = new GuardarDatos();
	    	guardar.setXml(link_str);
	    	GuardarDatosResponse g_resp = this.stub.guardarDatos(guardar);
	    	System.out.println(g_resp.get_return());
    	} catch (AxisFault e) {
            e.printStackTrace();
    	} catch (RemoteException e) {
            e.printStackTrace();
    	}
    	
    	// Agrego al cache de links
    	this.addToCacheLinks(link);
	}
	
	public Link get(int IDAmbiente, int IDLink) {
		// Busco en el cache de links
		Link link = this.searchCachedLink(IDAmbiente, IDLink);
		if (link != null) {
			return link;
		} else {
	    	try {
				// Consulto el link guardado
	    		String xml = this.parser.serializeLinkQuery(IDAmbiente, IDLink);
		    	SeleccionarDatos seleccionar_e = new SeleccionarDatos();
		    	seleccionar_e.setXml(xml);
		    	SeleccionarDatosResponse s_resp_e = this.stub.seleccionarDatos(seleccionar_e);
		    	String xml_resp_e = s_resp_e.get_return();
		    	link = this.parser.deserializeLink(xml_resp_e);
		    	
		    	// Agrego al cache de links
		    	this.addToCacheLinks(link);
		    	
		    	return link;
	    	} catch (AxisFault e) {
	            e.printStackTrace();
	    	} catch (RemoteException e) {
	            e.printStackTrace();
	    	}
		}
    	return null;
	}
	
	private void addToCacheLinks(Link link) {
		if (this.cacheLinks.size() < LinkRequester.MAX_ITEMS_LIST) {
			this.cacheLinks.add(link);
		} else {
			this.cacheLinks.set(this.indexToReplace, link);
			if (this.indexToReplace < (LinkRequester.MAX_ITEMS_LIST - 1)) {
				this.indexToReplace++;
			} else {
				this.indexToReplace = 0;
			}
		}
	}
	
	private Link searchCachedLink(int IDAmbiente, int IDLink) {
		Link link = null;
		int i = 0;
		Boolean found = false;
		while ((!found) && (i<this.cacheLinks.size())) {
			if ((this.cacheLinks.get(i).getIdAmbiente() == IDAmbiente) && (this.cacheLinks.get(i).getIdRecurso() == IDLink)) {
				link = this.cacheLinks.get(i);
				found = true;
			}
			i++;
		}
		return link;
	}
	
}
