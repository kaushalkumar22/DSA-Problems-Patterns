package desingpattern;

class Computer {
    private final int cpu;
    private final int gpu;
    private final int storage;
    private final int ram;

    private Computer(ComputerBuilder builder) {
        this.cpu = builder.cpu;
        this.gpu = builder.gpu;
        this.storage = builder.storage;
        this.ram = builder.ram;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "cpu=" + cpu +
                ", gpu=" + gpu +
                ", storage=" + storage +
                ", ram=" + ram +
                '}';
    }

    public int getCpu() {
        return cpu;
    }

    public int getGpu() {
        return gpu;
    }

    public int getStorage() {
        return storage;
    }

    public int getRam() {
        return ram;
    }

    public static class ComputerBuilder {
        private int cpu;
        private int gpu;
        private int storage;
        private int ram;

        public ComputerBuilder() {
        }

        public ComputerBuilder setCpu(int cpu) {
            this.cpu = cpu;
            return this;
        }

        public ComputerBuilder setGpu(int gpu) {
            this.gpu = gpu;
            return this;
        }

        public ComputerBuilder setStorage(int storage) {
            this.storage = storage;
            return this;
        }

        public ComputerBuilder setRam(int ram) {
            this.ram = ram;
            return this;
        }
        public Computer build(){
            return new Computer(this);
        }
    }

}

public class ComputerBuilderClient {
    public static void main(String[] args) {
        Computer computer = new Computer.ComputerBuilder()
                .setCpu(7)
                .setStorage(1000)
                .setGpu(3080)
                .setRam(32)
                .build();

        System.out.println(computer.toString());
    }
}
