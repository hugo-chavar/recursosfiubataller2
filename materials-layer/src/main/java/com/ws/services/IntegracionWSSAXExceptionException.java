/**
 * IntegracionWSSAXExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.ws.services;

public class IntegracionWSSAXExceptionException extends java.lang.Exception {

	private static final long serialVersionUID = 1384816774717L;

	private com.ws.services.IntegracionWSStub.IntegracionWSSAXException faultMessage;

	public IntegracionWSSAXExceptionException() {
		super("IntegracionWSSAXExceptionException");
	}

	public IntegracionWSSAXExceptionException(java.lang.String s) {
		super(s);
	}

	public IntegracionWSSAXExceptionException(java.lang.String s, java.lang.Throwable ex) {
		super(s, ex);
	}

	public IntegracionWSSAXExceptionException(java.lang.Throwable cause) {
		super(cause);
	}

	public void setFaultMessage(com.ws.services.IntegracionWSStub.IntegracionWSSAXException msg) {
		faultMessage = msg;
	}

	public com.ws.services.IntegracionWSStub.IntegracionWSSAXException getFaultMessage() {
		return faultMessage;
	}
}
