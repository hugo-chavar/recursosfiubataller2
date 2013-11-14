package model;

public class Archivo extends Recurso {

	public Archivo(Integer idRecurso, Integer idAmbiente, String descripcion) {
		super(idRecurso, idAmbiente, descripcion);
		// TODO Auto-generated constructor stub
	}

	public Archivo() {
		// TODO Auto-generated constructor stub
	}

	public String path;

	public Integer tamanio;

	public String tipo;

	public String nombre;

	public void Guardar() {
		// Guardar en la base de Datos
	}

	@Override
	public void Crear() {
		// TODO Auto-generated method stub

	}

}