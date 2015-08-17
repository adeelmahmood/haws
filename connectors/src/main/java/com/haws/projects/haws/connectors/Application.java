package com.haws.projects.haws.connectors;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import com.haws.projects.haws.common.model.Worker;
import com.haws.projects.haws.connectors.services.BucketService;
import com.haws.projects.haws.coordinator.exceptions.WorkerRegistrationException;
import com.haws.projects.haws.coordinator.services.WorkerRegistry;

@Configuration
@ComponentScan({ "com.haws.projects.haws.connectors", "com.haws.projects.haws.coordinator",
		"com.haws.projects.haws.communicator" })
@EnableAutoConfiguration(exclude = { IntegrationAutoConfiguration.class, JmxAutoConfiguration.class,
		VelocityAutoConfiguration.class })
@PropertySource("classpath:aws-credentials.properties")
@ImportResource({ "classpath:aws.xml", "classpath:s3flows/file.xml" })
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(false).logStartupInfo(false).run(args);
	}

	@Autowired
	BucketService service;

	@Value("${s3.bucket}")
	String bucket;

	@Autowired
	WorkerRegistry registry;

	@PostConstruct
	public void init() throws UnknownHostException, WorkerRegistrationException {
		// register this instance as worker
		registry.registerWorker(new Worker("haws-connector-" + InetAddress.getLocalHost().getHostName()));

		// create S3 bucket
		service.createBucket(bucket);
	}
}
