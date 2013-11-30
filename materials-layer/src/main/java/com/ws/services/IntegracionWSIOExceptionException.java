/**
 * IntegracionWSIOExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.ws.services;

public class IntegracionWSIOExceptionException extends java.lang.Exception {

	private static final long serialVersionUID = 1384816774741L;

	private com.ws.services.IntegracionWSStub.IntegracionWSIOException faultMessage;

	public IntegracionWSIOExceptionException() {
		super("IntegracionWSIOExceptionException");
	}

	public IntegracionWSIOExceptionException(java.lang.String s) {
		super(s);
	}

	public IntegracionWSIOExceptionException(java.lang.String s, java.lang.Throwable ex) {
		super(s, ex);
	}

	public IntegracionWSIOExceptionException(java.lang.Throwable cause) {
		super(cause);
	}

	public void setFaultMessage(com.ws.services.IntegracionWSStub.IntegracionWSIOException msg) {
		faultMessage = msg;
	}

	public com.ws.services.IntegracionWSStub.IntegracionWSIOException getFaultMessage() {
		return faultMessage;
	}
}
