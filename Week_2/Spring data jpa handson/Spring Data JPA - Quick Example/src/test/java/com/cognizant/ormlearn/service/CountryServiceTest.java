package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for CountryService
 * 
 * This test class demonstrates:
 * - Spring Boot Test configuration
 * - Integration testing with H2 database
 * - JPA repository testing
 * - Service layer testing
 * - Transaction management in tests
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CountryServiceTest {

    @Autowired
    private CountryService countryService;

    @Autowired
    private CountryRepository countryRepository;

    @BeforeEach
    void setUp() {
        // Clean up database before each test
        countryRepository.deleteAll();
    }

    @Test
    void testCreateCountry() {
        // Given
        String countryCode = "TST";
        String isoCode = "TS";
        String countryName = "Test Country";

        // When
        Country createdCountry = countryService.createCountry(countryCode, isoCode, countryName);

        // Then
        assertNotNull(createdCountry);
        assertNotNull(createdCountry.getId());
        assertEquals(countryCode, createdCountry.getCountryCode());
        assertEquals(isoCode, createdCountry.getIsoCode());
        assertEquals(countryName, createdCountry.getCountryName());
        assertNotNull(createdCountry.getCreatedAt());
        assertNotNull(createdCountry.getUpdatedAt());
    }

    @Test
    void testCreateDuplicateCountryCode() {
        // Given
        String countryCode = "DUP";
        countryService.createCountry(countryCode, "DP", "Duplicate Country");

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            countryService.createCountry(countryCode, "D2", "Another Duplicate");
        });
    }

    @Test
    void testGetAllCountries() {
        // Given
        countryService.createCountry("US1", "U1", "Test Country 1");
        countryService.createCountry("US2", "U2", "Test Country 2");
        countryService.createCountry("US3", "U3", "Test Country 3");

        // When
        List<Country> countries = countryService.getAllCountries();

        // Then
        assertEquals(3, countries.size());
    }

    @Test
    void testGetCountryById() {
        // Given
        Country createdCountry = countryService.createCountry("FND", "FN", "Find Country");

        // When
        Country foundCountry = countryService.getCountryById(createdCountry.getId());

        // Then
        assertNotNull(foundCountry);
        assertEquals(createdCountry.getId(), foundCountry.getId());
        assertEquals("FND", foundCountry.getCountryCode());
        assertEquals("Find Country", foundCountry.getCountryName());
    }

    @Test
    void testGetCountryByIdNotFound() {
        // When & Then
        assertThrows(RuntimeException.class, () -> {
            countryService.getCountryById(999L);
        });
    }

    @Test
    void testUpdateCountry() {
        // Given
        Country country = countryService.createCountry("UPD", "UP", "Update Country");
        country.setCountryName("Updated Country Name");

        // When
        Country updatedCountry = countryService.updateCountry(country);

        // Then
        assertEquals("Updated Country Name", updatedCountry.getCountryName());
        assertEquals(country.getId(), updatedCountry.getId());
    }

    @Test
    void testDeleteCountry() {
        // Given
        Country country = countryService.createCountry("DEL", "DL", "Delete Country");
        Long countryId = country.getId();

        // When
        countryService.deleteCountry(countryId);

        // Then
        assertThrows(RuntimeException.class, () -> {
            countryService.getCountryById(countryId);
        });
    }

    @Test
    void testFindByCountryCode() {
        // Given
        countryService.createCountry("SRC", "SR", "Search Country");

        // When
        Country foundCountry = countryService.findByCountryCode("SRC");

        // Then
        assertNotNull(foundCountry);
        assertEquals("SRC", foundCountry.getCountryCode());
        assertEquals("Search Country", foundCountry.getCountryName());
    }

    @Test
    void testFindByCountryCodeNotFound() {
        // When
        Country foundCountry = countryService.findByCountryCode("NON");

        // Then
        assertNull(foundCountry);
    }

    @Test
    void testFindByIsoCode() {
        // Given
        countryService.createCountry("ISO1", "IS", "ISO Country 1");
        countryService.createCountry("ISO2", "IS", "ISO Country 2");

        // When
        List<Country> countries = countryService.findByIsoCode("IS");

        // Then
        assertEquals(2, countries.size());
    }

    @Test
    void testFindByCountryNameContaining() {
        // Given
        countryService.createCountry("US1", "U1", "United States");
        countryService.createCountry("UK1", "U2", "United Kingdom");
        countryService.createCountry("FR1", "F1", "France");

        // When
        List<Country> countries = countryService.findByCountryNameContaining("United");

        // Then
        assertEquals(2, countries.size());
        assertTrue(countries.stream().allMatch(c -> c.getCountryName().contains("United")));
    }

    @Test
    void testGetCountryCount() {
        // Given
        countryService.createCountry("CNT1", "C1", "Count Country 1");
        countryService.createCountry("CNT2", "C2", "Count Country 2");

        // When
        long count = countryService.getCountryCount();

        // Then
        assertEquals(2, count);
    }

    @Test
    void testExistsByCountryCode() {
        // Given
        countryService.createCountry("EXT", "EX", "Exists Country");

        // When & Then
        assertTrue(countryService.existsByCountryCode("EXT"));
        assertFalse(countryService.existsByCountryCode("NEX"));
    }

    @Test
    void testFindAllCountryCodes() {
        // Given
        countryService.createCountry("CD1", "C1", "Code Country 1");
        countryService.createCountry("CD2", "C2", "Code Country 2");
        countryService.createCountry("CD3", "C3", "Code Country 3");

        // When
        List<String> codes = countryService.findAllCountryCodes();

        // Then
        assertEquals(3, codes.size());
        assertTrue(codes.contains("CD1"));
        assertTrue(codes.contains("CD2"));
        assertTrue(codes.contains("CD3"));
    }

    @Test
    void testFindCountriesWithLongNames() {
        // Given
        countryService.createCountry("SHT", "SH", "Short");
        countryService.createCountry("LNG", "LG", "Very Long Country Name");

        // When
        List<Country> longNameCountries = countryService.findCountriesWithLongNames(10);

        // Then
        assertEquals(1, longNameCountries.size());
        assertEquals("Very Long Country Name", longNameCountries.get(0).getCountryName());
    }
}
