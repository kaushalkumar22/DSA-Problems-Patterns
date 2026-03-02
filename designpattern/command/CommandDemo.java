package designpattern.command;

//Command Interface
interface Command {
    void execute();
}

//Receiver (Business Logic)
class Light {
    public void turnOn() {
        System.out.println("Light ON");
    }
    public void turnOff() {
        System.out.println("Light OFF");
    }
}

//Concrete Commands
class TurnOnCommand implements Command {
    private final Light light;
    public TurnOnCommand(Light light) {
        this.light = light;
    }
    @Override
    public void execute() {
        light.turnOn();
    }
}
class TurnOffCommand implements Command {
    private final Light light;
    public TurnOffCommand(Light light) {
        this.light = light;
    }
    @Override
    public void execute() {
        light.turnOff();
    }
}
//Invoker
class RemoteControl {
    private Command command;
    public void setCommand(Command command) {
        this.command = command;
    }
    public void pressButton() {
        command.execute();
    }
}

//Client
public class CommandDemo {
    public static void main(String[] args) {
        Light light = new Light();
        Command onCommand = new TurnOnCommand(light);
        Command offCommand = new TurnOffCommand(light);
        RemoteControl remote = new RemoteControl();
        remote.setCommand(onCommand);
        remote.pressButton();
        remote.setCommand(offCommand);
        remote.pressButton();
    }
}
