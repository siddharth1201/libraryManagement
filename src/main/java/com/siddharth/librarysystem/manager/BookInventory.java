package main.java.com.siddharth.librarysystem.manager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import main.java.com.siddharth.librarysystem.entity.Book;

/**
 * Manages the inventory of all books in the library.
 * It tracks the total count, available count, and borrowed count for each book.
 */
public class BookInventory {

    // Using ISBN as the key for efficiency, since it's unique per book edition.
    private final Map<String, Book> booksByIsbn = new HashMap<>();
    private final Map<String, Integer> availableCopies = new HashMap<>();
    private final Map<String, Integer> borrowedCopies = new HashMap<>();


    /**
     * Adds a new book to the inventory or increases the count if it already exists.
     * @param book The book to add.
     * @param quantity The number of copies to add.
     */
    public void addBook(Book book, int quantity) {
        if (quantity <= 0) {
            System.out.println("Error: Quantity must be positive.");
            return;
        }

        String isbn = book.getIsbn();
        booksByIsbn.putIfAbsent(isbn, book);

        // Increase the number of available copies
        availableCopies.put(isbn, availableCopies.getOrDefault(isbn, 0) + quantity);
        borrowedCopies.putIfAbsent(isbn, 0); // Ensure borrowed count is initialized

        System.out.println(quantity + " copies of '" + book.getTitle() + "' added to inventory.");
    }

    /**
     * Checks if a book is available to be borrowed.
     * @param isbn The ISBN of the book to check.
     * @return true if at least one copy is available, false otherwise.
     */
    public boolean isBookAvailable(String isbn) {
        return availableCopies.getOrDefault(isbn, 0) > 0;
    }

    /**
     * Marks one copy of a book as borrowed.
     * This should be called when a book is issued to a patron.
     * @param isbn The ISBN of the book.
     */
    public void markAsBorrowed(String isbn) {
        if (isBookAvailable(isbn)) {
            availableCopies.put(isbn, availableCopies.get(isbn) - 1);
            borrowedCopies.put(isbn, borrowedCopies.get(isbn) + 1);
        } else {
            // This case should ideally be prevented by checking availability first.
            System.out.println("Error: Book with ISBN " + isbn + " is not available to borrow.");
        }
    }

    /**
     * Marks one copy of a book as returned.
     * This should be called when a patron returns a book.
     * @param isbn The ISBN of the book.
     */
    public void markAsReturned(String isbn) {
        if (borrowedCopies.getOrDefault(isbn, 0) > 0) {
            availableCopies.put(isbn, availableCopies.get(isbn) + 1);
            borrowedCopies.put(isbn, borrowedCopies.get(isbn) - 1);
        } else {
            System.out.println("Error: No borrowed copies of ISBN " + isbn + " to return.");
        }
    }


    // --- Search Methods ---

    public Book getByIsbn(String isbn) {
        return booksByIsbn.get(isbn);
    }

    public List<Book> getAllByTitle(String title) {
        return booksByIsbn.values().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    public List<Book> getAllByAuthor(String author) {
        return booksByIsbn.values().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public List<Book> getAllByPubYear(int year) {
        return booksByIsbn.values().stream()
                .filter(book -> book.getPubYear() == year)
                .collect(Collectors.toList());
    }
    
    public List<Book> getAllBooks() {
        return new ArrayList<>(booksByIsbn.values());
    }

    // --- Getters for inventory counts ---

    public int getAvailableCopiesCount(String isbn) {
        return availableCopies.getOrDefault(isbn, 0);
    }

    public int getBorrowedCopiesCount(String isbn) {
        return borrowedCopies.getOrDefault(isbn, 0);
    }
}