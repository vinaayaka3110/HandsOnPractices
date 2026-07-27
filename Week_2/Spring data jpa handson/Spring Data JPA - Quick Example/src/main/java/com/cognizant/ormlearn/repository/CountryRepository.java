package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Country Repository interface extending JpaRepository
 * 
 * This repository demonstrates:
 * - Spring Data JPA repository pattern
 * - Auto-generated CRUD methods
 * - Custom query methods using method names
 * - Custom queries using @Query annotation
 * - Named parameters in queries
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    // Custom finder methods - Spring Data JPA will implement these automatically
    
    /**
     * Find country by country code (exact match)
     * Method name follows Spring Data JPA naming convention
     */
    Optional<Country> findByCountryCode(String countryCode);

    /**
     * Find country by ISO code (exact match)
     */
    List<Country> findByIsoCode(String isoCode);

    /**
     * Find countries by country name containing specified text (case-insensitive)
     */
    List<Country> findByCountryNameContainingIgnoreCase(String name);

    /**
     * Find countries by country code starting with specified prefix
     */
    List<Country> findByCountryCodeStartingWith(String prefix);

    /**
     * Check if country exists by country code
     */
    boolean existsByCountryCode(String countryCode);

    /**
     * Count countries by ISO code
     */
    long countByIsoCode(String isoCode);

    /**
     * Find countries ordered by country name
     */
    List<Country> findByOrderByCountryNameAsc();

    // Custom queries using @Query annotation
    
    /**
     * Custom query to find all country codes
     * Demonstrates JPQL (Java Persistence Query Language)
     */
    @Query("SELECT c.countryCode FROM Country c ORDER BY c.countryCode")
    List<String> findAllCountryCodes();

    /**
     * Custom query to find country by name (case-insensitive exact match)
     */
    @Query("SELECT c FROM Country c WHERE LOWER(c.countryName) = LOWER(:name)")
    Optional<Country> findByCountryNameIgnoreCase(@Param("name") String name);

    /**
     * Custom query to find countries with names longer than specified length
     */
    @Query("SELECT c FROM Country c WHERE LENGTH(c.countryName) > :length")
    List<Country> findCountriesWithNameLongerThan(@Param("length") int length);

    /**
     * Custom native SQL query example
     * Demonstrates native SQL usage (when JPQL is not sufficient)
     */
    @Query(value = "SELECT * FROM countries WHERE country_name LIKE %:pattern%", nativeQuery = true)
    List<Country> findByCountryNameLikeNative(@Param("pattern") String pattern);

    /**
     * Custom query to get count of countries by country code prefix
     */
    @Query("SELECT COUNT(c) FROM Country c WHERE c.countryCode LIKE :prefix%")
    long countByCountryCodePrefix(@Param("prefix") String prefix);

    /**
     * Custom query to find countries created after a certain date
     * Demonstrates date/time queries
     */
    @Query("SELECT c FROM Country c WHERE c.createdAt > :date")
    List<Country> findCountriesCreatedAfter(@Param("date") java.time.LocalDateTime date);
}
