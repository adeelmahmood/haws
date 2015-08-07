package com.haws.projects.haws.broker.flows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.haws.projects.haws.broker.MessageBroker;
import com.haws.projects.haws.broker.MessageBrokerException;
import com.haws.projects.haws.broker.MessageHolder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MessageBrokerTests {

	@Autowired
	MessageBroker<MessageHolder, String> broker;

	@Test
	public void test() throws MessageBrokerException, InterruptedException {
		MessageHolder holder = new MessageHolder();
		holder.setMessage("holder message");
		broker.send(holder, "k1");
		Thread.sleep(3000);
	}

	static class MessageAnalyzer {
		public void handle(Message<?> message) {
			System.out.println("Message headers => " + message.getHeaders());
			System.out.println("Message payload => " + message.getPayload());
		}
	}
}
