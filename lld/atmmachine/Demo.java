package lld.atmmachine;

/* ===================== STATE INTERFACE ===================== */

interface ATMState {

    default void insertCard(ATMContext ctx) {
        throw new IllegalStateException("Invalid operation");
    }

    default void enterPin(ATMContext ctx, int pin) {
        throw new IllegalStateException("Invalid operation");
    }

    default void selectTransaction(ATMContext ctx) {
        throw new IllegalStateException("Invalid operation");
    }

    default void withdraw(ATMContext ctx, int amount) {
        throw new IllegalStateException("Invalid operation");
    }

    default void ejectCard(ATMContext ctx) {
        throw new IllegalStateException("Invalid operation");
    }
}

/* ===================== STATES ===================== */

class IdleState implements ATMState {

    @Override
    public void insertCard(ATMContext ctx) {
        System.out.println("Card inserted");
        ctx.setState(new CardInsertedState());
    }
}

class CardInsertedState implements ATMState {

    @Override
    public void enterPin(ATMContext ctx, int pin) {
        if (ctx.isValidPin(pin)) {
            System.out.println("PIN verified");
            ctx.setState(new AuthenticatedState());
        } else {
            ctx.incrementInvalidPinAttempts();
            if (ctx.isCardBlocked()) {
                System.out.println("Card blocked");
                ctx.setState(new BlockedState());
            }
        }
    }

    @Override
    public void ejectCard(ATMContext ctx) {
        System.out.println("Card ejected");
        ctx.reset();
        ctx.setState(new IdleState());
    }
}

class AuthenticatedState implements ATMState {

    @Override
    public void selectTransaction(ATMContext ctx) {
        System.out.println("Transaction selected");
        ctx.setState(new TransactionState());
    }

    @Override
    public void ejectCard(ATMContext ctx) {
        System.out.println("Card ejected");
        ctx.reset();
        ctx.setState(new IdleState());
    }
}

class TransactionState implements ATMState {

    @Override
    public void withdraw(ATMContext ctx, int amount) {
        System.out.println("Dispensing cash: " + amount);
        System.out.println("Card ejected");
        ctx.reset();
        ctx.setState(new IdleState());   // ✅ session ends here
    }
}

class BlockedState implements ATMState {

    @Override
    public void ejectCard(ATMContext ctx) {
        System.out.println("Card retained / blocked");
        ctx.reset();
        ctx.setState(new IdleState());
    }
}

/* ===================== CONTEXT ===================== */

class ATMContext {

    private ATMState state = new IdleState();
    private static final int MAX_PIN_ATTEMPTS = 3;
    private int pinAttempts = 0;
    private final int VALID_PIN = 1234;

    void setState(ATMState state) {
        this.state = state;
    }

    boolean isValidPin(int pin) {
        return pin == VALID_PIN;
    }

    void incrementInvalidPinAttempts() {
        pinAttempts++;
        System.out.println("Invalid PIN attempt: " + pinAttempts);
    }

    boolean isCardBlocked() {
        return pinAttempts >= MAX_PIN_ATTEMPTS;
    }

    void reset() {
        pinAttempts = 0;
    }

    /* --------- Delegated Actions --------- */

    void insertCard() {
        state.insertCard(this);
    }

    void enterPin(int pin) {
        state.enterPin(this, pin);
    }

    void selectTransaction() {
        state.selectTransaction(this);
    }

    void withdraw(int amount) {
        state.withdraw(this, amount);
    }

    void ejectCard() {
        state.ejectCard(this);
    }
}

/* ===================== DEMO ===================== */

public class Demo {

    public static void main(String[] args) {

        ATMContext atm = new ATMContext();

        // Transaction 1
        atm.insertCard();
        atm.enterPin(1234);
        atm.selectTransaction();
        atm.withdraw(500);

        // Transaction 2
        atm.insertCard();
        atm.enterPin(1234);
        atm.selectTransaction();
        atm.withdraw(1000);

        System.out.println("---- Invalid PIN Scenario ----");

        atm.insertCard();
        atm.enterPin(1111);
        atm.enterPin(2222);
        atm.enterPin(3333); // blocked
        atm.ejectCard();
    }
}
