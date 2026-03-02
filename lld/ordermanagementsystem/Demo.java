package lld.ordermanagementsystem;

import java.util.*;

/* ===================== DOMAIN ===================== */

class Item {
    private final String itemId;
    private final String name;
    private final int price;

    public Item(String itemId, String name, int price) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    // Important: Item used as Map key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return itemId.equals(item.itemId);
    }

    @Override
    public int hashCode() {
        return itemId.hashCode();
    }
}

/* ===================== MENU ===================== */

class ItemMenu {
    private final List<Item> items = List.of(
            new Item("P1", "Margherita", 299),
            new Item("P2", "Pepperoni", 399),
            new Item("P3", "Veg Supreme", 349),
            new Item("S1", "Garlic Bread", 199)
    );

    public List<Item> search(String keyword) {
        return items.stream()
                .filter(i -> i.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    public Item getById(String id) {
        return items.stream()
                .filter(i -> i.getItemId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
    }
}

/* ===================== CART ===================== */

class Cart {
    private final Map<Item, Integer> items = new HashMap<>();

    public void addItem(Item item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public double totalAmount() {
        return items.entrySet()
                .stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}

/* ===================== PAYMENT (Strategy Pattern) ===================== */

interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println(amount + " paid using Credit Card");
    }
}

/* ===================== ORDER STATE (State Pattern) ===================== */

interface OrderState {
    void proceed(OrderContext ctx);
    void cancel(OrderContext ctx);
}

class CreatedState implements OrderState {

    @Override
    public void proceed(OrderContext ctx) {
        if (ctx.getTotalAmount() <= 0) {
            throw new IllegalStateException("Cannot place order with empty cart");
        }
        System.out.println("Order created");
        ctx.nextState(new PaymentPendingState());
    }

    @Override
    public void cancel(OrderContext ctx) {
        System.out.println("Order canceled");
        ctx.nextState(new OrderCanceledState());
    }
}

class PaymentPendingState implements OrderState {

    @Override
    public void proceed(OrderContext ctx) {
        ctx.processPayment();
        System.out.println("Payment successful");
        ctx.nextState(new OrderPlacedState());
    }

    @Override
    public void cancel(OrderContext ctx) {
        System.out.println("Order canceled");
        ctx.nextState(new OrderCanceledState());
    }
}

class OrderPlacedState implements OrderState {

    @Override
    public void proceed(OrderContext ctx) {
        System.out.println("Order already placed");
    }

    @Override
    public void cancel(OrderContext ctx) {
        System.out.println("Order cannot be canceled after placement");
    }
}

class OrderCanceledState implements OrderState {

    @Override
    public void proceed(OrderContext ctx) {
        System.out.println("Canceled order cannot proceed");
    }

    @Override
    public void cancel(OrderContext ctx) {
        System.out.println("Order already canceled");
    }
}

/* ===================== ORDER CONTEXT ===================== */

class OrderContext {
    private OrderState state = new CreatedState();
    private double totalAmount;
    private PaymentStrategy paymentStrategy;

    public void nextState(OrderState state) {
        this.state = state;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void processPayment() {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy not set");
        }
        paymentStrategy.pay(totalAmount);
    }

    public void proceed() {
        state.proceed(this);
    }

    public void cancel() {
        state.cancel(this);
    }
}

/* ===================== DEMO ===================== */

public class Demo {
    public static void main(String[] args) {

        ItemMenu menu = new ItemMenu();
        Cart cart = new Cart();

        // 🔍 Search
        System.out.println("Search results for 'veg':");
        menu.search("veg")
                .forEach(i -> System.out.println(i.getName() + " - " + i.getPrice()));

        // 🛒 Add to cart
        cart.addItem(menu.getById("P3"), 2);
        cart.addItem(menu.getById("S1"), 2);

        // 📦 Place order
        OrderContext order = new OrderContext();
        order.setTotalAmount(cart.totalAmount());
        order.proceed(); // CREATED → PAYMENT_PENDING

        // 💳 Payment
        order.setPaymentStrategy(new CreditCardPayment());
        order.proceed(); // PAYMENT_PENDING → PLACED

        // ❌ Invalid cancel
        order.cancel();
    }
}
