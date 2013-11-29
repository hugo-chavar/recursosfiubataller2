package com.utils;

import com.ws.pojos.Notificacion;

public class NotificacionFactory {

	public static Notificacion sinResultados(){
		return new Notificacion(3, "La consulta no produjo resultados");
	}
	
	public static Notificacion Error(){
		return new Notificacion(1, "Hubo un error al realizar la operación solicitada");
	}
	
	public static Notificacion Exito(){
		return new Notificacion(2, "Su solicitud fue procesada con éxito");
	}
	
	public static Notificacion OperacionInvalida(){
		return new Notificacion(0, "La operacion que trata de realizar es inválida");
	}
	
}
