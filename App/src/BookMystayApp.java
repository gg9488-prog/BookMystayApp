import java.util.HashMap;
import java.util.Map;

class InvalidBookingException extends Exception {
    InvalidBookingException(String message) {
        super(message);
    }
}

class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public void validateRoom(String type) throws InvalidBookingException {
        if (!inventory.containsKey(type)) {
            throw new InvalidBookingException("Invalid room type: " + type);
        }
        if (inventory.get(type) <= 0) {
            throw new InvalidBookingException("No availability for room type: " + type);
        }
    }

    public void allocateRoom(String type) throws InvalidBookingException {
        validateRoom(type);

        int count = inventory.get(type);

        if (count <= 0) {
            throw new InvalidBookingException("Cannot allocate. No rooms left for: " + type);
        }

        inventory.put(type, count - 1);
    }
}

class BookingService {

    private InventoryService inventory;

    BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void bookRoom(String guestName, String roomType) {

        try {
            inventory.allocateRoom(roomType);
            System.out.println("Booking Confirmed: " + guestName + " -> " + roomType);
        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

public class BookMystayApp {

    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();

        inventory.addRoom("Single", 1);
        inventory.addRoom("Double", 0);

        BookingService bookingService = new BookingService(inventory);

        bookingService.bookRoom("Harsha", "Single");
        bookingService.bookRoom("Ravi", "Double");
        bookingService.bookRoom("Anu", "Suite");
    }
}