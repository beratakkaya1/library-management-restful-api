package main.controller;


import lombok.extern.slf4j.Slf4j;
import main.entity.Book;
import main.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Slf4j
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public Book createBook(@RequestBody Book book) {
        log.info("Create Book method");
        return bookService.createBook(book);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        log.info("Fetching book with id: " + id);
        return bookService.getBookById(id);
    }

    @GetMapping("/authorName")
    public List<Book> getBookByAuthorName(@RequestParam(value="authorName") String authorName) {
        log.info("Fetching book with Author name: " + authorName);
        return bookService.getBookByAuthorName(authorName);
    }

    @GetMapping()
    public List<Book> getAllBooks() {
        log.info("Received request to list all books");
        return bookService.getAllBooks();
    }

    @PutMapping("/update/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        log.info("Updating book with id: " + id);
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable Long id) {
        log.info("Deleting book with id: " + id);
        bookService.deleteBook(id);
    }
}