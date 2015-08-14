package com.haws.projects.haws.coordinator.model;

public class Worker {

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
