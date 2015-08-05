package com.haws.projects.haws.services.exceptions;

public class AWSException extends Exception {

	private static final long serialVersionUID = -6571497988036757170L;

	public AWSException(String msg) {
		super(msg);
	}

	public AWSException(String msg, Throwable t) {
		super(msg, t);
	}
}
