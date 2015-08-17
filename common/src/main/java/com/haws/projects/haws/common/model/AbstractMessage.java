package com.haws.projects.haws.common.model;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractMessage<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	protected UUID id;

	protected MessageType type;

	protected Map<String, Object> headers;

	protected T payload;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public Map<String, Object> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, Object> headers) {
		this.headers = headers;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	public enum MessageType {
		LOG, STATUS, INFO;
	}
}
