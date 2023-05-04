package main.service.impl;

import lombok.extern.slf4j.Slf4j;
import main.entity.Author;
import main.handler.BadRequestException;
import main.handler.ResourceNotFoundException;
import main.repository.AuthorRepository;
import main.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAllAuthors() {
        log.info("Find all authors");
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(Long id) {
        log.info("Find author by id: " + id);
        return authorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Author not found"));
    }

    @Override
    public Author createAuthor(Author author) {
        log.info("Create author",author);
        if(Objects.isNull(author))
            throw new BadRequestException("Author cannot be empty.");

        if(isExist(author))
            throw new BadRequestException(
                    String.format("Author already exists a author with id =", author.getId()));

        if (author.getName() == null || author.getName().isEmpty()) {
            throw new BadRequestException("Author name cannot be empty.");
        }

        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Long id, Author author) {
        Author existingAuthor = getAuthorById(id);
        if (author.getName() == null || author.getName().isEmpty()) {
            throw new BadRequestException("Author name cannot be empty.");
        }

        log.info("Author with id: " + author.getId() + " updated! ");

        existingAuthor.setName(author.getName());
        return authorRepository.save(existingAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {
        log.info("Author by id: " + id + " Deleted!");
        Author existingAuthor = getAuthorById(id);
        authorRepository.delete(existingAuthor);
    }
    private boolean isExist(Author author) {
        return Objects.nonNull(authorRepository.findAuthorByNameOrId(author.getName(),author.getId()));
    }

    @Override
    public Author getAuthorByNameOrId(String name, Long id) {
        return authorRepository.findAuthorByNameOrId(name, id);
    }
}
