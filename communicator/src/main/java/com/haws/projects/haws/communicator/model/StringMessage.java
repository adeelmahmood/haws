package com.haws.projects.haws.communicator.model;

public class StringMessage extends AbstractMessage<String> {

	private static final long serialVersionUID = 5274530210562799701L;

	public StringMessage(String message) {
		this.payload = message;
	}

	@Override
	public String toString() {
		return payload;
	}
}
