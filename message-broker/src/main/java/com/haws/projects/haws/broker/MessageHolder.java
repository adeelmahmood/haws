package com.haws.projects.haws.broker;

import java.io.Serializable;

public class MessageHolder implements Serializable {

	private static final long serialVersionUID = -5811934560107441002L;

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}