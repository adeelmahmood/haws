package com.haws.projects.haws.master.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ConnectorsController {

	private static final Logger log = LoggerFactory.getLogger(ConnectorsController.class);
	
	@MessageMapping("/connectors")
	@SendTo("/topic/connectors")
	public void connectors() {
		log.debug("started connectors websocket");
		
		
	}
}
