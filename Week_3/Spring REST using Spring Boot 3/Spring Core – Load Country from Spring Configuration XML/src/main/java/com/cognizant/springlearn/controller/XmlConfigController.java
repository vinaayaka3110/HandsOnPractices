package com.cognizant.springlearn.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller to demonstrate Spring XML Configuration via web endpoints
 */
@RestController
public class XmlConfigController {

    private static final Logger logger = LoggerFactory.getLogger(XmlConfigController.class);

    /**
     * Home endpoint showing XML configuration demo results
     */
    @GetMapping("/")
    public String home() {
        logger.info("Home endpoint accessed - demonstrating XML configuration");
        
        StringBuilder response = new StringBuilder();
        response.append("<html><head><title>Spring XML Configuration Demo</title></head><body>");
        response.append("<h1>Spring Core - Load SimpleDateFormat from Spring Configuration XML</h1>");
        response.append("<h2>Demo Results:</h2>");
        
        try {
            // Create ApplicationContext from XML configuration
            ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");
            
            // Get the dateFormat bean from Spring container
            SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);
            
            // Parse the string '31/12/2018' to Date class
            String dateString = "31/12/2018";
            Date parsedDate = format.parse(dateString);
            
            response.append("<p><strong>Original date string:</strong> ").append(dateString).append("</p>");
            response.append("<p><strong>Parsed Date object:</strong> ").append(parsedDate).append("</p>");
            response.append("<p><strong>Formatted back to string:</strong> ").append(format.format(parsedDate)).append("</p>");
            response.append("<p><strong>Date pattern used:</strong> dd/MM/yyyy</p>");
            response.append("<p style='color: green;'><strong>✅ Successfully loaded SimpleDateFormat from Spring XML Configuration!</strong></p>");
            
            response.append("<h3>XML Configuration (date-format.xml):</h3>");
            response.append("<pre style='background-color: #f4f4f4; padding: 10px;'>");
            response.append("&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;\n");
            response.append("&lt;beans xmlns=\"http://www.springframework.org/schema/beans\"\n");
            response.append("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
            response.append("    xsi:schemaLocation=\"http://www.springframework.org/schema/beans\n");
            response.append("        https://www.springframework.org/schema/beans/spring-beans.xsd\"&gt;\n\n");
            response.append("    &lt;bean id=\"dateFormat\" class=\"java.text.SimpleDateFormat\"&gt;\n");
            response.append("        &lt;constructor-arg value=\"dd/MM/yyyy\" /&gt;\n");
            response.append("    &lt;/bean&gt;\n\n");
            response.append("&lt;/beans&gt;");
            response.append("</pre>");
            
        } catch (Exception e) {
            response.append("<p style='color: red;'><strong>❌ Error:</strong> ").append(e.getMessage()).append("</p>");
            logger.error("Error in XML configuration demo: " + e.getMessage());
        }
        
        response.append("<h3>Other Endpoints:</h3>");
        response.append("<ul>");
        response.append("<li><a href='/health'>Health Check</a></li>");
        response.append("<li><a href='/xml-demo'>XML Demo (JSON Response)</a></li>");
        response.append("</ul>");
        
        response.append("</body></html>");
        return response.toString();
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public String health() {
        logger.info("Health check endpoint accessed");
        return "Spring XML Configuration Demo Application is UP and running on port 8081!";
    }

    /**
     * XML demo endpoint with JSON response
     */
    @GetMapping("/xml-demo")
    public String xmlDemo() {
        logger.info("XML demo endpoint accessed");
        
        try {
            // Create ApplicationContext from XML configuration
            ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");
            
            // Get the dateFormat bean from Spring container
            SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);
            
            // Parse the string '31/12/2018' to Date class
            String dateString = "31/12/2018";
            Date parsedDate = format.parse(dateString);
            
            return "{\n" +
                   "  \"demo\": \"Spring XML Configuration\",\n" +
                   "  \"xmlFile\": \"date-format.xml\",\n" +
                   "  \"beanId\": \"dateFormat\",\n" +
                   "  \"beanClass\": \"java.text.SimpleDateFormat\",\n" +
                   "  \"pattern\": \"dd/MM/yyyy\",\n" +
                   "  \"originalString\": \"" + dateString + "\",\n" +
                   "  \"parsedDate\": \"" + parsedDate + "\",\n" +
                   "  \"formattedString\": \"" + format.format(parsedDate) + "\",\n" +
                   "  \"status\": \"SUCCESS\"\n" +
                   "}";
            
        } catch (Exception e) {
            return "{\n" +
                   "  \"demo\": \"Spring XML Configuration\",\n" +
                   "  \"error\": \"" + e.getMessage() + "\",\n" +
                   "  \"status\": \"ERROR\"\n" +
                   "}";
        }
    }
}
