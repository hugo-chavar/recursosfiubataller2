/**
 * IntegracionWSParserConfigurationExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.ws.services;

public class IntegracionWSParserConfigurationExceptionException extends java.lang.Exception {

	private static final long serialVersionUID = 1384816774764L;

	private com.ws.services.IntegracionWSStub.IntegracionWSParserConfigurationException faultMessage;

	public IntegracionWSParserConfigurationExceptionException() {
		super("IntegracionWSParserConfigurationExceptionException");
	}

	public IntegracionWSParserConfigurationExceptionException(java.lang.String s) {
		super(s);
	}

	public IntegracionWSParserConfigurationExceptionException(java.lang.String s, java.lang.Throwable ex) {
		super(s, ex);
	}

	public IntegracionWSParserConfigurationExceptionException(java.lang.Throwable cause) {
		super(cause);
	}

	public void setFaultMessage(com.ws.services.IntegracionWSStub.IntegracionWSParserConfigurationException msg) {
		faultMessage = msg;
	}

	public com.ws.services.IntegracionWSStub.IntegracionWSParserConfigurationException getFaultMessage() {
		return faultMessage;
	}
}
