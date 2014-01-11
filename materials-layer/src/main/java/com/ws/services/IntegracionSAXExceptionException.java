/**
 * IntegracionSAXExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.ws.services;

public class IntegracionSAXExceptionException extends java.lang.Exception {

	private static final long serialVersionUID = 1389458393199L;

	private com.ws.services.IntegracionStub.IntegracionSAXException faultMessage;

	public IntegracionSAXExceptionException() {
		super("IntegracionSAXExceptionException");
	}

	public IntegracionSAXExceptionException(java.lang.String s) {
		super(s);
	}

	public IntegracionSAXExceptionException(java.lang.String s, java.lang.Throwable ex) {
		super(s, ex);
	}

	public IntegracionSAXExceptionException(java.lang.Throwable cause) {
		super(cause);
	}

	public void setFaultMessage(com.ws.services.IntegracionStub.IntegracionSAXException msg) {
		faultMessage = msg;
	}

	public com.ws.services.IntegracionStub.IntegracionSAXException getFaultMessage() {
		return faultMessage;
	}
}
