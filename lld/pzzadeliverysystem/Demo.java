package lld.pzzadeliverysystem;

import java.util.*;

/* ===================== MENU ===================== */

class MenuItem {
    private final String id;
    private final String name;
    private final double price;

    MenuItem(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    double getPrice() {
        return price;
    }
}

class MenuService {

    private final List<MenuItem> menu = List.of(
            new MenuItem("P1", "Margherita", 299),
            new MenuItem("P2", "Pepperoni", 399),
            new MenuItem("P3", "Veg Supreme", 349),
            new MenuItem("S1", "Garlic Bread", 199)
    );

    List<MenuItem> search(String keyword) {
        return menu.stream()
                .filter(item -> item.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    MenuItem getById(String id) {
        return menu.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }
}

/* ===================== CART ===================== */

class Cart {
    private final List<MenuItem> items = new ArrayList<>();

    void addItem(MenuItem item) {
        items.add(item);
        System.out.println(item.getName() + " added to cart");
    }

    double totalAmount() {
        return items.stream().mapToDouble(MenuItem::getPrice).sum();
    }
}

/* ===================== PAYMENT STRATEGY ===================== */

interface PaymentStrategy {
    boolean pay(double amount);
}

class CardPayment implements PaymentStrategy {
    public boolean pay(double amount) {
        System.out.println("Paid ₹" + amount + " using Card");
        return true;
    }
}

class UPIPayment implements PaymentStrategy {
    public boolean pay(double amount) {
        System.out.println("Paid ₹" + amount + " using UPI");
        return true;
    }
}

class CashOnDeliveryPayment implements PaymentStrategy {
    public boolean pay(double amount) {
        System.out.println("Cash on Delivery selected. Pay ₹" + amount + " at delivery.");
        return true;
    }
}

/* ===================== ORDER STATE ===================== */

interface OrderState {
    void proceed(OrderContext ctx);
    void cancel(OrderContext ctx);
}

class CreatedState implements OrderState {

    public void proceed(OrderContext ctx) {
        System.out.println("Order created. Awaiting payment.");
        ctx.setState(new PaymentPendingState());
    }

    public void cancel(OrderContext ctx) {
        System.out.println("Order cancelled.");
        ctx.setState(new CancelledState());
    }
}

class PaymentPendingState implements OrderState {

    public void proceed(OrderContext ctx) {
        if (ctx.processPayment()) {
            System.out.println("Payment successful.");
            ctx.setState(new ConfirmedState());
            ctx.notifyUser();
        } else {
            System.out.println("Payment failed.");
        }
    }

    public void cancel(OrderContext ctx) {
        System.out.println("Order cancelled.");
        ctx.setState(new CancelledState());
    }
}

class ConfirmedState implements OrderState {

    public void proceed(OrderContext ctx) {
        System.out.println("Order already confirmed.");
    }

    public void cancel(OrderContext ctx) {
        System.out.println("Cannot cancel a confirmed order.");
    }
}

class CancelledState implements OrderState {

    public void proceed(OrderContext ctx) {
        System.out.println("Order already cancelled.");
    }

    public void cancel(OrderContext ctx) {
        System.out.println("Order already cancelled.");
    }
}

/* ===================== ORDER CONTEXT ===================== */

class OrderContext {

    private OrderState state = new CreatedState();
    private PaymentStrategy paymentStrategy;
    private final double amount;

    OrderContext(double amount) {
        this.amount = amount;
    }

    void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }

    void setState(OrderState state) {
        this.state = state;
    }

    void proceed() {
        state.proceed(this);
    }

    void cancel() {
        state.cancel(this);
    }

    boolean processPayment() {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment method not selected");
        }
        return paymentStrategy.pay(amount);
    }

    void notifyUser() {
        System.out.println("Notification sent: Your pizza order is confirmed 🍕");
    }
}

/* ===================== DEMO ===================== */

public class Demo {

    public static void main(String[] args) {

        MenuService menuService = new MenuService();
        Cart cart = new Cart();

        // 🔍 SEARCH MENU
        System.out.println("Search results for 'veg':");
        menuService.search("veg")
                .forEach(i -> System.out.println(i.getName() + " - ₹" + i.getPrice()));

        // 🛒 ADD TO CART
        cart.addItem(menuService.getById("P3"));
        cart.addItem(menuService.getById("S1"));

        // 📦 PLACE ORDER
        OrderContext order = new OrderContext(cart.totalAmount());
        order.proceed(); // CREATED → PAYMENT_PENDING

        // 💳 SELECT PAYMENT METHOD
        order.setPaymentStrategy(new CashOnDeliveryPayment());
        // order.setPaymentStrategy(new CardPayment());
        // order.setPaymentStrategy(new UPIPayment());

        order.proceed(); // PAYMENT_PENDING → CONFIRMED

        // ❌ INVALID CANCEL
        order.cancel();
    }
}
