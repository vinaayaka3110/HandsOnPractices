-- SQL Schema for Country table
-- This file demonstrates DDL operations and will be executed automatically by Spring Boot

-- Drop table if exists (for clean setup)
DROP TABLE IF EXISTS countries;

-- Create countries table
CREATE TABLE countries (
    country_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    country_code VARCHAR(3) NOT NULL UNIQUE,
    iso_code VARCHAR(2) NOT NULL,
    country_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create indexes for better query performance
CREATE INDEX idx_country_code ON countries(country_code);
CREATE INDEX idx_iso_code ON countries(iso_code);
CREATE INDEX idx_country_name ON countries(country_name);

-- Insert some sample data (optional)
INSERT INTO countries (country_code, iso_code, country_name) VALUES
('USA', 'US', 'United States'),
('GBR', 'GB', 'United Kingdom'),
('IND', 'IN', 'India'),
('DEU', 'DE', 'Germany'),
('FRA', 'FR', 'France'),
('JPN', 'JP', 'Japan'),
('CHN', 'CN', 'China'),
('CAN', 'CA', 'Canada'),
('AUS', 'AU', 'Australia'),
('BRA', 'BR', 'Brazil');
