package main.java.com.siddharth.librarysystem;

import main.java.com.siddharth.librarysystem.core.Library;
import main.java.com.siddharth.librarysystem.entity.Book;
import main.java.com.siddharth.librarysystem.entity.Patron;
import main.java.com.siddharth.librarysystem.payment.PaymentProcessor;
import main.java.com.siddharth.librarysystem.payment.UpiPaymentProcessor;
import main.java.com.siddharth.librarysystem.commandline.LibraryCLI;


public class Main {
    public static void main(String[] args) {
        // 1. Choose a payment processor (Strategy Pattern)
        PaymentProcessor paymentProcessor = new UpiPaymentProcessor();
        // You could easily switch to this one:
        // PaymentProcessor paymentProcessor = new NetBankingPaymentProcessor();

        // 2. Initialize the main Library system
        Library library = new Library(paymentProcessor);

        // 3. (Optional) Pre-populate the library with some data for testing
        seedData(library);

        // 4. Create and start the Command-Line Interface
        LibraryCLI cli = new LibraryCLI(library);
        cli.start();
    }

    /**
     * Adds some initial books and patrons to the library for demonstration purposes.
     * @param library The library instance to populate.
     */
    private static void seedData(Library library) {
        System.out.println("Seeding initial data...");

        // Add some books
        library.addNewBook(new Book("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", 1925), 3);
        library.addNewBook(new Book("To Kill a Mockingbird", "Harper Lee", "9780061120084", 1960), 5);
        library.addNewBook(new Book("1984", "George Orwell", "9780451524935", 1949), 2);
        library.addNewBook(new Book("Pride and Prejudice", "Jane Austen", "9780141439518", 1813), 4);
        
        // Register some patrons
        Patron alice = library.registerNewPatron("Alice Smith", "alice@example.com");
        Patron bob = library.registerNewPatron("Bob Johnson", "bob@example.com");
        
        System.out.println("Data seeding complete.\n");
    }
}