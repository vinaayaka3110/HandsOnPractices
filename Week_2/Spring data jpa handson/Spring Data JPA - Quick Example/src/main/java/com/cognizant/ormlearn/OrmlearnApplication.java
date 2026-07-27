package com.cognizant.ormlearn;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Main Spring Boot Application Class for Spring Data JPA Example
 * 
 * This application demonstrates:
 * - Spring Data JPA with Hibernate
 * - Entity management with JPA annotations
 * - Repository pattern with Spring Data JPA
 * - Basic CRUD operations
 * - Custom query methods
 */
@SpringBootApplication
public class OrmlearnApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(OrmlearnApplication.class);

    @Autowired
    private CountryService countryService;

    public static void main(String[] args) {
        logger.info("Starting Spring Data JPA Example Application...");
        SpringApplication.run(OrmlearnApplication.class, args);
        logger.info("Spring Data JPA Example Application started successfully!");
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("=== Spring Data JPA Demo Started ===");
        
        try {
            // Demonstrate CRUD operations
            demonstrateCRUDOperations();
            
            // Demonstrate custom queries
            demonstrateCustomQueries();
            
        } catch (Exception e) {
            logger.error("Error during demo execution: {}", e.getMessage(), e);
        }
        
        logger.info("=== Spring Data JPA Demo Completed ===");
    }

    private void demonstrateCRUDOperations() {
        logger.info("\n--- CRUD Operations Demo ---");
        
        // Create countries
        logger.info("Creating countries...");
        Country usa = countryService.createCountry("USA", "US", "United States");
        Country india = countryService.createCountry("IND", "IN", "India");
        Country uk = countryService.createCountry("GBR", "GB", "United Kingdom");
        Country japan = countryService.createCountry("JPN", "JP", "Japan");
        
        logger.info("Created countries - USA: {}, India: {}, UK: {}, Japan: {}", 
            usa.getId(), india.getId(), uk.getId(), japan.getId());
        
        // Read all countries
        logger.info("Reading all countries...");
        List<Country> allCountries = countryService.getAllCountries();
        allCountries.forEach(country -> 
            logger.info("Country: {} - Code: {} - Name: {}", 
                country.getId(), country.getCountryCode(), country.getCountryName()));
        
        // Update a country
        logger.info("Updating country...");
        usa.setCountryName("United States of America");
        countryService.updateCountry(usa);
        
        // Read updated country
        Country updatedUsa = countryService.getCountryById(usa.getId());
        logger.info("Updated Country: {}", updatedUsa.getCountryName());
        
        // Delete a country
        logger.info("Deleting country with ID: {}", japan.getId());
        countryService.deleteCountry(japan.getId());
        
        // Count remaining countries
        long count = countryService.getCountryCount();
        logger.info("Total countries after deletion: {}", count);
    }

    private void demonstrateCustomQueries() {
        logger.info("\n--- Custom Queries Demo ---");
        
        // Find by country code
        Country countryByCode = countryService.findByCountryCode("US");
        if (countryByCode != null) {
            logger.info("Found country by code 'US': {}", countryByCode.getCountryName());
        }
        
        // Find by country name containing
        List<Country> countriesWithUnited = countryService.findByCountryNameContaining("United");
        logger.info("Countries containing 'United': {}", 
            countriesWithUnited.stream()
                .map(Country::getCountryName)
                .collect(Collectors.toList()));
        
        // Find by ISO code
        List<Country> countriesWithIN = countryService.findByIsoCode("IN");
        logger.info("Countries with ISO code 'IN': {}", 
            countriesWithIN.stream()
                .map(Country::getCountryName)
                .collect(Collectors.toList()));
        
        // Custom query - find all country codes
        List<String> allCountryCodes = countryService.findAllCountryCodes();
        logger.info("All country codes: {}", allCountryCodes);
    }
}
