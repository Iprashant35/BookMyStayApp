import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation Class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Room Type: " + roomType;
    }
}

// Inventory Service with validation
class InventoryService {

    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Standard", 1);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    public boolean isAvailable(String roomType) {
        return inventory.get(roomType) > 0;
    }

    public void decrementRoom(String roomType) throws InvalidBookingException {
        int count = inventory.get(roomType);

        if (count <= 0) {
            throw new InvalidBookingException("Inventory cannot go below zero for " + roomType);
        }

        inventory.put(roomType, count - 1);
    }
}

// Validator Class
class BookingValidator {

    public static void validate(Reservation res, InventoryService inventory)
            throws InvalidBookingException {

        if (res.getGuestName() == null || res.getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        if (!inventory.isValidRoomType(res.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + res.getRoomType());
        }

        if (!inventory.isAvailable(res.getRoomType())) {
            throw new InvalidBookingException("No rooms available for " + res.getRoomType());
        }
    }
}

// Booking Service with error handling
class BookingService {

    private InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void confirmBooking(Reservation res) {
        try {
            // Step 1: Validate (Fail Fast)
            BookingValidator.validate(res, inventory);

            // Step 2: Allocation logic
            inventory.decrementRoom(res.getRoomType());

            System.out.println("✅ Booking Confirmed: " + res);

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("❌ Booking Failed: " + e.getMessage());
        }
    }
}

// Main Class
public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        // Valid booking
        bookingService.confirmBooking(new Reservation("Akshit", "Deluxe"));

        // Invalid room type
        bookingService.confirmBooking(new Reservation("Riya", "Penthouse"));

        // Empty name
        bookingService.confirmBooking(new Reservation("", "Suite"));

        // Overbooking case
        bookingService.confirmBooking(new Reservation("Karan", "Suite"));
        bookingService.confirmBooking(new Reservation("Aman", "Suite")); // should fail
    }
}