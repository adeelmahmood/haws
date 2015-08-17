package com.haws.projects.haws.coordinator.services;

import java.util.Arrays;

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
import com.haws.projects.haws.coordinator.exceptions.NotALeaderException;
import com.haws.projects.haws.coordinator.exceptions.WorkerRegistrationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@IntegrationTest({ "zk.host: localhost:2181" })
public class ZkLeaderRegistryTests extends ZkBaseTestClass {

	@Configuration
	@ComponentScan("com.haws.projects.haws.coordinator")
	@EnableAutoConfiguration
	static class Config {
	}

	@Autowired
	LeaderRegistry registry;
	@Autowired
	WorkerRegistry workerRegistry;

	@Test
	public void test() throws InterruptedException, WorkerRegistrationException, NotALeaderException {
		registry.requestLeadership();
		Worker w = new Worker("master");
		workerRegistry.registerWorker(w);
		Thread.sleep(3000);

		System.out.println("found worker " + workerRegistry.getWorker(w.getId()));

		System.out.println("is leader " + registry.isLeader());
		System.out.println("workers ==> " + Arrays.toString(registry.getWorkers().toArray()));
		Thread.sleep(1000);
	}

}
