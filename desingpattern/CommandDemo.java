package desingpattern;

interface TV{
    void execute();
}
class TVOnCommand implements TV{

    @Override
    public void execute() {
        System.out.println("TV on");
    }
}
class TVOffCommand implements TV{

    @Override
    public void execute() {
        System.out.println("TV off");
    }
}
class TVRemote{
    private TV tv;
    public void setCommand(TV tv){
        this.tv = tv;
    }
    public void press() {
        tv.execute();
    }
}
public class CommandDemo {
    public static void main(String[] args) {
        TVRemote remote = new TVRemote();
        remote.setCommand(new TVOnCommand());
        remote.press();
        remote.setCommand(new TVOffCommand());
        remote.press();
    }
}
