package com.haws.projects.haws.communicator;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.util.IdGenerator;

import reactor.util.UUIDUtils;

@Configuration
@ImportResource("kafka-broker.xml")
public class CommunicatorConfiguration {

	@Bean
	public IdGenerator randomUUIDGenerator() {
		return new IdGenerator() {
			@Override
			public UUID generateId() {
				return UUIDUtils.random();
			}
		};
	}
}
