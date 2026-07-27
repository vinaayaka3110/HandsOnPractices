package com.cognizant.springlearn;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Demonstration class showing how to load SimpleDateFormat from Spring XML Configuration
 */
public class DateFormatDemo {

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("Spring Core - Load SimpleDateFormat from Spring Configuration XML");
        System.out.println("=================================================================");
        
        displayDate();
        
        System.out.println("=================================================================");
        System.out.println("Demo completed successfully!");
        System.out.println("=================================================================");
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
            
            // Display the result
            System.out.println("Original date string: " + dateString);
            System.out.println("Parsed Date object: " + parsedDate);
            System.out.println("Formatted back to string: " + format.format(parsedDate));
            
            System.out.println("Successfully loaded SimpleDateFormat from Spring XML Configuration");
            System.out.println("Date pattern used: dd/MM/yyyy");
            System.out.println("Parsed date: " + parsedDate);
            
        } catch (Exception e) {
            System.err.println("Error in displayDate() method: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
