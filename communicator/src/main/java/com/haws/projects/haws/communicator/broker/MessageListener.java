package com.haws.projects.haws.communicator.broker;

import com.haws.projects.haws.common.model.AbstractMessage;

public interface MessageListener<T extends AbstractMessage<?>> {

	void receive(T message);
}
