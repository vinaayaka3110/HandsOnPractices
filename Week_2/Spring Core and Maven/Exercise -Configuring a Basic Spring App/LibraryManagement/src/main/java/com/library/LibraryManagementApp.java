package com.library;

import com.library.model.Book;
import com.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class LibraryManagementApp {
    private static final Logger logger = LoggerFactory.getLogger(LibraryManagementApp.class);

    public static void main(String[] args) {
        logger.info("Starting Library Management Application...");

        try {
            // Load Spring Application Context
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            logger.info("Spring Application Context loaded successfully");

            // Get BookService bean from Spring container
            BookService bookService = context.getBean("bookService", BookService.class);
            logger.info("BookService bean retrieved from Spring container");

            // Test the application
            testLibraryOperations(bookService);

        } catch (Exception e) {
            logger.error("Error occurred while running the application", e);
        }

        logger.info("Library Management Application finished.");
    }

    private static void testLibraryOperations(BookService bookService) {
        logger.info("=== Testing Library Management Operations ===");

        // Display all books
        logger.info("--- Displaying All Books ---");
        List<Book> allBooks = bookService.getAllBooks();
        allBooks.forEach(book -> logger.info("Book: {}", book));

        // Add a new book
        logger.info("--- Adding New Book ---");
        Book newBook = bookService.addBook("Spring in Action", "Craig Walls", "978-1-935182-35-1");
        logger.info("Added new book: {}", newBook);

        // Search books by title
        logger.info("--- Searching Books by Title ---");
        List<Book> booksFoundByTitle = bookService.searchBooksByTitle("Spring");
        booksFoundByTitle.forEach(book -> logger.info("Found by title: {}", book));

        // Search books by author
        logger.info("--- Searching Books by Author ---");
        List<Book> booksFoundByAuthor = bookService.searchBooksByAuthor("George");
        booksFoundByAuthor.forEach(book -> logger.info("Found by author: {}", book));

        // Borrow a book
        logger.info("--- Borrowing Book ---");
        boolean borrowResult = bookService.borrowBook(1L);
        logger.info("Borrow result for book ID 1: {}", borrowResult);

        // Try to borrow the same book again
        logger.info("--- Trying to Borrow Same Book Again ---");
        boolean borrowResult2 = bookService.borrowBook(1L);
        logger.info("Second borrow attempt for book ID 1: {}", borrowResult2);

        // Return the book
        logger.info("--- Returning Book ---");
        boolean returnResult = bookService.returnBook(1L);
        logger.info("Return result for book ID 1: {}", returnResult);

        // Update a book
        logger.info("--- Updating Book ---");
        try {
            Book updatedBook = bookService.updateBook(2L, "To Kill a Mockingbird - Updated", "Harper Lee", "978-0-06-112008-4");
            logger.info("Updated book: {}", updatedBook);
        } catch (Exception e) {
            logger.error("Error updating book", e);
        }

        // Display statistics
        logger.info("--- Library Statistics ---");
        logger.info("Total books: {}", bookService.getTotalBooksCount());
        logger.info("Available books: {}", bookService.getAvailableBooksCount());

        // Find a specific book
        logger.info("--- Finding Specific Book ---");
        Book foundBook = bookService.findBookById(3L);
        if (foundBook != null) {
            logger.info("Found book: {}", foundBook);
        } else {
            logger.warn("Book not found");
        }

        logger.info("=== Library Management Operations Completed ===");
    }
}
