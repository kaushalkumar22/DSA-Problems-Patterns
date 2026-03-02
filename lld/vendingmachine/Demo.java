package lld.vendingmachine;

/* ===================== STATE INTERFACE ===================== */

interface VendingState {
    default void insertMoney(VendingMachineContext context){
        throw new IllegalStateException("Invalid operation");
    }
    default void selectItem(VendingMachineContext context){
        throw new IllegalStateException("Invalid operation");
    }
    default void dispense(VendingMachineContext context){
        throw new IllegalStateException("Invalid operation");
    }
    default void refund(VendingMachineContext context){
        throw new IllegalStateException("Invalid operation");
    }
}

/* ===================== STATES ===================== */

class IdleState implements VendingState {

    public void insertMoney(VendingMachineContext ctx) {
        System.out.println("Money accepted");
        ctx.setState(new HasMoneyState());
    }
}

class HasMoneyState implements VendingState {


    public void selectItem(VendingMachineContext ctx) {
        if (!ctx.isItemAvailable()) {
            ctx.setState(new OutOfStockState());
            return;
        }
        System.out.println("Item selected");
        ctx.setState(new DispensingState());
    }

    public void refund(VendingMachineContext ctx) {
        ctx.refundAmount();
        ctx.setState(new IdleState());
    }
}

class DispensingState implements VendingState {


    public void dispense(VendingMachineContext ctx) {
        System.out.println("Item dispensed");
        ctx.decreaseItemCount();
        ctx.returnChange();
        ctx.resetAmount();
        ctx.setState(new IdleState());
    }

}

class OutOfStockState implements VendingState {

    public void refund(VendingMachineContext ctx) {
        ctx.refundAmount();
        ctx.setState(new IdleState());
    }
}

/* ===================== CONTEXT ===================== */

class VendingMachineContext {

    private VendingState state;
    private int itemCount = 5;
    private int currentAmount = 0;
    private final int itemPrice = 10;

    public VendingMachineContext() {
        this.state = new IdleState();
    }

    public void setState(VendingState state) {
        this.state = state;
    }

    /* ---------- VALIDATIONS ---------- */

    public void insertMoney(int amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount");
            return;
        }

        currentAmount += amount;
        System.out.println("Inserted amount: " + currentAmount);

        if (currentAmount < itemPrice) {
            System.out.println("Insufficient amount. Price: " + itemPrice);
            return;
        }

        state.insertMoney(this);
    }

    public boolean isItemAvailable() {
        return itemCount > 0;
    }

    public void decreaseItemCount() {
        if (itemCount > 0) {
            itemCount--;
        }
    }

    public void refundAmount() {
        if (currentAmount > 0) {
            System.out.println("Refunding amount: " + currentAmount);
            resetAmount();
        } else {
            System.out.println("No amount to refund");
        }
    }

    public void returnChange() {
        int change = currentAmount - itemPrice;
        if (change > 0) {
            System.out.println("Returning change: " + change);
        }
    }

    public void resetAmount() {
        currentAmount = 0;
    }

    /* ---------- ACTIONS ---------- */

    public void selectItem() {
        state.selectItem(this);
    }

    public void dispense() {
        state.dispense(this);
    }

    public void refund() {
        state.refund(this);
    }
}

/* ===================== CLIENT ===================== */

public class Demo {
    public static void main(String[] args) {

        VendingMachineContext machine = new VendingMachineContext();

        machine.insertMoney(5);    // insufficient
        machine.insertMoney(5);    // now enough
        machine.selectItem();
        machine.dispense();

        machine.insertMoney(10);
        machine.selectItem();
    }
}
