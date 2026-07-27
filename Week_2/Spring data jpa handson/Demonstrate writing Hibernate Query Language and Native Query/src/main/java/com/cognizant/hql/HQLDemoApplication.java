package com.cognizant.hql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application demonstrating HQL, JPQL, and Native Queries
 * 
 * This application demonstrates:
 * - HQL (Hibernate Query Language) examples
 * - JPQL (Java Persistence Query Language) examples
 * - Comparison between HQL and JPQL
 * - @Query annotation usage
 * - HQL fetch keyword for eager loading
 * - Aggregate functions in HQL (COUNT, SUM, AVG, MIN, MAX)
 * - Native SQL queries
 * - nativeQuery attribute in @Query
 * - Named queries and dynamic queries
 * - Query parameters (positional and named)
 * - Projection queries
 * - Subqueries and joins
 */
@SpringBootApplication
public class HQLDemoApplication implements CommandLineRunner {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(HQLDemoApplication.class);
    
    public static void main(String[] args) {
        LOGGER.info("Starting Hibernate Query Language (HQL) and Native Query Demonstration Application...");
        SpringApplication.run(HQLDemoApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("=".repeat(80));
        LOGGER.info("HQL DEMO APPLICATION STARTED SUCCESSFULLY!");
        LOGGER.info("=".repeat(80));
        LOGGER.info("H2 Console available at: http://localhost:8080/h2-console");
        LOGGER.info("JDBC URL: jdbc:h2:mem:hqldb");
        LOGGER.info("Username: sa, Password: password");
        LOGGER.info("=".repeat(80));
        LOGGER.info("REST API Endpoints:");
        LOGGER.info("  - Health Check: http://localhost:8080/api/hql-demo/health");
        LOGGER.info("  - Database Test: http://localhost:8080/api/hql-demo/test");
        LOGGER.info("  - Run HQL Demos: http://localhost:8080/api/hql-demo/run-all");
        LOGGER.info("=".repeat(80));
        LOGGER.info("Ready to serve requests!");
    }
}
