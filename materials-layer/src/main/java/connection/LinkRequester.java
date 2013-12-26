package connection;

import java.rmi.RemoteException;

import model.Link;
import model.Recurso;

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

		parser = new LinkParser();
		cache = new Cache<Link>();

		try {
			this.stub = new IntegracionWSStub();
		} catch (AxisFault e) {
			System.out.println("Error al intentar contectarse con Integracion");
		}

	}

	public OperationResponse save(Link link) {
		OperationResponse response = new OperationResponse();
		response.setSuccess(false);
		// Guardo el link
		String link_str = parser.serializeLink(link);
		try {
			GuardarDatos guardar = new GuardarDatos();
			guardar.setXml(link_str);
			GuardarDatosResponse g_resp = stub.guardarDatos(guardar);
			// TODO implementar método que chequee las respuestas
			// if (xmlUtil.isResponseOk(g_resp.get_return())) {
			// or.setSuccess(true);
			// or.setReason("algo");
			// }
			response.setSuccess(true);
			System.out.println(g_resp.get_return());
		} catch (AxisFault e) {
			String reason = "Error al intentar guardar la siguiente Link: " + link.getDescripcion();
			System.out.println(reason);
			response.setReason(link_str);
		} catch (RemoteException e) {
			String reason = "Error de conexion remota";
			System.out.println(reason);
			response.setReason(reason);
		}
		return response;
	}

	public Link get(int IDAmbiente, int IDRecurso) {

		// Busco en el cache de links
		Link target = new Link(IDAmbiente, IDRecurso, "");
		if (cache.contains(target)) {
			return cache.get(target);
		} else {
			try {

				// Consulto el link guardado
				String xml = this.parser.serializeLinkQuery(IDRecurso);
				SeleccionarDatos seleccionar_e = new SeleccionarDatos();
				seleccionar_e.setXml(xml);
				SeleccionarDatosResponse s_resp_e = this.stub.seleccionarDatos(seleccionar_e);
				String xml_resp_e = s_resp_e.get_return();
				Link link = this.parser.deserializeLink(xml_resp_e);

				// Agrego al cache de links
				cache.add(link);

				return link;

			} catch (AxisFault e) {
				System.out.println("Error al intentar obtener el siguiente Link:");
				System.out.println("IDLink: " + IDRecurso);
			} catch (RemoteException e) {
				System.out.println("Error de conexion remota");
			}

			return null;
		}

	}

	public void delete(int recursoId) {
		cache.remove(new Link(recursoId, 0, ""));
	}

	public Link get(Recurso recurso) {
		Link target = new Link(recurso);
		if (cache.contains(target)) {
			return cache.get(target);
		} else {
			try {
				String xml = parser.serializeLinkQuery(recurso);
				SeleccionarDatos seleccionar_e = new SeleccionarDatos();
				seleccionar_e.setXml(xml);
				SeleccionarDatosResponse s_resp_e = this.stub.seleccionarDatos(seleccionar_e);
				String xml_resp_e = s_resp_e.get_return();
				Link link = this.parser.deserializeLink(xml_resp_e, recurso);

				cache.add(link);

				return link;

			} catch (AxisFault e) {
				System.out.println("Error al intentar obtener el siguiente Link:");
				System.out.println("IDLink: " + recurso.getRecursoId());
			} catch (RemoteException e) {
				System.out.println("Error de conexion remota");
			}

			return null;
		}

	}

}
