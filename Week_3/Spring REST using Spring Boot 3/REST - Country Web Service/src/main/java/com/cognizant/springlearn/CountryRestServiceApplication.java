package com.cognizant.springlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application Class for Country REST Service
 */
@SpringBootApplication
public class CountryRestServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(CountryRestServiceApplication.class);

	public static void main(String[] args) {
		logger.info("=================================================================");
		logger.info("Starting Country RESTful Web Service Application...");
		logger.info("=================================================================");
		
		// Start the Spring Boot application
		SpringApplication.run(CountryRestServiceApplication.class, args);
		
		logger.info("=================================================================");
		logger.info("Country REST Service Application started successfully!");
		logger.info("Application is running on: http://localhost:8083");
		logger.info("Country endpoints:");
		logger.info("  - India Country: http://localhost:8083/country");
		logger.info("  - All Countries: http://localhost:8083/countries");
		logger.info("  - Health Check: http://localhost:8083/country/health");
		logger.info("=================================================================");
	}
}
