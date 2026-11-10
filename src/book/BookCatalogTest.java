package book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookCatalogTest {

    private BookCatalog catalog;
    private Book testBook;
    private Book secondBook;

    @BeforeEach
    void setUp() {
        catalog = new BookCatalog();

        testBook = new Book();
        testBook.setIsbn("12345");
        testBook.setTitle("Effective Java");
        Set<String> authors = new HashSet<>();
        authors.add("Joshua Bloch");
        testBook.setAuthor(authors);

        secondBook = new Book();
        secondBook.setIsbn("67890");
        secondBook.setTitle("Clean Code");
        Set<String> secondAuthors = new HashSet<>();
        secondAuthors.add("Robert C. Martin");
        secondBook.setAuthor(secondAuthors);
    }

    @Test
    void testAddAndFindBookByIsbn() {
        catalog.addBook(testBook);
        Book retrievedBook = catalog.findByIsbn("12345");
        assertNotNull(retrievedBook, "Book should not be null after adding.");
        assertEquals("Effective Java", retrievedBook.getTitle(), "Titles should match.");
    }

    @Test
    void testFindBookByAuthor() {
        catalog.addBook(testBook);
        Set<Book> blochBooks = catalog.findBookByAuthor("Joshua Bloch");
        assertEquals(1, blochBooks.size(), "Author should have exactly 1 book.");
        assertTrue(blochBooks.contains(testBook), "The set should contain our test book.");
    }

    @Test
    void testDeleteBookRemovesAuthorMapping() {
        catalog.addBook(testBook);
        catalog.deleteBook("12345");
        assertNull(catalog.findByIsbn("12345"), "Book should be deleted from ISBN map.");

        Set<Book> blochBooks = catalog.findBookByAuthor("Joshua Bloch");
        assertTrue(blochBooks.isEmpty(), "Author mapping should be empty after deleting their only book.");
    }

    @Test
    void testUpdateBookUpdatesAuthorMapping() {
        catalog.addBook(testBook);

        Book updatedBook = new Book();
        updatedBook.setTitle("Effective Java 3rd Edition");
        Set<String> newAuthors = new HashSet<>();
        newAuthors.add("Joshua Bloch");
        newAuthors.add("Neal Gafter");
        updatedBook.setAuthor(newAuthors);

        catalog.updateBook("12345", updatedBook);

        assertEquals("Effective Java 3rd Edition", catalog.findByIsbn("12345").getTitle());
        assertTrue(catalog.findBookByAuthor("Neal Gafter").contains(testBook), "Should be found under new author.");
    }

    @Test
    void testUpdateNonExistentBookReturnsNull() {
        Book updatedBook = new Book();
        updatedBook.setTitle("Ghost Book");
        assertNull(catalog.updateBook("99999", updatedBook), "Updating a book that doesn't exist should safely return null.");
    }

    @Test
    void testGetAllBooksAndTotalCount() {
        catalog.addBook(testBook);
        catalog.addBook(secondBook);

        assertEquals(2, catalog.totalBooks(), "Total books should be 2.");
        Collection<Book> allBooks = catalog.getAllBooks();
        assertEquals(2, allBooks.size(), "Collection should contain 2 books.");
    }
}