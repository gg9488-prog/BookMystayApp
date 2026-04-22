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

class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Request added: " + reservation.guestName + " -> " + reservation.roomType);
    }

    public void showAllRequests() {
        System.out.println("\nAll Booking Requests (FIFO Order):");

        for (Reservation r : queue) {
            System.out.println(r.guestName + " requested " + r.roomType);
        }
    }
}

public class BookMystayApp {

    public static void main(String[] args) {

        BookingRequestQueue requestQueue = new BookingRequestQueue();

        requestQueue.addRequest(new Reservation("Harsha", "Single"));
        requestQueue.addRequest(new Reservation("Ravi", "Double"));
        requestQueue.addRequest(new Reservation("Anu", "Suite"));

        requestQueue.showAllRequests();
    }
}