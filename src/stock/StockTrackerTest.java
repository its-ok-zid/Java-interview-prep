package stock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StockTrackerTest {

    private StockTracker tracker;

    @BeforeEach
    void setUp() {
        tracker = new StockTracker();
    }

    @Test
    void testAddAndGetStockPrice() {
        tracker.addOrUpdateStock("AAPL", 150.0);
        assertEquals(150.0, tracker.getStockPrice("AAPL"), "Price should match added stock price.");
        assertNull(tracker.getStockPrice("FAKE"), "Non-existent stock should return null.");
    }

    @Test
    void testUpdateStockPriceResortsCollection() {
        tracker.addOrUpdateStock("AAPL", 100.0);
        tracker.addOrUpdateStock("MSFT", 200.0);

        tracker.addOrUpdateStock("AAPL", 300.0); // Now AAPL is top

        List<Stock> topStocks = tracker.getTopPerformingStock(2);
        assertEquals("AAPL", topStocks.get(0).getTicker(), "AAPL should be top after update.");
        assertEquals("MSFT", topStocks.get(1).getTicker(), "MSFT should be second.");
    }

    @Test
    void testGetTopPerformingStockWithLimit() {
        tracker.addOrUpdateStock("A", 10.0);
        tracker.addOrUpdateStock("B", 50.0);
        tracker.addOrUpdateStock("C", 30.0);

        List<Stock> top = tracker.getTopPerformingStock(2);
        assertEquals(2, top.size(), "Should respect the limit of 2.");
        assertEquals("B", top.get(0).getTicker(), "Highest price should be first.");
        assertEquals("C", top.get(1).getTicker(), "Second highest should be second.");
    }

    @Test
    void testGetStockInPriceRangeWithDummyBoundaries() {
        tracker.addOrUpdateStock("CHEAP", 50.0);
        tracker.addOrUpdateStock("AAPL", 100.0);
        tracker.addOrUpdateStock("MSFT", 150.0);
        tracker.addOrUpdateStock("ZBRA", 200.0);
        tracker.addOrUpdateStock("EXPEN", 250.0);

        Set<Stock> rangeResult = tracker.getStockInPriceRange(100.0, 200.0);
        assertEquals(3, rangeResult.size(), "Should only capture AAPL, MSFT, and ZBRA.");

        List<Stock> resultList = new ArrayList<>(rangeResult);
        assertEquals("AAPL", resultList.get(0).getTicker(), "Lowest edge AAPL should be first.");
        assertEquals("ZBRA", resultList.get(2).getTicker(), "Highest edge ZBRA should be last.");
    }

    @Test
    void testDeleteStockRemovesItEntirely() {
        tracker.addOrUpdateStock("AAPL", 150.0);
        tracker.deleteStock("AAPL");

        assertNull(tracker.getStockPrice("AAPL"), "Stock should be deleted from lookup map.");
        assertTrue(tracker.getTopPerformingStock(1).isEmpty(), "Stock should be deleted from sorted tree.");
    }
}