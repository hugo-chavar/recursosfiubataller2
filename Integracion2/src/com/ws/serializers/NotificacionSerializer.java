package com.ws.serializers;

import com.thoughtworks.xstream.XStream;
import com.ws.pojos.Notificacion;



public class NotificacionSerializer {

	public static String getXMLfromPojo(Notificacion unanotificacion ){
		
		XStream xstream = new XStream();
		
		xstream.alias("notificacion", Notificacion.class);
		String xml = xstream.toXML(unanotificacion);	
		return xml;
	}
	
}
