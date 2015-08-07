package com.haws.projects.haws.broker;

public class MessageBrokerException extends Exception {

	private static final long serialVersionUID = -2784596795013805023L;

	public MessageBrokerException(String msg) {
		super(msg);
	}

	public MessageBrokerException(String msg, Throwable t) {
		super(msg, t);
	}
}
