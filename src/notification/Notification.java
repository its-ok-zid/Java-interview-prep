package notification;

import java.util.Objects;

public abstract class Notification {
    private String id;
    private long timestamp;
    private String content;

    public Notification(String id, long timestamp, String content) {
        this.id = id;
        this.timestamp = timestamp;
        this.content = content;
    }

    public String getId() { return id; }
    public long getTimestamp() { return timestamp; }
    public String getContent() { return content; }

    // Client Handbook Rule: Immutable-style ID equality to prevent HashSet corruption
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false; // Modern instanceof check
        Notification that = (Notification) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}