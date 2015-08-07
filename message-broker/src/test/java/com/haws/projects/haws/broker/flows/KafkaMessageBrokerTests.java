package com.haws.projects.haws.broker.flows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.haws.projects.haws.broker.MessageBroker;
import com.haws.projects.haws.broker.MessageHolder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@IntegrationTest({ "kafka.broker.list: localhost:9092", "kafka.topic: haws-broker", "zk.host: localhost:2181" })
public class KafkaMessageBrokerTests {

	@Configuration
	@ImportResource("kafka-broker.xml")
	@ComponentScan("com.haws.projects.broker")
	@EnableAutoConfiguration
	static class Config {
	}

	@Autowired
	MessageBroker<MessageHolder, String> broker;

	@Test
	public void test() throws Exception {
		MessageHolder holder = new MessageHolder();
		holder.setMessage("holder message");

		broker.send(holder, "k1");
		Thread.sleep(6000);
	}
}
