package com.haws.projects.haws.communicator.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

import com.haws.projects.haws.common.model.AbstractMessage;
import com.haws.projects.haws.communicator.broker.MessageBroker;
import com.haws.projects.haws.communicator.exceptions.MessageSendException;

@Service
public class DefaultMessagingService implements MessagingService {

	private static final Logger log = LoggerFactory.getLogger(DefaultMessagingService.class);

	private final IdGenerator idGenerator;
	private final MessageBroker broker;

	@Autowired
	public DefaultMessagingService(IdGenerator idGenerator, MessageBroker broker) {
		this.idGenerator = idGenerator;
		this.broker = broker;
	}

	@Override
	public <T extends AbstractMessage<?>> void sendMessage(T message) throws MessageSendException {
		message.setId(idGenerator.generateId());

		try {
			// send the message using broker service
			broker.send(message, message.getId().toString());
		} catch (Exception e) {
			log.error("error in sending message", e);
			throw new MessageSendException("error in sending message", e);
		}
	}

}
