package com.cognizant.springlearn.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.cognizant.springlearn.model.Country;

/**
 * Country Service Class
 * Service Method: com.cognizant.spring-learn.service.CountryService.getCountry(String code)
 * Handles business logic for country operations
 */
@Service
public class CountryService {

    private static final Logger logger = LoggerFactory.getLogger(CountryService.class);

    /**
     * Service Method Implementation:
     * • Get country list from country.xml
     * • Iterate through the country list
     * • Make a case insensitive matching of country code and return the country
     * • Lambda expression is used instead of iterating the country list
     * 
     * @param code Country code (case insensitive)
     * @return Country object if found, null otherwise
     */
    @SuppressWarnings("unchecked")
    public Country getCountry(String code) {
        logger.info("START - getCountry() method called with code: {}", code);
        
        if (code == null || code.trim().isEmpty()) {
            logger.warn("Country code is null or empty");
            return null;
        }
        
        String upperCaseCode = code.toUpperCase().trim();
        logger.info("Searching for country with code: {} (original: {})", upperCaseCode, code);
        
        try {
            // Get country list from country.xml
            logger.info("Loading country list from country.xml");
            try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("country.xml")) {
                List<Country> countries = (List<Country>) context.getBean("countryList");
                
                logger.info("Successfully loaded {} countries from XML configuration", countries.size());
                
                // Lambda expression for case insensitive matching of country code
                Optional<Country> foundCountry = countries.stream()
                    .filter(country -> country.getCode() != null && 
                            country.getCode().equalsIgnoreCase(upperCaseCode))
                    .findFirst();
                
                if (foundCountry.isPresent()) {
                    Country country = foundCountry.get();
                    logger.info("Country found: {}", country);
                    logger.info("END - getCountry() method completed - Returning: {}", country);
                    return country;
                } else {
                    logger.warn("No country found with code: {}", upperCaseCode);
                    logger.info("END - getCountry() method completed - Returning: null");
                    return null;
                }
            }
            
        } catch (Exception e) {
            logger.error("Error in getCountry() method: " + e.getMessage());
            logger.info("END - getCountry() method completed with error - Returning: null");
            return null;
        }
    }

    /**
     * Get all countries from XML configuration
     * Helper method for demonstration purposes
     * 
     * @return List of all countries
     */
    @SuppressWarnings("unchecked")
    public List<Country> getAllCountries() {
        logger.info("START - getAllCountries() method called");
        
        try {
            logger.info("Loading all countries from country.xml");
            try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("country.xml")) {
                List<Country> countries = (List<Country>) context.getBean("countryList");
                
                logger.info("Successfully loaded {} countries", countries.size());
                logger.info("END - getAllCountries() method completed");
                
                return countries;
            }
            
        } catch (Exception e) {
            logger.error("Error in getAllCountries() method: " + e.getMessage());
            return List.of(); // Return empty list on error
        }
    }
}
