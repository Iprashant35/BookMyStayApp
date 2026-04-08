import java.util.*;

// Reservation class (same as Use Case 5)
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

// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

// Booking Service
class BookingService {
    private Queue<Reservation> queue;
    private InventoryService inventoryService;

    // Prevent duplicate room IDs
    private Set<String> allocatedRoomIds;

    // Map roomType → allocated room IDs
    private Map<String, Set<String>> roomAllocationMap;

    public BookingService(Queue<Reservation> queue, InventoryService inventoryService) {
        this.queue = queue;
        this.inventoryService = inventoryService;
        this.allocatedRoomIds = new HashSet<>();
        this.roomAllocationMap = new HashMap<>();
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        String roomId;
        do {
            roomId = roomType.substring(0, 3).toUpperCase() + new Random().nextInt(1000);
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }

    // Process bookings
    public void processBookings() {
        while (!queue.isEmpty()) {
            Reservation res = queue.poll(); // FIFO

            System.out.println("\nProcessing: " + res);

            String type = res.getRoomType();

            if (inventoryService.isAvailable(type)) {

                String roomId = generateRoomId(type);

                // Add to global set
                allocatedRoomIds.add(roomId);

                // Map room type → IDs
                roomAllocationMap.putIfAbsent(type, new HashSet<>());
                roomAllocationMap.get(type).add(roomId);

                // Update inventory immediately
                inventoryService.decrementRoom(type);

                System.out.println("✅ Booking Confirmed!");
                System.out.println("Assigned Room ID: " + roomId);

            } else {
                System.out.println("❌ Booking Failed - No rooms available for " + type);
            }
        }
    }

    public void displayAllocations() {
        System.out.println("\nRoom Allocations:");
        for (String type : roomAllocationMap.keySet()) {
            System.out.println(type + " -> " + roomAllocationMap.get(type));
        }
    }
}

// Main Class
public class UseCase6RoomAllocationService {
    public static void main(String[] args) {

        // Step 1: Create queue (from Use Case 5)
        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.offer(new Reservation("Akshit", "Deluxe"));
        bookingQueue.offer(new Reservation("Riya", "Suite"));
        bookingQueue.offer(new Reservation("Karan", "Standard"));
        bookingQueue.offer(new Reservation("Aman", "Suite")); // should fail

        // Step 2: Inventory
        InventoryService inventoryService = new InventoryService();

        // Step 3: Booking Service
        BookingService bookingService = new BookingService(bookingQueue, inventoryService);

        // Step 4: Process bookings
        bookingService.processBookings();

        // Step 5: Show results
        bookingService.displayAllocations();
        inventoryService.displayInventory();
    }
}