package com.haws.projects.haws.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.haws.projects.haws.common.model.WorkerMessage;
import com.haws.projects.haws.communicator.broker.MessageListener;

@Service
public class ConnectorsMessageListener implements MessageListener<WorkerMessage> {

	private static final Logger log = LoggerFactory.getLogger(ConnectorsMessageListener.class);

	@Override
	public void receive(WorkerMessage message) {
		log.debug("received == " + message.getPayload() + " from " + message.getSender() + " of type "
				+ message.getType());
	}

}
