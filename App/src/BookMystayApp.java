import java.util.HashMap;
import java.util.Map;

class Room {
    String type;
    double price;

    Room(String type, double price) {
        this.type = type;
        this.price = price;
    }
}

class Inventory {
    private Map<String, Integer> availability = new HashMap<>();

    public void addRoom(String type, int count) {
        availability.put(type, count);
    }

    public int getAvailableCount(String type) {
        return availability.getOrDefault(type, 0);
    }

    public Map<String, Integer> getAllRooms() {
        return availability;
    }
}

class SearchService {

    public void displayAvailableRooms(Inventory inventory, Map<String, Room> roomDetails) {

        System.out.println("Available Rooms:");

        for (String type : inventory.getAllRooms().keySet()) {

            int count = inventory.getAvailableCount(type);

            if (count > 0) {
                Room room = roomDetails.get(type);
                System.out.println("Type: " + type +
                        " | Price: " + room.price +
                        " | Available: " + count);
            }
        }
    }
}

public class BookMystayApp {

    public static void main(String[] args) {

        Inventory inventory = new Inventory();

        inventory.addRoom("Single", 3);
        inventory.addRoom("Double", 0);
        inventory.addRoom("Suite", 2);

        Map<String, Room> roomDetails = new HashMap<>();
        roomDetails.put("Single", new Room("Single", 2000));
        roomDetails.put("Double", new Room("Double", 3500));
        roomDetails.put("Suite", new Room("Suite", 5000));

        SearchService service = new SearchService();

        service.displayAvailableRooms(inventory, roomDetails);
    }
}