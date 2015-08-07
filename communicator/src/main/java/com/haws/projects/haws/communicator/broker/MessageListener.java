package com.haws.projects.haws.communicator.broker;

import com.haws.projects.haws.communicator.model.AbstractMessage;

public interface MessageListener<T extends AbstractMessage<?>> {

	void receive(T message);
}
