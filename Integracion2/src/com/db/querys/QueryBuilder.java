package com.db.querys;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.thoughtworks.xstream.XStream;
import com.utils.Base64Coder;



public abstract class QueryBuilder {
	
	
	/*
	 * --------------Para modificar rapido la forma en la que se envian los detached---------------------
	 * Binario o XML solo modificar la estrategia.
	 */
	
	public enum SERIALIZATIONTYPE {
		   BIN, XML
		 }
	
	private static SERIALIZATIONTYPE CodificationStrategy = SERIALIZATIONTYPE.BIN; //modificar para cambiar a XML
	
	
	protected static String getSerializedCriteria(DetachedCriteria crit){
		
		switch (CodificationStrategy) {
		case BIN:
			return getBinaryStringfromCriteria(crit);
		case XML:
			return getXMLfromCriteria(crit);
		default:
			return getXMLfromCriteria(crit);
		}
	}
	
	//-------------------
	
	private static String getXMLfromCriteria(DetachedCriteria crit){
		XStream xstream = new XStream();
		String xml = xstream.toXML(crit);		
		return xml;
	}
	
	/*
	 * Agrego la serializacion para Detached que usan ellos sino no van a funcionar
	 * las consultas en tanto no lo cambien.
	 */
    private static String getBinaryStringfromCriteria(Serializable o) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream( baos );
			oos.writeObject( o );
	        oos.close();
		} catch (IOException e) {
			
			e.printStackTrace();
			return null;
		}
        
        return new String( Base64Coder.encode( baos.toByteArray() ) );
    }
		
	public abstract String getAllById(Long id);
	public abstract String getAllByAttributes(Map<String, String> attributes);
	public abstract String removeById(Long id);
}
