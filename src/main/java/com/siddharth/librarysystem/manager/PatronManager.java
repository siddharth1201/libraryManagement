package main.java.com.siddharth.librarysystem.manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import main.java.com.siddharth.librarysystem.entity.Book;
import main.java.com.siddharth.librarysystem.entity.LendingTicket;
import main.java.com.siddharth.librarysystem.entity.Patron;
import main.java.com.siddharth.librarysystem.entity.PaymentStatus;
import main.java.com.siddharth.librarysystem.payment.PaymentProcessor;

/**
 * Manages patrons, their lending activities, and payments.
 */
public class PatronManager {
    // Maps Patron ID to Patron object
    private final Map<String, Patron> patrons = new HashMap<>();
    // Maps Patron ID to a list of their active lending tickets
    private final Map<String, List<LendingTicket>> patronLendingHistory = new HashMap<>();
    
    private final PaymentProcessor paymentProcessor;
    private final BookInventory bookInventory; // To check book availability

    public PatronManager(PaymentProcessor paymentProcessor, BookInventory bookInventory) {
        this.paymentProcessor = paymentProcessor;
        this.bookInventory = bookInventory;
    }

    /**
     * Registers a new patron in the system.
     * @param name The name of the patron.
     * @param email The email of the patron.
     * @return The newly created Patron object.
     */
    public Patron registerPatron(String name, String email) {
        Patron patron = new Patron(name, email);
        patrons.put(patron.getId(), patron);
        patronLendingHistory.put(patron.getId(), new ArrayList<>());
        System.out.println("Patron registered successfully: " + patron.getName());
        return patron;
    }
    
    public Patron findPatronById(String id) {
        return patrons.get(id);
    }

    /**
     * Issues a book to a patron.
     * Creates a lending ticket and processes the payment.
     * @param book The book to be issued.
     * @param patron The patron borrowing the book.
     * @return The created LendingTicket, or null if the process fails.
     */
    public LendingTicket issueBook(Book book, Patron patron) {
        if (!bookInventory.isBookAvailable(book.getIsbn())) {
            System.out.println("Sorry, the book '" + book.getTitle() + "' is currently not available.");
            return null;
        }

        // Define lending period and price
        LocalDate issueDate = LocalDate.now();
        LocalDate returnDate = issueDate.plusDays(14); // 14-day lending period
        double issuePrice = 5.0; // Example price

        // Process payment
        boolean paymentSuccess = paymentProcessor.processPayment(issuePrice);
        
        if (paymentSuccess) {
            // Create the ticket
            LendingTicket ticket = new LendingTicket(book, patron, issueDate, returnDate, issuePrice);
            ticket.setPaymentStatus(PaymentStatus.PAID);

            // Update inventory
            bookInventory.markAsBorrowed(book.getIsbn());

            // Add ticket to patron's history
            patronLendingHistory.get(patron.getId()).add(ticket);
            
            System.out.println("Book '" + book.getTitle() + "' issued successfully to " + patron.getName());
            return ticket;
        } else {
            System.out.println("Payment failed. Could not issue book.");
            return null;
        }
    }

    /**
     * Returns a book from a patron.
     * Finds the corresponding lending ticket and marks the book as returned in the inventory.
     * @param book The book being returned.
     * @param patron The patron returning the book.
     */
    public void returnBook(Book book, Patron patron) {
        List<LendingTicket> activeTickets = patronLendingHistory.get(patron.getId());

        // Find the active ticket for this specific book
        LendingTicket ticketToReturn = null;
        for (LendingTicket ticket : activeTickets) {
            if (ticket.getBook().getIsbn().equals(book.getIsbn())) {
                ticketToReturn = ticket;
                break;
            }
        }
        
        if (ticketToReturn != null) {
            // Update inventory
            bookInventory.markAsReturned(book.getIsbn());

            // Remove the ticket from the active list (could be moved to an archive)
            activeTickets.remove(ticketToReturn);
            
            System.out.println("Book '" + book.getTitle() + "' returned successfully by " + patron.getName());
            
            // Here you could add logic to check for overdue fines
            if (LocalDate.now().isAfter(ticketToReturn.getReturnDate())) {
                System.out.println("Note: This book was returned late. Fines may apply.");
            }
            
        } else {
            System.out.println("Error: No record found of " + patron.getName() + " borrowing '" + book.getTitle() + "'.");
        }
    }
    
    /**
     * Retrieves a list of books currently borrowed by a specific patron.
     * @param patron The patron to check.
     * @return A list of books.
     */
    public List<Book> getBorrowedBooks(Patron patron) {
        if (!patronLendingHistory.containsKey(patron.getId())) {
            return new ArrayList<>(); // Return empty list if patron has no history
        }
        
        return patronLendingHistory.get(patron.getId())
                .stream()
                .map(LendingTicket::getBook)
                .collect(Collectors.toList());
    }
}