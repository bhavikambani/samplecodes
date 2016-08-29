package com.bhavik.misc.log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;

/**
 * The Class MyApp.
 * 
 * @author Bhavik Aniruddh Ambani
 */
public class MyApp {

	/** The Constant logger. */
	private static Logger appLog;
	
	/** The Constant consoleLogger. */
	private static final Logger consoleLog ;
	
	static{
		try {
	    	InputStream is = MyApp.class.getClassLoader().getResourceAsStream("newlog4j2.xml");
			ConfigurationSource source = new ConfigurationSource(is);
	        Configuration config = XmlConfigurationFactory.getInstance().getConfiguration(source);
	        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
	        ctx.setConfigLocation(MyApp.class.getClassLoader().getResource("newlog4j2.xml").toURI());
	        ctx.start();
	        
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		appLog = LogManager.getLogger(MyApp.class.getName() + ".APPLOG");
		 consoleLog = LogManager.getLogger(MyApp.class.getName() + ".CONSOLELOG");
		System.out.println("Starting application, now all the logs will be placed in system.log");
		//System.setOut(createLoggingProxy(System.out));
		
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException 
	 */
	public static void main(final String... args) throws IOException {

		System.out.println("MyApp This is console log");
		// Set up a simple configuration that logs on the console.
		System.out.println("appLog ### " + appLog);
		System.out.println("consoleLog ### " + consoleLog);
		appLog.debug(MyApp.class.getName());
		consoleLog.debug("MyApp Thread started with Logger");
		for (long i = 0; i < 10L; i++) {
			appLog.trace( i + " MyApp Entering application." + MyApp.class.getName());
		}
		//logger = LogManager.getLogger("APPLOGNEW");
		consoleLog.debug("MyApp Thread ended with Logger");
		Bar bar = new Bar();
		if (!bar.doIt()) {
			appLog.info("MyApp Didn't do it.");
		}
		appLog.trace("MyApp Exiting application.");
	}

	/**
	 * Creates the logging proxy.
	 *
	 * @param realPrintStream the real print stream
	 * @return the prints the stream
	 */
	public static PrintStream createLoggingProxy(
			final PrintStream realPrintStream) {
		return new PrintStream(realPrintStream) {
			public void print(final String string) { 
				consoleLog.debug(string);
				//realPrintStream.print(string);
			}
		};
	}
}

class MyApp2{
	public static Logger logger;
	public static Logger consoleLogger;
	static{
		InputStream in = MyApp2.class.getClassLoader().getResourceAsStream("log4j2forMyApp1.xml");
		ConfigurationSource source;
		try {
			source = new ConfigurationSource(in);
			ConfigurationFactory factory = new XmlConfigurationFactory();
			Configuration configuration = factory.getConfiguration(source);
			LoggerContext context = (LoggerContext) LogManager.getContext(true);
			context.start(configuration);
			context.updateLoggers();

			logger = LogManager.getLogger("APPLOGNEWMYAPP1");
			consoleLogger = LogManager.getLogger("CONSOLELOGMYAPP1");
			System.out.println("MyApp1 Starting application, now all the logs will be placed in system.log");

			logger.debug("MyApp1 This is new myapp1 applog");
			consoleLogger.debug("MyApp1 This is new myapp1 consolelog");
			logger.debug("MyApp1 This is new myapp1 applog");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Bar {
	private static final Logger logger = LogManager.getLogger(MyApp.class.getName() + ".APPLOG");

	public boolean doIt() {
		logger.entry();
		logger.error("Did it again!");
		return logger.exit(false);
	}
}