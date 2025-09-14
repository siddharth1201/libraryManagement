package main.java.com.siddharth.librarysystem.payment;

public class NetBankingPaymentProcessor implements PaymentProcessor {

    @Override
    public boolean processPayment(double amount) {
        System.out.println("\nInitiating Net Banking payment of $" + String.format("%.2f", amount) + "...");

        // --- DUMMY LOGIC ---
        // In a real application, you would integrate with a bank's API here.
        // We will simulate a successful transaction.
        try {
            // Simulate network delay
            System.out.println("Connecting to the bank's server...");
            Thread.sleep(1500); // Wait for 1.5 seconds
            System.out.println("Payment authorized by the bank.");
        } catch (InterruptedException e) {
            System.err.println("Payment process was interrupted.");
            Thread.currentThread().interrupt();
            return false;
        }
        // --- END OF DUMMY LOGIC ---

        System.out.println("Net Banking payment of $" + String.format("%.2f", amount) + " was successful.");
        return true; // Assume payment is always successful for this simulation
    }
}