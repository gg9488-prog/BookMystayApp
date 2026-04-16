import java.util.HashMap;
import java.util.Map;

abstract class Room {
    String type;
    int beds;
    double price;

    Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    void displayDetails() {
        System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 1000);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 1800);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 3000);
    }
}

class RoomInventory {
    private Map<String, Integer> availability;

    RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single Room", 5);
        availability.put("Double Room", 3);
        availability.put("Suite Room", 2);
    }

    int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    void updateAvailability(String roomType, int count) {
        availability.put(roomType, count);
    }

    void displayInventory() {
        System.out.println("===== Room Inventory =====");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}

public class BookMystayApp {

    public static void main(String[] args) {

        Room r1 = new SingleRoom();
        Room r2 = new DoubleRoom();
        Room r3 = new SuiteRoom();

        RoomInventory inventory = new RoomInventory();

        r1.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(r1.type));
        System.out.println();

        r2.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(r2.type));
        System.out.println();

        r3.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(r3.type));
        System.out.println();

        inventory.displayInventory();
    }
}