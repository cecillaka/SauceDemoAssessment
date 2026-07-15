package test.utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jLogger {

    // Initialize Log4j instance
    private static final Logger logger = LogManager.getLogger(Log4jLogger.class);

    // Info Level Logs
    public static void loggerInfo(String message) {
        //logger.info(message);
        logger.info("\u001B[32mTest Passed: "+message+"\u001B[0m"); // Green color
    }

    // Warn Level Logs
    public static void  loggerWarn(String message) {
     //   logger.warn(message);
        logger.warn("\u001B[38;5;208m " + message + "\u001B[0m"); // Orange color
    }

    // Error Level Logs
    public static void  loggerError(String message) {
       // logger.error(message);
        logger.error("\u001B[31mTest Failed: "+message+"\u001B[0m"); // Red color
    }

    // Debug Level Logs
    public static void  loggerDebug(String message) {
        logger.debug(message);
       
    }
}
