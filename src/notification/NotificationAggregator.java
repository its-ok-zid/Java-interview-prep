package notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class NotificationAggregator {

    // O(1) Lookup and Deduplication (e.g., if a retry sends the same SMS twice)
    private final Map<String, Notification> notificationById = new HashMap<>();

    // O(log N) Chronological Buffer for out-of-order arrivals
    private final TreeSet<Notification> buffer = new TreeSet<>((n1, n2) -> {
        int timeCompare = Long.compare(n1.getTimestamp(), n2.getTimestamp());
        // CRITICAL Tie-breaker: If two notifications arrive at the exact same millisecond,
        // we use the ID so the TreeSet doesn't treat them as duplicates and delete one!
        return timeCompare != 0 ? timeCompare : n1.getId().compareTo(n2.getId());
    });

    /**
     * Requirement: Queue incoming notifications from different services.
     */
    public void receiveNotification(Notification notification) {
        // Idempotency check: Ignore duplicate messages arriving from network retries
        if (notificationById.containsKey(notification.getId())) {
            return;
        }
        notificationById.put(notification.getId(), notification);
        buffer.add(notification);
    }

    /**
     * Requirement: Process/Flush the buffer chronologically up to a specific time.
     * This simulates a scheduled job that runs every X seconds to process ordered data.
     */
    public List<Notification> flushBufferUpTo(long currentTimestamp) {
        List<Notification> processed = new ArrayList<>();

        // Polling the lowest time element (O(log N) per removal)
        while (!buffer.isEmpty() && buffer.first().getTimestamp() <= currentTimestamp) {
            Notification oldest = buffer.pollFirst(); // Removes and returns the first element
            notificationById.remove(oldest.getId());  // Memory cleanup!
            processed.add(oldest);
        }

        return processed;
    }

    /**
     * Requirement: Delete a buffered notification if the sender cancels it.
     */
    public void cancelNotification(String id) {
        Notification notification = notificationById.remove(id);
        if (notification != null) {
            buffer.remove(notification);
        }
    }

    public int getBufferSize() {
        return buffer.size();
    }
}