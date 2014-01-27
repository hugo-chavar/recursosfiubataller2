package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sun.istack.ByteArrayDataSource;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Archivo extends Recurso {

	public Archivo(Integer idRecurso, Integer idAmbiente, String descripcion) {
		super(idRecurso, idAmbiente, descripcion);
		this.tipo="Archivo";
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
	@XmlMimeType("application/octet-stream")
	private DataHandler rawFile;

	@XmlTransient
	private byte[] fileBinary;

	@XmlTransient
	private String contentType;

	@XmlTransient
	private String stringFile;
	
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
			System.out.println(e.getMessage());
			System.out.println("error al setear el DataHandler");
		}
   	 	this.setContentType(this.rawFile.getContentType());
   	 	this.fileBinary = output.toByteArray();
   	 	try {
			this.stringFile =  new String(this.fileBinary,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
			System.out.println("codificacion no valida");
		}
    }
    
    public byte[] getByteArray() throws IOException{
    	
    	//return new String(this.fileBinary, "UTF-8");
    	 return this.fileBinary;
    }
    public String getStringFile(){
    	try {
			return new String(this.fileBinary,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Codificacion de archivo erronea");
			System.out.println(e.getMessage());
			return "ERROR";
		}
    }
    public void setStringFile(String stringFile){
    	this.fileBinary = stringFile.getBytes();
    	DataSource dataSource = new ByteArrayDataSource(this.fileBinary, "UTF-8");
    	this.rawFile = new DataHandler(dataSource);
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

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String getInfo() {
		return super.getInfo() + " " + getNombreArchivo();
	}

}