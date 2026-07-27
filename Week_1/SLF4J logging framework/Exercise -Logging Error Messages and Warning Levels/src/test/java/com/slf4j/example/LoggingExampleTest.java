package com.slf4j.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

import static org.junit.jupiter.api.Assertions.*;

public class LoggingExampleTest {
    
    private LoggingExample loggingExample;
    private ListAppender<ILoggingEvent> listAppender;
    private ch.qos.logback.classic.Logger logger;

    @BeforeEach
    void setUp() {
        loggingExample = new LoggingExample();
        
        // Set up test appender to capture log messages
        logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(LoggingExample.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @AfterEach
    void tearDown() {
        logger.detachAppender(listAppender);
    }

    @Test
    void testLogErrorMessage() {
        // Act
        loggingExample.logErrorMessage("Test error message");
        
        // Assert
        assertEquals(1, listAppender.list.size());
        ILoggingEvent logEvent = listAppender.list.get(0);
        assertEquals(Level.ERROR, logEvent.getLevel());
        assertEquals("Test error message", logEvent.getMessage());
    }

    @Test
    void testLogWarningMessage() {
        // Act
        loggingExample.logWarningMessage("Test warning message");
        
        // Assert
        assertEquals(1, listAppender.list.size());
        ILoggingEvent logEvent = listAppender.list.get(0);
        assertEquals(Level.WARN, logEvent.getLevel());
        assertEquals("Test warning message", logEvent.getMessage());
    }

    @Test
    void testLogInfoMessage() {
        // Act
        loggingExample.logInfoMessage("Test info message");
        
        // Assert
        assertEquals(1, listAppender.list.size());
        ILoggingEvent logEvent = listAppender.list.get(0);
        assertEquals(Level.INFO, logEvent.getLevel());
        assertEquals("Test info message", logEvent.getMessage());
    }

    @Test
    void testLogDebugMessage() {
        // Set logger level to DEBUG to capture debug messages
        logger.setLevel(Level.DEBUG);
        
        // Act
        loggingExample.logDebugMessage("Test debug message");
        
        // Assert
        assertEquals(1, listAppender.list.size());
        ILoggingEvent logEvent = listAppender.list.get(0);
        assertEquals(Level.DEBUG, logEvent.getLevel());
        assertEquals("Test debug message", logEvent.getMessage());
    }

    @Test
    void testLogWithException() {
        // Arrange
        Exception testException = new RuntimeException("Test exception");
        
        // Act
        loggingExample.logWithException("Error with exception", testException);
        
        // Assert
        assertEquals(1, listAppender.list.size());
        ILoggingEvent logEvent = listAppender.list.get(0);
        assertEquals(Level.ERROR, logEvent.getLevel());
        assertEquals("Error with exception", logEvent.getMessage());
        assertNotNull(logEvent.getThrowableProxy());
        assertEquals("Test exception", logEvent.getThrowableProxy().getMessage());
    }

    @Test
    void testMultipleLogLevels() {
        // Act
        loggingExample.logInfoMessage("Info message");
        loggingExample.logWarningMessage("Warning message");
        loggingExample.logErrorMessage("Error message");
        
        // Assert
        assertEquals(3, listAppender.list.size());
        
        assertEquals(Level.INFO, listAppender.list.get(0).getLevel());
        assertEquals("Info message", listAppender.list.get(0).getMessage());
        
        assertEquals(Level.WARN, listAppender.list.get(1).getLevel());
        assertEquals("Warning message", listAppender.list.get(1).getMessage());
        
        assertEquals(Level.ERROR, listAppender.list.get(2).getLevel());
        assertEquals("Error message", listAppender.list.get(2).getMessage());
    }

    @Test
    void testLoggerClassConfiguration() {
        // Assert that the logger is properly configured
        Logger testLogger = LoggerFactory.getLogger(LoggingExample.class);
        assertNotNull(testLogger);
        assertEquals("com.slf4j.example.LoggingExample", testLogger.getName());
    }
}
