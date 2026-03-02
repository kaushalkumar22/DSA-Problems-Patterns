package lld.notificationsystem;

import java.util.ArrayList;
import java.util.List;

enum EventType {
    ORDER_PLACED,
    PAYMENT_SUCCESS,
    PAYMENT_FAILURE,
    ORDER_CANCELLED
}

class Event {

    private final EventType type;
    private final String payload;

    public Event(EventType type, String payload) {
        this.type = type;
        this.payload = payload;
    }

    public EventType getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "Event{type=" + type + ", payload='" + payload + "'}";
    }
}

interface NotificationDispatcher {
    void addListener(NotificationListener listener);
    void removeListener(NotificationListener listener);
    void publish(Event event);
}

interface NotificationListener {
    void onEvent(Event event);
}


class EventNotificationDispatcher implements NotificationDispatcher {

    private final List<NotificationListener> listeners = new ArrayList<>();

    @Override
    public void addListener(NotificationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(NotificationListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void publish(Event event) {
        for (NotificationListener listener : listeners) {
            try {
                listener.onEvent(event);
            } catch (Exception e) {
                // failure isolation
                System.out.println("Listener failed: " +
                        listener.getClass().getSimpleName());
            }
        }
    }
}

class EmailNotificationListener implements NotificationListener {
    public void onEvent(Event event) {
        System.out.println("[EMAIL] " + event);
    }
}

class SMSNotificationListener implements NotificationListener {
    public void onEvent(Event event) {
        System.out.println("[SMS] " + event);
    }
}

class PushNotificationListener implements NotificationListener {
    public void onEvent(Event event) {
        System.out.println("[PUSH] " + event);
    }
}

public class Demo {

    public static void main(String[] args) {

        NotificationDispatcher dispatcher = new EventNotificationDispatcher();

        dispatcher.addListener(new EmailNotificationListener());
        dispatcher.addListener(new SMSNotificationListener());
        dispatcher.addListener(new PushNotificationListener());

        dispatcher.publish(
                new Event(EventType.ORDER_PLACED, "order123"));

        dispatcher.publish(
                new Event(EventType.PAYMENT_SUCCESS, "order123"));

        dispatcher.publish(
                new Event(EventType.ORDER_CANCELLED, "order123"));
    }
}
