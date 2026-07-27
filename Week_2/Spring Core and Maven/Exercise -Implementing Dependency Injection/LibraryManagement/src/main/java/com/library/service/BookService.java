package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    
    private BookRepository bookRepository;
    private String serviceName;
    private Integer maxBooksPerUser;
    private Integer loanPeriodDays;

    // Default constructor for setter injection
    public BookService() {
        logger.info("BookService initialized with default constructor");
    }

    // Constructor for constructor injection
    public BookService(BookRepository bookRepository, String serviceName, Integer maxBooksPerUser, Integer loanPeriodDays) {
        this.bookRepository = bookRepository;
        this.serviceName = serviceName;
        this.maxBooksPerUser = maxBooksPerUser;
        this.loanPeriodDays = loanPeriodDays;
        logger.info("BookService initialized with constructor injection - Service: {}, Max Books: {}, Loan Period: {} days", 
                   serviceName, maxBooksPerUser, loanPeriodDays);
    }

    public Book addBook(String title, String author, String isbn, String category, Double price) {
        logger.info("Adding new book: {} by {} in category: {}", title, author, category);
        
        if (bookRepository.isAtCapacity()) {
            logger.error("Cannot add book - repository at capacity");
            throw new RuntimeException("Repository at maximum capacity");
        }
        
        Book book = new Book(null, title, author, isbn, category, price);
        return bookRepository.save(book);
    }

    public Book findBookById(Long id) {
        logger.debug("Finding book with ID: {}", id);
        Book book = bookRepository.findById(id);
        if (book == null) {
            logger.warn("Book not found with ID: {}", id);
        }
        return book;
    }

    public List<Book> getAllBooks() {
        logger.debug("Retrieving all books using service: {}", serviceName);
        List<Book> books = bookRepository.findAll();
        logger.info("Found {} books in the library", books.size());
        return books;
    }

    public List<Book> getAvailableBooks() {
        logger.debug("Retrieving all available books");
        List<Book> availableBooks = bookRepository.findAvailableBooks();
        logger.info("Found {} available books", availableBooks.size());
        return availableBooks;
    }

    public List<Book> searchBooksByTitle(String title) {
        logger.info("Searching books by title: '{}'", title);
        return bookRepository.findByTitle(title);
    }

    public List<Book> searchBooksByAuthor(String author) {
        logger.info("Searching books by author: '{}'", author);
        return bookRepository.findByAuthor(author);
    }

    public List<Book> searchBooksByCategory(String category) {
        logger.info("Searching books by category: '{}'", category);
        return bookRepository.findByCategory(category);
    }

    public List<Book> searchBooksByPriceRange(Double minPrice, Double maxPrice) {
        logger.info("Searching books in price range: {} - {}", minPrice, maxPrice);
        return bookRepository.findBooksByPriceRange(minPrice, maxPrice);
    }

    public Book updateBook(Long id, String title, String author, String isbn, String category, Double price) {
        logger.info("Updating book with ID: {}", id);
        Book existingBook = bookRepository.findById(id);
        if (existingBook != null) {
            existingBook.setTitle(title);
            existingBook.setAuthor(author);
            existingBook.setIsbn(isbn);
            existingBook.setCategory(category);
            existingBook.setPrice(price);
            return bookRepository.save(existingBook);
        } else {
            logger.error("Cannot update book - book not found with ID: {}", id);
            throw new RuntimeException("Book not found with ID: " + id);
        }
    }

    public void deleteBook(Long id) {
        logger.info("Deleting book with ID: {}", id);
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            logger.info("Book deleted successfully with ID: {}", id);
        } else {
            logger.error("Cannot delete book - book not found with ID: {}", id);
            throw new RuntimeException("Book not found with ID: " + id);
        }
    }

    public boolean borrowBook(Long id, String userId) {
        logger.info("Processing book borrow request for ID: {} by user: {}", id, userId);
        
        // Check user's current book count (simplified logic)
        long userBorrowedBooks = getUserBorrowedBooksCount(userId);
        if (userBorrowedBooks >= maxBooksPerUser) {
            logger.warn("User {} has reached maximum books limit: {}", userId, maxBooksPerUser);
            return false;
        }
        
        Book book = bookRepository.findById(id);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            bookRepository.save(book);
            logger.info("Book borrowed successfully: {} for {} days", book.getTitle(), loanPeriodDays);
            return true;
        } else if (book != null && !book.isAvailable()) {
            logger.warn("Book is not available for borrowing: {}", book.getTitle());
            return false;
        } else {
            logger.error("Book not found with ID: {}", id);
            return false;
        }
    }

    public boolean returnBook(Long id, String userId) {
        logger.info("Processing book return for ID: {} by user: {}", id, userId);
        Book book = bookRepository.findById(id);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            bookRepository.save(book);
            logger.info("Book returned successfully: {}", book.getTitle());
            return true;
        } else if (book != null && book.isAvailable()) {
            logger.warn("Book is already available: {}", book.getTitle());
            return false;
        } else {
            logger.error("Book not found with ID: {}", id);
            return false;
        }
    }

    public long getTotalBooksCount() {
        return bookRepository.count();
    }

    public long getAvailableBooksCount() {
        return bookRepository.findAvailableBooks().size();
    }

    public long getBorrowedBooksCount() {
        return getTotalBooksCount() - getAvailableBooksCount();
    }

    private long getUserBorrowedBooksCount(String userId) {
        // Simplified logic - in real application, this would query user borrowing records
        return 2; // Mock value
    }

    public void testRepositoryConnection() {
        logger.info("Testing repository connection from service: {}", serviceName);
        bookRepository.testConnection();
    }

    public String getServiceSummary() {
        return String.format("Service: %s, Max Books Per User: %d, Loan Period: %d days, Repository: %s", 
                           serviceName, maxBooksPerUser, loanPeriodDays, 
                           bookRepository != null ? bookRepository.getRepositoryName() : "Not Configured");
    }

    // Dependency injection validation
    public boolean isDependencyInjected() {
        boolean isInjected = bookRepository != null;
        logger.info("Dependency injection status - BookRepository: {}", isInjected ? "INJECTED" : "NOT INJECTED");
        return isInjected;
    }

    // Getters and Setters for Setter Injection
    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        logger.info("BookRepository injected into BookService via setter");
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
        logger.info("Service name set to: {}", serviceName);
    }

    public Integer getMaxBooksPerUser() {
        return maxBooksPerUser;
    }

    public void setMaxBooksPerUser(Integer maxBooksPerUser) {
        this.maxBooksPerUser = maxBooksPerUser;
        logger.info("Max books per user set to: {}", maxBooksPerUser);
    }

    public Integer getLoanPeriodDays() {
        return loanPeriodDays;
    }

    public void setLoanPeriodDays(Integer loanPeriodDays) {
        this.loanPeriodDays = loanPeriodDays;
        logger.info("Loan period set to: {} days", loanPeriodDays);
    }
}
