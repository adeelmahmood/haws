package com.haws.projects.haws.coordinator.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.haws.projects.haws.common.model.Worker;
import com.haws.projects.haws.coordinator.ZkBaseTestClass;
import com.haws.projects.haws.coordinator.exceptions.WorkerRegistrationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@IntegrationTest({ "zk.host: localhost:2181" })
public class ZkWorkerRegistryTests extends ZkBaseTestClass {

	@Configuration
	@ComponentScan("com.haws.projects.haws.coordinator")
	@EnableAutoConfiguration
	static class Config {
	}

	@Autowired
	WorkerRegistry registry;

	@Test
	public void testRegisterWorker() throws WorkerRegistrationException, InterruptedException {

		Worker worker = new Worker("test worker");

		registry.registerWorker(worker);
		Thread.sleep(3000);

		Worker w = registry.getWorker(worker.getId());
		System.out.println("worker " + w);
	}
}
