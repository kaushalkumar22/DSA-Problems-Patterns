package designpattern.observer;

import java.util.ArrayList;
import java.util.List;

class StockPriceEvent {
    final String symbol;
    final int oldPrice;
    final int newPrice;
    final long timestamp;

    StockPriceEvent(String symbol, int oldPrice, int newPrice) {
        this.symbol = symbol;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.timestamp = System.currentTimeMillis();
    }
}

interface StockObserver {
    void update(StockPriceEvent event);
}
interface StockSubject {
    void addObserver(StockObserver observer);
    void removeObserver(StockObserver observer);
    void notifyObservers(StockPriceEvent event);
}

class StockMarket implements StockSubject {

    private final List<StockObserver> observers = new ArrayList<>();
    private final String symbol;
    private int price;

    StockMarket(String symbol, int initialPrice) {
        this.symbol = symbol;
        this.price = initialPrice;
    }

    @Override
    public void addObserver(StockObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(StockObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(StockPriceEvent event) {
        for (StockObserver observer : observers) {
            try {
                observer.update(event); // failure isolation
            } catch (Exception e) {
                System.out.println("Observer failed: "
                        + observer.getClass().getSimpleName());
            }
        }
    }

    public void onPriceChange(int newPrice) {
        StockPriceEvent event =
                new StockPriceEvent(symbol, price, newPrice);
        this.price = newPrice;
        notifyObservers(event);
    }
}
class MobileClient implements StockObserver {
    public void update(StockPriceEvent event) {
        System.out.println("[MOBILE] " + event.symbol +
                " price updated to " + event.newPrice);
    }
}
class WebClient implements StockObserver {
    public void update(StockPriceEvent event) {
        System.out.println("[WEB] " + event.symbol +
                " price updated to " + event.newPrice);
    }
}
public class Demo1 {

    public static void main(String[] args) {

        StockMarket market = new StockMarket("AAPL", 150);

        market.addObserver(new MobileClient());
        market.addObserver(new WebClient());

        market.onPriceChange(155);
        market.onPriceChange(162);
    }
}
