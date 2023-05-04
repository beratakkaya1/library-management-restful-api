package main.service.impl;

import lombok.extern.slf4j.Slf4j;
import main.entity.Author;
import main.entity.Book;
import main.handler.BadRequestException;
import main.handler.ResourceNotFoundException;
import main.repository.BookRepository;
import main.service.AuthorService;
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
    private final AuthorService authorService;
    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
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
        if(!isAuthorExist(book)){
            return bookRepository.save(book);
        }
        book.setAuthor(getAuthor(book.getAuthor()));
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

    @Override
    public List<Book> getBookByAuthorName(String name) {
        return bookRepository.findByAuthor_Name(name);
    }

    private boolean isAuthorExist(Book newBook){
        if(Objects.nonNull(newBook.getAuthor())){
        Author author = getAuthor(newBook.getAuthor());
           return Objects.nonNull(author);
        }
        throw new BadRequestException("Author cannot be empty.");
    }
    private Author getAuthor(Author author){
        return authorService.getAuthorByNameOrId(author.getName(),author.getId());
    }
}
