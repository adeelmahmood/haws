package com.haws.projects.haws.coordinator.services;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.utils.EnsurePath;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haws.projects.haws.common.model.Worker;
import com.haws.projects.haws.coordinator.exceptions.WorkerRegistrationException;
import com.haws.projects.haws.coordinator.utils.Constants;

@Service
public class ZkWorkerRegistry implements WorkerRegistry {

	private static final Logger log = LoggerFactory.getLogger(ZkWorkerRegistry.class);

	private final CuratorFramework client;
	private final ObjectMapper mapper;

	@Autowired
	public ZkWorkerRegistry(CuratorFramework client, ObjectMapper mapper) {
		this.client = client;
		this.mapper = mapper;

		// start curator
		if (client.getState() != CuratorFrameworkState.STARTED) {
			client.start();
		}
	}

	public void registerWorker(Worker worker) throws WorkerRegistrationException {
		try {
			// set worker info
			worker.setId(InetAddress.getLocalHost().getHostAddress());
			if (StringUtils.isEmpty(worker.getName())) {
				worker.setName(InetAddress.getLocalHost().getHostName());
			}
		} catch (UnknownHostException e) {
			log.warn("error in capturing localhost info", e);
			worker.setId(System.nanoTime() + "");
		}

		// ensure workers path
		EnsurePath workersPath = client.newNamespaceAwareEnsurePath(Constants.ZK_WORKERS_PATH);
		try {
			workersPath.ensure(client.getZookeeperClient());
		} catch (Exception e) {
			log.error("error in creating workers path", e);
			throw new WorkerRegistrationException("error in creating workers path", e);
		}

		log.debug("registering worker " + worker);
		try {
			// @formatter:off
			// create worker node
			client.create()
				.withMode(CreateMode.EPHEMERAL)
				.inBackground()
				.forPath(Constants.ZK_WORKERS_PATH + "/" + worker.getId(), mapper.writeValueAsBytes(worker));
			// @formatter:on
		} catch (Exception e) {
			log.error("error in creating worker node for id " + worker.getId(), e);
			throw new WorkerRegistrationException("error in creating worker node for id " + worker.getId(), e);
		}
	}

	@Override
	public Worker getWorker(String id) {
		try {
			// check if worker node exists
			if (client.checkExists().forPath(Constants.ZK_WORKERS_PATH + "/" + id) != null) {
				byte[] data = client.getData().forPath(Constants.ZK_WORKERS_PATH + "/" + id);
				return mapper.readValue(data, Worker.class);
			}
		} catch (Exception e) {
		}
		return null;
	}
}
