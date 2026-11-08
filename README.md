# Java Backend Systems: In-Memory Data Structures & Optimization

This repository contains a collection of high-performance, in-memory data structure challenges implemented in Java. It demonstrates advanced usage of the Java Collections Framework to achieve strict `O(1)` and `O(log N)` time complexities for real-time operations.

These scenarios were developed as part of rigorous preparation for Senior Java Backend Engineering interviews, focusing heavily on time/space complexity, memory management, and robust object-oriented contracts.

## 🚀 Core Concepts Demonstrated

* **Asymptotic Time Complexity (Big-O):** Designing storage layers that guarantee constant or logarithmic time for lookups, insertions, deletions, and range queries.
* **The Hashing Contract:** Custom `equals()` and `hashCode()` implementations to prevent ghost duplicates and enforce entity uniqueness based on business logic (e.g., unique IDs) rather than default memory addresses.
* **Secondary Indexing:** Utilizing dual-map architectures (e.g., `HashMap` + `TreeMap`) to avoid `O(N)` linear scans when querying data by secondary attributes.
* **Memory Leak Prevention:** Safely cascading deletions across multiple data structures and removing empty nested collections to ensure predictable memory footprints.
* **Tree Traversal & Sorting Mechanics:** Manipulating `TreeSet` and `TreeMap` Red-Black tree structures using custom `Comparator` chains, `ceilingEntry()`, and bounded `subSet()` / `subMap()` range queries.
* **Modern Java (8+):** Utilizing `computeIfAbsent`, the Stream API, and Method References for clean, expressive code.

## 📂 Project Scenarios

### 1. In-Memory Book Catalog
* **Requirements:** Support adding a book, fetching a book by its unique ISBN, finding all books by a specific author, updating book details, and deleting a book.
* **Constraints:** All operations (including fetching all books by an author) must execute in strictly `O(1)` time complexity. No linear scanning `O(N)` is allowed.
* **Focus:** `O(1)` operations and Safe Secondary Indexing.
* **Architecture:** Uses a primary `HashMap` for fast ISBN lookups and a secondary `HashMap<String, Set<Book>>` for Author lookups.
* **Highlights:** Implements aggressive memory cleanup logic during updates and deletions to ensure empty author sets are actively purged from the heap.

### 2. Movie Catalog with Dynamic Ratings
* **Requirements:** Support adding a movie, updating a movie's rating, retrieving a movie by ID, fetching movies by genre, and returning the Top-N highest-rated movies overall.
* **Constraints:** ID lookups, genre filtering, and updates must be `O(1)`. Fetching the Top-N movies must be handled efficiently without resorting the entire dataset on every query (targeting `O(log N)` insertion/sorting).
* **Focus:** `O(log N)` Top-N Queries and multi-index synchronization.
* **Architecture:** Combines `HashMap` for ID/Genre routing with a `TreeSet` for maintaining a constantly sorted leaderboard of movies by rating.
* **Highlights:** Demonstrates the necessity of removing objects from a Red-Black tree *before* mutating the fields they are sorted by, preventing permanent tree corruption.

### 3. Real-Time Stock Price Tracker
* **Requirements:** Insert/update stock prices, retrieve a stock price by its unique ticker symbol, return the Top-N performing stocks, retrieve all stocks within a specific price range, and delete a stock.
* **Constraints:** Ticker lookups and deletions must be `O(1)`. The range query must be highly optimized (strictly `O(log N)` to find the boundaries).
* **Focus:** Range Queries and Custom Comparators.
* **Architecture:** Dual-index system mapping Stock Tickers (`HashMap`) alongside a price-sorted `TreeSet`.
* **Highlights:** Uses mathematical boundaries (dummy search probes using `""` and `"~"`) to execute highly efficient `O(log N)` price range queries (`subSet()`). Implements complex comparator tie-breaking logic.

### 4. Real-Time Event Scheduler
* **Requirements:** Add an event with a unique ID and timestamp, retrieve the *next* upcoming event based on current time, fetch all events in a given time range, update an event's timestamp, and delete an event by ID.
* **Constraints:** Finding the next event and time-range queries must be `O(log N)`. Deletions and lookups by ID must be `O(1)`.
* **Focus:** Time-Series Data and Temporal Queries.
* **Architecture:** A `HashMap` for `O(1)` ID resolution combined with a `TreeMap<LocalDateTime, Event>` for temporal organization.
* **Highlights:** Replaces `O(N)` iterations with `TreeMap.ceilingEntry()` to find the absolute "next" upcoming event in `O(log N)` time without scanning.

## 🛠️ Tech Stack

* **Language:** Java 8+
* **Key Libraries:** `java.util.Collections`, `java.util.stream.*`, `java.time.LocalDateTime`

## 💡 Key Technical Takeaways

1. **Wrapper vs. Primitive Types:** Returning primitives (e.g., `double`) in map lookups can lead to severe `NullPointerExceptions` during unboxing. Always use wrapper classes (`Double`) when a "not found" state is possible.
2. **Tree Mutability Safety:** If an entity is stored in a `TreeSet`/`TreeMap`, you **cannot** mutate the field it is sorted by directly. You must `remove()` -> `update` -> `add()` to preserve binary tree integrity.
3. **Comparator Contracts:** A Java `Comparator` does not return values; it acts as a directional referee returning `-1`, `0`, or `1`. Tie-breakers are mandatory to prevent accidental object deletion upon a `0` evaluation.

## 🚀 How to Run

Each scenario is self-contained within its respective package. Run the `main` method in the corresponding `*Application.java` file (e.g., `EventApplication.java`) to execute the integration tests and view console output.