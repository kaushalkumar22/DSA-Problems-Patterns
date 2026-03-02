package lld.shoppingcartsystem;

import java.util.*;

class Item {
    private final String id;
    private final String name;
    private final double price;
    public Item(String id, String name, double price) {
        this.id = id; this.name = name; this.price = price;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}

// ---------- CART ----------
class CartItem {
    Item item;
    int qty;
    CartItem(Item item, int qty) { this.item = item; this.qty = qty; }
}
class ShoppingCart {
    private final String userId;
    private final Map<String, CartItem> items = new HashMap<>();
    ShoppingCart(String userId) { this.userId = userId; }
    public void addItem(Item item, int qty) {
        if (qty <= 0) return;
        items.merge(item.getId(), new CartItem(item, qty),
                (old, neu) -> { old.qty += neu.qty; return old; });
    }
    public void removeItem(String itemId) { items.remove(itemId); }
    public double getTotalAmount() {
        return items.values().stream()
                .mapToDouble(ci -> ci.item.getPrice() * ci.qty)
                .sum();
    }
    public void clear() { items.clear(); }
    public Collection<CartItem> getItems() { return items.values(); }
}
// ---------- ORDER ----------
class OrderItem {
    final String itemId;
    final String name;
    final double price;
    final int qty;
    OrderItem(String itemId, String name, double price, int qty) {
        this.itemId = itemId; this.name = name; this.price = price; this.qty = qty;
    }
}
enum OrderStatus { CREATED, PAID, CANCELLED }
class Order {
    final String orderId;
    final String userId;
    final List<OrderItem> items;
    final double totalAmount;
    OrderStatus status = OrderStatus.CREATED;
    final long createdAt;
    Order(String userId, List<OrderItem> items, double total) {
        this.orderId = UUID.randomUUID().toString();
        this.userId = userId;
        this.items = items;
        this.totalAmount = total;
        this.createdAt = System.currentTimeMillis();
    }
    void markPaid() { status = OrderStatus.PAID; }
    void markCancelled() { status = OrderStatus.CANCELLED; }
}
// ---------- PAYMENT ----------
class PaymentResult {
    final boolean success;
    final String transactionId;
    final String message;
    PaymentResult(boolean success, String tid, String msg) {
        this.success = success;
        transactionId = tid; message = msg;
    }
}
interface PaymentStrategy {
    PaymentResult pay(String orderId, double amount);
}
class CardPayment implements PaymentStrategy {
    public PaymentResult pay(String orderId, double amount) {
        // mock logic
        System.out.println("Charging card for " + amount);
        return new PaymentResult(true, "tx-" + UUID.randomUUID(), "Card Payment successful");
    }
}
class UpiPayment implements PaymentStrategy {
    public PaymentResult pay(String orderId, double amount) {
        System.out.println("Charging via UPI for " + amount);
        return new PaymentResult(true, "tx-" + UUID.randomUUID(), "UPI Payment successful");
    }
}
class CashOnDelivery implements PaymentStrategy {
    public PaymentResult pay(String orderId, double amount) {
        return new PaymentResult(true, null, "Cash on Delivery selected");
    }
}
// ---------- PAYMENT SERVICE ----------
class PaymentService {
    PaymentStrategy getStrategy(String type) {
        return switch (type.toLowerCase()) {
            case "card" -> new CardPayment();
            case "upi"  -> new UpiPayment();
            case "cod"  -> new CashOnDelivery();
            default -> throw new IllegalArgumentException("Unknown payment type");
        };
    }
    PaymentResult processPayment(Order order, String type) {
        PaymentStrategy strat = getStrategy(type);
        return strat.pay(order.orderId, order.totalAmount);
    }
}
// ---------- CART SERVICE ----------
class CartService {
    private final Map<String, ShoppingCart> carts = new HashMap<>();
    private final Map<String, Item> itemCatalog; // assume populated
    CartService(Map<String, Item> catalog) { this.itemCatalog = catalog; }
    ShoppingCart getOrCreateCart(String userId) {
        return carts.computeIfAbsent(userId, ShoppingCart::new);
    }
    void addItem(String userId, String itemId, int qty) {
        Item item = itemCatalog.get(itemId);
        if (item == null) throw new IllegalArgumentException("Unknown item");
        getOrCreateCart(userId).addItem(item, qty);
    }
    void removeItem(String userId, String itemId) {
        getOrCreateCart(userId).removeItem(itemId);
    }
    ShoppingCart viewCart(String userId) { return getOrCreateCart(userId); }
    void clearCart(String userId) { getOrCreateCart(userId).clear(); }
}

// ---------- ORDER SERVICE ----------
class OrderService {
    private final Map<String, Order> orders = new HashMap<>();
    private final CartService cartService;
    OrderService(CartService cartService) { this.cartService = cartService; }

    Order placeOrder(String userId) {
        ShoppingCart cart = cartService.viewCart(userId);
        if (cart.getItems().isEmpty()) throw new IllegalStateException("Cart empty");
        List<OrderItem> oi = new ArrayList<>();
        cart.getItems().forEach(ci -> oi.add(
                new OrderItem(ci.item.getId(), ci.item.getName(), ci.item.getPrice(), ci.qty)));
        Order order = new Order(userId, oi, cart.getTotalAmount());
        orders.put(order.orderId, order);
        cart.clear();
        return order;
    }
    Order getOrder(String orderId) { return orders.get(orderId); }
}
// ---------- NOTIFICATION ----------
class NotificationService {
    void notify(String userId, String message) {
        System.out.println("Notify " + userId + ": " + message);
    }
}
// ---------- DEMO ----------
public class Demo {
    public static void main(String[] args) {
        // Setup catalog
        Map<String, Item> catalog = new HashMap<>();
        catalog.put("1", new Item("1","Cheese Pizza",250));
        catalog.put("2", new Item("2","Pepsi",50));
        CartService cartService = new CartService(catalog);
        OrderService orderService = new OrderService(cartService);
        PaymentService paymentService = new PaymentService();
        NotificationService notifier = new NotificationService();
        String userId = "user-1";
// Add to cart
        cartService.addItem(userId, "1", 2);
        cartService.addItem(userId, "2", 1);
// Place order
        Order order = orderService.placeOrder(userId);
        System.out.println("Created Order: " + order.orderId + " Total: " + order.totalAmount);
// Pay
        PaymentResult result = paymentService.processPayment(order, "upi");
        if (result.success) {
            order.markPaid();
        }
        notifier.notify(userId, result.message);
// Inspect
        System.out.println("Order Status: " + order.status);
    }
}
