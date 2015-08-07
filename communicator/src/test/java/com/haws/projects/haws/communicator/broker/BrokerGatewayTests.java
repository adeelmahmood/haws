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

import com.haws.projects.haws.communicator.model.MapMessage;
import com.haws.projects.haws.communicator.model.StringMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BrokerGatewayTests {

	@Autowired
	MessageBroker broker;

	@Autowired
	QueueChannel channel;

	@Test
	public void test() throws Exception {
		StringMessage message = new StringMessage("simple message from java");
		broker.send(message, "1");
		Message<?> received = channel.receive(3000);
		assertThat(received.getPayload().getClass(), equalTo(StringMessage.class));
		assertThat(((StringMessage) received.getPayload()).getPayload(), equalTo(message.getPayload()));

		MapMessage mapMessage = new MapMessage();
		mapMessage.addItem("name", "adeel");
		broker.send(mapMessage, "1");
		Message<?> receivedMap = channel.receive(3000);
		assertThat(receivedMap.getPayload().getClass(), equalTo(MapMessage.class));
		assertThat(((MapMessage) receivedMap.getPayload()).getItem("name"), equalTo(mapMessage.getItem("name")));
	}

}
