package com.haws.projects.haws.communicator.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.haws.projects.haws.communicator.broker.MessageListener;
import com.haws.projects.haws.communicator.exceptions.MessageSendException;
import com.haws.projects.haws.communicator.model.StringMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@IntegrationTest({ "kafka.broker.list: localhost:9092", "kafka.topic: haws-broker", "zk.host: localhost:2181" })
public class MessagingServiceTests {

	@Configuration
	@ComponentScan("com.haws.projects.haws.communicator")
	@EnableAutoConfiguration
	static class Config {
		@Bean
		public StringMessageListener listener() {
			return new StringMessageListener();
		}
	}

	@Autowired
	MessagingService service;
	@Autowired
	StringMessageListener listener;

	@Test
	public void test() throws MessageSendException, InterruptedException {
		for (int i = 1; i <= 5; i++) {
			service.sendMessage(new StringMessage("string message " + i));
		}
		Thread.sleep(5000);

		for (int i = 6; i <= 10; i++) {
			service.sendMessage(new StringMessage("string message " + i));
		}
		Thread.sleep(10000);
	}

	static class StringMessageListener implements MessageListener<StringMessage> {

		@Override
		public void receive(StringMessage message) {
			System.out.println("\nReceieved => " + message.getPayload() + "\n");
		}
	}
}
