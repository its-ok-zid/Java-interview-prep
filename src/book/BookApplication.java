package book;

import java.util.HashSet;
import java.util.Set;

public class BookApplication {

    public static void main(String[] args) {

        BookCatalog bookCatalog = getBookCatalog();

        System.out.println("\n========== ALL BOOKS ==========");
        System.out.println(bookCatalog.getAllBooks());

        // ---------------- FIND BOOK BY ISBN ----------------

        System.out.println("\n========== FIND BOOK BY ISBN ==========");
        System.out.println(bookCatalog.findByIsbn("9700700"));

        // ---------------- FIND BOOKS BY AUTHOR ----------------

        System.out.println("\n========== FIND BOOKS BY AUTHOR ==========");

        Set<Book> erenBooks = bookCatalog.findBookByAuthor("Eren");
        System.out.println("Books written by Eren: " + erenBooks);

        Set<Book> zidanBooks = bookCatalog.findBookByAuthor("Zidan");
        System.out.println("Books written by Zidan: " + zidanBooks);

        Set<Book> unknownAuthorBooks = bookCatalog.findBookByAuthor("Levi");
        System.out.println("Books written by Levi: " + unknownAuthorBooks);

        // ---------------- TOTAL BOOKS ----------------

        System.out.println("\n========== TOTAL BOOKS ==========");
        System.out.println("Total books: " + bookCatalog.totalBooks());

        // ---------------- UPDATE BOOK ----------------

        System.out.println("\n========== UPDATE BOOK ==========");

        Book updatedBook = new Book();
        updatedBook.setIsbn("9700700");
        updatedBook.setTitle("Game of Thrones - Updated");

        Set<String> updatedAuthors = new HashSet<>();
        updatedAuthors.add("George R. R. Martin");
        updatedBook.setAuthor(updatedAuthors);

        System.out.println("Updated book: "
                + bookCatalog.updateBook("9700700", updatedBook));

        // Verify update
        System.out.println("After update: "
                + bookCatalog.findByIsbn("9700700"));

        // ---------------- DELETE BOOK ----------------

        System.out.println("\n========== DELETE BOOK ==========");

        bookCatalog.deleteBook("5353453");

        System.out.println("All books after deletion: "
                + bookCatalog.getAllBooks());

        // Verify deletion
        System.out.println("Find deleted book: "
                + bookCatalog.findByIsbn("5353453"));

        // ---------------- FINAL BOOK COUNT ----------------

        System.out.println("\n========== FINAL BOOK COUNT ==========");
        System.out.println("Total books: " + bookCatalog.totalBooks());
    }

    private static BookCatalog getBookCatalog() {
        BookCatalog bookCatalog = new BookCatalog();

        // ---------------- CREATE BOOKS ----------------

        Book book1 = new Book();
        book1.setIsbn("2321312");
        book1.setTitle("AOT");

        Set<String> book1Author = new HashSet<>();
        book1Author.add("Zidan");
        book1.setAuthor(book1Author);

        Book book2 = new Book();
        book2.setIsbn("5353453");
        book2.setTitle("Harry Potter");

        Set<String> book2Author = new HashSet<>();
        book2Author.add("Eren");
        book2Author.add("Mikasa");
        book2.setAuthor(book2Author);

        Book book3 = new Book();
        book3.setIsbn("9700700");
        book3.setTitle("GOT");

        Set<String> book3Author = new HashSet<>();
        book3Author.add("Eren");
        book3Author.add("Zidan");
        book3Author.add("Mikasa");
        book3.setAuthor(book3Author);

        // ---------------- ADD BOOKS ----------------

        bookCatalog.addBook(book1);
        bookCatalog.addBook(book2);
        bookCatalog.addBook(book3);
        return bookCatalog;
    }
}
