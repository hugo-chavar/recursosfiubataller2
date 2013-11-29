package com.ws.handler;


import java.util.Map;
import javax.xml.datatype.XMLGregorianCalendar;
import org.w3c.dom.Document;

import ar.fiuba.redsocedu.datalayer.ws.Usuario;

import com.db.querys.UsuarioQueryBuilder;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import com.utils.NotificacionFactory;
import com.ws.parsers.UsuarioParser;
import com.ws.serializers.NotificacionSerializer;
import com.ws.serializers.UsuarioSerializer;


public class UsuarioHandlerMock extends Handler {
	
	
	public UsuarioHandlerMock() {
		super();
		this.queryBuilder = new UsuarioQueryBuilder();
	}
	
	@Override
	public String guardarDatos(Document doc) {
		return NotificacionSerializer.getXMLfromPojo(NotificacionFactory.Exito());	
	}

	@Override
	public String actualizarDatos(Document doc) {	
		return NotificacionSerializer.getXMLfromPojo(NotificacionFactory.Exito());
	}

	@Override
	public String borrarDatos(Document doc) {

		return NotificacionSerializer.getXMLfromPojo(NotificacionFactory.Exito());
	}

	@Override
	public String seleccionarDatos(Document doc) {
		
		
		Usuario miusuario = new Usuario();
		
		miusuario.setActivado(true);
		miusuario.setApellido("Perez");
		miusuario.setNombre("Alfonso");
		miusuario.setEmail("ap@fiuba.edu.ar");
		miusuario.setPadron("1234");
		miusuario.setPassword("123456");
		miusuario.setHabilitado(true);
		
		XMLGregorianCalendar fecha = new XMLGregorianCalendarImpl();
		fecha.setYear(1986);
		fecha.setMonth(5);
		fecha.setDay(5);
		
		miusuario.setFechaNac(fecha);
		miusuario.setUsername("Pepe");
		miusuario.setUsuarioId(12L);
		
		return UsuarioSerializer.getXMLfromPojo(miusuario);
	}
	
//	@Override
//	public String seleccionarDatos(Document doc) {
//		UsuarioParser parser = new UsuarioParser(doc);
//		Usuario miusuario  = parser.getEntidadUsuario();
//
//		miusuario.setEmail("ap@fiuba.edu.ar");
//		miusuario.setPadron("1234");
//		
//		XMLGregorianCalendar fecha = new XMLGregorianCalendarImpl();
//		fecha.setYear(1986);
//		fecha.setMonth(5);
//		fecha.setDay(5);
//		
//		miusuario.setFechaNac(fecha);
//		miusuario.setUsuarioId(12L);
//		
//		return UsuarioSerializer.getXMLfromPojo(miusuario);
//	}
	
	
	protected Map<String, String> getCampos(Document doc) {
		UsuarioParser parser = new UsuarioParser(doc);
		return parser.obtenerCampos();
	}

}
