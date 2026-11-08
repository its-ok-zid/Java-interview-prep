package book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookCatalog {
    private final HashMap<String, Book> books = new HashMap<>();
    private final HashMap<String, Set<Book>> bookByAuthor = new HashMap<>();


    public void addBook(Book book) {
        String id = book.getIsbn();
        books.put(id, book);

        for (String author : book.getAuthor()) {
            bookByAuthor.computeIfAbsent(author, k -> new HashSet<>())
                    .add(book);
        }
    }

    public Book findByIsbn(String isbn) {
        return books.get(isbn);
    }

    public Book updateBook(String isbn, Book book) {

        Book currentBook = findByIsbn(isbn);

        if (currentBook == null) {
            return null;
        }

        // Remove old author mappings
        for (String author : currentBook.getAuthor()) {

            Set<Book> authorBooks = bookByAuthor.get(author);

            if (authorBooks != null) {
                authorBooks.remove(currentBook);

                if (authorBooks.isEmpty()) {
                    bookByAuthor.remove(author);
                }
            }
        }
        currentBook.setAuthor(book.getAuthor());
        currentBook.setTitle(book.getTitle());

        for (String author : currentBook.getAuthor()) {
            bookByAuthor
                    .computeIfAbsent(author, k -> new HashSet<>())
                    .add(currentBook);
        }

        return currentBook;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public int totalBooks() {
        return books.size();
    }

    public void deleteBook(String isbn) {

        Book book = books.remove(isbn);

        if (book == null) {
            return;
        }

        for (String author : book.getAuthor()) {

            Set<Book> authorBooks = bookByAuthor.get(author);

            if (authorBooks != null) {
                authorBooks.remove(book);

                if (authorBooks.isEmpty()) {
                    bookByAuthor.remove(author);
                }
            }
        }
    }

    public Set<Book> findBookByAuthor(String author) {
        return bookByAuthor.getOrDefault(author, Collections.emptySet());
    }

}
