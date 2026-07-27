package com.library.repository;

import com.library.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookRepository {
    private static final Logger logger = LoggerFactory.getLogger(BookRepository.class);
    
    private String repositoryName;
    private Map<Long, Book> books;

    public BookRepository() {
        this.books = new HashMap<>();
        initializeData();
        logger.info("BookRepository initialized");
    }

    private void initializeData() {
        // Initialize with some sample data
        books.put(1L, new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald", "978-0-7432-7356-5"));
        books.put(2L, new Book(2L, "To Kill a Mockingbird", "Harper Lee", "978-0-06-112008-4"));
        books.put(3L, new Book(3L, "1984", "George Orwell", "978-0-452-28423-4"));
        books.put(4L, new Book(4L, "Pride and Prejudice", "Jane Austen", "978-0-14-143951-8"));
        books.put(5L, new Book(5L, "The Catcher in the Rye", "J.D. Salinger", "978-0-316-76948-0"));
    }

    public Book findById(Long id) {
        logger.debug("Finding book with ID: {}", id);
        return books.get(id);
    }

    public List<Book> findAll() {
        logger.debug("Retrieving all books");
        return new ArrayList<>(books.values());
    }

    public Book save(Book book) {
        logger.info("Saving book: {}", book.getTitle());
        if (book.getId() == null) {
            // Generate new ID
            Long newId = books.keySet().stream().max(Long::compareTo).orElse(0L) + 1;
            book.setId(newId);
        }
        books.put(book.getId(), book);
        return book;
    }

    public void deleteById(Long id) {
        logger.info("Deleting book with ID: {}", id);
        books.remove(id);
    }

    public List<Book> findByTitle(String title) {
        logger.debug("Finding books with title containing: {}", title);
        return books.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(ArrayList::new, (list, book) -> list.add(book), ArrayList::addAll);
    }

    public List<Book> findByAuthor(String author) {
        logger.debug("Finding books by author: {}", author);
        return books.values().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(ArrayList::new, (list, book) -> list.add(book), ArrayList::addAll);
    }

    public boolean existsById(Long id) {
        return books.containsKey(id);
    }

    public long count() {
        return books.size();
    }

    // Getters and Setters
    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
        logger.info("Repository name set to: {}", repositoryName);
    }
}
