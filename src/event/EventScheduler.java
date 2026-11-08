package event;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class EventScheduler {
    // For O(1) Lookups and Deletions by ID
    private final Map<String, Event> eventsById = new HashMap<>();

    // For O(log N) Time Queries. Key = Timestamp, Value = Event
    private final TreeMap<LocalDateTime, Event> eventsByTime = new TreeMap<>();

    // Requirement: Add an event
    public void addEvent(Event event) {
        eventsById.put(event.getEventId(), event);
        eventsByTime.put(event.getTimestamp(), event);
    }

    // Requirement: Update an event
    public void updateEvent(String eventId, LocalDateTime newTimestamp, String newTitle) {
        Event event = eventsById.get(eventId);
        if (event == null) return;

        // CRITICAL: We are changing the time. We MUST remove the old Key from the TreeMap first!
        eventsByTime.remove(event.getTimestamp());

        event.setTimestamp(newTimestamp);
        event.setTitle(newTitle);

        // Put it back with the new Key so it sorts correctly
        eventsByTime.put(newTimestamp, event);
    }

    // Requirement: Find the next event (O(log N))
    public Event getNextEvent(LocalDateTime currentTimestamp) {
        // ceilingEntry instantly finds the closest key >= currentTimestamp
        Map.Entry<LocalDateTime, Event> entry = eventsByTime.ceilingEntry(currentTimestamp);
        return entry != null ? entry.getValue() : null;
    }

    // Requirement: Find all events in a time range
    public Collection<Event> getEventsInRange(LocalDateTime startTime, LocalDateTime endTime) {
        // No dummy objects needed! Just pass the raw Long keys.
        return eventsByTime.subMap(startTime, true, endTime, true).values();
    }

    // Requirement: Delete an event by ID
    public void deleteEvent(String eventId) {
        Event event = eventsById.remove(eventId);
        if (event != null) {
            eventsByTime.remove(event.getTimestamp());
        }
    }
}