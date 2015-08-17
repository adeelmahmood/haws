package com.haws.projects.haws.communicator.broker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.haws.projects.haws.common.model.WorkerMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BrokerGatewayTests {

	@Autowired
	MessageBroker broker;

	@Autowired
	QueueChannel channel;

	@Test
	public void test() throws Exception {
		WorkerMessage message = new WorkerMessage("simple message from java");
		broker.send(message, "1");
		Message<?> received = channel.receive(3000);
		assertThat(received.getPayload().getClass(), equalTo(WorkerMessage.class));
		assertThat(((WorkerMessage) received.getPayload()).getPayload(), equalTo(message.getPayload()));
	}

}
