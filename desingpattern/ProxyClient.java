package desingpattern;

interface Service{
    void execute();
}
class RealService implements Service{

    @Override
    public void execute() {
        System.out.println("Real service");
    }
}
class ProxyService implements Service{
    private Service service;

    @Override
    public void execute() {
        if(service == null){
            service = new RealService();
        }
        System.out.println("Proxy service wrapping real");
        service.execute();
    }
}
public class ProxyClient {
    public static void main(String[] args) {
        Service service = new ProxyService();
        service.execute();
    }
}

