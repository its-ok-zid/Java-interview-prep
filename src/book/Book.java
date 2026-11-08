package book;

import java.util.Objects;
import java.util.Set;

public class Book {

    private String isbn;
    private String title;
    private Set<String> author;

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAuthor(Set<String> author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public Set<String> getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "book.Book{isbn= '" + isbn + ", title= '" + title + "', author= '" + author + "}";
    }
}
