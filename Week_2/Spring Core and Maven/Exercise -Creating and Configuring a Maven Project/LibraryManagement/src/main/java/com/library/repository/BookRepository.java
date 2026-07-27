package com.library.repository;

import com.library.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Repository class for managing Book entities
 */
@Repository
public class BookRepository {
    
    private static final Logger logger = LoggerFactory.getLogger(BookRepository.class);
    
    @Value("${database.url}")
    private String databaseUrl;
    
    @Value("${database.max.connections}")
    private Integer maxConnections;
    
    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private Long nextId = 1L;

    public BookRepository() {
        logger.info("BookRepository initialized");
        initializeData();
    }

    private void initializeData() {
        logger.info("Initializing repository with sample data");
        
        // Sample books
        save(new Book("The Great Gatsby", "F. Scott Fitzgerald", "978-0-7432-7356-5", 
                     "Fiction", 12.99));
        save(new Book("To Kill a Mockingbird", "Harper Lee", "978-0-06-112008-4", 
                     "Fiction", 14.99));
        save(new Book("1984", "George Orwell", "978-0-452-28423-4", 
                     "Dystopian", 13.99));
        save(new Book("Pride and Prejudice", "Jane Austen", "978-0-14-143951-8", 
                     "Romance", 11.99));
        save(new Book("Spring in Action", "Craig Walls", "978-1-935182-35-1", 
                     "Technology", 49.99));
        save(new Book("Clean Code", "Robert C. Martin", "978-0-13-235088-4", 
                     "Technology", 42.99));
        save(new Book("Design Patterns", "Gang of Four", "978-0-20163-361-0", 
                     "Technology", 54.99));
        save(new Book("Effective Java", "Joshua Bloch", "978-0-32135-668-0", 
                     "Technology", 45.99));
        
        logger.info("Repository initialized with {} books", books.size());
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(nextId++);
        }
        books.put(book.getId(), book);
        logger.debug("Book saved: {} (ID: {})", book.getTitle(), book.getId());
        return book;
    }

    public Book findById(Long id) {
        logger.debug("Finding book with ID: {}", id);
        return books.get(id);
    }

    public List<Book> findAll() {
        logger.debug("Retrieving all books from database: {}", databaseUrl);
        return new ArrayList<>(books.values());
    }

    public void deleteById(Long id) {
        Book removedBook = books.remove(id);
        if (removedBook != null) {
            logger.info("Book deleted: {} (ID: {})", removedBook.getTitle(), id);
        } else {
            logger.warn("Attempted to delete non-existent book with ID: {}", id);
        }
    }

    public boolean existsById(Long id) {
        return books.containsKey(id);
    }

    public long count() {
        return books.size();
    }

    public List<Book> findByTitle(String title) {
        return books.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> findByAuthor(String author) {
        return books.values().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> findByCategory(String category) {
        return books.values().stream()
                .filter(book -> book.getCategory().toLowerCase().contains(category.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> findByIsbn(String isbn) {
        return books.values().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .collect(Collectors.toList());
    }

    public List<Book> findAvailableBooks() {
        return books.values().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    public List<Book> findByPriceRange(Double minPrice, Double maxPrice) {
        return books.values().stream()
                .filter(book -> book.getPrice() >= minPrice && book.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public List<String> findAllCategories() {
        return books.values().stream()
                .map(Book::getCategory)
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> findAllAuthors() {
        return books.values().stream()
                .map(Book::getAuthor)
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    // Getters and Setters for dependency injection
    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        logger.info("Database URL configured: {}", databaseUrl);
    }

    public Integer getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
        logger.info("Max connections configured: {}", maxConnections);
    }
}
