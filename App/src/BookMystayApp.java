import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    String reservationId;
    String guestName;
    String roomType;

    Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class InventoryService implements Serializable {
    Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}

class PersistenceService {

    public void saveState(List<Reservation> bookings, InventoryService inventory) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.ser"))) {
            out.writeObject(bookings);
            out.writeObject(inventory);
            System.out.println("State saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    public Object[] loadState() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.ser"))) {
            List<Reservation> bookings = (List<Reservation>) in.readObject();
            InventoryService inventory = (InventoryService) in.readObject();
            System.out.println("State loaded successfully.");
            return new Object[]{bookings, inventory};
        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
            return new Object[]{new ArrayList<>(), new InventoryService()};
        }
    }
}

public class BookMystayApp {

    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        Object[] data = persistence.loadState();

        List<Reservation> bookings = (List<Reservation>) data[0];
        InventoryService inventory = (InventoryService) data[1];

        if (inventory.getInventory().isEmpty()) {
            inventory.addRoom("Single", 2);
            inventory.addRoom("Double", 1);
        }

        bookings.add(new Reservation("RES-101", "Harsha", "Single"));
        bookings.add(new Reservation("RES-102", "Ravi", "Double"));

        System.out.println("\nCurrent Bookings:");
        for (Reservation r : bookings) {
            System.out.println(r.reservationId + " | " + r.guestName + " | " + r.roomType);
        }

        System.out.println("\nInventory:");
        for (String type : inventory.getInventory().keySet()) {
            System.out.println(type + " -> " + inventory.getInventory().get(type));
        }

        persistence.saveState(bookings, inventory);
    }
}