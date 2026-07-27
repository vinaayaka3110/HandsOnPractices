package com.cognizant.ormlearn.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Country Entity class representing a country in the database
 * 
 * This entity demonstrates:
 * - JPA annotations for entity mapping
 * - Primary key generation strategies
 * - Column mappings and constraints
 * - Validation annotations
 * - Lifecycle callbacks
 */
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Long id;

    @NotBlank(message = "Country code is required")
    @Size(min = 2, max = 3, message = "Country code must be 2-3 characters")
    @Column(name = "country_code", nullable = false, unique = true, length = 3)
    private String countryCode;

    @NotBlank(message = "ISO code is required")
    @Size(min = 2, max = 2, message = "ISO code must be exactly 2 characters")
    @Column(name = "iso_code", nullable = false, length = 2)
    private String isoCode;

    @NotBlank(message = "Country name is required")
    @Size(min = 2, max = 100, message = "Country name must be between 2 and 100 characters")
    @Column(name = "country_name", nullable = false, length = 100)
    private String countryName;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Default constructor (required by JPA)
    public Country() {
    }

    // Constructor with required fields
    public Country(String countryCode, String isoCode, String countryName) {
        this.countryCode = countryCode;
        this.isoCode = isoCode;
        this.countryName = countryName;
    }

    // JPA lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // toString method for logging
    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", countryCode='" + countryCode + '\'' +
                ", isoCode='" + isoCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    // equals and hashCode methods (based on natural key - countryCode)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;
        return countryCode != null ? countryCode.equals(country.countryCode) : country.countryCode == null;
    }

    @Override
    public int hashCode() {
        return countryCode != null ? countryCode.hashCode() : 0;
    }
}
