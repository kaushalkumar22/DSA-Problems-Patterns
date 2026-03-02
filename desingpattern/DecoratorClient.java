package desingpattern;

interface Notifier{
    void send();
}
class SimpleNotifier implements Notifier{

    @Override
    public void send() {
        System.out.println("Notifing");
    }
}
abstract class NotifierDecorator implements Notifier{
    private Notifier  notifier;
    public NotifierDecorator(Notifier notifier){
        this.notifier = notifier;
    }
    public void send(){
        notifier.send();
    }
}

class SMSNotifier extends NotifierDecorator{

    public SMSNotifier(Notifier notifier) {
        super(notifier);
    }
    public void send(){
        super.send();
        System.out.println("SMS");
    }
}
//optional for adding more and more behaviour
class EmailNotifier extends NotifierDecorator {
    public EmailNotifier(Notifier notifier) {
        super(notifier);
    }
    public void send() {
        super.send();
        System.out.println("Email");
    }
}

public class DecoratorClient {
    public static void main(String[] args) {
        Notifier notifier = new SMSNotifier(new SimpleNotifier());
        notifier.send();

        Notifier notifier1 =
                new EmailNotifier(new SMSNotifier(new SimpleNotifier()));
        notifier1.send();

    }
}
