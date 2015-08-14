package com.haws.projects.haws.coordinator.services;

import java.io.Closeable;
import java.util.List;

import com.haws.projects.haws.coordinator.exceptions.LeadershipException;
import com.haws.projects.haws.coordinator.exceptions.NotALeaderException;
import com.haws.projects.haws.coordinator.model.Worker;

public interface LeaderRegistry extends Closeable {

	void requestLeadership() throws LeadershipException;

	boolean isLeader();

	List<Worker> getWorkers() throws NotALeaderException;

}
