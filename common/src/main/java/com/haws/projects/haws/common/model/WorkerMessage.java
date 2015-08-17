package com.haws.projects.haws.common.model;

public class WorkerMessage extends AbstractMessage<String> {

	private static final long serialVersionUID = -3246931007775662933L;

	private Worker sender;

	public Worker getSender() {
		return sender;
	}

	public void setSender(Worker sender) {
		this.sender = sender;
	}

	public WorkerMessage(String payload) {
		this.payload = payload;
	}

	public WorkerMessage() {
	}
}
