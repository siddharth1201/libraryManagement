package main.java.com.siddharth.librarysystem.entity;



import java.util.Objects;
import java.util.UUID;

public class Book {
    private String id;
    private String title;
    private String author;
    private String isbn;
    private int pubYear;

    /**
     * Constructor for the Book class.
     * Generates a unique ID for each new book.
     */
    public Book(String title, String author, String isbn, int pubYear) {
        this.id = UUID.randomUUID().toString(); // Assign a unique ID
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.pubYear = pubYear;
    }

    // --- Getters ---

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPubYear() {
        return pubYear;
    }

    // --- Setters ---

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPubYear(int pubYear) {
        this.pubYear = pubYear;
    }

    // --- Overridden Methods ---

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", pubYear=" + pubYear +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        // Two books are considered equal if they have the same ISBN
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        // Use ISBN for a consistent hash code
        return Objects.hash(isbn);
    }
}