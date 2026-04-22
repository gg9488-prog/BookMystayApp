import java.util.*;

class Reservation {
    String reservationId;
    String roomType;

    Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }
}

class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public void decrement(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void increment(String type) {
        inventory.put(type, inventory.get(type) + 1);
    }

    public int getCount(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

class BookingService {

    Map<String, Reservation> confirmedBookings = new HashMap<>();
    Stack<String> rollbackStack = new Stack<>();

    public void confirmBooking(String reservationId, String roomType, InventoryService inventory) {

        if (inventory.getCount(roomType) > 0) {

            inventory.decrement(roomType);

            confirmedBookings.put(reservationId, new Reservation(reservationId, roomType));

            rollbackStack.push(reservationId);

            System.out.println("Booking Confirmed: " + reservationId + " -> " + roomType);
        } else {
            System.out.println("Booking Failed: No availability for " + roomType);
        }
    }
}

class CancellationService {

    public void cancelBooking(String reservationId, BookingService bookingService, InventoryService inventory) {

        if (!bookingService.confirmedBookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Invalid Reservation ID " + reservationId);
            return;
        }

        Reservation r = bookingService.confirmedBookings.remove(reservationId);

        inventory.increment(r.roomType);

        bookingService.rollbackStack.push(reservationId);

        System.out.println("Booking Cancelled: " + reservationId + " -> " + r.roomType);
    }
}

public class BookMystayApp {

    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 1);
        inventory.addRoom("Double", 1);

        BookingService bookingService = new BookingService();
        CancellationService cancellationService = new CancellationService();

        bookingService.confirmBooking("RES-101", "Single", inventory);
        bookingService.confirmBooking("RES-102", "Double", inventory);

        cancellationService.cancelBooking("RES-101", bookingService, inventory);
        cancellationService.cancelBooking("RES-999", bookingService, inventory);

        System.out.println("\nCurrent Inventory:");
        System.out.println("Single: " + inventory.getCount("Single"));
        System.out.println("Double: " + inventory.getCount("Double"));
    }
}