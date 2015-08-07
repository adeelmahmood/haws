package com.haws.projects.haws.communicator.broker;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.haws.projects.haws.communicator.model.AbstractMessage;

@Component
public class MessageHandler<T extends AbstractMessage<?>> {

	@Value("${kafka.topic}")
	private String topic;

	private final List<MessageListener<T>> listeners;

	@Autowired
	public MessageHandler(List<MessageListener<T>> listeners) {
		this.listeners = listeners;
	}

	public void handle(Message<?> message) {
		T msg = parseMessage(message);
		if (msg == null) {
			return;
		}

		// send message receive notification to all listeners
		for (MessageListener<T> listener : listeners) {
			listener.receive(msg);
		}
	}

	@SuppressWarnings("unchecked")
	private T parseMessage(Message<?> message) {
		Map<String, Object> data = (Map<String, Object>) message.getPayload();
		Map<String, Object> dat = (Map<String, Object>) data.get(topic);
		List<Object> da = (List<Object>) dat.entrySet().iterator().next().getValue();
		return (T) da.get(0);
	}
}
