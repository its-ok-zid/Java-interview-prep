package stock;

import java.util.Objects;

public class Stock {

    private String ticker;
    private double price;

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTicker() {
        return ticker;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;
        return Objects.equals(ticker, stock.ticker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker);
    }

    @Override
    public String toString() {
        return ticker + "($ " + price + ")";
    }

}
