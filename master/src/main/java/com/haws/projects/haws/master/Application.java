package com.haws.projects.haws.master;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.haws.projects.haws.coordinator.exceptions.WorkerRegistrationException;
import com.haws.projects.haws.coordinator.services.LeaderRegistry;

@Configuration
@ComponentScan({ "com.haws.projects.haws.master", "com.haws.projects.haws.coordinator",
		"com.haws.projects.haws.communicator" })
@EnableAutoConfiguration(exclude = { IntegrationAutoConfiguration.class, JmxAutoConfiguration.class,
		VelocityAutoConfiguration.class })
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).logStartupInfo(false).run(args);
	}

	@Autowired
	LeaderRegistry registry;

	@PostConstruct
	public void init() throws InterruptedException, WorkerRegistrationException {
		registry.requestLeadership();
	}
}
