package com.cognizant.springlearn.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.model.Country;

/**
 * Country REST Controller
 * 
 * Controller: com.cognizant.spring-learn.controller.CountryController
 * Demonstrates RESTful Web Service for Country operations using Spring Web Framework
 */
@RestController
public class CountryController {

    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    /**
     * REST service that returns India country details
     * 
     * URL: /country
     * Controller: com.cognizant.spring-learn.controller.CountryController
     * Method Annotation: @RequestMapping
     * Method Name: getCountryIndia()
     * Method Implementation: Load India bean from spring xml configuration and return
     * Sample Request: http://localhost:8083/country
     * Sample Response: { "code": "IN", "name": "India" }
     * 
     * @return Country object representing India
     */
    @RequestMapping("/country")
    public Country getCountryIndia() {
        logger.info("START - getCountryIndia() method called");
        logger.info("Processing GET request for /country endpoint");
        
        try {
            // Load India bean from Spring XML configuration
            logger.info("Loading India country bean from india-country.xml");
            try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("india-country.xml")) {
                Country indiaCountry = context.getBean("indiaCountry", Country.class);
                
                logger.info("Successfully loaded India country: {}", indiaCountry);
                logger.info("END - getCountryIndia() method completed - Returning: {}", indiaCountry);
                
                return indiaCountry;
            }
            
        } catch (Exception e) {
            logger.error("Error in getCountryIndia() method: " + e.getMessage());
            // Return fallback India country object
            Country fallbackIndia = new Country("IN", "India");
            logger.info("Returning fallback India country: {}", fallbackIndia);
            return fallbackIndia;
        }
    }

    /**
     * REST service that returns all countries
     * 
     * Controller: com.cognizant.spring-learn.controller.CountryController
     * Method Annotation: @GetMapping("/countries")
     * Method Name: getAllCountries()
     * Method Implementation: Load country list from country.xml and return
     * Sample Request: http://localhost:8083/countries
     * Sample Response: [{"code":"IN","name":"India"},{"code":"US","name":"United States"},{"code":"JP","name":"Japan"},{"code":"DE","name":"Germany"}]
     * 
     * @return List of Country objects
     */
    @GetMapping("/countries")
    @SuppressWarnings("unchecked")
    public List<Country> getAllCountries() {
        logger.info("START - getAllCountries() method called");
        logger.info("Processing GET request for /countries endpoint");
        
        try {
            // Load country list from country.xml
            logger.info("Loading country list from country.xml");
            try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("country.xml")) {
                List<Country> countries = (List<Country>) context.getBean("countryList");
                
                logger.info("Successfully loaded {} countries from XML configuration", countries.size());
                for (Country country : countries) {
                    logger.debug("Country loaded: {}", country);
                }
                
                logger.info("END - getAllCountries() method completed - Returning {} countries", countries.size());
                
                return countries;
            }
            
        } catch (Exception e) {
            logger.error("Error in getAllCountries() method: " + e.getMessage());
            // Return fallback country list
            List<Country> fallbackCountries = List.of(
                new Country("IN", "India"),
                new Country("US", "United States"),
                new Country("JP", "Japan"),
                new Country("DE", "Germany")
            );
            logger.info("Returning fallback country list with {} countries", fallbackCountries.size());
            return fallbackCountries;
        }
    }

    /**
     * Health check endpoint for Country service
     * 
     * @return Health status
     */
    @GetMapping("/country/health")
    public String health() {
        logger.info("Country service health check accessed");
        return "Country REST Service is UP and running on port 8083!";
    }
}
