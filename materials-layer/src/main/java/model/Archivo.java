package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.activation.DataHandler;

import org.omg.CORBA.portable.InputStream;

public class Archivo extends Recurso {

	public Archivo(Integer idRecurso, Integer idAmbiente, String descripcion) {
		super(idRecurso, idAmbiente, descripcion);
		this.tipo="Archivo";
		// TODO Auto-generated constructor stub
	}

	public Archivo() {
		this.tipo="Archivo";
	}

//	private String path;

	private Integer size;

	private String tipoArchivo;

	private String nombreArchivo;
	
	private DataHandler rawFile;

	private byte[] fileBinary;
	
    public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getTipoArchivo() {
        return tipoArchivo;
    }
 
    public void setTipoArchivo(String fileType) {
        this.tipoArchivo = fileType;
    }
 
    public DataHandler getFile() {
        return rawFile;
    }
 
    public void setRawFile(DataHandler file)  {
    	
        this.rawFile = file;
    }
    
    public byte[] getByteArray() throws IOException{
    	 ByteArrayOutputStream output = new ByteArrayOutputStream();
    	 this.rawFile.writeTo(output);
    	 this.fileBinary = output.toByteArray();
    	//return new String(this.fileBinary, "UTF-8");
    	 return this.fileBinary;
    }
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setByteArray(byte[] byteArray) {
		this.fileBinary =byteArray;
		
	}
}