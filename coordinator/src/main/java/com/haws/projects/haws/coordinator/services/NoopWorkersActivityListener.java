package com.haws.projects.haws.coordinator.services;

import org.springframework.stereotype.Service;

import com.haws.projects.haws.coordinator.model.Worker;

@Service
public class NoopWorkersActivityListener implements WorkersActivityListener {

	@Override
	public void workerAdded(Worker worker) {
	}

	@Override
	public void workerRemoved(Worker worker) {
	}

}
