/**
 * UseCase4RoomSearch.java
 *
 * This class demonstrates room search functionality with read-only access
 * to centralized inventory. It ensures that system state is not modified
 * while displaying only available rooms.
 *
 * @author YourName
 * @version 4.0
 */

import java.util.*;

// Abstract Room class
abstract class Room {
    private String roomType;
    private int beds;
    private double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayDetails() {
        System.out.println("Room Type   : " + roomType);
        System.out.println("Beds        : " + beds);
        System.out.println("Price/Night : ₹" + price);
    }
}

// Concrete Room types
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1500.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000.0);
    }
}

// Centralized Inventory (read-only usage here)
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 0); // intentionally unavailable
        inventory.put("Suite Room", 2);
    }

    // Read-only access method
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Expose all keys for search
    public Set<String> getRoomTypes() {
        return inventory.keySet();
    }
}

// Search Service (Read-only)
class RoomSearchService {

    public void searchAvailableRooms(List<Room> rooms, RoomInventory inventory) {
        System.out.println("---- Available Rooms ----\n");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());

            // Defensive check: only show available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available   : " + available);
                System.out.println("--------------------------");
            }
        }
    }
}

// Main Class
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Version: 4.0");
        System.out.println("=====================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room objects (domain model)
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Perform search (read-only)
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(rooms, inventory);

        System.out.println("\nSearch completed. No changes made to inventory.");
    }
}