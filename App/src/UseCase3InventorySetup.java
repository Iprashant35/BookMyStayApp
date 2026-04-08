/**
 * UseCase3InventorySetup.java
 *
 * This class demonstrates centralized room inventory management
 * using HashMap to maintain a single source of truth.
 *
 * It replaces scattered availability variables with a structured,
 * scalable approach.
 *
 * @author YourName
 * @version 3.1
 */

import java.util.HashMap;
import java.util.Map;

// Inventory class responsible for managing room availability
class RoomInventory {

    // HashMap to store room type and its availability
    private Map<String, Integer> inventory;

    // Constructor to initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initialize room availability
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 2);
    }

    // Method to get availability of a specific room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Method to update availability (increase or decrease)
    public void updateAvailability(String roomType, int change) {
        int current = getAvailability(roomType);
        int updated = current + change;

        if (updated < 0) {
            System.out.println("Error: Cannot reduce below zero for " + roomType);
            return;
        }

        inventory.put(roomType, updated);
    }

    // Method to display full inventory
    public void displayInventory() {
        System.out.println("---- Current Room Inventory ----");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main class
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Version: 3.1");
        System.out.println("=====================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Simulate updates
        System.out.println("\nUpdating inventory...\n");

        inventory.updateAvailability("Single Room", -2); // booking
        inventory.updateAvailability("Double Room", -1); // booking
        inventory.updateAvailability("Suite Room", 1);   // cancellation

        // Display updated inventory
        System.out.println();
        inventory.displayInventory();

        System.out.println("\nApplication executed successfully.");
    }
}