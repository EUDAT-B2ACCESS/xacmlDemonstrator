package eu.eudat.b2access.authz;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

public class Log4j2Test {
	
	static{
		//This should be defined in the entry point of the program
		System.setProperty("log4j.configurationFile","src/main/resources/log4j2.properties");
//		LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
//		File file = new File(System.getProperty("log4j.configurationFile"));
		// this will force a reconfiguration
//        context.setConfigLocation(file.toURI());
	}
	
	private static Logger log = LogManager.getLogger(Log4j2Test.class);
	
	public static void main(String[] args) throws Exception {
		while (true) {
		  log.debug("test debug");
		  Thread.sleep(1000);
		  log.info("test info");
		  Thread.sleep(1000);
		}
		
	}
}
