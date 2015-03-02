package com.journaldev.spring.exceptions;
public class ExampleException extends RuntimeException {
	private String exceptionMsg;

	public ExampleException(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
}