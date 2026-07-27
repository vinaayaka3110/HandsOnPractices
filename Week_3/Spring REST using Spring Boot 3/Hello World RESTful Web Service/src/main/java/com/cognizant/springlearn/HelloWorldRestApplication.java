package com.cognizant.springlearn;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main Spring Boot Application Class with Hello World REST Service
 */
@SpringBootApplication
public class HelloWorldRestApplication {

	private static final Logger logger = LoggerFactory.getLogger(HelloWorldRestApplication.class);

	public static void main(String[] args) {
		logger.info("=================================================================");
		logger.info("Starting Hello World RESTful Web Service Application...");
		logger.info("=================================================================");
		
		// Demonstrate Spring XML Configuration with SimpleDateFormat
		displayDate();
		
		// Start the Spring Boot application
		SpringApplication.run(HelloWorldRestApplication.class, args);
		
		logger.info("=================================================================");
		logger.info("Hello World REST Application started successfully!");
		logger.info("Application is running on: http://localhost:8083");
		logger.info("Hello World endpoint: http://localhost:8083/hello");
		logger.info("=================================================================");
	}

	/**
	 * Method to demonstrate Spring XML Configuration for SimpleDateFormat
	 */
	public static void displayDate() {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");
			SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);
			String dateString = "31/12/2018";
			Date parsedDate = format.parse(dateString);
			
			System.out.println("Original date string: " + dateString);
			System.out.println("Parsed Date object: " + parsedDate);
			System.out.println("Formatted back to string: " + format.format(parsedDate));
			
			logger.info("Successfully loaded SimpleDateFormat from Spring XML Configuration");
			
		} catch (Exception e) {
			logger.error("Error in displayDate() method: " + e.getMessage());
		}
	}
}
