package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.activation.DataHandler;

public class Archivo extends Recurso {

	public Archivo(Integer idRecurso, Integer idAmbiente, String descripcion) {
		super(idRecurso, idAmbiente, descripcion);
		this.tipo = "Archivo";
		// TODO Auto-generated constructor stub
	}

	public Archivo() {
		this.tipo = "Archivo";
	}

	// private String path;

	private Integer size;

	private String tipoArchivo;

	private String nombreArchivo;

	private DataHandler rawFile;

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

	public void setRawFile(DataHandler file) {
		this.rawFile = file;

		// pruebo grabar el archivo recibido
		// TODO: solo para pruebas.. dejar comentado

//		try {
//			File tempFile = File.createTempFile("123probando", ".txt");
//			FileOutputStream fos = new FileOutputStream(tempFile);
//			rawFile.writeTo(fos);
//			fos.flush();
//			fos.close();
//			System.out.println("Saved response to file : " + tempFile.getAbsolutePath());
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
}