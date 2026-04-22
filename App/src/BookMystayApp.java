import java.util.*;

class Service {
    String name;
    double price;

    Service(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class AddOnServiceManager {

    private Map<String, List<Service>> reservationServices = new HashMap<>();

    public void addService(String reservationId, Service service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);

        System.out.println("Added Service: " + service.name + " for Reservation " + reservationId);
    }

    public double calculateTotalCost(String reservationId) {
        double total = 0;

        List<Service> services = reservationServices.getOrDefault(reservationId, new ArrayList<>());

        for (Service s : services) {
            total += s.price;
        }

        return total;
    }

    public void displayServices(String reservationId) {
        System.out.println("\nServices for Reservation " + reservationId + ":");

        List<Service> services = reservationServices.getOrDefault(reservationId, new ArrayList<>());

        for (Service s : services) {
            System.out.println(s.name + " - " + s.price);
        }

        System.out.println("Total Add-On Cost: " + calculateTotalCost(reservationId));
    }
}

public class BookMystayApp {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "RES-101";

        manager.addService(reservationId, new Service("Breakfast", 500));
        manager.addService(reservationId, new Service("Airport Pickup", 1000));
        manager.addService(reservationId, new Service("Spa Access", 1500));

        manager.displayServices(reservationId);
    }
}