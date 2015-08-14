package com.haws.projects.haws.coordinator.exceptions;

public class WorkerRegistrationException extends Exception {

	private static final long serialVersionUID = -6075715782669880358L;

	public WorkerRegistrationException(String msg) {
		super(msg);
	}

	public WorkerRegistrationException(String msg, Throwable t) {
		super(msg, t);
	}
}
