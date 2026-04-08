import java.util.*;

// Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType;
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Standard", 1);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\nInventory State:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

// Booking History
class BookingHistory {
    private Map<String, Reservation> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new HashMap<>();
    }

    public void addReservation(Reservation res) {
        confirmedBookings.put(res.getReservationId(), res);
    }

    public Reservation getReservation(String id) {
        return confirmedBookings.get(id);
    }

    public void removeReservation(String id) {
        confirmedBookings.remove(id);
    }

    public void displayBookings() {
        System.out.println("\nCurrent Bookings:");
        for (Reservation r : confirmedBookings.values()) {
            System.out.println(r);
        }
    }
}

// Cancellation Service
class CancellationService {

    private BookingHistory history;
    private InventoryService inventory;

    // Stack for rollback (LIFO)
    private Stack<String> releasedRoomStack;

    public CancellationService(BookingHistory history, InventoryService inventory) {
        this.history = history;
        this.inventory = inventory;
        this.releasedRoomStack = new Stack<>();
    }

    public void cancelBooking(String reservationId) {

        System.out.println("\nProcessing cancellation for ID: " + reservationId);

        Reservation res = history.getReservation(reservationId);

        // Validation
        if (res == null) {
            System.out.println("❌ Cancellation Failed: Reservation does not exist.");
            return;
        }

        // Step 1: Push to rollback stack
        releasedRoomStack.push(reservationId);

        // Step 2: Restore inventory
        inventory.incrementRoom(res.getRoomType());

        // Step 3: Remove from history
        history.removeReservation(reservationId);

        System.out.println("✅ Cancellation Successful for " + reservationId);
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Recent Releases): " + releasedRoomStack);
    }
}

// Main Class
public class UseCase10BookingCancellation {
    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        history.addReservation(new Reservation("DEL101", "Akshit", "Deluxe"));
        history.addReservation(new Reservation("SUI202", "Riya", "Suite"));

        history.displayBookings();

        // Cancellation Service
        CancellationService cancelService = new CancellationService(history, inventory);

        // Valid cancellation
        cancelService.cancelBooking("DEL101");

        // Invalid cancellation
        cancelService.cancelBooking("DEL101"); // already removed

        // Another valid cancellation
        cancelService.cancelBooking("SUI202");

        // Show final state
        history.displayBookings();
        inventory.displayInventory();
        cancelService.showRollbackStack();
    }
}