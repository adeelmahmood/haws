package com.haws.projects.haws.coordinator.services;

import com.haws.projects.haws.coordinator.exceptions.WorkerRegistrationException;
import com.haws.projects.haws.coordinator.model.Worker;

public interface WorkerRegistry {

	void registerWorker(Worker worker) throws WorkerRegistrationException;

	Worker getWorker(String id);
}
