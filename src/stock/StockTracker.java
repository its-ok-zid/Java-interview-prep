package stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class StockTracker {
    private final Map<String, Stock> stocks = new HashMap<>();
    private final TreeSet<Stock> sortedStocks = new TreeSet<>((s1, s2) -> {
        int priceCompare = Double.compare(s1.getPrice(), s2.getPrice());
        return priceCompare != 0 ? priceCompare : s1.getTicker().compareTo(s2.getTicker());
    });

    /**
     * This about TreeSet can also  be written as
     * <p>
     * 1. private final TreeSet<Stock> sortedStocks = new TreeSet<>(Comparator.comparingDouble(Stock::getPrice).thenComparing(Stock::getTicker));
     * OR
     * 2.private final TreeSet<Stock> sortedStocks = new TreeSet<>(
     * Comparator.comparingDouble(Stock::getPrice)
     * .thenComparing(Stock::getTicker));
     * <p>
     * for Descending order sorting :-
     * <p>
     * 1. private final TreeSet<Stock> sortedStocks = new TreeSet<>((s1, s2) -> {
     * // SWAPPED s2 and s1 here for descending order!
     * int priceCompare = Double.compare(s2.getPrice(), s1.getPrice());
     * <p>
     * // Tie-breaker remains alphabetical (ascending)
     * return priceCompare != 0 ? priceCompare : s1.getTicker().compareTo(s2.getTicker());
     * });
     * OR
     * 2. private final TreeSet<Stock> sortedStocks = new TreeSet<>(
     * Comparator.comparingDouble(Stock::getPrice)
     * .reversed()
     * .thenComparing(Stock::getTicker));
     *
     */

    public void addOrUpdateStock(String ticker, double price) {
        Stock existingStock = stocks.get(ticker);

        if (existingStock != null) {
            sortedStocks.remove(existingStock);
            existingStock.setPrice(price);
            sortedStocks.add(existingStock);
        } else {
            Stock newStock = new Stock();
            newStock.setTicker(ticker);
            newStock.setPrice(price);

            stocks.put(ticker, newStock);
            sortedStocks.add(newStock);
        }
    }


    public Double getStockPrice(String ticker) {
        Stock stock = stocks.get(ticker);
        return stock != null ? stock.getPrice() : null;
    }

    public List<Stock> getTopPerformingStock(int n) {
        return sortedStocks.descendingSet().stream()
                .limit(n)
                .collect(Collectors.toList());
    }

    public Set<Stock> getStockInPriceRange(double minPrice, double maxPrice) {
        Stock minStock = new Stock();
        minStock.setTicker("");
        minStock.setPrice(minPrice);

        Stock maxStock = new Stock();
        maxStock.setTicker("~");
        maxStock.setPrice(maxPrice);

        return sortedStocks.subSet(minStock, true, maxStock, true);
    }

    public void deleteStock(String ticker) {
        Stock removeStock = stocks.remove(ticker);
        if (removeStock != null) {
            sortedStocks.remove(removeStock);
        }
    }

}
