package com.ws.serializers;

import java.io.StringWriter;
import java.util.Collection;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.ws.pojos.Cartelera;
import com.ws.tags.CarteleraTags;

public class CarteleraSerializer {

	//TODO  Cambiar la clase del POJO
	
	protected static void setAttributeMappings(XStream xstream) {
		
		//El nombre de la clase (atributo raiz) sera
		xstream.alias(CarteleraTags.CLASS_TAG, Cartelera.class);
		
		//Mapeos de los nombres de atributo
		xstream.aliasField(CarteleraTags.ID_TAG, Cartelera.class, "id");
		xstream.aliasField(CarteleraTags.NOMBRE_TAG, Cartelera.class, "nombre");
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
