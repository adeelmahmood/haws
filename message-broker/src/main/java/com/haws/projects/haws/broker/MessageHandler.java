package com.haws.projects.haws.broker;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageHandler {

	@Value("${kafka.topic}")
	private String topic;

	public void handle(Message<?> message) {
		System.out.println("headers ==> " + message.getHeaders());
		System.out.println("message ==> " + message.getPayload());
		System.out.println("message ==> " + message.getPayload().getClass());

		Map<Object, Object> d = (Map<Object, Object>) message.getPayload();
		Map<Object, Object> x = (Map<Object, Object>) d.get(topic);
		System.out.println(x.entrySet().iterator().next().getValue());
		System.out.println(x.entrySet().iterator().next().getValue().getClass());
		List<Object> l = (List<Object>) x.entrySet().iterator().next().getValue();
		MessageHolder h = (MessageHolder) l.get(0);
		System.out.println(h.getMessage());
	}
}
