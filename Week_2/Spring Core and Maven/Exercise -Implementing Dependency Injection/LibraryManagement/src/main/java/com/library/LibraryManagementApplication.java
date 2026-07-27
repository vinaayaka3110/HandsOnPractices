package com.library;

import com.library.config.LibraryConfig;
import com.library.service.BookService;
import com.library.service.LibraryManager;
import com.library.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LibraryManagementApplication {
    private static final Logger logger = LoggerFactory.getLogger(LibraryManagementApplication.class);

    public static void main(String[] args) {
        logger.info("Starting Enhanced Library Management Application with Dependency Injection...");

        try {
            // Load Spring Application Context
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            logger.info("Spring Application Context loaded successfully");

            // Demonstrate different types of dependency injection
            demonstrateDependencyInjection(context);

        } catch (Exception e) {
            logger.error("Error occurred while running the application", e);
        }

        logger.info("Enhanced Library Management Application finished.");
    }

    private static void demonstrateDependencyInjection(ApplicationContext context) {
        logger.info("=== Demonstrating Dependency Injection Types ===");

        // 1. Test Setter-based Dependency Injection
        logger.info("\n--- 1. Setter-based Dependency Injection ---");
        testSetterBasedInjection(context);

        // 2. Test Constructor-based Dependency Injection
        logger.info("\n--- 2. Constructor-based Dependency Injection ---");
        testConstructorBasedInjection(context);

        // 3. Test Complex Multi-level Dependency Injection
        logger.info("\n--- 3. Multi-level Dependency Injection ---");
        testMultiLevelInjection(context);

        // 4. Test Configuration Injection
        logger.info("\n--- 4. Configuration Property Injection ---");
        testConfigurationInjection(context);
    }

    private static void testSetterBasedInjection(ApplicationContext context) {
        logger.info("Testing Setter-based Dependency Injection:");
        
        // Get beans configured with setter injection
        BookService bookService = context.getBean("bookService", BookService.class);
        BookRepository bookRepository = context.getBean("bookRepository", BookRepository.class);

        // Verify dependencies are injected
        logger.info("BookService dependency injection status: {}", bookService.isDependencyInjected());
        logger.info("Service configuration: {}", bookService.getServiceSummary());
        logger.info("Repository details: {} (Capacity: {})", 
                   bookRepository.getRepositoryName(), bookRepository.getMaxCapacity());

        // Test functionality
        logger.info("Testing functionality with {} books", bookService.getTotalBooksCount());
        bookService.testRepositoryConnection();
    }

    private static void testConstructorBasedInjection(ApplicationContext context) {
        logger.info("Testing Constructor-based Dependency Injection:");
        
        // Get beans configured with constructor injection
        BookService constructorBookService = context.getBean("bookServiceWithConstructor", BookService.class);
        BookRepository constructorBookRepository = context.getBean("bookRepositoryWithConstructor", BookRepository.class);

        // Verify dependencies are injected
        logger.info("Constructor-based BookService dependency injection status: {}", 
                   constructorBookService.isDependencyInjected());
        logger.info("Constructor service configuration: {}", constructorBookService.getServiceSummary());
        logger.info("Constructor repository details: {} (Capacity: {})", 
                   constructorBookRepository.getRepositoryName(), 
                   constructorBookRepository.getMaxCapacity());

        // Test functionality
        logger.info("Testing constructor-based service with {} books", 
                   constructorBookService.getTotalBooksCount());
        constructorBookService.testRepositoryConnection();
    }

    private static void testMultiLevelInjection(ApplicationContext context) {
        logger.info("Testing Multi-level Dependency Injection:");
        
        // Get LibraryManager bean which has multiple dependencies
        LibraryManager libraryManager = context.getBean("libraryManager", LibraryManager.class);

        // Verify all dependencies are properly injected
        boolean allDependenciesInjected = libraryManager.verifyDependencyInjection();
        logger.info("All dependencies properly injected: {}", allDependenciesInjected);

        if (allDependenciesInjected) {
            // Initialize and run library operations
            libraryManager.initializeLibrary();
            libraryManager.performLibraryOperations();
        } else {
            logger.error("Dependency injection failed for LibraryManager");
        }
    }

    private static void testConfigurationInjection(ApplicationContext context) {
        logger.info("Testing Configuration Property Injection:");
        
        // Get configuration bean
        LibraryConfig config = context.getBean("libraryConfig", LibraryConfig.class);
        config.displayConfiguration();

        // Test individual property injection
        logger.info("Configuration properties successfully injected:");
        logger.info("  - Application: {}", config.getApplicationName());
        logger.info("  - Version: {}", config.getVersion());
        logger.info("  - Logging: {}", config.getEnableLogging());
        logger.info("  - Caching: {}", config.getEnableCaching());
    }
}
