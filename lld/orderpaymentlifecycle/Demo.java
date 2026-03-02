package lld.orderpaymentlifecycle;

interface OrderState {
    void pay(OrderContext ctx);
    void ship(OrderContext ctx);
    void deliver(OrderContext ctx);
    void cancel(OrderContext ctx);
}

class NewState implements OrderState {

    public void pay(OrderContext ctx) {
        System.out.println("Payment successful");
        ctx.setState(new PaidState());
    }

    public void ship(OrderContext ctx) {
        System.out.println("Pay before shipping");
    }

    public void deliver(OrderContext ctx) {
        System.out.println("Order not shipped yet");
    }

    public void cancel(OrderContext ctx) {
        System.out.println("Order cancelled");
        ctx.setState(new CancelledState());
    }
}
class PaidState implements OrderState {

    public void pay(OrderContext ctx) {
        System.out.println("Already paid");
    }

    public void ship(OrderContext ctx) {
        System.out.println("Order shipped");
        ctx.setState(new ShippedState());
    }

    public void deliver(OrderContext ctx) {
        System.out.println("Order not shipped yet");
    }

    public void cancel(OrderContext ctx) {
        System.out.println("Order cancelled and refunded");
        ctx.setState(new CancelledState());
    }
}
class ShippedState implements OrderState {

    public void pay(OrderContext ctx) {
        System.out.println("Already paid");
    }

    public void ship(OrderContext ctx) {
        System.out.println("Already shipped");
    }

    public void deliver(OrderContext ctx) {
        System.out.println("Order delivered");
        ctx.setState(new DeliveredState());
    }

    public void cancel(OrderContext ctx) {
        System.out.println("Cannot cancel after shipping");
    }
}
class DeliveredState implements OrderState {

    public void pay(OrderContext ctx) {
        System.out.println("Already paid");
    }

    public void ship(OrderContext ctx) {
        System.out.println("Already delivered");
    }

    public void deliver(OrderContext ctx) {
        System.out.println("Already delivered");
    }

    public void cancel(OrderContext ctx) {
        System.out.println("Cannot cancel after delivery");
    }
}
class CancelledState implements OrderState {

    public void pay(OrderContext ctx) {
        System.out.println("Order is cancelled");
    }

    public void ship(OrderContext ctx) {
        System.out.println("Order is cancelled");
    }

    public void deliver(OrderContext ctx) {
        System.out.println("Order is cancelled");
    }

    public void cancel(OrderContext ctx) {
        System.out.println("Already cancelled");
    }
}
class OrderContext {

    private OrderState state = new NewState();

    public void setState(OrderState state) {
        this.state = state;
    }

    public void pay() {
        state.pay(this);
    }

    public void ship() {
        state.ship(this);
    }

    public void deliver() {
        state.deliver(this);
    }

    public void cancel() {
        state.cancel(this);
    }
}
public class Demo {
    public static void main(String[] args) {

        OrderContext order = new OrderContext();

        order.pay();
        order.ship();
        order.deliver();
        order.cancel(); // invalid
    }
}

