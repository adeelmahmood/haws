package com.haws.projects.haws.broker;

import java.io.Serializable;

public interface MessageBroker<T extends MessageHolder, ID extends Serializable> {

	void send(T message, ID key) throws MessageBrokerException;
}
