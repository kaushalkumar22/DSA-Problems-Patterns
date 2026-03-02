package lld.trafficlightsystem;

interface TrafficLightState{
    void display();
    void next(TrafficLightContext context);
}
class RedLightState implements TrafficLightState{

    @Override
    public void display() {
        System.out.println("Red Light");

    }

    @Override
    public void next(TrafficLightContext context) {
        context.setState(new GreenLightState());
    }
}
class GreenLightState implements TrafficLightState{

    @Override
    public void display() {
        System.out.println("Green Light");
    }

    @Override
    public void next(TrafficLightContext context) {
        context.setState(new YellowLightState());
    }

}
class YellowLightState implements TrafficLightState{

    @Override
    public void display() {
        System.out.println("Yellow Light");
    }

    @Override
    public void next(TrafficLightContext context) {
        context.setState(new RedLightState());
    }

}
class TrafficLightContext{
    private TrafficLightState state;

    public TrafficLightContext(){
        this.state = new RedLightState();
    }

    public void setState(TrafficLightState state){
        this.state = state;
    }
    public void display(){
        state.display();
    }

    public void next(){
        state.next(this);
    }

}
public class Demo {
    public static void main(String[] args) throws InterruptedException {
        TrafficLightContext ctx = new TrafficLightContext();
        for(int i = 0 ;i<6 ;i++){
            ctx.display();
            Thread.sleep(1000);
            ctx.next();
        }
    }
}
