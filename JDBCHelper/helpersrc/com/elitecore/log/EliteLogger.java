package com.elitecore.log;

import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

public class EliteLogger {

	public static Logger genLog = LogManager.getLogger(EliteLogger.class.getName());
	public static Logger connectionPoolLog = LogManager.getLogger(EliteLogger.class.getName() + ".CONNECTIONPOOLLOG");
	public static Logger auClientLog = LogManager.getLogger(EliteLogger.class.getName() + ".AUCLIENTLOG");
	public static Logger auServerLog = LogManager.getLogger(EliteLogger.class.getName() + ".AUSERVERLOG");

	static {
		try {
			String loggerPropertyName = "EliteLogger_log4j2.xml";
			try {
				LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
				ctx.setConfigLocation(EliteLogger.class.getClassLoader().getResource(loggerPropertyName).toURI());
				ctx.start();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

			EliteLogger.genLog.info("This is general log");
			EliteLogger.connectionPoolLog.info("This is connnection pool log");
			EliteLogger.auClientLog.info("This is auto upgrade client log");
			EliteLogger.auServerLog.info("This is auto upgrade server log");
		} catch (Exception e) {
			System.out.println("Exception in intializing logger: " + e);
			e.printStackTrace();
		}
	}
}
