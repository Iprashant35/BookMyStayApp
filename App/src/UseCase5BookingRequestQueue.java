import java.util.*;

// Class representing a booking request
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

// Queue manager for booking requests
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Booking request added: " + reservation);
    }

    // View all requests (without removing)
    public void viewRequests() {
        if (queue.isEmpty()) {
            System.out.println("No booking requests in queue.");
            return;
        }

        System.out.println("\nCurrent Booking Queue (FIFO Order):");
        for (Reservation r : queue) {
            System.out.println(r);
        }
    }

    // Get next request (for future allocation, not removing here if you want strict intake phase)
    public Reservation peekNextRequest() {
        return queue.peek();
    }
}

// Main class
public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulating booking requests
        bookingQueue.addRequest(new Reservation("Akshit", "Deluxe"));
        bookingQueue.addRequest(new Reservation("Riya", "Suite"));
        bookingQueue.addRequest(new Reservation("Karan", "Standard"));

        // View queue
        bookingQueue.viewRequests();

        // Show next request (without removing)
        Reservation next = bookingQueue.peekNextRequest();
        if (next != null) {
            System.out.println("\nNext request to process: " + next);
        }

        System.out.println("\nNote: No room allocation done at this stage.");
    }
}