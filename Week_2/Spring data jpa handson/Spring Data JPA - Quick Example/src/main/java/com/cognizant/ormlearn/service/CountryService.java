package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Country Service class for business logic operations
 * 
 * This service demonstrates:
 * - Service layer pattern
 * - Transaction management with @Transactional
 * - Business logic implementation
 * - Exception handling
 * - Integration with repository layer
 */
@Service
@Transactional
public class CountryService {

    private static final Logger logger = LoggerFactory.getLogger(CountryService.class);

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
        logger.info("CountryService initialized");
    }

    // CRUD Operations

    /**
     * Create a new country
     */
    public Country createCountry(String countryCode, String isoCode, String countryName) {
        logger.info("Creating country: {} - {} - {}", countryCode, isoCode, countryName);
        
        // Check if country with same code already exists
        if (countryRepository.existsByCountryCode(countryCode)) {
            throw new RuntimeException("Country with code " + countryCode + " already exists");
        }
        
        Country country = new Country(countryCode, isoCode, countryName);
        Country savedCountry = countryRepository.save(country);
        
        logger.info("Country created successfully with ID: {}", savedCountry.getId());
        return savedCountry;
    }

    /**
     * Get all countries
     */
    @Transactional(readOnly = true)
    public List<Country> getAllCountries() {
        logger.info("Retrieving all countries");
        List<Country> countries = countryRepository.findAll();
        logger.info("Retrieved {} countries", countries.size());
        return countries;
    }

    /**
     * Get country by ID
     */
    @Transactional(readOnly = true)
    public Country getCountryById(Long id) {
        logger.info("Retrieving country with ID: {}", id);
        Optional<Country> country = countryRepository.findById(id);
        
        if (country.isPresent()) {
            logger.info("Country found: {}", country.get().getCountryName());
            return country.get();
        } else {
            logger.warn("Country not found with ID: {}", id);
            throw new RuntimeException("Country not found with ID: " + id);
        }
    }

    /**
     * Update country
     */
    public Country updateCountry(Country country) {
        logger.info("Updating country with ID: {}", country.getId());
        
        if (!countryRepository.existsById(country.getId())) {
            throw new RuntimeException("Country not found with ID: " + country.getId());
        }
        
        Country updatedCountry = countryRepository.save(country);
        logger.info("Country updated successfully: {}", updatedCountry.getCountryName());
        return updatedCountry;
    }

    /**
     * Delete country by ID
     */
    public void deleteCountry(Long id) {
        logger.info("Deleting country with ID: {}", id);
        
        if (!countryRepository.existsById(id)) {
            throw new RuntimeException("Country not found with ID: " + id);
        }
        
        countryRepository.deleteById(id);
        logger.info("Country deleted successfully with ID: {}", id);
    }

    /**
     * Get total count of countries
     */
    @Transactional(readOnly = true)
    public long getCountryCount() {
        long count = countryRepository.count();
        logger.info("Total countries count: {}", count);
        return count;
    }

    // Custom query methods

    /**
     * Find country by country code
     */
    @Transactional(readOnly = true)
    public Country findByCountryCode(String countryCode) {
        logger.info("Finding country by code: {}", countryCode);
        Optional<Country> country = countryRepository.findByCountryCode(countryCode);
        
        if (country.isPresent()) {
            logger.info("Country found by code: {}", country.get().getCountryName());
            return country.get();
        } else {
            logger.warn("Country not found with code: {}", countryCode);
            return null;
        }
    }

    /**
     * Find countries by ISO code
     */
    @Transactional(readOnly = true)
    public List<Country> findByIsoCode(String isoCode) {
        logger.info("Finding countries by ISO code: {}", isoCode);
        List<Country> countries = countryRepository.findByIsoCode(isoCode);
        logger.info("Found {} countries with ISO code: {}", countries.size(), isoCode);
        return countries;
    }

    /**
     * Find countries by name containing specified text
     */
    @Transactional(readOnly = true)
    public List<Country> findByCountryNameContaining(String name) {
        logger.info("Finding countries containing name: {}", name);
        List<Country> countries = countryRepository.findByCountryNameContainingIgnoreCase(name);
        logger.info("Found {} countries containing name: {}", countries.size(), name);
        return countries;
    }

    /**
     * Get all country codes
     */
    @Transactional(readOnly = true)
    public List<String> findAllCountryCodes() {
        logger.info("Retrieving all country codes");
        List<String> codes = countryRepository.findAllCountryCodes();
        logger.info("Retrieved {} country codes", codes.size());
        return codes;
    }

    /**
     * Find countries ordered by name
     */
    @Transactional(readOnly = true)
    public List<Country> findCountriesOrderedByName() {
        logger.info("Retrieving countries ordered by name");
        List<Country> countries = countryRepository.findByOrderByCountryNameAsc();
        logger.info("Retrieved {} countries ordered by name", countries.size());
        return countries;
    }

    /**
     * Find countries with name longer than specified length
     */
    @Transactional(readOnly = true)
    public List<Country> findCountriesWithLongNames(int minLength) {
        logger.info("Finding countries with names longer than {} characters", minLength);
        List<Country> countries = countryRepository.findCountriesWithNameLongerThan(minLength);
        logger.info("Found {} countries with long names", countries.size());
        return countries;
    }

    /**
     * Check if country exists by country code
     */
    @Transactional(readOnly = true)
    public boolean existsByCountryCode(String countryCode) {
        boolean exists = countryRepository.existsByCountryCode(countryCode);
        logger.info("Country with code {} exists: {}", countryCode, exists);
        return exists;
    }

    /**
     * Find countries created after specified date
     */
    @Transactional(readOnly = true)
    public List<Country> findCountriesCreatedAfter(LocalDateTime date) {
        logger.info("Finding countries created after: {}", date);
        List<Country> countries = countryRepository.findCountriesCreatedAfter(date);
        logger.info("Found {} countries created after specified date", countries.size());
        return countries;
    }
}
