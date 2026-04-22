import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public boolean isAvailable(String type) {
        return inventory.getOrDefault(type, 0) > 0;
    }

    public void decrement(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public int getCount(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

class BookingService {

    private Set<String> allocatedRooms = new HashSet<>();
    private Map<String, Set<String>> roomAllocations = new HashMap<>();
    private int roomCounter = 1;

    public void processQueue(Queue<Reservation> queue, InventoryService inventory) {

        while (!queue.isEmpty()) {

            Reservation r = queue.poll();

            if (inventory.isAvailable(r.roomType)) {

                String roomId = r.roomType + "-" + roomCounter++;

                allocatedRooms.add(roomId);

                roomAllocations.putIfAbsent(r.roomType, new HashSet<>());
                roomAllocations.get(r.roomType).add(roomId);

                inventory.decrement(r.roomType);

                System.out.println("Booking Confirmed: " + r.guestName +
                        " -> Room ID: " + roomId);
            } else {
                System.out.println("Booking Failed (No Availability): " + r.guestName +
                        " -> " + r.roomType);
            }
        }
    }
}

public class BookMystayApp {

    public static void main(String[] args) {

        Queue<Reservation> queue = new LinkedList<>();

        queue.add(new Reservation("Harsha", "Single"));
        queue.add(new Reservation("Ravi", "Double"));
        queue.add(new Reservation("Anu", "Single"));
        queue.add(new Reservation("Kiran", "Suite"));

        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 2);
        inventory.addRoom("Double", 1);
        inventory.addRoom("Suite", 0);

        BookingService bookingService = new BookingService();

        bookingService.processQueue(queue, inventory);
    }
}