package com.haws.projects.haws.communicator.model;

import java.util.HashMap;
import java.util.Map;

public class MapMessage extends AbstractMessage<Map<String, Object>> {

	private static final long serialVersionUID = 7338426103870451822L;

	public MapMessage() {
		this.payload = new HashMap<String, Object>();
	}

	public void addItem(String key, Object value) {
		this.payload.put(key, value);
	}

	public Object getItem(String key) {
		return this.payload.get(key);
	}

	public void resetItems() {
		this.payload.clear();
	}
}
