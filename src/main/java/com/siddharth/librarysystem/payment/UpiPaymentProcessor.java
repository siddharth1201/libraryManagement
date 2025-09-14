package main.java.com.siddharth.librarysystem.payment;



public class UpiPaymentProcessor implements PaymentProcessor {

    @Override
    public boolean processPayment(double amount) {
        System.out.println("\nProcessing UPI payment of $" + String.format("%.2f", amount) + "...");

        // --- DUMMY LOGIC ---
        // In a real application, this would involve generating a QR code or
        // sending a payment request to a UPI app.
        try {
            // Simulate user action delay (e.g., scanning QR code)
            System.out.println("Waiting for payment confirmation from UPI app...");
            Thread.sleep(2000); // Wait for 2 seconds
            System.out.println("Confirmation received.");
        } catch (InterruptedException e) {
            System.err.println("Payment process was interrupted.");
            Thread.currentThread().interrupt();
            return false;
        }
        // --- END OF DUMMY LOGIC ---

        System.out.println("UPI payment of $" + String.format("%.2f", amount) + " completed successfully.");
        return true; // Assume payment is always successful for this simulation
    }
}