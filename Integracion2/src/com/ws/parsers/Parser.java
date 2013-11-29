package com.ws.parsers;

import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.db.querys.QueryBuilder;

public abstract class Parser {
	
	private static String JOIN_TAG = "join";
	private static String SELECT_TAG = "select";
	Document doc;
	QueryBuilder queryBuilder;
	
	public Parser(Document doc) {
		this.doc = doc;
	}
	
	public Boolean esJoin() {
		NodeList joinNode = this.doc.getElementsByTagName(JOIN_TAG);
		return (joinNode != null && joinNode.getLength() > 0);
	}
	
	public Boolean esSelect() {
		NodeList joinNode = this.doc.getElementsByTagName(SELECT_TAG);
		return (joinNode != null && joinNode.getLength() > 0);		
	}
	
	public abstract Map<String, String> obtenerCampos(); 
}
