package com.ws.pojos;

/**
 * 
 * numero: 
 * 	0 Operacion no permitida 
 * 	1 Error al realizar la operacion
 *  2 Operacion correcta
 *  3 Sin resultados
 * @author zeke
 *
 */
public class Notificacion {

	int numero;
	String mensaje;
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Notificacion(int numero, String mensaje) {
		super();
		this.numero = numero;
		this.mensaje = mensaje;
	}

	
}
