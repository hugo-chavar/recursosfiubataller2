/**
 * IntegracionWSCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.ws.services;

/**
 * IntegracionWSCallbackHandler Callback class, Users can extend this class and
 * implement their own receiveResult and receiveError methods.
 */
public abstract class IntegracionWSCallbackHandler {

	protected Object clientData;

	/**
	 * User can pass in any object that needs to be accessed once the
	 * NonBlocking Web service call is finished and appropriate method of this
	 * CallBack is called.
	 * 
	 * @param clientData
	 *            Object mechanism by which the user can pass in user data that
	 *            will be avilable at the time this callback is called.
	 */
	public IntegracionWSCallbackHandler(Object clientData) {
		this.clientData = clientData;
	}

	/**
	 * Please use this constructor if you don't want to set any clientData
	 */
	public IntegracionWSCallbackHandler() {
		this.clientData = null;
	}

	/**
	 * Get the client data
	 */

	public Object getClientData() {
		return clientData;
	}

	/**
	 * auto generated Axis2 call back method for eliminarDatos method override
	 * this method for handling normal response from eliminarDatos operation
	 */
	public void receiveResulteliminarDatos(com.ws.services.IntegracionWSStub.EliminarDatosResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from eliminarDatos operation
	 */
	public void receiveErroreliminarDatos(java.lang.Exception e) {
	}

	/**
	 * auto generated Axis2 call back method for actualizarDatos method override
	 * this method for handling normal response from actualizarDatos operation
	 */
	public void receiveResultactualizarDatos(com.ws.services.IntegracionWSStub.ActualizarDatosResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from actualizarDatos operation
	 */
	public void receiveErroractualizarDatos(java.lang.Exception e) {
	}

	/**
	 * auto generated Axis2 call back method for seleccionarDatos method
	 * override this method for handling normal response from seleccionarDatos
	 * operation
	 */
	public void receiveResultseleccionarDatos(com.ws.services.IntegracionWSStub.SeleccionarDatosResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from seleccionarDatos operation
	 */
	public void receiveErrorseleccionarDatos(java.lang.Exception e) {
	}

	/**
	 * auto generated Axis2 call back method for guardarDatos method override
	 * this method for handling normal response from guardarDatos operation
	 */
	public void receiveResultguardarDatos(com.ws.services.IntegracionWSStub.GuardarDatosResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from guardarDatos operation
	 */
	public void receiveErrorguardarDatos(java.lang.Exception e) {
	}

	/**
	 * auto generated Axis2 call back method for getXMLDocument method override
	 * this method for handling normal response from getXMLDocument operation
	 */
	public void receiveResultgetXMLDocument(com.ws.services.IntegracionWSStub.GetXMLDocumentResponse result) {
	}

	/**
	 * auto generated Axis2 Error handler override this method for handling
	 * error response from getXMLDocument operation
	 */
	public void receiveErrorgetXMLDocument(java.lang.Exception e) {
	}

}
