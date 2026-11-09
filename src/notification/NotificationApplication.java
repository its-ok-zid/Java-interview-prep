package notification;

import java.util.List;

public class NotificationApplication {
    public static void main(String[] args) {
        NotificationAggregator aggregator = new NotificationAggregator();

        // 1. Simulate receiving notifications asynchronously
        aggregator.receiveNotification(new SmsNotification("SMS-1", 1000, "Your OTP is 1234", "555-0000"));
        
        // This email is scheduled far in the future
        aggregator.receiveNotification(new EmailNotification("EML-1", 5000, "Weekly Report", "admin@corp.com"));
        
        aggregator.receiveNotification(new SmsNotification("SMS-2", 2000, "Payment Failed", "555-9999"));

        System.out.println("Initial Buffer Size: " + aggregator.getBufferSize()); // Outputs: 3

        // 2. Implement the Cancel Usage
        // The user updates their payment method quickly, so we intercept and cancel the failure SMS!
        System.out.println("\nUser fixed payment. Cancelling SMS-2...");
        aggregator.cancelNotification("SMS-2");

        // 3. Implement the Flush Usage
        // Simulate a background worker waking up at system time 3000
        long currentSystemTime = 3000;
        System.out.println("\nWorker waking up. Flushing buffer up to timestamp: " + currentSystemTime);
        
        List<Notification> readyToSend = aggregator.flushBufferUpTo(currentSystemTime);

        // 4. Output the results
        System.out.println("\n========== DISPATCHING NOTIFICATIONS ==========");
        for (Notification n : readyToSend) {
            // In the real world, this loop sends the data to an SMTP server or Twilio API
            System.out.println(n.toString()); 
        }

        System.out.println("\n========== REMAINING IN BUFFER ==========");
        System.out.println("Buffer Size: " + aggregator.getBufferSize());
        // Only EML-1 remains because its timestamp (5000) is safely in the future
    }
}