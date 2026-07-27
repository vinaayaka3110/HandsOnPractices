package com.slf4j.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingExample {
    private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        
        // Demonstrate different logging levels
        logger.trace("This is a trace message");
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warning message");
        logger.error("This is an error message");
        
        // Demonstrate logging with parameters
        String user = "John Doe";
        int attempts = 3;
        logger.warn("User {} failed to login after {} attempts", user, attempts);
        
        // Demonstrate error logging with exception
        try {
            performOperation();
        } catch (Exception e) {
            logger.error("An error occurred during operation", e);
        }
        
        logger.info("Application finished.");
    }
    
    private static void performOperation() throws Exception {
        // Simulate an operation that might fail
        throw new RuntimeException("Simulated operation failure");
    }
    
    // Methods for testing purposes
    public void logErrorMessage(String message) {
        logger.error(message);
    }
    
    public void logWarningMessage(String message) {
        logger.warn(message);
    }
    
    public void logInfoMessage(String message) {
        logger.info(message);
    }
    
    public void logDebugMessage(String message) {
        logger.debug(message);
    }
    
    public void logWithException(String message, Exception exception) {
        logger.error(message, exception);
    }
}
