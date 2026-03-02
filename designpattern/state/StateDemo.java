package designpattern.state;

//State Interface
interface OrderState {
    void next(OrderContext context);
}

// Concrete States
class NewOrderState implements OrderState {
    public void next(OrderContext context) {
        System.out.println("Current Order state: New");
        context.setNextState(new PaymentsOrderState());
        System.out.println("Order state changed to: PAYMENT");
    }
}
class PaymentsOrderState implements OrderState {
    public void next(OrderContext context) {
        System.out.println("Current Order state: Pay");
        context.setNextState(new ShippedOrderState());
        System.out.println("Order state changed to: SHIPPED");
    }
}
class ShippedOrderState implements OrderState {
    public void next(OrderContext context) {
        System.out.println("Current Order state: Shipped");
        context.setNextState(new DeliverOrderState());
        System.out.println("Order state changed to: DELIVERED");
    }
}
class DeliverOrderState implements OrderState {
    public void next(OrderContext context) {
        System.out.println("Order already delivered");
    }
}

//Context
class OrderContext {
    private OrderState orderState;
    public OrderContext() {
        this.orderState = new NewOrderState(); // initial state
    }
    public void setNextState(OrderState state) {
        this.orderState = state;
    }
    public void next() {
        orderState.next(this);
    }
}

// Client
public class StateDemo {
    public static void main(String[] args) {
        OrderContext context = new OrderContext();
        context.next(); // NEW → PAYMENT
        context.next(); // PAYMENT → SHIPPED
        context.next(); // SHIPPED → DELIVERED
        context.next(); // Order already delivered
    }
}
