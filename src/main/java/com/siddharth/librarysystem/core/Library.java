package main.java.com.siddharth.librarysystem.core;



import java.util.List;

import main.java.com.siddharth.librarysystem.entity.Book;
import main.java.com.siddharth.librarysystem.entity.Patron;
import main.java.com.siddharth.librarysystem.manager.BookInventory;
import main.java.com.siddharth.librarysystem.manager.PatronManager;
import main.java.com.siddharth.librarysystem.payment.PaymentProcessor;

/**
 * The main facade class for the library system.
 * It coordinates operations between the BookInventory and PatronManager.
 * This class provides a simplified interface for the CLI.
 */
public class Library {
    private final BookInventory bookInventory;
    private final PatronManager patronManager;

    /**
     * Constructor for the Library.
     * Initializes the core components of the system.
     * @param paymentProcessor The payment processor to be used for transactions.
     */
    public Library(PaymentProcessor paymentProcessor) {
        this.bookInventory = new BookInventory();
        // The PatronManager needs access to the BookInventory to check for book availability
        // and to the PaymentProcessor to handle payments.
        this.patronManager = new PatronManager(paymentProcessor, this.bookInventory);
    }

    // --- Book Inventory Methods ---

    public void addNewBook(Book book, int quantity) {
        bookInventory.addBook(book, quantity);
    }

    public List<Book> searchBookByTitle(String title) {
        return bookInventory.getAllByTitle(title);
    }

    public List<Book> searchBookByAuthor(String author) {
        return bookInventory.getAllByAuthor(author);
    }
    
    public Book searchBookByIsbn(String isbn) {
        return bookInventory.getByIsbn(isbn);
    }
    
    public List<Book> listAllBooks() {
        return bookInventory.getAllBooks();
    }
    
     public int getAvailableCopies(Book book) {
        if (book == null) return 0;
        return bookInventory.getAvailableCopiesCount(book.getIsbn());
    }

    // --- Patron Manager Methods ---

    public Patron registerNewPatron(String name, String email) {
        return patronManager.registerPatron(name, email);
    }
    
    public Patron findPatronById(String id) {
        return patronManager.findPatronById(id);
    }

    public void issueBookToPatron(Book book, Patron patron) {
        if (book == null || patron == null) {
            System.out.println("Error: Book or Patron not found.");
            return;
        }
        patronManager.issueBook(book, patron);
    }

    public void returnBookFromPatron(Book book, Patron patron) {
        if (book == null || patron == null) {
            System.out.println("Error: Book or Patron not found.");
            return;
        }
        patronManager.returnBook(book, patron);
    }

    public List<Book> getBooksBorrowedByPatron(Patron patron) {
        return patronManager.getBorrowedBooks(patron);
    }
}