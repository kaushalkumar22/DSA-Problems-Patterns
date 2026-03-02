package lld.paymentgateway;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/* ===================== ENUMS ===================== */

enum VehicleType {
    CAR, MOTORCYCLE, TRUCK
}

enum SpotType {
    CAR, MOTORCYCLE, TRUCK
}

/* ===================== DOMAIN MODELS ===================== */

class Vehicle {
    private final String licensePlate;
    private final VehicleType vehicleType;

    public Vehicle(String licensePlate, VehicleType vehicleType) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
}

/* ===================== PARKING SPOT ===================== */

class ParkingSpot {
    private final int spotId;
    private final SpotType type;
    private final int floorNumber;
    private Vehicle parkedVehicle;

    public ParkingSpot(int spotId, SpotType type, int floorNumber) {
        this.spotId = spotId;
        this.type = type;
        this.floorNumber = floorNumber;
    }

    public synchronized boolean isAvailable() {
        return parkedVehicle == null;
    }

    public synchronized void park(Vehicle vehicle) {
        if (!isAvailable()) {
            throw new IllegalStateException("Spot already occupied");
        }
        this.parkedVehicle = vehicle;
    }

    public synchronized void unpark() {
        if (parkedVehicle == null) {
            throw new IllegalStateException("Spot already empty");
        }
        this.parkedVehicle = null;
    }

    public SpotType getType() {
        return type;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}

/* ===================== PARKING FLOOR ===================== */

class ParkingFloor {
    private final int levelNumber;
    private final List<ParkingSpot> spots = new ArrayList<>();

    public ParkingFloor(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public void addSpot(ParkingSpot spot) {
        spots.add(spot);
    }

    public Optional<ParkingSpot> findFreeSpot(SpotType requiredType) {
        return spots.stream()
                .filter(spot -> spot.isAvailable() && spot.getType() == requiredType)
                .findFirst();
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}

/* ===================== TICKET ===================== */

final class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final long entryTime;

    public Ticket(Vehicle vehicle, ParkingSpot spot) {
        this.ticketId = UUID.randomUUID().toString();
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = System.currentTimeMillis();
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public long getEntryTime() {
        return entryTime;
    }
}

/* ===================== PRICING ===================== */

class PricingService {

    public double calculate(long entryTime, VehicleType type) {
        double hours = Math.ceil((System.currentTimeMillis() - entryTime) / 3600000.0);
        return hours * getRate(type);
    }

    private double getRate(VehicleType type) {
        return switch (type) {
            case CAR -> 50.0;
            case MOTORCYCLE -> 30.0;
            case TRUCK -> 70.0;
        };
    }
}

/* ===================== PAYMENT ===================== */

class Payment {
    public void processPayment(double amount) {
        System.out.println("Payment successful: ₹" + amount);
    }
}

/* ===================== PARKING LOT (FACADE) ===================== */

class ParkingLot {

    private final List<ParkingFloor> floors = new ArrayList<>();
    private final Map<String, Ticket> activeTickets = new ConcurrentHashMap<>();
    private final PricingService pricingService;

    public ParkingLot(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public Ticket park(Vehicle vehicle) {
        ParkingSpot spot = assignSpot(vehicle);
        if (spot == null) {
            throw new RuntimeException("No parking spot available");
        }

        spot.park(vehicle);
        Ticket ticket = new Ticket(vehicle, spot);
        activeTickets.put(ticket.getTicketId(), ticket);
        return ticket;
    }

    public double unpark(String ticketId) {
        Ticket ticket = activeTickets.remove(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Invalid ticket");
        }

        double amount = pricingService.calculate(
                ticket.getEntryTime(),
                ticket.getVehicle().getVehicleType()
        );

        ticket.getSpot().unpark();
        return amount;
    }

    private ParkingSpot assignSpot(Vehicle vehicle) {
        SpotType required = mapVehicleToSpot(vehicle.getVehicleType());

        for (ParkingFloor floor : floors) {
            Optional<ParkingSpot> spot = floor.findFreeSpot(required);
            if (spot.isPresent()) {
                return spot.get();
            }
        }
        return null;
    }

    private SpotType mapVehicleToSpot(VehicleType type) {
        return switch (type) {
            case CAR -> SpotType.CAR;
            case MOTORCYCLE -> SpotType.MOTORCYCLE;
            case TRUCK -> SpotType.TRUCK;
        };
    }
}

/* ===================== DEMO ===================== */

public class Demo {

    public static void main(String[] args) throws InterruptedException {

        PricingService pricingService = new PricingService();
        ParkingLot parkingLot = new ParkingLot(pricingService);

        ParkingFloor floor0 = new ParkingFloor(0);
        ParkingFloor floor1 = new ParkingFloor(1);

        floor0.addSpot(new ParkingSpot(1, SpotType.MOTORCYCLE, 0));
        floor0.addSpot(new ParkingSpot(2, SpotType.MOTORCYCLE, 0));
        floor0.addSpot(new ParkingSpot(3, SpotType.CAR, 0));
        floor0.addSpot(new ParkingSpot(4, SpotType.TRUCK, 0));

        floor1.addSpot(new ParkingSpot(5, SpotType.MOTORCYCLE, 1));
        floor1.addSpot(new ParkingSpot(6, SpotType.MOTORCYCLE, 1));
        floor1.addSpot(new ParkingSpot(7, SpotType.CAR, 1));
        floor1.addSpot(new ParkingSpot(8, SpotType.TRUCK, 1));

        parkingLot.addFloor(floor0);
        parkingLot.addFloor(floor1);

        Vehicle bike = new Vehicle("KA-01-BI-1234", VehicleType.MOTORCYCLE);
        Vehicle car = new Vehicle("KA-02-CA-5678", VehicleType.CAR);
        Vehicle truck = new Vehicle("KA-03-TR-9999", VehicleType.TRUCK);

        Ticket bikeTicket = parkingLot.park(bike);
        Ticket carTicket = parkingLot.park(car);
        Ticket truckTicket = parkingLot.park(truck);

        Thread.sleep(2000);

        Payment payment = new Payment();

        double bikeFee = parkingLot.unpark(bikeTicket.getTicketId());
        payment.processPayment(bikeFee);

        double carFee = parkingLot.unpark(carTicket.getTicketId());
        payment.processPayment(carFee);

        double truckFee = parkingLot.unpark(truckTicket.getTicketId());
        payment.processPayment(truckFee);
    }
}
