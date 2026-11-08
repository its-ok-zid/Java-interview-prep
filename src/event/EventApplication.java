package event;

import java.time.LocalDateTime;
import java.util.Collection;

public class EventApplication {

    public static void main(String[] args) {

        EventScheduler scheduler = new EventScheduler();

        // ---------------- CREATE EVENTS ----------------

        Event event1 = new Event();
        event1.setEventId("E101");
        event1.setTitle("Java Workshop");
        event1.setTimestamp(LocalDateTime.of(2026, 11, 10, 10, 0));

        Event event2 = new Event();
        event2.setEventId("E102");
        event2.setTitle("Spring Boot Session");
        event2.setTimestamp(LocalDateTime.of(2026, 11, 12, 14, 30));

        Event event3 = new Event();
        event3.setEventId("E103");
        event3.setTitle("Microservices Architecture");
        event3.setTimestamp(LocalDateTime.of(2026, 11, 15, 11, 0));

        Event event4 = new Event();
        event4.setEventId("E104");
        event4.setTitle("System Design Interview");
        event4.setTimestamp(LocalDateTime.of(2026, 11, 18, 16, 0));

        Event event5 = new Event();
        event5.setEventId("E105");
        event5.setTitle("Database Workshop");
        event5.setTimestamp(LocalDateTime.of(2026, 11, 20, 9, 0));


        // ---------------- ADD EVENTS ----------------

        scheduler.addEvent(event1);
        scheduler.addEvent(event2);
        scheduler.addEvent(event3);
        scheduler.addEvent(event4);
        scheduler.addEvent(event5);

        System.out.println("\n========== EVENTS ADDED ==========");
        System.out.println("5 events added successfully.");


        // ---------------- FIND NEXT EVENT ----------------

        System.out.println("\n========== FIND NEXT EVENT ==========");

        LocalDateTime currentTime =
                LocalDateTime.of(2026, 11, 13, 10, 0);

        Event nextEvent = scheduler.getNextEvent(currentTime);

        System.out.println("Current time: " + currentTime);
        System.out.println("Next event: " + nextEvent);


        // ---------------- FIND EVENTS IN TIME RANGE ----------------

        System.out.println("\n========== EVENTS IN TIME RANGE ==========");

        LocalDateTime startTime =
                LocalDateTime.of(2026, 11, 11, 0, 0);

        LocalDateTime endTime =
                LocalDateTime.of(2026, 11, 18, 23, 59);

        Collection<Event> eventsInRange =
                scheduler.getEventsInRange(startTime, endTime);

        System.out.println("Start time: " + startTime);
        System.out.println("End time: " + endTime);

        for (Event event : eventsInRange) {
            System.out.println(event);
        }


        // ---------------- UPDATE EVENT ----------------

        System.out.println("\n========== UPDATE EVENT ==========");

        System.out.println("Before update:");
        System.out.println(scheduler.getNextEvent(
                LocalDateTime.of(2026, 11, 14, 0, 0)
        ));

        scheduler.updateEvent(
                "E103",
                LocalDateTime.of(2026, 11, 16, 15, 0),
                "Advanced Microservices Workshop"
        );

        System.out.println("After update:");

        Event updatedEvent = scheduler.getNextEvent(
                LocalDateTime.of(2026, 11, 14, 0, 0)
        );

        System.out.println(updatedEvent);


        // ---------------- DELETE EVENT ----------------

        System.out.println("\n========== DELETE EVENT ==========");

        scheduler.deleteEvent("E102");

        System.out.println("Deleted event with ID: E102");

        // Verify deletion
        Event nextAfterDeletion = scheduler.getNextEvent(
                LocalDateTime.of(2026, 11, 11, 0, 0)
        );

        System.out.println("Next event after deletion: " + nextAfterDeletion);


        // ---------------- VERIFY FINAL TIME RANGE ----------------

        System.out.println("\n========== FINAL EVENT SCHEDULE ==========");

        Collection<Event> remainingEvents =
                scheduler.getEventsInRange(
                        LocalDateTime.of(2026, 11, 1, 0, 0),
                        LocalDateTime.of(2026, 11, 30, 23, 59)
                );

        for (Event event : remainingEvents) {
            System.out.println(event);
        }
    }
}
