/**
 * UseCase2RoomInitialization.java
 *
 * This class demonstrates basic object-oriented modeling using
 * abstraction, inheritance, and polymorphism in a Hotel Booking System.
 *
 * It defines different room types and displays their static availability.
 *
 * @author YourName
 * @version 2.1
 */

// Abstract class representing a generic Room
abstract class Room {
    private String roomType;
    private int numberOfBeds;
    private double pricePerNight;

    // Constructor
    public Room(String roomType, int numberOfBeds, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.pricePerNight = pricePerNight;
    }

    // Method to display room details
    public void displayRoomDetails() {
        System.out.println("Room Type      : " + roomType);
        System.out.println("Beds           : " + numberOfBeds);
        System.out.println("Price/Night    : ₹" + pricePerNight);
    }
}

// Concrete class for Single Room
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 1500.0);
    }
}

// Concrete class for Double Room
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 2500.0);
    }
}

// Concrete class for Suite Room
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 5000.0);
    }
}

// Main application class
public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Version: 2.1");
        System.out.println("=====================================\n");

        // Create room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability (simple variables)
        int singleAvailability = 10;
        int doubleAvailability = 5;
        int suiteAvailability = 2;

        // Display details
        System.out.println("---- Room Details & Availability ----\n");

        System.out.println("Single Room:");
        single.displayRoomDetails();
        System.out.println("Available     : " + singleAvailability + "\n");

        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available     : " + doubleAvailability + "\n");

        System.out.println("Suite Room:");
        suite.displayRoomDetails();
        System.out.println("Available     : " + suiteAvailability + "\n");

        System.out.println("Application executed successfully.");
    }
}