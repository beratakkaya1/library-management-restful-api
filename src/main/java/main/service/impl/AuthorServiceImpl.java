package main.service.impl;

import lombok.extern.slf4j.Slf4j;
import main.dto.AuthorDto;
import main.entity.Author;
import main.handler.BadRequestException;
import main.handler.ResourceNotFoundException;
import main.repository.AuthorRepository;
import main.service.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
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
        return Objects.isNull(authorRepository.findById(author.getId()));
    }


    private AuthorDto convertToDto(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }

    private Author convertEntity(AuthorDto authorDto) {
        return  modelMapper.map(authorDto, Author.class);
    }
}
