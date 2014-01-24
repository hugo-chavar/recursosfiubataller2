package connection;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import model.Recurso;

import org.apache.axis2.AxisFault;

import com.ws.services.IntegracionStub.SeleccionarDatos;
import com.ws.services.IntegracionStub.SeleccionarDatosResponse;

import connection.cache.Cache;
import connection.exceptions.GetException;
import connection.exceptions.ParseException;
import connection.responses.OperationResponse;
import connection.responses.RecursosResponse;


public class RecursosRequester extends HandlerRequester {

//	private IntegracionStub stub;
	private RecursosParser parser;
	private Cache<Recurso> cache;
//	private Boolean statusOk;
//	private String statusMessage; 
	private List<Recurso> recursosEjemplo;

	
	public RecursosRequester() {

		parser = new RecursosParser();
		cache = new Cache<Recurso>();
		cache.changeSize(1000);

//		try {
//			stub = new IntegracionStub();
//
////			statusOk = true;
//		} catch (AxisFault e) {
////			statusMessage = "Error al intentar contectarse con Integracion";
//			System.out.println("Error al intentar contectarse con Integracion");
//			System.out.println(e.toString());
//		}
		generateTestData();

	}

	protected void generateTestData() {
		recursosEjemplo = new ArrayList<Recurso>();
		Recurso r = new Recurso(11002,-1,"un link a google copado","Link");
		recursosEjemplo.add(r);
		r = new Recurso(11003,-1,"una encuesta chica","Encuesta");
		recursosEjemplo.add(r);
		r = new Recurso(11004,-1,"una encuesta grande","Encuesta");
		recursosEjemplo.add(r);
	}
	
	public OperationResponse get(Recurso target) throws GetException, ParseException {
		current = target;
		// Busco en el cache de recursos.
		if (cache.contains(target)) {
			OperationResponse response = OperationResponse.createSuccess();
			response.setRecurso(cache.get(target));
			return response;
		} else {
			// Consulto el recurso guardado
			String xml = parser.serializeRecursoQuery(target.getRecursoId());
//			System.out.println("Esto es lo que voy a buscar:");
//			System.out.println(xml);
			return get(xml);
		}
//			try {
//			
//				////////////// PRUEBAS //////////////
//				String xml_resp_e;
//				
//				if (xml.equals("<WS><recurso><recursoId>15</recursoId></recurso></WS>")) {
//					xml_resp_e = "<WS><recurso><recursoId>15</recursoId><ambitoId>2</ambitoId><descripcion>Encuesta con preguntas a completar</descripcion><tipo>Encuesta</tipo></recurso></WS>";
//				} else if (xml.equals("<WS><recurso><recursoId>10</recursoId></recurso></WS>")) {
//					xml_resp_e = "<WS><recurso><recursoId>10</recursoId><ambitoId>3</ambitoId><descripcion>Encuesta con preguntas fijas</descripcion><tipo>Encuesta</tipo></recurso></WS>";
//				} else if (xml.equals("<WS><recurso><recursoId>1003</recursoId></recurso></WS>")) {//TODO: sacar harcodeo para testear los archivos.
//					xml_resp_e = "<WS><recurso><recursoId>1003</recursoId><ambitoId>3</ambitoId><descripcion>Es un Archivo</descripcion><tipo>Archivo</tipo></recurso></WS>";
//				}else{
//					SeleccionarDatos seleccionar_e = new SeleccionarDatos();
//					seleccionar_e.setXml(xml);
//					SeleccionarDatosResponse s_resp_e = stub.seleccionarDatos(seleccionar_e);
//					xml_resp_e = s_resp_e.get_return();
//				}
//				////////////// PRUEBAS //////////////
//				
//				System.out.println(xml_resp_e);
////				if (xml_resp_e != null) {
//					Recurso recurso = parser.deserializeRecurso(xml_resp_e);
//				if (recurso == null) {	
//					String message = "Integracion dice: " + xml_resp_e.substring(0, xml_resp_e.indexOf('<') -2);
//					System.out.println(message);
//					throw new GetException(message);
//				}
//					// Agrego el recurso al cache
//					cache.add(recurso);
//					
//					return recurso;
////				}
//					
//			} catch (AxisFault e) {
//				String message = "Error al intentar obtener el recurso con IDRecurso: " + target.getRecursoId();
//				System.out.println(message);
//			} catch (RemoteException e) {
//				String message = "Error de conexion remota";
//				System.out.println(message);
//			}
//		}
//
//		return null;

	}

	public OperationResponse getAll(int IDAmbito) {

		List<Recurso> recursos;
		RecursosResponse recursosResponse = new RecursosResponse();
		String message;
		
		try {

			// Consulto los recursos guardados
			String xml = parser.serializeRecursosQuery(IDAmbito);
			SeleccionarDatos seleccionar_e = new SeleccionarDatos();
			seleccionar_e.setXml(xml);
			SeleccionarDatosResponse s_resp_e = stub.seleccionarDatos(seleccionar_e);
			String xml_resp_e = s_resp_e.get_return();
			System.out.println(xml_resp_e);
			recursos = parser.deserializeRecursos(xml_resp_e);
			
			recursosResponse.setRecursos(recursos);
			
			cache.addAll(recursos);
			
			recursosResponse.setSuccess(true);
			return recursosResponse;
		} catch (ParseException e) {
			message = e.getMessage();
			
			// TODO : (Hugo) devuelvo datos de ejemplo, mientras no funcione integracion
			recursosResponse.setRecursos(recursosEjemplo);
			recursosResponse.setSuccess(true);
			return recursosResponse;
		} catch (AxisFault e) {
			message = "Error al intentar obtener los recursos del IDAmbito: " + IDAmbito;
		} catch (RemoteException e) {
			message = "Error de conexion remota";
		}
		System.out.println(message);

		return OperationResponse.createFailed(message);

	}

	public OperationResponse delete(Recurso recurso) {
		
		current = recurso;

		// Consulto el link guardado
//		String xml = this.parser.serializeQueryByType(recurso.getRecursoId(), LinkParser.LINK_TAG);
		
		
//		OperationResponse response = new OperationResponse();

		// Borro el recurso
		String xml = parser.serializeDeleteQuery(recurso.getRecursoId());
		
		return delete(xml);
//		try {
//			
//			EliminarDatos eliminar = new EliminarDatos();
//			eliminar.setXml(xml);
//			EliminarDatosResponse e_resp = stub.eliminarDatos(eliminar);
//			System.out.println(e_resp.get_return());
//			
//			// TODO implementar método que chequee las respuestas
//			// if (xmlUtil.isResponseOk(g_resp.get_return())) {
//			// or.setSuccess(true);
//			// or.setReason("algo");
//			// }
//			
//			response.setSuccess(true);
//			
//			// Elimino el recurso del cache
//			cache.remove(new Recurso(recurso.getRecursoId(), 0, ""));
//			
//		} catch (AxisFault e) {
//			String reason = "Error al intentar eliminar el Recurso, Id: " + recurso.getRecursoId();
//			System.out.println(reason);
//			response.setReason(reason);
//		} catch (RemoteException e) {
//			String reason = "Error de conexion remota";
//			System.out.println(reason);
//			response.setReason(reason);
//		}
//		
//		return response;

	}
	
	public void updateCache(Recurso target) {
		if (cache.contains(target)) {
			cache.remove(target);
			cache.add(target);
		}
	}

	@Override
	public void deleteFromCache(int recursoId) {
		cache.remove(new Recurso(recursoId, 0, ""));
		
	}

	@Override
	protected boolean cacheContains(int recursoId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Recurso retrieveCached(int recursoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void deserialize(String xml_resp_e) throws ParseException {
		current = parser.deserializeRecurso(xml_resp_e);
	}

	@Override
	protected String getHandledType() {
		return "Recurso";
	}

	@Override
	protected Parser getParser() {
		return parser;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Cache getCache() {
		return cache;
	}

}
