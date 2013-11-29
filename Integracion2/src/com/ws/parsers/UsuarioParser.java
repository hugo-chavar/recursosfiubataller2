package com.ws.parsers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;
import com.ws.pojos.Usuario;
import com.ws.tags.UsuarioTags;

public class UsuarioParser extends Parser {

	private Map<String, String> campos;
	
	public UsuarioParser(Document doc) {
		super(doc);
		this.inicializarCampos();
	}

	
	private Map<String, String> inicializarCampos() {
		NodeList nodes = doc.getElementsByTagName(UsuarioTags.CLASS_TAG);
		NodeList childNodes = nodes.item(0).getChildNodes(); 
		this.campos = new HashMap<String, String>();
		
	    if (childNodes != null) {
	        for (int i = 0; i < childNodes.getLength(); i++) {
        	   Element el = (Element) childNodes.item(i);
        	   this.campos.put(el.getNodeName(), el.getTextContent());
	        }
	    }
		return campos;
	}
	
	@Override
	public Map<String, String> obtenerCampos(){
		
		return this.campos;
	} 
	
	/**
	 * Este método retorna una instancia de usuario que representa el Usuario en la capa de Negocio.
	 * @return
	 */
	public Usuario getEntidadUsuario(){
		Usuario usuario = new Usuario();
	
		usuario.setUsername(this.campos.get(UsuarioTags.USERNANME_TAG));
		usuario.setPassword (this.campos.get(UsuarioTags.PASSWORD_TAG));
		usuario.setNombre(this.campos.get(UsuarioTags.NOMBRE_TAG));
		usuario.setApellido(this.campos.get(UsuarioTags.APELLIDO_TAG));
		usuario.setPadron(this.campos.get(UsuarioTags.PADRON_TAG));
		usuario.setEmail(this.campos.get(UsuarioTags.EMAIL_TAG));
		if(this.campos.get(UsuarioTags.ID_TAG) != null) {
			usuario.setUsuarioId(Long.parseLong(this.campos.get(UsuarioTags.ID_TAG)));
		}
		
		//Conversion de fecha a XMLGregorianCalendar
		XMLGregorianCalendar fecha;
		if(this.campos.get(UsuarioTags.FECHANAC_TAG) != null)
		{
			try {
				fecha = this.stringToXMLGregorianCalendar((this.campos.get(UsuarioTags.FECHANAC_TAG)));
				usuario.setFechaNac(fecha);
			} catch (ParseException e) {
					e.printStackTrace();
			} catch (DatatypeConfigurationException e) {
					e.printStackTrace();
			}
		}
		usuario.setHabilitado(Boolean.parseBoolean(this.campos.get(UsuarioTags.HABILITADO_TAG)));
		usuario.setActivado(Boolean.parseBoolean(this.campos.get(UsuarioTags.ACTIVADO_TAG)));
		
		return usuario;
		
	}
	
	public Long getIdUsuario(){
		
		return Long.parseLong(this.campos.get(UsuarioTags.ID_TAG));
	}
	
	
	private XMLGregorianCalendar stringToXMLGregorianCalendar(String s) throws ParseException, 
																		DatatypeConfigurationException {
		XMLGregorianCalendar result = null;
		Date date;
		SimpleDateFormat simpleDateFormat;
		GregorianCalendar gregorianCalendar;

		simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
        try {
			date = simpleDateFormat.parse(s);
			gregorianCalendar = 
		    (GregorianCalendar)GregorianCalendar.getInstance();
		    gregorianCalendar.setTime(date);
		    result = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
        
        return result;
	}


}
