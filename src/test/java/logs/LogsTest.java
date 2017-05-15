package logs;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LogsTest {

	public static void main(String[] args) {
		Logger logger = LogManager.getLogger("mylogtofile");    
        logger.trace("trace level");    
        logger.debug("debug level");    
        logger.info("info level");    
        logger.warn("warn level");    
        logger.error("error level");    
        logger.fatal("fatal level");
	}

}
