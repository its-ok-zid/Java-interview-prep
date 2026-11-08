package stock;

public class StockApplication {

    public static void main(String[] args) {
        StockTracker tracker = new StockTracker();

        System.out.println("\n========== ADDING STOCKS ==========");
        tracker.addOrUpdateStock("AAPL", 150.50);
        tracker.addOrUpdateStock("GOOGL", 2800.75);
        tracker.addOrUpdateStock("MSFT", 305.20);
        tracker.addOrUpdateStock("AMZN", 3300.00);
        tracker.addOrUpdateStock("TSLA", 750.10);
        tracker.addOrUpdateStock("ZBRA", 2800.75); // Same price as GOOGL to test tie-breaker
        System.out.println("Initial stocks added successfully.");

        System.out.println("\n========== GET STOCK PRICE ==========");
        System.out.println("AAPL Price: $" + tracker.getStockPrice("AAPL"));
        System.out.println("MSFT Price: $" + tracker.getStockPrice("MSFT"));

        System.out.println("\n========== GET TOP PERFORMING STOCKS ==========");
        System.out.println("Top 3 Stocks:");
        for (Stock stock : tracker.getTopPerformingStock(3)) {
            System.out.println(stock);
        }

        System.out.println("\n========== UPDATE STOCK PRICE ==========");
        System.out.println("Updating TSLA to $3500.00...");
        tracker.addOrUpdateStock("TSLA", 3500.00);
        System.out.println("New Top 3 Stocks:");
        for (Stock stock : tracker.getTopPerformingStock(3)) {
            System.out.println(stock);
        }

        System.out.println("\n========== GET STOCKS IN PRICE RANGE ==========");
        System.out.println("Stocks between $200.00 and $3000.00:");
        for (Stock stock : tracker.getStockInPriceRange(200.00, 3000.00)) {
            System.out.println(stock);
        }

        System.out.println("\n========== DELETE STOCK ==========");
        System.out.println("Deleting AMZN...");
        tracker.deleteStock("AMZN");
        System.out.println("Top 3 Stocks after deleting AMZN:");
        for (Stock stock : tracker.getTopPerformingStock(3)) {
            System.out.println(stock);
        }

        System.out.println("\n========== TEST NULL RETURN ==========");
        try {
            System.out.println("Fetching deleted stock (AMZN): " + tracker.getStockPrice("AMZN"));
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException when unboxing null Double to double.");
        }
    }
}