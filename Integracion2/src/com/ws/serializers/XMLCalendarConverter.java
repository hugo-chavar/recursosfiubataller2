package com.ws.serializers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

/*
 * Esta clase permite manipular la forma en la que el Xstream arma el xml
 * En este caso el XMLGregorianblahblah tiene que ajustarse a la fecha necesria
 * por eso se manipula su salida.
 */
public class XMLCalendarConverter extends AbstractSingleValueConverter {

	@SuppressWarnings("rawtypes")
    public boolean canConvert(Class clazz) {
    	return clazz.equals(XMLGregorianCalendarImpl.class);
    }
    
    public String toString(Object obj){
    	
    	if (obj == null) return "";
    	
    	XMLGregorianCalendarImpl fechaimp = (XMLGregorianCalendarImpl) obj;
    	
    	Calendar calendar = fechaimp.toGregorianCalendar();
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
    	String dateString = formatter.format(calendar.getTime());
    	
    	return dateString;
    	
    }

	@Override
	public Object fromString(String arg0) {
		return null;
	}
}