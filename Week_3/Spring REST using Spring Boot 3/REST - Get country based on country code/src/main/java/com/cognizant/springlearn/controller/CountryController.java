package com.cognizant.springlearn.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.model.Country;
import com.cognizant.springlearn.service.CountryService;

/**
 * Country REST Controller
 * 
 * Controller: com.cognizant.spring-learn.controller.CountryController
 * Demonstrates RESTful Web Service for getting country by country code
 */
@RestController
public class CountryController {

    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;

    /**
     * REST service that returns a specific country based on country code
     * The country code should be case insensitive
     * 
     * Controller: com.cognizant.spring-learn.controller.CountryController
     * Method Annotation: @GetMapping("/countries/{code}")
     * Method Name: getCountry(String code)
     * Method Implementation: Invoke countryService.getCountry(code)
     * 
     * Service Method Implementation:
     * • Get the country code using @PathVariable
     * • Get country list from country.xml
     * • Iterate through the country list
     * • Make a case insensitive matching of country code and return the country
     * • Lambda expression is used instead of iterating the country list
     * 
     * Sample Request: http://localhost:8083/countries/in
     * Sample Response: { "code": "IN", "name": "India" }
     * 
     * @param code Country code (case insensitive)
     * @return Country object if found, 404 if not found
     */
    @GetMapping("/countries/{code}")
    public ResponseEntity<Country> getCountry(@PathVariable String code) {
        logger.info("START - getCountry() method called with PathVariable code: {}", code);
        logger.info("Processing GET request for /countries/{} endpoint", code);
        
        // Invoke countryService.getCountry(code)
        Country country = countryService.getCountry(code);
        
        if (country != null) {
            logger.info("Country found and returning: {}", country);
            logger.info("END - getCountry() method completed - Returning: {}", country);
            return ResponseEntity.ok(country);
        } else {
            logger.warn("Country not found for code: {}", code);
            logger.info("END - getCountry() method completed - Returning: 404 Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Get all countries endpoint for testing purposes
     * 
     * @return List of all countries
     */
    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getAllCountries() {
        logger.info("START - getAllCountries() method called");
        logger.info("Processing GET request for /countries endpoint");
        
        List<Country> countries = countryService.getAllCountries();
        
        logger.info("Returning {} countries", countries.size());
        logger.info("END - getAllCountries() method completed");
        
        return ResponseEntity.ok(countries);
    }

    /**
     * Simple home endpoint
     * 
     * @return Welcome message
     */
    @GetMapping("/")
    public String home() {
        logger.info("Home endpoint accessed");
        return "Welcome to Country Code REST Service! " +
               "Use /countries/{code} to get a specific country by code. " +
               "Example: /countries/in for India";
    }

    /**
     * Health check endpoint
     * 
     * @return Health status
     */
    @GetMapping("/health")
    public String health() {
        logger.info("Health check endpoint accessed");
        return "Country Code REST Service is UP and running on port 8083!";
    }
}
