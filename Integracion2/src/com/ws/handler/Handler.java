package com.ws.handler;

import org.w3c.dom.Document;

import ar.fiuba.redsocedu.datalayer.ws.DataService;
import ar.fiuba.redsocedu.datalayer.ws.IData;

import com.db.querys.QueryBuilder;

public abstract class Handler {
	
	QueryBuilder queryBuilder;
	DataService service;
	IData port;
	
	public Handler(){
		this.service = new DataService();
		this.port = service.getDataPort();
	}
	
	public abstract String guardarDatos(Document doc);
	public abstract String actualizarDatos(Document doc);
	public abstract String borrarDatos(Document doc);
	public abstract String seleccionarDatos(Document doc);
}
