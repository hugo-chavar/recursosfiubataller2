package com.ws.serializers;



import java.io.StringWriter;
import java.util.Collection;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.ws.pojos.Muro;
import com.ws.tags.MuroTags;



//TODO  Cambiar la clase del POJO


public class MuroSerializer {
	
	
	protected static void setAttributeMappings(XStream xstream) {
		
		//El nombre de la clase (atributo raiz) sera
		xstream.alias(MuroTags.CLASS_TAG, Muro.class);
		
		//Mapeos de los nombres de atributo
		xstream.aliasField(MuroTags.ID_TAG, Muro.class, "id");
		xstream.aliasField(MuroTags.NOMBRE_TAG, Muro.class, "nombre");
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
