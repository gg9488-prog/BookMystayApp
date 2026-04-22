import java.util.*;

class Reservation {
    String reservationId;
    String guestName;
    String roomType;

    Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Stored Booking: " + reservation.reservationId);
    }

    public List<Reservation> getAllBookings() {
        return history;
    }
}

class BookingReportService {

    public void displayAllBookings(List<Reservation> bookings) {

        System.out.println("\nBooking History:");

        for (Reservation r : bookings) {
            System.out.println("ID: " + r.reservationId +
                    " | Guest: " + r.guestName +
                    " | Room: " + r.roomType);
        }
    }

    public void generateSummary(List<Reservation> bookings) {

        Map<String, Integer> summary = new HashMap<>();

        for (Reservation r : bookings) {
            summary.put(r.roomType, summary.getOrDefault(r.roomType, 0) + 1);
        }

        System.out.println("\nBooking Summary:");

        for (String type : summary.keySet()) {
            System.out.println(type + " Rooms Booked: " + summary.get(type));
        }
    }
}

public class BookMystayApp {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        history.addReservation(new Reservation("RES-101", "Harsha", "Single"));
        history.addReservation(new Reservation("RES-102", "Ravi", "Double"));
        history.addReservation(new Reservation("RES-103", "Anu", "Single"));

        BookingReportService reportService = new BookingReportService();

        reportService.displayAllBookings(history.getAllBookings());
        reportService.generateSummary(history.getAllBookings());
    }
}