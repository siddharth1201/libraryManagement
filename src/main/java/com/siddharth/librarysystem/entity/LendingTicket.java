package main.java.com.siddharth.librarysystem.entity;

import java.time.LocalDate;
import java.util.UUID;

public class LendingTicket {
    private String id;
    private Book book;
    private Patron patron;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private double issuePrice;
    private PaymentStatus paymentStatus;

    /**
     * Constructor for creating a lending ticket.
     */
    public LendingTicket(Book book, Patron patron, LocalDate issueDate, LocalDate returnDate, double issuePrice) {
        this.id = UUID.randomUUID().toString();
        this.book = book;
        this.patron = patron;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.issuePrice = issuePrice;
        this.paymentStatus = PaymentStatus.PENDING; // Default status
    }

    // --- Getters ---

    public String getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Patron getPatron() {
        return patron;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public double getIssuePrice() {
        return issuePrice;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    // --- Setters ---

    public void setBook(Book book) {
        this.book = book;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setIssuePrice(double issuePrice) {
        this.issuePrice = issuePrice;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    // --- Overridden Methods ---
    
    @Override
    public String toString() {
        return "LendingTicket{" +
                "id='" + id + '\'' +
                ", bookTitle=" + book.getTitle() +
                ", patronName=" + patron.getName() +
                ", issueDate=" + issueDate +
                ", returnDate=" + returnDate +
                ", issuePrice=" + issuePrice +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}