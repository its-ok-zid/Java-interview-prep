package event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventSchedulerTest {

    private EventScheduler scheduler;
    private Event event1;
    private Event event2;
    private Event event3;

    @BeforeEach
    void setUp() {
        scheduler = new EventScheduler();

        event1 = new Event();
        event1.setEventId("E1");
        event1.setTitle("Morning Meeting");
        event1.setTimestamp(LocalDateTime.of(2026, 11, 10, 9, 0));

        event2 = new Event();
        event2.setEventId("E2");
        event2.setTitle("Lunch Break");
        event2.setTimestamp(LocalDateTime.of(2026, 11, 10, 13, 0));

        event3 = new Event();
        event3.setEventId("E3");
        event3.setTitle("Evening Sync");
        event3.setTimestamp(LocalDateTime.of(2026, 11, 10, 18, 0));
    }

    @Test
    void testAddAndGetNextEvent() {
        scheduler.addEvent(event1);
        scheduler.addEvent(event3);

        LocalDateTime queryTime = LocalDateTime.of(2026, 11, 10, 10, 0);
        Event next = scheduler.getNextEvent(queryTime);

        assertNotNull(next, "Should find an upcoming event.");
        assertEquals("E3", next.getEventId(), "Evening Sync should be the next event after 10 AM.");
    }

    @Test
    void testGetEventsInRange() {
        scheduler.addEvent(event1);
        scheduler.addEvent(event2);
        scheduler.addEvent(event3);

        LocalDateTime start = LocalDateTime.of(2026, 11, 10, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 11, 10, 20, 0);

        Collection<Event> range = scheduler.getEventsInRange(start, end);
        assertEquals(2, range.size(), "Should capture E2 and E3 only.");

        List<Event> resultList = new ArrayList<>(range);
        assertEquals("E2", resultList.get(0).getEventId());
        assertEquals("E3", resultList.get(1).getEventId());
    }

    @Test
    void testUpdateEventResortsTreeMap() {
        scheduler.addEvent(event1); // 9:00 AM
        scheduler.addEvent(event2); // 1:00 PM

        // Push Morning Meeting to 2:00 PM
        LocalDateTime newTime = LocalDateTime.of(2026, 11, 10, 14, 0);
        scheduler.updateEvent("E1", newTime, "Delayed Meeting");

        LocalDateTime queryTime = LocalDateTime.of(2026, 11, 10, 13, 30);
        Event next = scheduler.getNextEvent(queryTime);

        assertEquals("E1", next.getEventId(), "Delayed Meeting should now be after Lunch Break.");
        assertEquals("Delayed Meeting", next.getTitle(), "Title should be updated.");
    }

    @Test
    void testDeleteEvent() {
        scheduler.addEvent(event1);
        scheduler.deleteEvent("E1");

        LocalDateTime queryTime = LocalDateTime.of(2026, 11, 10, 8, 0);
        assertNull(scheduler.getNextEvent(queryTime), "Event should be deleted from the TreeMap.");
    }

    @Test
    void testDeleteNonExistentEventDoesNotThrow() {
        assertDoesNotThrow(() -> scheduler.deleteEvent("FAKE_ID"), "Deleting non-existent event should be safe.");
    }
}