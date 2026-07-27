package com.library.repository;

import com.library.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookRepository {
    private static final Logger logger = LoggerFactory.getLogger(BookRepository.class);
    
    private String repositoryName;
    private Integer maxCapacity;
    private Integer connectionTimeout;
    private Map<Long, Book> books;

    // Default constructor for setter injection
    public BookRepository() {
        this.books = new HashMap<>();
        initializeData();
        logger.info("BookRepository initialized with default constructor");
    }

    // Constructor for constructor injection
    public BookRepository(String repositoryName, Integer maxCapacity, Integer connectionTimeout) {
        this.repositoryName = repositoryName;
        this.maxCapacity = maxCapacity;
        this.connectionTimeout = connectionTimeout;
        this.books = new HashMap<>();
        initializeData();
        logger.info("BookRepository initialized with constructor injection - Name: {}, Capacity: {}, Timeout: {}", 
                   repositoryName, maxCapacity, connectionTimeout);
    }

    private void initializeData() {
        // Initialize with enhanced sample data
        books.put(1L, new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald", "978-0-7432-7356-5", "Fiction", 12.99));
        books.put(2L, new Book(2L, "To Kill a Mockingbird", "Harper Lee", "978-0-06-112008-4", "Fiction", 14.99));
        books.put(3L, new Book(3L, "1984", "George Orwell", "978-0-452-28423-4", "Dystopian", 13.99));
        books.put(4L, new Book(4L, "Pride and Prejudice", "Jane Austen", "978-0-14-143951-8", "Romance", 11.99));
        books.put(5L, new Book(5L, "The Catcher in the Rye", "J.D. Salinger", "978-0-316-76948-0", "Fiction", 13.50));
        books.put(6L, new Book(6L, "Spring in Action", "Craig Walls", "978-1-935182-35-1", "Technology", 49.99));
        books.put(7L, new Book(7L, "Clean Code", "Robert C. Martin", "978-0-13-235088-4", "Technology", 42.99));
        
        logger.debug("Initialized repository with {} books", books.size());
    }

    public Book findById(Long id) {
        logger.debug("Finding book with ID: {}", id);
        Book book = books.get(id);
        if (book == null) {
            logger.warn("Book not found with ID: {}", id);
        }
        return book;
    }

    public List<Book> findAll() {
        logger.debug("Retrieving all books from repository: {}", repositoryName);
        return new ArrayList<>(books.values());
    }

    public Book save(Book book) {
        logger.info("Saving book: {} to repository: {}", book.getTitle(), repositoryName);
        if (book.getId() == null) {
            // Generate new ID
            Long newId = books.keySet().stream().max(Long::compareTo).orElse(0L) + 1;
            book.setId(newId);
        }
        
        if (books.size() >= maxCapacity) {
            logger.error("Repository capacity exceeded. Max capacity: {}", maxCapacity);
            throw new RuntimeException("Repository capacity exceeded");
        }
        
        books.put(book.getId(), book);
        logger.debug("Book saved successfully with ID: {}", book.getId());
        return book;
    }

    public void deleteById(Long id) {
        logger.info("Deleting book with ID: {} from repository: {}", id, repositoryName);
        Book removedBook = books.remove(id);
        if (removedBook != null) {
            logger.debug("Book deleted successfully: {}", removedBook.getTitle());
        } else {
            logger.warn("Attempted to delete non-existent book with ID: {}", id);
        }
    }

    public List<Book> findByTitle(String title) {
        logger.debug("Finding books with title containing: '{}'", title);
        return books.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> findByAuthor(String author) {
        logger.debug("Finding books by author: '{}'", author);
        return books.values().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> findByCategory(String category) {
        logger.debug("Finding books in category: '{}'", category);
        return books.values().stream()
                .filter(book -> book.getCategory().toLowerCase().contains(category.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> findAvailableBooks() {
        logger.debug("Finding all available books");
        return books.values().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    public List<Book> findBooksByPriceRange(Double minPrice, Double maxPrice) {
        logger.debug("Finding books in price range: {} - {}", minPrice, maxPrice);
        return books.values().stream()
                .filter(book -> book.getPrice() >= minPrice && book.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public boolean existsById(Long id) {
        return books.containsKey(id);
    }

    public long count() {
        return books.size();
    }

    public boolean isAtCapacity() {
        return books.size() >= maxCapacity;
    }

    public void testConnection() {
        logger.info("Testing repository connection with timeout: {} seconds", connectionTimeout);
        // Simulate connection test
        try {
            Thread.sleep(100); // Simulate connection time
            logger.info("Repository connection test successful");
        } catch (InterruptedException e) {
            logger.error("Connection test interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    // Getters and Setters
    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
        logger.info("Repository name set to: {}", repositoryName);
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
        logger.info("Repository capacity set to: {}", maxCapacity);
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        logger.info("Connection timeout set to: {} seconds", connectionTimeout);
    }
}
