package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LibraryManager {
    private static final Logger logger = LoggerFactory.getLogger(LibraryManager.class);
    
    private BookService bookService;
    private BookRepository bookRepository;
    private String managerName;
    private String operatingHours;

    public LibraryManager() {
        logger.info("LibraryManager initialized");
    }

    public void initializeLibrary() {
        logger.info("Initializing library under manager: {}", managerName);
        logger.info("Operating hours: {}", operatingHours);
        
        if (bookService != null && bookRepository != null) {
            logger.info("All dependencies successfully injected");
            bookRepository.testConnection();
            displayLibraryStatus();
        } else {
            logger.error("Dependencies not properly injected - BookService: {}, BookRepository: {}", 
                        bookService != null, bookRepository != null);
        }
    }

    public void performLibraryOperations() {
        logger.info("=== Library Manager performing operations ===");
        
        // Display current library status
        displayLibraryStatus();
        
        // Add a new book through the service
        try {
            Book newBook = bookService.addBook("Dependency Injection Guide", "Spring Team", 
                                             "978-0-123456-78-9", "Technology", 35.99);
            logger.info("Manager added new book: {}", newBook.getTitle());
        } catch (Exception e) {
            logger.error("Failed to add book", e);
        }
        
        // Perform search operations
        performSearchOperations();
        
        // Test borrowing workflow
        testBorrowingWorkflow();
        
        // Generate library report
        generateLibraryReport();
    }

    private void displayLibraryStatus() {
        logger.info("=== Library Status ===");
        logger.info("Manager: {}", managerName);
        logger.info("Operating Hours: {}", operatingHours);
        logger.info("Total Books: {}", bookService.getTotalBooksCount());
        logger.info("Available Books: {}", bookService.getAvailableBooksCount());
        logger.info("Borrowed Books: {}", bookService.getBorrowedBooksCount());
        logger.info("Repository: {}", bookRepository.getRepositoryName());
        logger.info("Repository Capacity: {}", bookRepository.getMaxCapacity());
        logger.info("Service Configuration: {}", bookService.getServiceSummary());
    }

    private void performSearchOperations() {
        logger.info("=== Performing Search Operations ===");
        
        // Search by category
        List<Book> techBooks = bookService.searchBooksByCategory("Technology");
        logger.info("Found {} technology books", techBooks.size());
        
        // Search by author
        List<Book> georgeOrwellBooks = bookService.searchBooksByAuthor("George");
        logger.info("Found {} books by authors containing 'George'", georgeOrwellBooks.size());
        
        // Search by price range
        List<Book> affordableBooks = bookService.searchBooksByPriceRange(10.0, 20.0);
        logger.info("Found {} books in price range $10-$20", affordableBooks.size());
    }

    private void testBorrowingWorkflow() {
        logger.info("=== Testing Borrowing Workflow ===");
        
        String testUserId = "USER001";
        Long testBookId = 1L;
        
        // Borrow a book
        boolean borrowResult = bookService.borrowBook(testBookId, testUserId);
        logger.info("Borrow result for book ID {}: {}", testBookId, borrowResult);
        
        // Try to borrow the same book again
        boolean secondBorrowResult = bookService.borrowBook(testBookId, testUserId);
        logger.info("Second borrow attempt result: {}", secondBorrowResult);
        
        // Return the book
        boolean returnResult = bookService.returnBook(testBookId, testUserId);
        logger.info("Return result for book ID {}: {}", testBookId, returnResult);
    }

    private void generateLibraryReport() {
        logger.info("=== Library Management Report ===");
        
        List<Book> allBooks = bookService.getAllBooks();
        List<Book> availableBooks = bookService.getAvailableBooks();
        
        logger.info("Library managed by: {}", managerName);
        logger.info("Total books in collection: {}", allBooks.size());
        logger.info("Books currently available: {}", availableBooks.size());
        logger.info("Books currently borrowed: {}", allBooks.size() - availableBooks.size());
        
        // Display books by category
        allBooks.stream()
                .collect(java.util.stream.Collectors.groupingBy(Book::getCategory))
                .forEach((category, books) -> 
                    logger.info("Category '{}': {} books", category, books.size()));
        
        // Calculate total collection value
        double totalValue = allBooks.stream()
                                  .mapToDouble(Book::getPrice)
                                  .sum();
        logger.info("Total collection value: ${}", String.format("%.2f", totalValue));
        
        logger.info("Repository configuration: {} (Max capacity: {})", 
                   bookRepository.getRepositoryName(), bookRepository.getMaxCapacity());
    }

    public boolean verifyDependencyInjection() {
        boolean allDependenciesInjected = bookService != null && 
                                        bookRepository != null && 
                                        bookService.isDependencyInjected();
        
        logger.info("Dependency injection verification:");
        logger.info("BookService injected: {}", bookService != null);
        logger.info("BookRepository injected: {}", bookRepository != null);
        logger.info("BookService->BookRepository injected: {}", 
                   bookService != null ? bookService.isDependencyInjected() : false);
        logger.info("All dependencies properly injected: {}", allDependenciesInjected);
        
        return allDependenciesInjected;
    }

    // Getters and Setters
    public BookService getBookService() {
        return bookService;
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
        logger.info("BookService injected into LibraryManager");
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        logger.info("BookRepository injected into LibraryManager");
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
        logger.info("Manager name set to: {}", managerName);
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
        logger.info("Operating hours set to: {}", operatingHours);
    }
}
