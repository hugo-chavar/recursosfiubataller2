package com.ws.serializers;

import java.io.StringWriter;
import java.util.Collection;

import javax.xml.datatype.XMLGregorianCalendar;
import ar.fiuba.redsocedu.datalayer.ws.Usuario;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.ws.tags.UsuarioTags;

public class UsuarioSerializer {
	
	protected static void setAttributeMappings(XStream xstream) {
		
		//El nombre de la clase (atributo raiz) sera
		xstream.alias(UsuarioTags.CLASS_TAG, Usuario.class);
		
		//Mapeos de los nombres de atributo
		xstream.aliasField(UsuarioTags.ID_TAG, Usuario.class, "usuarioId");
		xstream.aliasField(UsuarioTags.ACTIVADO_TAG, Usuario.class, "activado");
		xstream.aliasField(UsuarioTags.APELLIDO_TAG, Usuario.class, "apellido");
		xstream.aliasField(UsuarioTags.EMAIL_TAG, Usuario.class, "email");
		xstream.aliasField(UsuarioTags.FECHANAC_TAG, Usuario.class, "fechaNac");
		xstream.aliasField(UsuarioTags.HABILITADO_TAG, Usuario.class, "habilitado");
		xstream.aliasField(UsuarioTags.NOMBRE_TAG, Usuario.class, "nombre");
		xstream.aliasField(UsuarioTags.PADRON_TAG, Usuario.class, "padron");
		xstream.aliasField(UsuarioTags.PASSWORD_TAG, Usuario.class, "password");
		xstream.aliasField(UsuarioTags.USERNANME_TAG, Usuario.class, "username");
		
		//alias implementacion interfaz
		xstream.alias("fechaNac", XMLGregorianCalendar.class, XMLGregorianCalendarImpl.class);
		
		//Conversor de la clase XMLGregorian para que devuelva la fecha en el formato indicado
		xstream.registerConverter(new XMLCalendarConverter());
	}
	
	private static String addSuperTags(String body) {
		
		return "<WS>"+body+"</WS>";
	}

	public static String getXMLfromPojo(Object dto) {
		XStream xstream = new XStream();
		setAttributeMappings(xstream);
		
		StringWriter sw = new StringWriter();
		xstream.marshal(dto, new CompactWriter(sw));
		
		//String xml = xstream.toXML(dto);		
		return addSuperTags(sw.toString());
	}

	public static String getXMLfromPojo(Collection<?> dtos) {
		XStream xstream = new XStream();
		setAttributeMappings(xstream);
		String xml = xstream.toXML(dtos);		
		return addSuperTags(xml);
	}
		
}
