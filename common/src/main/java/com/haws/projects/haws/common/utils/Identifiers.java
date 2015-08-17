package com.haws.projects.haws.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Identifiers {

	public static String selfHostIp() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}

	public static String selfHostName() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}

	public static String currentThread() {
		return Thread.currentThread().getName();
	}
}
