package com.library;

import com.library.model.Book;
import com.library.service.BookService;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryManagementTest {

    private ApplicationContext context;
    private BookService bookService;
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        bookService = context.getBean("bookService", BookService.class);
        bookRepository = context.getBean("bookRepository", BookRepository.class);
    }

    @Test
    void testSpringContextLoads() {
        assertNotNull(context, "Spring context should not be null");
        assertNotNull(bookService, "BookService bean should not be null");
        assertNotNull(bookRepository, "BookRepository bean should not be null");
    }

    @Test
    void testBeanConfiguration() {
        assertNotNull(bookService.getBookRepository(), "BookRepository should be injected into BookService");
        assertEquals("Library Book Management Service", bookService.getServiceName());
        assertEquals("Library Database Repository", bookRepository.getRepositoryName());
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = bookService.getAllBooks();
        assertNotNull(books, "Books list should not be null");
        assertEquals(5, books.size(), "Should have 5 initial books");
    }

    @Test
    void testFindBookById() {
        Book book = bookService.findBookById(1L);
        assertNotNull(book, "Book should be found");
        assertEquals("The Great Gatsby", book.getTitle());
        assertEquals("F. Scott Fitzgerald", book.getAuthor());
    }

    @Test
    void testAddBook() {
        Book newBook = bookService.addBook("Test Book", "Test Author", "978-0-123456-78-9");
        assertNotNull(newBook, "New book should not be null");
        assertNotNull(newBook.getId(), "New book should have an ID");
        assertEquals("Test Book", newBook.getTitle());
        assertEquals("Test Author", newBook.getAuthor());
        assertTrue(newBook.isAvailable(), "New book should be available");
    }

    @Test
    void testSearchBooksByTitle() {
        List<Book> books = bookService.searchBooksByTitle("Great");
        assertNotNull(books, "Search result should not be null");
        assertEquals(1, books.size(), "Should find one book with 'Great' in title");
        assertEquals("The Great Gatsby", books.get(0).getTitle());
    }

    @Test
    void testSearchBooksByAuthor() {
        List<Book> books = bookService.searchBooksByAuthor("George");
        assertNotNull(books, "Search result should not be null");
        assertEquals(1, books.size(), "Should find one book by George Orwell");
        assertEquals("George Orwell", books.get(0).getAuthor());
    }

    @Test
    void testBorrowAndReturnBook() {
        // Borrow a book
        boolean borrowResult = bookService.borrowBook(1L);
        assertTrue(borrowResult, "Should be able to borrow available book");
        
        Book borrowedBook = bookService.findBookById(1L);
        assertFalse(borrowedBook.isAvailable(), "Book should not be available after borrowing");

        // Try to borrow the same book again
        boolean borrowResult2 = bookService.borrowBook(1L);
        assertFalse(borrowResult2, "Should not be able to borrow unavailable book");

        // Return the book
        boolean returnResult = bookService.returnBook(1L);
        assertTrue(returnResult, "Should be able to return borrowed book");
        
        Book returnedBook = bookService.findBookById(1L);
        assertTrue(returnedBook.isAvailable(), "Book should be available after returning");
    }

    @Test
    void testUpdateBook() {
        Book updatedBook = bookService.updateBook(2L, "Updated Title", "Updated Author", "978-0-000000-00-0");
        assertNotNull(updatedBook, "Updated book should not be null");
        assertEquals("Updated Title", updatedBook.getTitle());
        assertEquals("Updated Author", updatedBook.getAuthor());
        assertEquals("978-0-000000-00-0", updatedBook.getIsbn());
    }

    @Test
    void testUpdateNonExistentBook() {
        assertThrows(RuntimeException.class, () -> {
            bookService.updateBook(999L, "Title", "Author", "ISBN");
        }, "Should throw exception when updating non-existent book");
    }

    @Test
    void testDeleteBook() {
        long initialCount = bookService.getTotalBooksCount();
        bookService.deleteBook(5L);
        long finalCount = bookService.getTotalBooksCount();
        assertEquals(initialCount - 1, finalCount, "Book count should decrease by 1 after deletion");
        
        Book deletedBook = bookService.findBookById(5L);
        assertNull(deletedBook, "Deleted book should not be found");
    }

    @Test
    void testDeleteNonExistentBook() {
        assertThrows(RuntimeException.class, () -> {
            bookService.deleteBook(999L);
        }, "Should throw exception when deleting non-existent book");
    }

    @Test
    void testLibraryStatistics() {
        long totalBooks = bookService.getTotalBooksCount();
        long availableBooks = bookService.getAvailableBooksCount();
        
        assertTrue(totalBooks >= 5, "Should have at least 5 books");
        assertTrue(availableBooks <= totalBooks, "Available books should not exceed total books");
        
        // Borrow a book and check statistics
        bookService.borrowBook(1L);
        long availableAfterBorrow = bookService.getAvailableBooksCount();
        assertEquals(availableBooks - 1, availableAfterBorrow, "Available count should decrease after borrowing");
    }
}
