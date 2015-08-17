package com.haws.projects.haws.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.haws.projects.haws.common.model.Worker;
import com.haws.projects.haws.coordinator.services.WorkersActivityListener;

@Service
public class ConnectorsActivityListener implements WorkersActivityListener {

	private static final Logger log = LoggerFactory.getLogger(ConnectorsActivityListener.class);

	@Override
	public void workerAdded(Worker worker) {
		log.debug("worker added == " + worker);
	}

	@Override
	public void workerRemoved(Worker worker) {
		log.debug("worker removed == " + worker);
	}

}
