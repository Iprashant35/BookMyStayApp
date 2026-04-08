import java.util.*;

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
        return guestName + " (" + roomType + ")";
    }
}

// Shared Inventory Service (Thread-Safe)
class InventoryService {
    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }

    // Critical section
    public synchronized boolean allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

// Shared Booking Queue
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
        System.out.println("Request Added: " + r);
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }
}

// Booking Processor (Thread)
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private InventoryService inventory;

    public BookingProcessor(BookingQueue queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            Reservation res;

            // synchronized retrieval
            synchronized (queue) {
                res = queue.getRequest();
            }

            if (res == null) break;

            processBooking(res);
        }
    }

    private void processBooking(Reservation res) {
        System.out.println(Thread.currentThread().getName() +
                " processing " + res);

        boolean success = inventory.allocateRoom(res.getRoomType());

        if (success) {
            System.out.println("✅ Booking Confirmed for " + res);
        } else {
            System.out.println("❌ Booking Failed (No Availability) for " + res);
        }
    }
}

// Main Class
public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) {

        BookingQueue queue = new BookingQueue();
        InventoryService inventory = new InventoryService();

        // Simulate multiple requests
        queue.addRequest(new Reservation("Akshit", "Deluxe"));
        queue.addRequest(new Reservation("Riya", "Suite"));
        queue.addRequest(new Reservation("Karan", "Standard"));
        queue.addRequest(new Reservation("Aman", "Suite")); // conflict
        queue.addRequest(new Reservation("Neha", "Standard"));

        // Create multiple threads
        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final state
        inventory.displayInventory();
    }
}