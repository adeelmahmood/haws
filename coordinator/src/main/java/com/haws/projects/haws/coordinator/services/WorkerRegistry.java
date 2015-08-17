package com.haws.projects.haws.coordinator.services;

import com.haws.projects.haws.common.model.Worker;
import com.haws.projects.haws.coordinator.exceptions.WorkerRegistrationException;

public interface WorkerRegistry {

	void registerWorker(Worker worker) throws WorkerRegistrationException;

	Worker getWorker(String id);
}
