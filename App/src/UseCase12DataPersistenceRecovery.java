import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

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

// System State (Inventory + Booking History)
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("✅ System state saved successfully.");

        } catch (IOException e) {
            System.out.println("❌ Error saving state: " + e.getMessage());
        }
    }

    // Load state
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("✅ System state loaded successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("⚠️ No saved state found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error loading state: " + e.getMessage());
        }

        return null;
    }
}

// Main Class
public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {

        // Step 1: Try to load previous state
        SystemState state = PersistenceService.load();

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        if (state == null) {
            // Fresh start
            inventory = new HashMap<>();
            inventory.put("Standard", 2);
            inventory.put("Deluxe", 1);
            inventory.put("Suite", 1);

            bookings = new ArrayList<>();

            System.out.println("🔄 Initialized new system state.");

        } else {
            // Restore state
            inventory = state.inventory;
            bookings = state.bookings;

            System.out.println("🔁 Restored previous system state.");
        }

        // Simulate booking
        Reservation r1 = new Reservation("DEL101", "Akshit", "Deluxe");

        if (inventory.get("Deluxe") > 0) {
            inventory.put("Deluxe", inventory.get("Deluxe") - 1);
            bookings.add(r1);
            System.out.println("✅ Booking Added: " + r1);
        } else {
            System.out.println("❌ No Deluxe rooms available.");
        }

        // Display current state
        System.out.println("\nCurrent Bookings:");
        for (Reservation r : bookings) {
            System.out.println(r);
        }

        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }

        // Step 2: Save state before shutdown
        PersistenceService.save(new SystemState(inventory, bookings));

        System.out.println("\n💾 System shutdown complete.");
    }
}