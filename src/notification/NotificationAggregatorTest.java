package notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotificationAggregatorTest {

    private NotificationAggregator aggregator;

    @BeforeEach
    void setUp() {
        aggregator = new NotificationAggregator();
    }

    @Test
    void testOutOfOrderBuffering() {
        // Arrange: Create notifications out of order to simulate network delay
        Notification lateSms = new SmsNotification("SMS-1", 1000, "Hello", "555-1234");
        Notification earlyEmail = new EmailNotification("EML-1", 3000, "Report", "a@b.com");
        Notification onTimeSms = new SmsNotification("SMS-2", 2000, "Update", "555-9999");

        // Act: Receive them completely scrambled
        aggregator.receiveNotification(earlyEmail);
        aggregator.receiveNotification(lateSms);
        aggregator.receiveNotification(onTimeSms);

        // Assert: The buffer correctly sorted them chronologically despite the chaotic arrival
        List<Notification> flushed = aggregator.flushBufferUpTo(5000);

        assertEquals(3, flushed.size());
        assertEquals("SMS-1", flushed.get(0).getId(), "1000ms should be first");
        assertEquals("SMS-2", flushed.get(1).getId(), "2000ms should be second");
        assertEquals("EML-1", flushed.get(2).getId(), "3000ms should be third");
    }

    @Test
    void testFlushLeavesFutureNotificationsInBuffer() {
        aggregator.receiveNotification(new SmsNotification("S1", 1000, "A", "123"));
        aggregator.receiveNotification(new SmsNotification("S2", 5000, "B", "123")); // Future

        // Flush only up to timestamp 2000
        List<Notification> flushed = aggregator.flushBufferUpTo(2000);

        assertEquals(1, flushed.size(), "Only the 1000ms notification should process");
        assertEquals("S1", flushed.get(0).getId());

        // Assert memory cleanup: one is gone, one remains
        assertEquals(1, aggregator.getBufferSize(), "The 5000ms notification should stay buffered");
    }
}