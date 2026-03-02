package lld.pubsubsystem;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

class Event{
    private int id ;
    private String payload;

    public Event(int id, String payload) {
        this.id = id;
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", payload='" + payload + '\'' +
                '}';
    }
}
//Subject
class Publisher{
    public void publish(Event event, Broker broker ,String topicName){
        broker.accept(event,topicName);
    }

}

interface Subscriber{
    void consume(Event event);
}

class Broker{
    Map<String, Topic> topicNameVsTopic = new ConcurrentHashMap<>();

    public void addTopic(Topic topic){
        topicNameVsTopic.putIfAbsent(topic.getTopicName(), topic);
    }

    public void accept(Event event ,String topicName){
        Topic topic = topicNameVsTopic.get(topicName);
        if(topic == null){
            System.out.println("Invald Topic Name");
            return;
        }
        topic.notifySubscribers(event);
    }
}

class Topic{
    private final List<Subscriber> subscribers = new CopyOnWriteArrayList<>();
    private final String topicName;

    public Topic(String topicName){
        this.topicName = topicName;
    }

    public String getTopicName(){
        return this.topicName;
    }

    public void addSubscriber(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    public void removeSubscriber(Subscriber subscriber){
        subscribers.remove(subscriber);
    }

    public void notifySubscribers(Event event){
        for(Subscriber subscriber : subscribers){
            subscriber.consume(event);
        }
    }
}
class LoggingSubscriber  implements Subscriber{
    @Override
    public void consume(Event event) {
        System.out.println("Event Received "+event);
    }
}
public class Demo {
    public static void main(String[] args) {
        Broker broker = new Broker();
        Topic topic = new Topic("Topic1");
        Subscriber subscriber1 = new LoggingSubscriber ();
        Subscriber subscriber2 = new LoggingSubscriber ();
        topic.addSubscriber(subscriber1);
        topic.addSubscriber(subscriber2);
        broker.addTopic(topic);

        Event event = new Event(1, "Event1");
        Publisher publisher = new Publisher();
        publisher.publish(event ,broker,"Topic1");

        Event event2 = new Event(2, "Event2");

        publisher.publish(event2 ,broker,"Topic1");
    }

}