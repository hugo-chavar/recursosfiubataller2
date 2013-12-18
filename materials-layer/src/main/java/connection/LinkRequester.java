package connection;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import model.Link;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionWSStub;
import com.ws.services.IntegracionWSStub.GuardarDatos;
import com.ws.services.IntegracionWSStub.GuardarDatosResponse;
import com.ws.services.IntegracionWSStub.SeleccionarDatos;
import com.ws.services.IntegracionWSStub.SeleccionarDatosResponse;

import connection.cache.Cache;


public class LinkRequester {
	
	private IntegracionWSStub stub;
	private LinkParser parser;
	private Cache<Link> cache;
	
	
	public LinkRequester() {
		cache = new Cache<Link>();
    	try {
	    	this.stub = new IntegracionWSStub();
    	} catch (AxisFault e) {
			System.out.println("Error al intentar contectarse con Integracion");
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
			System.out.println("Error al intentar guardar la siguiente Link:");
			System.out.println(link.getDescripcion());
		} catch (RemoteException e) {
			System.out.println("Error de conexion remota");
		}
	}
	
	public List<Link> requestLinks(int IDAmbiente, int IDLink) {
		List<Link> links = new ArrayList<Link>();
    	try {
			// Consulto el link guardado
    		String xml = this.parser.serializeLinkQuery(IDAmbiente, IDLink);
	    	SeleccionarDatos seleccionar_e = new SeleccionarDatos();
	    	seleccionar_e.setXml(xml);
	    	SeleccionarDatosResponse s_resp_e = this.stub.seleccionarDatos(seleccionar_e);
	    	String xml_resp_e = s_resp_e.get_return();
	    	links = this.parser.deserializeLink(xml_resp_e);
	    	return links;
    	} catch (AxisFault e) {
			System.out.println("Error al intentar obtener el siguiente Link:");
			System.out.println("IDLink: " + IDLink);
		} catch (RemoteException e) {
			System.out.println("Error de conexion remota");
		}
    	return null;
	}
	
	public Link get(int IDAmbiente, int IDLink) {
		// Busco en el cache de links
		Link target = new Link(IDAmbiente, IDLink, "");
		if (cache.contains(target)) {
			return cache.get(target);
		} else {
			Link link = this.requestLinks(IDAmbiente, IDLink).get(0);

			// Agrego al cache de links
			cache.add(link);

			return link;
		}
	}
	
	public List<Link> getAll(int IDAmbiente) {
		List<Link> links = new ArrayList<Link>();
		links = this.requestLinks(IDAmbiente, -1);
		
		return links;
	}
	
}
