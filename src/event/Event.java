package event;

import java.time.LocalDateTime;
import java.util.Objects;

public class Event {
    private String eventId;
    private String title;
    private LocalDateTime timestamp;

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getEventId() {
        return eventId;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;
        return Objects.equals(eventId, event.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }

    @Override
    public String toString() {
        return "event= { id=" + eventId + ", timestamp " + timestamp + "}";
    }
}
