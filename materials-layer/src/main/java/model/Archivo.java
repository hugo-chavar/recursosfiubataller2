package model;

import javax.activation.DataHandler;

public class Archivo extends Recurso {

	public Archivo(Integer idRecurso, Integer idAmbiente, String descripcion) {
		super(idRecurso, idAmbiente, descripcion);
		this.tipo="Archivo";
		// TODO Auto-generated constructor stub
	}

	public Archivo() {
		this.tipo="Archivo";
		// TODO Auto-generated constructor stub
	}

	private String path;

	private Integer tamanio;

	private String tipo;

	private String nombre;
	
	private DataHandler archivo;
	
	
	public void setFileName(String fileName) {
        this.nombre = fileName;
    }
 
    public String getFileType() {
        return nombre;
    }
 
    public void setFileType(String fileType) {
        this.tipo = fileType;
    }
 
    public DataHandler getFile() {
        return archivo;
    }
 
    public void setFile(DataHandler file) {
        this.archivo = file;
    }

}