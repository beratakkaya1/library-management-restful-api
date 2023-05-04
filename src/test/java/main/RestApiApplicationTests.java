package main;

import main.controller.AuthorController;
import main.controller.BookController;
import main.entity.Author;
import main.entity.Book;
import main.handler.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestApiApplicationTests {

	@Autowired
	private AuthorController authorController;

	@Autowired
	private BookController bookController;
	@Test
	void contextLoads() {
	}


	@Test
	void testCreateAuthor() {
		Author author = new Author();
		author.setName("Test Author");
		author.setId(30L);

		// Test creating author
		Author createdAuthor = authorController.createAuthor(author);
		assertNotNull(createdAuthor);
		assertEquals(author.getName(), createdAuthor.getName());

		Author fetchedAuthor = authorController.getAuthorById(createdAuthor.getId());
		assertNotNull(fetchedAuthor);
		assertEquals(createdAuthor.getName(), fetchedAuthor.getName());

	}

	@Test
	void testUpdateAuthor() {
		Author author = new Author();
		author.setName("Original Author");
		author.setId(3L);
		// Create author
		Author createdAuthor = authorController.createAuthor(author);
		assertNotNull(createdAuthor);

		// Update author
		Author updatedAuthor = new Author();
		updatedAuthor.setName("Updated Author");
		Author result = authorController.updateAuthor(createdAuthor.getId(), updatedAuthor);
		assertNotNull(result);
		assertEquals(updatedAuthor.getName(), result.getName());
	}

	@Test
	void testDeleteAuthor() {
		Author author = new Author();
		author.setName("Author To Be Deleted");
		author.setId(2L);

		// Create author
		Author createdAuthor = authorController.createAuthor(author);
		assertNotNull(createdAuthor);

		// Delete author
		authorController.deleteAuthor(createdAuthor.getId());

		// Verify deletion
		assertThrows(ResourceNotFoundException.class, () -> authorController.getAuthorById(createdAuthor.getId()));
	}

	@Test
	void testCreateAndGetBook() {
		Author author = new Author();
		author.setName("Test Author");
		author.setId(2L);
		Author createdAuthor = authorController.createAuthor(author);

		Book book = new Book();
		book.setTitle("Test Book");
		book.setId(2L);
		book.setPublicationYear(2022);
		book.setAuthor(createdAuthor);

		// Test creating book
		Book createdBook = bookController.createBook(book);
		assertNotNull(createdBook);
		assertEquals(book.getTitle(), createdBook.getTitle());
		assertEquals(book.getPublicationYear(), createdBook.getPublicationYear());

		// Test getting book
		Book fetchedBook = bookController.getBookById(createdBook.getId());
		assertNotNull(fetchedBook);
		assertEquals(createdBook.getTitle(), fetchedBook.getTitle());
		assertEquals(createdBook.getPublicationYear(), fetchedBook.getPublicationYear());
	}


	@Test
	void testUpdateBook() {
		Author author = new Author();
		author.setName("Test Author");
		author.setId(2L);
		Author createdAuthor = authorController.createAuthor(author);

		Book book = new Book();
		book.setTitle("Original Book");
		book.setPublicationYear(2022);
		book.setId(2L);
		book.setAuthor(createdAuthor);
		Book createdBook = bookController.createBook(book);

		// Update book
		Book updatedBook = new Book();
		updatedBook.setTitle("Updated Book");
		updatedBook.setPublicationYear(2023);
		Book result = bookController.updateBook(createdBook.getId(), updatedBook);
		assertNotNull(result);
		assertEquals(updatedBook.getTitle(), result.getTitle());
		assertEquals(updatedBook.getPublicationYear(), result.getPublicationYear());
	}

	@Test
	void testDeleteBook() {
		Author author = new Author();
		author.setName("Test Author");
		author.setId(2L);
		Author createdAuthor = authorController.createAuthor(author);

		Book book = new Book();
		book.setTitle("Book To Be Deleted");
		book.setId(2L);
		book.setPublicationYear(2022);
		book.setAuthor(createdAuthor);
		Book createdBook = bookController.createBook(book);

		// Delete book
		bookController.deleteBook(createdBook.getId());

		// Verify deletion
		assertThrows(ResourceNotFoundException.class, () -> bookController.getBookById(createdBook.getId()));
	}

}
