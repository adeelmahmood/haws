package com.haws.projects.haws.communicator.broker;

import org.springframework.stereotype.Service;

import com.haws.projects.haws.communicator.model.AbstractMessage;

@Service
public class NoopMessageListener implements MessageListener<AbstractMessage<?>> {

	@Override
	public void receive(AbstractMessage<?> message) {
	}

}
