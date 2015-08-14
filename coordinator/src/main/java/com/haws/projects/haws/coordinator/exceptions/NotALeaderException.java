package com.haws.projects.haws.coordinator.exceptions;

public class NotALeaderException extends Exception {

	private static final long serialVersionUID = 386908497192323376L;

	public NotALeaderException(String msg) {
		super(msg);
	}

	public NotALeaderException(String msg, Throwable t) {
		super(msg, t);
	}
}
