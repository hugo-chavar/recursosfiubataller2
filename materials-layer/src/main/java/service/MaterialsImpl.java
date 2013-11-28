package service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import model.Archivo;
import model.Encuesta;
import model.Encuesta2;
import model.Link;
import model.ListaDeRecursos;
import model.Recurso;

@WebService(endpointInterface = "service.Materials")
public class MaterialsImpl implements Materials {

	@Override
	public String sayHello(String name) {
		return "Hello, Welcom to jax-ws " + name;
	}

	@Override
	public Encuesta2 getEncuesta() {
		Encuesta2 e = new Encuesta2();
		e.rellenar();
		return e;
	}

	@Override
	public ListaDeRecursos obtenerRecursos(int idAmbiente, int idUsuario) {
		List<Recurso> recursos = new ArrayList<Recurso>();
		List<Recurso> recursosPermitidos = new ArrayList<Recurso>();

		//Recurso r = new Recurso(4, 3, "DescriboRecurso");
		//recursosPermitidos.add(r);

		// obtengo los recursos
		recursos = solicitarBdRecursosAmbiente(idAmbiente);
		for (int i = 0; i < recursos.size(); i++) {
			if (consultarPermisoUsuario(recursos.get(i).getIdRecurso(),
					idUsuario)) {
				recursosPermitidos.add(recursos.get(i));
				//String tipo = recursos.get(i).getClass().getName();
				//System.out.println(tipo);
				// TODO:Ver como devolvemos el tipo de cada recurso y si
				// devolvemos
				// vector de recursos
			}
		}
		ListaDeRecursos lista = new ListaDeRecursos(recursosPermitidos);
		return lista;
	}

	// TODO:METODOS QUE DEBEN COMPLETARSE CON LLAMADOS A SEGURIDAD
	private boolean consultarPermisoUsuario(int idRecurso, int idUsuario) {
		// Harcodeo
		if (idRecurso < idUsuario)
			return true;
		return false;
	}

	// TODO:METODOS QUE DEBEN COMPLETARSE CON LLAMADAS A BASE DE DATOS
	// Aca va la logica de la solicitud de un Recurso a la base de datos
	private Recurso obtenerRecursoBd(int idAmbiente, int idRecurso) {
		// Harcodeo segun id de Recurso el recurso a crear para prueba
		Recurso recurso;
		if (idRecurso <= 2) {
			recurso = new Archivo();
			recurso.setDescripcion("Archivo resumen del Tp");
		} else if (idRecurso > 2 && idRecurso <= 4) {
			recurso = new Link();
			recurso.setDescripcion("Link a pagina web");
		} else {
			recurso = new Encuesta();
			recurso.setDescripcion("Encuesta a pagina web");
		}
		recurso.setIdRecurso(idRecurso);

		return recurso;
	}

	private List<Recurso> solicitarBdRecursosAmbiente(int idAmbiente) {
		// Harcodeo recursos del ambiente.
		List<Recurso> recursos = new ArrayList<Recurso>();
		Recurso recurso = new Archivo();
		recurso.setDescripcion("Archivo resumen del Tp");
		recurso.setIdRecurso(1);
		recursos.add(recurso);
		recurso = new Link();
		recurso.setDescripcion("Link a pagina web");
		recurso.setIdRecurso(3);
		recursos.add(recurso);
		recurso = new Encuesta();
		recurso.setDescripcion("Encuesta a pagina web");
		recurso.setIdRecurso(6);
		recursos.add(recurso);

		return recursos;
	}

	private boolean agregarRecursoBD(int idAmbiente, Recurso recurso, int idUsuario) {
		// Harcodeo
		if (idUsuario > 5)
			return false;
		return true;
	}

	private boolean eliminarRecursoBD(int idAmbiente, int idRecurso, int idUsuario) {
		// Harcodeo
		if (idUsuario > 5)
			return false;
		return true;
	}

}
