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

import com.haws.projects.haws.common.model.WorkerMessage;
import com.haws.projects.haws.communicator.broker.MessageListener;
import com.haws.projects.haws.communicator.exceptions.MessageSendException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@IntegrationTest({ "kafka.broker.list: localhost:9092", "kafka.topic: haws-broker", "zk.host: localhost:2181" })
public class MessagingServiceTests {

	@Configuration
	@ComponentScan("com.haws.projects.haws.communicator")
	@EnableAutoConfiguration
	static class Config {
		@Bean
		public WorkerMessageListener listener() {
			return new WorkerMessageListener();
		}
	}

	@Autowired
	MessagingService service;
	@Autowired
	WorkerMessageListener listener;

	@Test
	public void test() throws MessageSendException, InterruptedException {
		for (int i = 1; i <= 5; i++) {
			service.sendMessage(new WorkerMessage("string message " + i));
		}
		Thread.sleep(5000);

		for (int i = 6; i <= 10; i++) {
			service.sendMessage(new WorkerMessage("string message " + i));
		}
		Thread.sleep(10000);
	}

	static class WorkerMessageListener implements MessageListener<WorkerMessage> {

		@Override
		public void receive(WorkerMessage message) {
			System.out.println("\nReceieved => " + message.getPayload() + "\n");
		}
	}
}
