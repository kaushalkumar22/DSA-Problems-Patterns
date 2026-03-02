package code.arrays;

import java.util.*;
import java.util.concurrent.*;

enum Direction {
    UP, DOWN, IDLE
}

class Request {
    final Integer hallFloor;        // Floor for hall call
    final Integer cabinDestination; // Destination for cabin call
    final Direction direction;      // Only for hall call

    private Request(Integer hall, Integer cabin, Direction dir) {
        this.hallFloor = hall;
        this.cabinDestination = cabin;
        this.direction = dir;
    }

    static Request hall(int floor, Direction dir) {
        return new Request(floor, null, dir);
    }

    static Request cabin(int dest) {
        return new Request(null, dest, null);
    }
}

// -------------------------------------------------------
// ELEVATOR (Runnable logic)
// -------------------------------------------------------
class Elevator implements Runnable {

    private volatile boolean running = true;
    private int currentFloor = 1;
    private Direction direction = Direction.IDLE;

    private final TreeSet<Integer> upQueue = new TreeSet<>();
    private final TreeSet<Integer> downQueue = new TreeSet<>(Collections.reverseOrder());

    private final BlockingQueue<Request> incoming = new LinkedBlockingQueue<>();

    public void addRequest(Request r) {
        incoming.offer(r);
    }

    private void consumeRequests() {
        Request r;
        while ((r = incoming.poll()) != null) {

            if (r.hallFloor != null) {
                if (r.direction == Direction.UP)
                    upQueue.add(r.hallFloor);
                else
                    downQueue.add(r.hallFloor);
            }

            if (r.cabinDestination != null) {
                if (r.cabinDestination > currentFloor)
                    upQueue.add(r.cabinDestination);
                else
                    downQueue.add(r.cabinDestination);
            }
        }
    }

    private Integer nextStop() {

        switch (direction) {
            case UP:
                if (!upQueue.isEmpty()) return upQueue.first();
                if (!downQueue.isEmpty()) {
                    direction = Direction.DOWN;
                    return downQueue.first();
                }
                direction = Direction.IDLE;
                return null;

            case DOWN:
                if (!downQueue.isEmpty()) return downQueue.first();
                if (!upQueue.isEmpty()) {
                    direction = Direction.UP;
                    return upQueue.first();
                }
                direction = Direction.IDLE;
                return null;

            case IDLE:
                if (!upQueue.isEmpty()) {
                    direction = Direction.UP;
                    return upQueue.first();
                }
                if (!downQueue.isEmpty()) {
                    direction = Direction.DOWN;
                    return downQueue.first();
                }
                return null;
        }

        return null;
    }

    private void arrive(int target) {
        System.out.println(">> Arrived at floor " + target);

        upQueue.remove(target);
        downQueue.remove(target);

        if (upQueue.isEmpty() && downQueue.isEmpty()) {
            direction = Direction.IDLE;
        }
    }

    @Override
    public void run() {
        System.out.println("Elevator started (ExecutorService)…");

        while (running) {
            try {
                consumeRequests();

                Integer target = nextStop();

                if (target == null) {
                    System.out.println("Idle at floor " + currentFloor);
                    Thread.sleep(500);
                    continue;
                }

                if (target > currentFloor) {
                    currentFloor++;
                    direction = Direction.UP;
                } else if (target < currentFloor) {
                    currentFloor--;
                    direction = Direction.DOWN;
                }

                System.out.println("Floor=" + currentFloor + " | Dir=" + direction);

                if (currentFloor == target) {
                    arrive(target);
                }

                Thread.sleep(700);

            } catch (InterruptedException ignored) {}
        }

        System.out.println("Elevator stopped.");
    }

    public void shutdown() {
        running = false;
    }
}

// -------------------------------------------------------
// ELEVATOR SYSTEM (Uses ExecutorService)
// -------------------------------------------------------
class ElevatorSystem {

    private final Elevator elevator = new Elevator();

    // Single-thread pool for elevator simulation
    private final ExecutorService executorService =
            Executors.newSingleThreadExecutor();

    public ElevatorSystem() {
        executorService.submit(elevator);
    }

    public void pressHallButton(int floor, Direction dir) {
        System.out.println("Hall Call: floor=" + floor + ", dir=" + dir);
        elevator.addRequest(Request.hall(floor, dir));
    }

    public void pressCabinButton(int dest) {
        System.out.println("Cabin Call: dest=" + dest);
        elevator.addRequest(Request.cabin(dest));
    }

    public void shutdown() {
        elevator.shutdown();
        executorService.shutdownNow();
    }
}

// -------------------------------------------------------
// DEMO BOOTSTRAP
// -------------------------------------------------------
public class ElevatorDemo {
    public static void main(String[] args) throws Exception {

        ElevatorSystem system = new ElevatorSystem();

        // hall calls
        system.pressHallButton(3, Direction.UP);
        system.pressHallButton(7, Direction.DOWN);

        Thread.sleep(2000);

        // cabin calls
        system.pressCabinButton(10);
        system.pressCabinButton(2);

        Thread.sleep(15000);
        system.shutdown();
    }
}
