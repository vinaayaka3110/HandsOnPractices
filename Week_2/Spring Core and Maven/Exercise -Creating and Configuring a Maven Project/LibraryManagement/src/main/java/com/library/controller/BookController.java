package com.library.controller;

import com.library.model.Book;
import com.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for handling Book-related web requests
 */
@Controller
@RequestMapping("/books")
public class BookController {
    
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    
    @Autowired
    private BookService bookService;

    @GetMapping
    public String listBooks(Model model) {
        logger.info("Displaying all books");
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("totalBooks", books.size());
        model.addAttribute("availableBooks", bookService.getAvailableBooksCount());
        return "books/list";
    }

    @GetMapping("/available")
    public String listAvailableBooks(Model model) {
        logger.info("Displaying available books");
        List<Book> books = bookService.getAvailableBooks();
        model.addAttribute("books", books);
        model.addAttribute("title", "Available Books");
        return "books/list";
    }

    @GetMapping("/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        logger.info("Viewing book with ID: {}", id);
        try {
            Book book = bookService.findBookById(id);
            model.addAttribute("book", book);
            return "books/view";
        } catch (RuntimeException e) {
            logger.error("Error viewing book with ID: {}", id, e);
            model.addAttribute("error", "Book not found");
            return "error/404";
        }
    }

    @GetMapping("/new")
    public String showAddBookForm(Model model) {
        logger.info("Showing add book form");
        model.addAttribute("book", new Book());
        model.addAttribute("categories", bookService.getAllCategories());
        return "books/form";
    }

    @PostMapping
    public String addBook(@Valid @ModelAttribute Book book, BindingResult result, Model model) {
        logger.info("Adding new book: {}", book.getTitle());
        
        if (result.hasErrors()) {
            logger.warn("Validation errors while adding book");
            model.addAttribute("categories", bookService.getAllCategories());
            return "books/form";
        }
        
        try {
            bookService.addBook(book);
            logger.info("Book added successfully: {}", book.getTitle());
            return "redirect:/books";
        } catch (Exception e) {
            logger.error("Error adding book", e);
            model.addAttribute("error", "Error adding book: " + e.getMessage());
            model.addAttribute("categories", bookService.getAllCategories());
            return "books/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        logger.info("Showing edit form for book ID: {}", id);
        try {
            Book book = bookService.findBookById(id);
            model.addAttribute("book", book);
            model.addAttribute("categories", bookService.getAllCategories());
            return "books/form";
        } catch (RuntimeException e) {
            logger.error("Error loading book for edit: {}", id, e);
            model.addAttribute("error", "Book not found");
            return "error/404";
        }
    }

    @PostMapping("/{id}")
    public String updateBook(@PathVariable Long id, @Valid @ModelAttribute Book book, 
                            BindingResult result, Model model) {
        logger.info("Updating book with ID: {}", id);
        
        if (result.hasErrors()) {
            logger.warn("Validation errors while updating book");
            model.addAttribute("categories", bookService.getAllCategories());
            return "books/form";
        }
        
        try {
            book.setId(id);
            bookService.updateBook(id, book);
            logger.info("Book updated successfully: {}", book.getTitle());
            return "redirect:/books/" + id;
        } catch (Exception e) {
            logger.error("Error updating book", e);
            model.addAttribute("error", "Error updating book: " + e.getMessage());
            model.addAttribute("categories", bookService.getAllCategories());
            return "books/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        logger.info("Deleting book with ID: {}", id);
        try {
            bookService.deleteBook(id);
            logger.info("Book deleted successfully with ID: {}", id);
            return "redirect:/books";
        } catch (Exception e) {
            logger.error("Error deleting book with ID: {}", id, e);
            return "redirect:/books?error=delete_failed";
        }
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(required = false) String title,
                             @RequestParam(required = false) String author,
                             @RequestParam(required = false) String category,
                             @RequestParam(required = false) String isbn,
                             Model model) {
        logger.info("Searching books - title: {}, author: {}, category: {}, isbn: {}", 
                   title, author, category, isbn);
        
        List<Book> books = null;
        String searchType = "";
        
        if (title != null && !title.trim().isEmpty()) {
            books = bookService.searchBooksByTitle(title);
            searchType = "title: " + title;
        } else if (author != null && !author.trim().isEmpty()) {
            books = bookService.searchBooksByAuthor(author);
            searchType = "author: " + author;
        } else if (category != null && !category.trim().isEmpty()) {
            books = bookService.searchBooksByCategory(category);
            searchType = "category: " + category;
        } else if (isbn != null && !isbn.trim().isEmpty()) {
            books = bookService.searchBooksByIsbn(isbn);
            searchType = "ISBN: " + isbn;
        } else {
            books = bookService.getAllBooks();
            searchType = "all books";
        }
        
        model.addAttribute("books", books);
        model.addAttribute("searchType", searchType);
        model.addAttribute("title", "Search Results for " + searchType);
        return "books/list";
    }

    // REST API endpoints
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Book>> getAllBooksApi() {
        logger.info("API: Getting all books");
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Book> getBookApi(@PathVariable Long id) {
        logger.info("API: Getting book with ID: {}", id);
        try {
            Book book = bookService.findBookById(id);
            return ResponseEntity.ok(book);
        } catch (RuntimeException e) {
            logger.error("API: Book not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<Book> addBookApi(@Valid @RequestBody Book book, BindingResult result) {
        logger.info("API: Adding new book: {}", book.getTitle());
        
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            Book savedBook = bookService.addBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
        } catch (Exception e) {
            logger.error("API: Error adding book", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Book> updateBookApi(@PathVariable Long id, @Valid @RequestBody Book book) {
        logger.info("API: Updating book with ID: {}", id);
        try {
            Book updatedBook = bookService.updateBook(id, book);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            logger.error("API: Error updating book with ID: {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteBookApi(@PathVariable Long id) {
        logger.info("API: Deleting book with ID: {}", id);
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("API: Error deleting book with ID: {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/{id}/borrow")
    @ResponseBody
    public ResponseEntity<String> borrowBookApi(@PathVariable Long id, @RequestParam String userId) {
        logger.info("API: Borrowing book ID: {} for user: {}", id, userId);
        try {
            boolean success = bookService.borrowBook(id, userId);
            if (success) {
                return ResponseEntity.ok("Book borrowed successfully");
            } else {
                return ResponseEntity.badRequest().body("Book not available for borrowing");
            }
        } catch (RuntimeException e) {
            logger.error("API: Error borrowing book", e);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/{id}/return")
    @ResponseBody
    public ResponseEntity<String> returnBookApi(@PathVariable Long id, @RequestParam String userId) {
        logger.info("API: Returning book ID: {} for user: {}", id, userId);
        try {
            boolean success = bookService.returnBook(id, userId);
            if (success) {
                return ResponseEntity.ok("Book returned successfully");
            } else {
                return ResponseEntity.badRequest().body("Book return failed");
            }
        } catch (RuntimeException e) {
            logger.error("API: Error returning book", e);
            return ResponseEntity.notFound().build();
        }
    }
}
