package com.haws.projects.haws.communicator.services;

import com.haws.projects.haws.communicator.exceptions.MessageSendException;
import com.haws.projects.haws.communicator.model.AbstractMessage;

public interface MessagingService {

	<T extends AbstractMessage<?>> void sendMessage(T message) throws MessageSendException;
}
