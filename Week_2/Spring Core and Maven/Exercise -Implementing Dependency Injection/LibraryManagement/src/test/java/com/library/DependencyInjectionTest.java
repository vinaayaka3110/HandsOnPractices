package com.library;

import com.library.config.LibraryConfig;
import com.library.model.Book;
import com.library.service.BookService;
import com.library.service.LibraryManager;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DependencyInjectionTest {

    private ApplicationContext context;

    @BeforeEach
    void setUp() {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Test
    void testSpringContextLoads() {
        assertNotNull(context, "Spring context should not be null");
    }

    @Test
    void testSetterBasedDependencyInjection() {
        // Get beans configured with setter injection
        BookService bookService = context.getBean("bookService", BookService.class);
        BookRepository bookRepository = context.getBean("bookRepository", BookRepository.class);

        // Verify beans are not null
        assertNotNull(bookService, "BookService should not be null");
        assertNotNull(bookRepository, "BookRepository should not be null");

        // Verify dependency injection
        assertTrue(bookService.isDependencyInjected(), "BookRepository should be injected into BookService");
        assertNotNull(bookService.getBookRepository(), "BookService should have BookRepository injected");

        // Verify property injection
        assertEquals("Enhanced Library Book Management Service", bookService.getServiceName());
        assertEquals(Integer.valueOf(5), bookService.getMaxBooksPerUser());
        assertEquals(Integer.valueOf(14), bookService.getLoanPeriodDays());

        assertEquals("Advanced Library Database Repository", bookRepository.getRepositoryName());
        assertEquals(Integer.valueOf(1000), bookRepository.getMaxCapacity());
        assertEquals(Integer.valueOf(30), bookRepository.getConnectionTimeout());
    }

    @Test
    void testConstructorBasedDependencyInjection() {
        // Get beans configured with constructor injection
        BookService constructorBookService = context.getBean("bookServiceWithConstructor", BookService.class);
        BookRepository constructorBookRepository = context.getBean("bookRepositoryWithConstructor", BookRepository.class);

        // Verify beans are not null
        assertNotNull(constructorBookService, "Constructor BookService should not be null");
        assertNotNull(constructorBookRepository, "Constructor BookRepository should not be null");

        // Verify constructor injection
        assertTrue(constructorBookService.isDependencyInjected(), 
                  "BookRepository should be injected via constructor");

        // Verify constructor parameter injection
        assertEquals("Constructor-Based Book Service", constructorBookService.getServiceName());
        assertEquals(Integer.valueOf(3), constructorBookService.getMaxBooksPerUser());
        assertEquals(Integer.valueOf(21), constructorBookService.getLoanPeriodDays());

        assertEquals("Constructor-Based Repository", constructorBookRepository.getRepositoryName());
        assertEquals(Integer.valueOf(500), constructorBookRepository.getMaxCapacity());
        assertEquals(Integer.valueOf(60), constructorBookRepository.getConnectionTimeout());
    }

    @Test
    void testMultiLevelDependencyInjection() {
        // Get LibraryManager bean with multiple dependencies
        LibraryManager libraryManager = context.getBean("libraryManager", LibraryManager.class);

        // Verify LibraryManager is not null
        assertNotNull(libraryManager, "LibraryManager should not be null");

        // Verify all dependencies are injected
        assertNotNull(libraryManager.getBookService(), "BookService should be injected into LibraryManager");
        assertNotNull(libraryManager.getBookRepository(), "BookRepository should be injected into LibraryManager");

        // Verify property injection
        assertEquals("Chief Library Manager", libraryManager.getManagerName());
        assertEquals("8:00 AM - 6:00 PM", libraryManager.getOperatingHours());

        // Verify complex dependency chain
        assertTrue(libraryManager.verifyDependencyInjection(), 
                  "All dependencies should be properly injected");
    }

    @Test
    void testConfigurationPropertyInjection() {
        // Get configuration bean
        LibraryConfig config = context.getBean("libraryConfig", LibraryConfig.class);

        // Verify configuration is not null
        assertNotNull(config, "LibraryConfig should not be null");

        // Verify property injection
        assertEquals("Digital Library Management System", config.getApplicationName());
        assertEquals("2.0", config.getVersion());
        assertTrue(config.getEnableLogging(), "Logging should be enabled");
        assertFalse(config.getEnableCaching(), "Caching should be disabled");
    }

    @Test
    void testBookServiceFunctionality() {
        BookService bookService = context.getBean("bookService", BookService.class);

        // Test basic functionality
        List<Book> allBooks = bookService.getAllBooks();
        assertNotNull(allBooks, "Books list should not be null");
        assertTrue(allBooks.size() > 0, "Should have books in the repository");

        // Test book operations
        Book foundBook = bookService.findBookById(1L);
        assertNotNull(foundBook, "Should find book with ID 1");

        // Test search functionality
        List<Book> techBooks = bookService.searchBooksByCategory("Technology");
        assertNotNull(techBooks, "Technology books search should not be null");

        // Test statistics
        long totalBooks = bookService.getTotalBooksCount();
        long availableBooks = bookService.getAvailableBooksCount();
        assertTrue(totalBooks > 0, "Should have books in total");
        assertTrue(availableBooks <= totalBooks, "Available books should not exceed total");
    }

    @Test
    void testBorrowingWorkflow() {
        BookService bookService = context.getBean("bookService", BookService.class);

        // Test borrowing
        boolean borrowResult = bookService.borrowBook(1L, "TEST_USER");
        assertTrue(borrowResult, "Should be able to borrow available book");

        // Verify book is no longer available
        Book borrowedBook = bookService.findBookById(1L);
        assertFalse(borrowedBook.isAvailable(), "Book should not be available after borrowing");

        // Test returning
        boolean returnResult = bookService.returnBook(1L, "TEST_USER");
        assertTrue(returnResult, "Should be able to return borrowed book");

        // Verify book is available again
        Book returnedBook = bookService.findBookById(1L);
        assertTrue(returnedBook.isAvailable(), "Book should be available after returning");
    }

    @Test
    void testRepositoryCapacityConstraints() {
        BookRepository repository = context.getBean("bookRepository", BookRepository.class);

        // Test capacity settings
        assertEquals(Integer.valueOf(1000), repository.getMaxCapacity());
        assertFalse(repository.isAtCapacity(), "Repository should not be at capacity initially");

        // Test connection functionality
        assertDoesNotThrow(() -> repository.testConnection(), 
                          "Repository connection test should not throw exception");
    }

    @Test
    void testBeanScopeAndLifecycle() {
        // Test singleton scope (default)
        BookService service1 = context.getBean("bookService", BookService.class);
        BookService service2 = context.getBean("bookService", BookService.class);

        assertSame(service1, service2, "BookService beans should be the same instance (singleton scope)");

        BookRepository repo1 = context.getBean("bookRepository", BookRepository.class);
        BookRepository repo2 = context.getBean("bookRepository", BookRepository.class);

        assertSame(repo1, repo2, "BookRepository beans should be the same instance (singleton scope)");
    }

    @Test
    void testDifferentInjectionConfigurations() {
        // Compare setter-based and constructor-based configurations
        BookService setterService = context.getBean("bookService", BookService.class);
        BookService constructorService = context.getBean("bookServiceWithConstructor", BookService.class);

        // Both should have dependencies injected but with different configurations
        assertTrue(setterService.isDependencyInjected(), "Setter-based service should have dependencies");
        assertTrue(constructorService.isDependencyInjected(), "Constructor-based service should have dependencies");

        // They should be different instances
        assertNotSame(setterService, constructorService, "Services should be different instances");

        // But both should work functionally
        assertNotNull(setterService.getAllBooks(), "Setter service should return books");
        assertNotNull(constructorService.getAllBooks(), "Constructor service should return books");
    }
}
