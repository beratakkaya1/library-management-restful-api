package main.service.impl;

import lombok.extern.slf4j.Slf4j;
import main.entity.Book;
import main.handler.BadRequestException;
import main.handler.ResourceNotFoundException;
import main.repository.BookRepository;
import main.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        log.info("Retrieve the list of all books!");
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        log.info("Find Book by id: " + id);
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    @Override
    public Book createBook(Book book) {
        log.info("Create book",book);
        if(Objects.isNull(book))
            throw new BadRequestException("Book cannot be empty.");

        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new BadRequestException("Book title cannot be empty.");
        }
        return bookRepository.save(book);
    }
    @Override
    public Book updateBook(Long id, Book book) {
        log.info("Author with id: " + book.getId() + " updated! ");
        Book existingBook = getBookById(id);
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new BadRequestException("Book title cannot be empty.");
        }

        existingBook.setTitle(book.getTitle());
        existingBook.setPublicationYear(book.getPublicationYear());
        return bookRepository.save(existingBook);
    }

    @Override
    public void deleteBook(Long id) {
        log.info("Book by id: " + id + " Deleted!");
        Book existingBook = getBookById(id);
        bookRepository.delete(existingBook);
    }
}
