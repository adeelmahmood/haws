package com.haws.projects.haws.communicator.broker;

import java.io.Serializable;

import com.haws.projects.haws.communicator.model.AbstractMessage;

public interface MessageBroker {

	<T extends AbstractMessage<?>, ID extends Serializable> void send(T message, ID key);
}
