package com.haws.projects.haws.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.haws.projects.haws.communicator.broker.MessageListener;
import com.haws.projects.haws.communicator.model.StringMessage;

@Service
public class ConnectorsMessageListener implements MessageListener<StringMessage> {

	private static final Logger log = LoggerFactory.getLogger(ConnectorsMessageListener.class);

	@Override
	public void receive(StringMessage message) {
		log.debug("received == " + message.getPayload() + " from " + message.getSender() + " of type "
				+ message.getType());
	}

}
