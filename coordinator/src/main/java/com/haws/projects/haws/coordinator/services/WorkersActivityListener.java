package com.haws.projects.haws.coordinator.services;

import com.haws.projects.haws.coordinator.model.Worker;

public interface WorkersActivityListener {

	void workerAdded(Worker worker);

	void workerRemoved(Worker worker);
}
