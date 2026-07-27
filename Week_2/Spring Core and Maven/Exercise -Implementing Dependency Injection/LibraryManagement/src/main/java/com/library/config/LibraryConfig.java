package com.library.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryConfig {
    private static final Logger logger = LoggerFactory.getLogger(LibraryConfig.class);
    
    private String applicationName;
    private String version;
    private Boolean enableLogging;
    private Boolean enableCaching;

    public LibraryConfig() {
        logger.info("LibraryConfig initialized");
    }

    public void displayConfiguration() {
        logger.info("=== Library Configuration ===");
        logger.info("Application Name: {}", applicationName);
        logger.info("Version: {}", version);
        logger.info("Logging Enabled: {}", enableLogging);
        logger.info("Caching Enabled: {}", enableCaching);
    }

    // Getters and Setters
    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        logger.info("Application name configured: {}", applicationName);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
        logger.info("Version configured: {}", version);
    }

    public Boolean getEnableLogging() {
        return enableLogging;
    }

    public void setEnableLogging(Boolean enableLogging) {
        this.enableLogging = enableLogging;
        logger.info("Logging enabled: {}", enableLogging);
    }

    public Boolean getEnableCaching() {
        return enableCaching;
    }

    public void setEnableCaching(Boolean enableCaching) {
        this.enableCaching = enableCaching;
        logger.info("Caching enabled: {}", enableCaching);
    }
}
