package model;

import java.util.List;

public class ListaDeRecursos {

	private List<Recurso> recursos;
	
	public ListaDeRecursos(List<Recurso> recursos) {
		setRecursos(recursos);
	}

	public List<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(List<Recurso> recursos) {
		this.recursos = recursos;
	}

}
