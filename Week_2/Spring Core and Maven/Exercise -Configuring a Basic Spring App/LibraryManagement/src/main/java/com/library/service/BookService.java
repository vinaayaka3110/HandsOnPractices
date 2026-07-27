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

    public BookService() {
        logger.info("BookService initialized");
    }

    public Book addBook(String title, String author, String isbn) {
        logger.info("Adding new book: {} by {}", title, author);
        Book book = new Book(null, title, author, isbn);
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
        logger.debug("Retrieving all books");
        List<Book> books = bookRepository.findAll();
        logger.info("Found {} books", books.size());
        return books;
    }

    public List<Book> searchBooksByTitle(String title) {
        logger.info("Searching books by title: {}", title);
        return bookRepository.findByTitle(title);
    }

    public List<Book> searchBooksByAuthor(String author) {
        logger.info("Searching books by author: {}", author);
        return bookRepository.findByAuthor(author);
    }

    public Book updateBook(Long id, String title, String author, String isbn) {
        logger.info("Updating book with ID: {}", id);
        Book existingBook = bookRepository.findById(id);
        if (existingBook != null) {
            existingBook.setTitle(title);
            existingBook.setAuthor(author);
            existingBook.setIsbn(isbn);
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

    public boolean borrowBook(Long id) {
        logger.info("Processing book borrow request for ID: {}", id);
        Book book = bookRepository.findById(id);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            bookRepository.save(book);
            logger.info("Book borrowed successfully: {}", book.getTitle());
            return true;
        } else if (book != null && !book.isAvailable()) {
            logger.warn("Book is not available for borrowing: {}", book.getTitle());
            return false;
        } else {
            logger.error("Book not found with ID: {}", id);
            return false;
        }
    }

    public boolean returnBook(Long id) {
        logger.info("Processing book return for ID: {}", id);
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
        return bookRepository.findAll().stream()
                .mapToLong(book -> book.isAvailable() ? 1 : 0)
                .sum();
    }

    // Getters and Setters
    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        logger.info("BookRepository injected into BookService");
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
        logger.info("Service name set to: {}", serviceName);
    }
}
