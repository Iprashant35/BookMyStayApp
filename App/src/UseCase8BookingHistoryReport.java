import java.util.*;

// Reservation class (extended with roomId for confirmed bookings)
class Reservation {
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName +
                ", Room Type: " + roomType +
                ", Room ID: " + roomId;
    }
}

// Booking History (stores confirmed reservations)
class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Added to history: " + reservation);
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportService {

    // Display all bookings
    public void showAllBookings(List<Reservation> history) {
        System.out.println("\n📜 Booking History:");

        if (history.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : history) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> history) {
        System.out.println("\n📊 Booking Summary Report:");

        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Reservation r : history) {
            roomTypeCount.put(
                    r.getRoomType(),
                    roomTypeCount.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        for (String type : roomTypeCount.keySet()) {
            System.out.println(type + " bookings: " + roomTypeCount.get(type));
        }

        System.out.println("Total bookings: " + history.size());
    }
}

// Main Class
public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {

        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings (from Use Case 6)
        bookingHistory.addReservation(new Reservation("Akshit", "Deluxe", "DEL101"));
        bookingHistory.addReservation(new Reservation("Riya", "Suite", "SUI202"));
        bookingHistory.addReservation(new Reservation("Karan", "Standard", "STA303"));
        bookingHistory.addReservation(new Reservation("Aman", "Deluxe", "DEL104"));

        // Admin views history
        reportService.showAllBookings(bookingHistory.getAllReservations());

        // Admin generates report
        reportService.generateSummary(bookingHistory.getAllReservations());

        System.out.println("\nNote: Reporting does not modify booking history.");
    }
}