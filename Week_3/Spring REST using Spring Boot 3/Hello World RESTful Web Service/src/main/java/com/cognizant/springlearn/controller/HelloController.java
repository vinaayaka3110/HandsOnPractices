package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello World REST Controller
 * 
 * Controller: com.cognizant.spring-learn.controller.HelloController
 * Demonstrates RESTful Web Service using Spring Web Framework
 */
@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    /**
     * Simple endpoint to verify the application is running
     * 
     * @return Welcome message
     */
    @GetMapping("/")
    public String home() {
        logger.info("Home endpoint accessed");
        return "Welcome to Hello World RESTful Web Service! The application is running successfully on port 8083.";
    }

    /**
     * Health check endpoint
     * 
     * @return Health status
     */
    @GetMapping("/health")
    public String health() {
        logger.info("Health check endpoint accessed");
        return "Hello World REST Service is UP and running on port 8083!";
    }

    /**
     * Hello World RESTful Web Service
     * 
     * Method: GET
     * URL: /hello
     * Controller: com.cognizant.spring-learn.controller.HelloController
     * Method Signature: public String sayHello()
     * Sample Request: http://localhost:8083/hello
     * Sample Response: Hello World!!
     * 
     * @return Hello World message
     */
    @GetMapping("/hello")
    public String sayHello() {
        // IMPORTANT NOTE: Start and end log in the sayHello() method
        
        // Start log
        logger.info("START - sayHello() method called - Hello World RESTful Web Service");
        logger.info("Processing GET request for /hello endpoint");
        
        // Method Implementation: return hard coded string "Hello World!!"
        String response = "Hello World!!";
        
        // End log
        logger.info("END - sayHello() method completed - Returning: " + response);
        logger.info("Response sent to client: " + response);
        
        return response;
    }

    /**
     * Additional endpoint for testing purposes
     * 
     * @return JSON response with application info
     */
    @GetMapping("/api/info")
    public String info() {
        logger.info("Info endpoint accessed");
        return "{\n" +
                "  \"service\": \"Hello World RESTful Web Service\",\n" +
                "  \"version\": \"1.0.0\",\n" +
                "  \"framework\": \"Spring Boot 3.2.0\",\n" +
                "  \"java\": \"17\",\n" +
                "  \"port\": \"8083\",\n" +
                "  \"endpoint\": \"/hello\",\n" +
                "  \"method\": \"GET\",\n" +
                "  \"status\": \"Active\"\n" +
                "}";
    }
}
