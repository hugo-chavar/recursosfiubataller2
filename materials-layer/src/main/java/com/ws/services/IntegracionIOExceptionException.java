/**
 * IntegracionIOExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.ws.services;

public class IntegracionIOExceptionException extends java.lang.Exception {

	private static final long serialVersionUID = 1389458393189L;

	private com.ws.services.IntegracionStub.IntegracionIOException faultMessage;

	public IntegracionIOExceptionException() {
		super("IntegracionIOExceptionException");
	}

	public IntegracionIOExceptionException(java.lang.String s) {
		super(s);
	}

	public IntegracionIOExceptionException(java.lang.String s, java.lang.Throwable ex) {
		super(s, ex);
	}

	public IntegracionIOExceptionException(java.lang.Throwable cause) {
		super(cause);
	}

	public void setFaultMessage(com.ws.services.IntegracionStub.IntegracionIOException msg) {
		faultMessage = msg;
	}

	public com.ws.services.IntegracionStub.IntegracionIOException getFaultMessage() {
		return faultMessage;
	}
}
