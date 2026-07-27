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
 * Main Spring Boot Application Class demonstrating XML Configuration
 */
@SpringBootApplication
public class SpringXmlConfigDemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(SpringXmlConfigDemoApplication.class);

	public static void main(String[] args) {
		logger.info("=================================================================");
		logger.info("Spring Core - Load SimpleDateFormat from Spring Configuration XML");
		logger.info("=================================================================");
		
		// Demonstrate Spring XML Configuration with SimpleDateFormat
		displayDate();
		
		// Start the Spring Boot application
		SpringApplication.run(SpringXmlConfigDemoApplication.class, args);
		
		logger.info("=================================================================");
		logger.info("Spring XML Configuration Demo completed successfully!");
		logger.info("Application is running on: http://localhost:8081");
		logger.info("=================================================================");
	}

	/**
	 * Method to demonstrate Spring XML Configuration for SimpleDateFormat
	 * This method loads the date-format.xml configuration and uses the dateFormat bean
	 */
	public static void displayDate() {
		try {
			// Create ApplicationContext from XML configuration
			ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");
			
			// Get the dateFormat bean from Spring container
			SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);
			
			// Parse the string '31/12/2018' to Date class
			String dateString = "31/12/2018";
			Date parsedDate = format.parse(dateString);
			
			// Display the result using System.out.println
			System.out.println("Original date string: " + dateString);
			System.out.println("Parsed Date object: " + parsedDate);
			System.out.println("Formatted back to string: " + format.format(parsedDate));
			
			logger.info("Successfully loaded SimpleDateFormat from Spring XML Configuration");
			logger.info("Date pattern used: dd/MM/yyyy");
			logger.info("Parsed date: " + parsedDate);
			
		} catch (Exception e) {
			logger.error("Error in displayDate() method: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
