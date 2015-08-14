package com.haws.projects.haws.coordinator.services;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.utils.EnsurePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haws.projects.haws.coordinator.exceptions.LeadershipException;
import com.haws.projects.haws.coordinator.exceptions.NotALeaderException;
import com.haws.projects.haws.coordinator.model.Worker;
import com.haws.projects.haws.coordinator.utils.Constants;

@Service
public class ZkLeaderRegistry implements LeaderRegistry {

	private static final Logger log = LoggerFactory.getLogger(ZkLeaderRegistry.class);

	private volatile LeaderSelector leaderSelector;

	private final LeaderSelectorListener leaderListener = new LeaderListener();

	private final CuratorFramework client;
	private final ObjectMapper mapper;

	private volatile ConnectionState currentState;

	private PathChildrenCache workers;

	private final CountDownLatch latch = new CountDownLatch(1);

	private final List<WorkersActivityListener> workersActivityListeners;

	@Autowired
	public ZkLeaderRegistry(CuratorFramework client, ObjectMapper mapper,
			List<WorkersActivityListener> workersActivityListeners) {
		this.client = client;
		this.mapper = mapper;
		this.workersActivityListeners = workersActivityListeners;

		// start curator
		if (client.getState() != CuratorFrameworkState.STARTED) {
			client.start();
		}

		// watch for events
		client.getConnectionStateListenable().addListener(connectionListener);
		try {
			// start workers cache
			workers = new PathChildrenCache(client, Constants.ZK_WORKERS_PATH, true);
			workers.start();
			workers.getListenable().addListener(workersListener);
		} catch (Exception e) {
			log.error("error in starting workers cache", e);
		}
	}

	@Override
	public void requestLeadership() throws LeadershipException {
		try {
			// ensure leader path
			EnsurePath path = client.newNamespaceAwareEnsurePath(Constants.ZK_LEADER_PATH);
			path.ensure(client.getZookeeperClient());

			// initialize leader selector
			if (leaderSelector == null) {
				leaderSelector = new LeaderSelector(client, Constants.ZK_LEADER_PATH, leaderListener);
				leaderSelector.setId(InetAddress.getLocalHost().getHostAddress());
				leaderSelector.autoRequeue();
				leaderSelector.start();
			}
		} catch (Exception e) {
			log.error("error in requesting leadership", e);
			throw new LeadershipException("error in requesting leadership", e);
		}
	}

	@Override
	public boolean isLeader() {
		return isConnected() && (leaderSelector != null && leaderSelector.hasLeadership());
	}

	@Override
	public List<Worker> getWorkers() throws NotALeaderException {
		if (!isLeader()) {
			throw new NotALeaderException("this instance must be a leader to pull workers");
		}

		return workers.getCurrentData().stream().map(w -> {
			try {
				return mapper.readValue(w.getData(), Worker.class);
			} catch (IOException e) {
				log.error("error in retrieving workers list data", e);
			}
			return null;
		}).collect(Collectors.toList());
	}

	@Override
	public void close() {
		if (leaderSelector != null) {
			latch.countDown();
			leaderSelector.close();
			leaderSelector = null;
		}
	}

	private boolean isConnected() {
		return (currentState == ConnectionState.CONNECTED || currentState == ConnectionState.RECONNECTED);
	}

	class LeaderListener extends LeaderSelectorListenerAdapter {
		@Override
		public void takeLeadership(CuratorFramework client) throws Exception {
			log.info(">>> leadership obtained at [" + new Date() + "], this instance will do the publishing");
			// attempt to never give up leadership
			latch.await();
		}
	}

	ConnectionStateListener connectionListener = new ConnectionStateListener() {
		@Override
		public void stateChanged(CuratorFramework client, ConnectionState newState) {
			currentState = newState;
			switch (newState) {
			case CONNECTED:
			case RECONNECTED:
				log.info(">>> Curator connected event: " + newState + " current state " + currentState);
				requestLeadership();
				break;
			case LOST:
			case SUSPENDED:
				log.info(">>> Curator disconnect event: " + newState);
				close();
				break;
			case READ_ONLY:
				log.info(">>> Curator readonly event: " + newState);
				break;
			}
		}
	};

	PathChildrenCacheListener workersListener = new PathChildrenCacheListener() {
		@Override
		public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
			Worker worker = mapper.readValue(event.getData().getData(), Worker.class);
			switch (event.getType()) {
			case CHILD_ADDED:
				for (WorkersActivityListener list : workersActivityListeners) {
					list.workerAdded(worker);
				}
				break;
			case CHILD_REMOVED:
				for (WorkersActivityListener list : workersActivityListeners) {
					list.workerRemoved(worker);
				}
				break;
			default:
				log.debug("workers event " + event);
				break;
			}
		}
	};

}
