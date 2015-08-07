package com.haws.projects.haws.communicator.exceptions;

public class MessageSendException extends Exception {

	private static final long serialVersionUID = -5608033370839165419L;

	public MessageSendException(String msg) {
		super(msg);
	}

	public MessageSendException(String msg, Throwable t) {
		super(msg, t);
	}
}
