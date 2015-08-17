package com.haws.projects.haws.connectors.support;

import java.io.File;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.haws.projects.haws.common.model.AbstractMessage.MessageType;
import com.haws.projects.haws.common.model.Worker;
import com.haws.projects.haws.common.model.WorkerMessage;
import com.haws.projects.haws.common.utils.Identifiers;
import com.haws.projects.haws.communicator.exceptions.MessageSendException;
import com.haws.projects.haws.communicator.services.MessagingService;

@Component
public class ProgressReporter {

	private static final Logger log = LoggerFactory.getLogger(ProgressReporter.class);

	private final MessagingService service;

	@Autowired
	public ProgressReporter(MessagingService service) {
		this.service = service;
	}

	public void onUploadStarted(File file) {
		log.debug("reporting upload started for file " + file.getName());

		// construct a message to report status for this file
		WorkerMessage message = new WorkerMessage("Upload started for " + file.getPath());
		try {
			Worker worker = new Worker();
			worker.setName("haws-connector-" + Identifiers.selfHostName() + "@" + Identifiers.currentThread());
			message.setSender(worker);
		} catch (UnknownHostException e) {
			log.warn("unable to obtain worker information", e);
		}
		message.setType(MessageType.INFO);

		try {
			// send the message
			service.sendMessage(message);
		} catch (MessageSendException e) {
			log.error("error in sending message", e);
		}
	}
}
