package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing Book operations
 */
@Service
public class BookService {
    
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    
    @Autowired
    private BookRepository bookRepository;
    
    @Value("${library.max.books.per.user}")
    private Integer maxBooksPerUser;

    public BookService() {
        logger.info("BookService initialized");
    }

    public Book addBook(Book book) {
        logger.info("Adding new book: {}", book.getTitle());
        validateBook(book);
        return bookRepository.save(book);
    }

    public Book findBookById(Long id) {
        logger.debug("Finding book with ID: {}", id);
        Book book = bookRepository.findById(id);
        if (book == null) {
            logger.warn("Book not found with ID: {}", id);
            throw new RuntimeException("Book not found with ID: " + id);
        }
        return book;
    }

    public List<Book> getAllBooks() {
        logger.debug("Retrieving all books");
        List<Book> books = bookRepository.findAll();
        logger.info("Found {} books", books.size());
        return books;
    }

    public List<Book> getAvailableBooks() {
        logger.debug("Retrieving available books");
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

    public List<Book> searchBooksByIsbn(String isbn) {
        logger.info("Searching books by ISBN: '{}'", isbn);
        return bookRepository.findByIsbn(isbn);
    }

    public List<Book> searchBooksByPriceRange(Double minPrice, Double maxPrice) {
        logger.info("Searching books in price range: {} - {}", minPrice, maxPrice);
        return bookRepository.findByPriceRange(minPrice, maxPrice);
    }

    public Book updateBook(Long id, Book updatedBook) {
        logger.info("Updating book with ID: {}", id);
        Book existingBook = findBookById(id);
        
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setCategory(updatedBook.getCategory());
        existingBook.setPublisher(updatedBook.getPublisher());
        existingBook.setPrice(updatedBook.getPrice());
        existingBook.setDescription(updatedBook.getDescription());
        
        validateBook(existingBook);
        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        logger.info("Deleting book with ID: {}", id);
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with ID: " + id);
        }
        bookRepository.deleteById(id);
        logger.info("Book deleted successfully with ID: {}", id);
    }

    public boolean borrowBook(Long bookId, String userId) {
        logger.info("Processing book borrow request for book ID: {} by user: {}", bookId, userId);
        
        Book book = findBookById(bookId);
        
        if (!book.isAvailable()) {
            logger.warn("Book is not available for borrowing: {}", book.getTitle());
            return false;
        }
        
        boolean borrowed = book.borrowBook();
        if (borrowed) {
            bookRepository.save(book);
            logger.info("Book borrowed successfully: {} by user: {}", book.getTitle(), userId);
        }
        
        return borrowed;
    }

    public boolean returnBook(Long bookId, String userId) {
        logger.info("Processing book return for book ID: {} by user: {}", bookId, userId);
        
        Book book = findBookById(bookId);
        
        boolean returned = book.returnBook();
        if (returned) {
            bookRepository.save(book);
            logger.info("Book returned successfully: {} by user: {}", book.getTitle(), userId);
        } else {
            logger.warn("Book return failed - all copies already returned: {}", book.getTitle());
        }
        
        return returned;
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

    public List<String> getAllCategories() {
        return bookRepository.findAllCategories();
    }

    public List<String> getAllAuthors() {
        return bookRepository.findAllAuthors();
    }

    private void validateBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be empty");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be empty");
        }
        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("Book ISBN cannot be empty");
        }
        if (book.getPrice() != null && book.getPrice() < 0) {
            throw new IllegalArgumentException("Book price cannot be negative");
        }
    }

    // Getters and Setters for dependency injection
    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        logger.info("BookRepository injected into BookService");
    }

    public Integer getMaxBooksPerUser() {
        return maxBooksPerUser;
    }

    public void setMaxBooksPerUser(Integer maxBooksPerUser) {
        this.maxBooksPerUser = maxBooksPerUser;
        logger.info("Max books per user configured: {}", maxBooksPerUser);
    }
}
