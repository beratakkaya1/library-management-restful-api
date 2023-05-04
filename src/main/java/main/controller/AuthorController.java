package main.controller;

import lombok.extern.slf4j.Slf4j;
import main.entity.Author;
import main.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/authors")

public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/create")
    public Author createAuthor(@RequestBody Author author) {
        log.info("create Author method");
        return authorService.createAuthor(author);
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        log.info("Fetching author with id: " + id);
        return authorService.getAuthorById(id);
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        log.info("Received request to list all authors");
        return authorService.getAllAuthors();
    }

    @PutMapping("/update/{id}")
    public Author updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        log.info("Updating author with id: " + id);
        return authorService.updateAuthor(id, author);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        log.info("Deleting author with id: " + id);
        authorService.deleteAuthor(id);
    }
}