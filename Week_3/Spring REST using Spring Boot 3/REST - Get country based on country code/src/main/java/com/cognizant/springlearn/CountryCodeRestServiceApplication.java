package com.cognizant.springlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application Class for Country Code REST Service
 */
@SpringBootApplication
public class CountryCodeRestServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(CountryCodeRestServiceApplication.class);

	public static void main(String[] args) {
		logger.info("=================================================================");
		logger.info("Starting Country Code RESTful Web Service Application...");
		logger.info("=================================================================");
		
		// Start the Spring Boot application
		SpringApplication.run(CountryCodeRestServiceApplication.class, args);
		
		logger.info("=================================================================");
		logger.info("Country Code REST Service Application started successfully!");
		logger.info("Application is running on: http://localhost:8083");
		logger.info("Country endpoints:");
		logger.info("  - Get Country by Code: http://localhost:8083/countries/{code}");
		logger.info("  - Example: http://localhost:8083/countries/in");
		logger.info("  - All Countries: http://localhost:8083/countries");
		logger.info("  - Health Check: http://localhost:8083/health");
		logger.info("=================================================================");
	}
}
