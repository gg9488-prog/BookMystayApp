import java.util.LinkedList;
import java.util.Queue;

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class InventoryService {

    private int singleRooms;

    public InventoryService(int singleRooms) {
        this.singleRooms = singleRooms;
    }

    public synchronized boolean allocateRoom(String guestName) {

        if (singleRooms > 0) {
            System.out.println(guestName + " allocated room.");
            singleRooms--;
            return true;
        } else {
            System.out.println(guestName + " booking failed (No rooms left).");
            return false;
        }
    }

    public int getAvailableRooms() {
        return singleRooms;
    }
}

class BookingProcessor implements Runnable {

    private Queue<Reservation> queue;
    private InventoryService inventory;

    BookingProcessor(Queue<Reservation> queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            Reservation r;

            synchronized (queue) {
                if (queue.isEmpty()) {
                    break;
                }
                r = queue.poll();
            }

            if (r != null) {
                inventory.allocateRoom(r.guestName);
            }
        }
    }
}

public class BookMystayApp {

    public static void main(String[] args) {

        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Harsha", "Single"));
        bookingQueue.add(new Reservation("Ravi", "Single"));
        bookingQueue.add(new Reservation("Anu", "Single"));
        bookingQueue.add(new Reservation("Kiran", "Single"));

        InventoryService inventory = new InventoryService(2);

        Thread t1 = new Thread(new BookingProcessor(bookingQueue, inventory));
        Thread t2 = new Thread(new BookingProcessor(bookingQueue, inventory));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nRemaining Rooms: " + inventory.getAvailableRooms());
    }
}