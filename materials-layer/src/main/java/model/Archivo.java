package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.NONE)
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
	@XmlTransient
	private Integer tamanio;
	@XmlElement
	private String tipoArchivo;
	@XmlElement
	private String nombreArchivo;
	@XmlElement
	private DataHandler rawFile;
	@XmlElement
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
 
    public DataHandler getRawFile() {
        return rawFile;
    }
 
    public void setRawFile(DataHandler file)  {
    	
        this.rawFile = file;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
   	 	try {
			this.rawFile.writeTo(output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	 	this.fileBinary = output.toByteArray();
    }
    
    public byte[] getByteArray() throws IOException{
    	
    	//return new String(this.fileBinary, "UTF-8");
    	 return this.fileBinary;
    }
	public Integer getSize() {
		return this.fileBinary.length;
	}

	public void setSize(Integer size) {
		this.tamanio = size;
	}

	public void setByteArray(byte[] byteArray) {
		this.fileBinary =byteArray;
		
	}

}