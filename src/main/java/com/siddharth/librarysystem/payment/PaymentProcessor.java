package main.java.com.siddharth.librarysystem.payment;

public interface PaymentProcessor {

    /**
     * Processes a payment for a given amount.
     *
     * @param amount The amount to be processed.
     * @return true if the payment was successful, false otherwise.
     */
    boolean processPayment(double amount);
}