package designpattern.stratgy;
//Strategy Interface
interface PaymentStrategy {
    void pay(double amount);
}

//Concrete Strategies
class CreditCardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println(amount + " paid using Credit Card: " + amount);
    }
}
class UPIPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println(amount + " paid using UPI ID: " + amount);
    }
}

//Context Class
class PaymentContext {
    private PaymentStrategy strategy;
    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }
    public void pay(double amount) {
        strategy.pay(amount);
    }
}

//Client Code
public class Demo {
    public static void main(String[] args) {
        PaymentContext context = new PaymentContext();
        context.setStrategy(new CreditCardPayment());
        context.pay(200);
        context.setStrategy(new UPIPayment());
        context.pay(500);
    }
}
