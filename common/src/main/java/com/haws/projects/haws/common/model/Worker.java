package com.haws.projects.haws.common.model;

import java.io.Serializable;

public class Worker implements Serializable {

	private static final long serialVersionUID = -4333879331392805355L;

	private String id;

	private String name;

	public Worker() {
	}

	public Worker(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Worker [id=" + id + ", name=" + name + "]";
	}
}
