package com.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.persistence.internal.jpa.metadata.converters.AbstractConverterMetadata;
import org.junit.Test;

import ar.fiuba.redsocedu.datalayer.ws.Usuario;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.utils.NotificacionFactory;
import com.ws.parsers.UsuarioParser;
import com.ws.pojos.Notificacion;
import com.ws.serializers.UsuarioSerializer;
import com.ws.tags.UsuarioTags;

public class PruebaConversionXML {
	
	public class XMLCalendarConverter extends AbstractSingleValueConverter {

        public boolean canConvert(Class clazz) {
        	return clazz.equals(XMLGregorianCalendarImpl.class);
        }
        
        public String toString(Object obj){
        	
        	XMLGregorianCalendarImpl fechaimp = (XMLGregorianCalendarImpl) obj;
        	return fechaimp.toXMLFormat();
        	
        }

		@Override
		public Object fromString(String arg0) {
			return null;
		}

//		@Override
//		public void marshal(Object arg0, HierarchicalStreamWriter writer,
//				MarshallingContext context) {
//			
//			XMLGregorianCalendarImpl fechaimp = (XMLGregorianCalendarImpl) arg0;
//			writer.startNode("fechaNac");
//			writer.setValue(fechaimp.toXMLFormat());
//			writer.endNode();
//		}
//
//		@Override
//		public Object unmarshal(HierarchicalStreamReader arg0,
//				UnmarshallingContext arg1) {
//			
//			return null;
//		}

}
	

	@Test
	public void test() {
		
		Usuario miusuario = new Usuario();
		
		miusuario.setActivado(true);
		miusuario.setApellido("Perez");
		miusuario.setNombre("Alfonso");
		miusuario.setEmail("ap@fiuba.edu.ar");
		miusuario.setPadron("999999");
		miusuario.setPassword("123456");
		miusuario.setHabilitado(true);
		
		XMLGregorianCalendar fecha = new XMLGregorianCalendarImpl();
		fecha.setYear(1986);
		fecha.setMonth(5);
		fecha.setDay(5);
		
		miusuario.setFechaNac(fecha);
		miusuario.setUsername("Pepe");
		miusuario.setUsuarioId(12L);
		
		
		
//		
//		
//		Usuario miusuario2 = new Usuario();
//		
//		miusuario2.setActivado(true);
//		miusuario2.setApellido("sanchez");
//		miusuario2.setNombre("Alfonso");
//		miusuario2.setEmail("ap@fiuba.edu.ar");
//		miusuario2.setPadron("999999");
//		miusuario2.setPassword("123456");
//		miusuario2.setHabilitado(true);
//		
//		XMLGregorianCalendar fecha2 = new XMLGregorianCalendarImpl();
//		fecha2.setYear(1986);
//		fecha2.setMonth(5);
//		fecha2.setDay(5);
//		
//		miusuario2.setFechaNac(fecha2);
//		miusuario2.setUsername("Pepe");
//		miusuario2.setUsuarioId(12L);
		
		
		
//		
//		//System.out.println(miusuario.getFechaNac().toXMLFormat());
//		
//		ArrayList<Usuario> listado = new ArrayList<Usuario>();
//		listado.add(miusuario);
//		listado.add(miusuario2);
//		
		
//		String s="05-05-1986T00:00:00";
//		
//		XMLGregorianCalendar result = null;
//		Date date;
//		SimpleDateFormat simpleDateFormat;
//		GregorianCalendar gregorianCalendar;
//
//		simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
//        try {
//			date = simpleDateFormat.parse(s);
//			gregorianCalendar = 
//		    (GregorianCalendar)GregorianCalendar.getInstance();
//		    gregorianCalendar.setTime(date);
//		    result = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}        
//        
//        System.out.println(result);
		
		System.out.println(UsuarioSerializer.getXMLfromPojo(miusuario));
		
				
		
		
//		XStream xstream = new XStream();
//		
////		xstream.alias("notificacion", Notificacion.class);
////		String xml = xstream.toXML(NotificacionFactory.sinResultados());		
////		System.out.println(xml);
//		
//		//El nombre de la clase (atributo raiz) sera
//		xstream.alias(UsuarioTags.CLASS_TAG, Usuario.class);
//		
//		//Mapeos de los nombres de atributo
//		xstream.aliasField(UsuarioTags.ID_TAG, Usuario.class, "usuarioId");
//		xstream.aliasField(UsuarioTags.ACTIVADO_TAG, Usuario.class, "activado");
//		xstream.aliasField(UsuarioTags.APELLIDO_TAG, Usuario.class, "apellido");
//		xstream.aliasField(UsuarioTags.EMAIL_TAG, Usuario.class, "email");
//		xstream.aliasField(UsuarioTags.FECHANAC_TAG, Usuario.class, "fechaNac");
//		xstream.aliasField(UsuarioTags.HABILITADO_TAG, Usuario.class, "habilitado");
//		xstream.aliasField(UsuarioTags.NOMBRE_TAG, Usuario.class, "nombre");
//		xstream.aliasField(UsuarioTags.PADRON_TAG, Usuario.class, "padron");
//		xstream.aliasField(UsuarioTags.PASSWORD_TAG, Usuario.class, "password");
//		xstream.aliasField(UsuarioTags.USERNANME_TAG, Usuario.class, "username");
//		
//		xstream.alias("usuario", Usuario.class);
//		xstream.alias("lista", List.class, ArrayList.class);
//		xstream.processAnnotations(Usuario.class);
//		xstream.registerConverter(new XMLCalendarConverter());
//		//xstream.alias("fechaNac", XMLGregorianCalendarImpl.class);
//		xstream.alias("fechaNac", XMLGregorianCalendar.class, XMLGregorianCalendarImpl.class);
//		//xstream.useAttributeFor(XMLGregorianCalendar.class, "fechaNac");
//		try{
//		String xml = xstream.toXML(miusuario);		
//		System.out.println(xml);
//		}catch (Exception e){
//			e.printStackTrace();
//		}
	}

}
