package com.haws.projects.haws.coordinator.exceptions;

public class LeadershipException extends RuntimeException {

	private static final long serialVersionUID = -8324002547810372704L;

	public LeadershipException(String msg) {
		super(msg);
	}

	public LeadershipException(String msg, Throwable t) {
		super(msg, t);
	}
}
