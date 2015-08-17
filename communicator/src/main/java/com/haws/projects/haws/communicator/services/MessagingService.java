package com.haws.projects.haws.communicator.services;

import com.haws.projects.haws.common.model.AbstractMessage;
import com.haws.projects.haws.communicator.exceptions.MessageSendException;

public interface MessagingService {

	<T extends AbstractMessage<?>> void sendMessage(T message) throws MessageSendException;
}
