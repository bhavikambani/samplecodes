package com.bhavik.misc.log4j2.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HelloWorld {
	private static final Logger logger = LogManager.getLogger("databaseAppender");

	public static void main(String args[]) throws InterruptedException {
		logger.trace("Trace Message!");
		logger.debug("Debug Message!");
		logger.info("Info Message!");
		logger.warn("Warn Message!");
		logger.error("Error Message!");
		logger.fatal("Fatal Message!");
	}
}