/**
 * IntegracionParserConfigurationExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package com.ws.services;

public class IntegracionParserConfigurationExceptionException extends java.lang.Exception {

	private static final long serialVersionUID = 1389458393179L;

	private com.ws.services.IntegracionStub.IntegracionParserConfigurationException faultMessage;

	public IntegracionParserConfigurationExceptionException() {
		super("IntegracionParserConfigurationExceptionException");
	}

	public IntegracionParserConfigurationExceptionException(java.lang.String s) {
		super(s);
	}

	public IntegracionParserConfigurationExceptionException(java.lang.String s, java.lang.Throwable ex) {
		super(s, ex);
	}

	public IntegracionParserConfigurationExceptionException(java.lang.Throwable cause) {
		super(cause);
	}

	public void setFaultMessage(com.ws.services.IntegracionStub.IntegracionParserConfigurationException msg) {
		faultMessage = msg;
	}

	public com.ws.services.IntegracionStub.IntegracionParserConfigurationException getFaultMessage() {
		return faultMessage;
	}
}
