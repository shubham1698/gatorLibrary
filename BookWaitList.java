import java.time.LocalDateTime;

/**
 * The `BookWaitList` class represents a patron waiting in the reservation queue
 * for a book in the Gator Library
 * Book Management System. It stores the patron's ID, priority number, and the
 * time of their reservation.
 */
public class BookWaitList {

    private int patronId;
    private int priorityNumber;
    private LocalDateTime timeOfReservation;

    /**
     * Constructs a new `BookWaitList` object with the given patron ID and priority
     * number. It initializes the
     * `timeOfReservation` with the current system time.
     *
     * @param patronId       The ID of the patron waiting in the reservation queue.
     * @param priorityNumber The priority number assigned to the patron.
     */
    public BookWaitList(int patronId, int priorityNumber) {
        this.patronId = patronId;
        this.priorityNumber = priorityNumber;

        // Initialize timeOfReservation with the current system time
        this.timeOfReservation = getCurrentTime();
    }

    // Getter methods for patronId, priorityNumber, and timeOfReservation
    public int getPatronId() {
        return patronId;
    }

    public int getPriorityNumber() {
        return priorityNumber;
    }

    public LocalDateTime getTimeOfReservation() {
        return timeOfReservation;
    }

    /**
     * Helper method to get the current system time in the form of a `LocalDateTime`
     * object.
     *
     * @return The current system time.
     */
    private LocalDateTime getCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime;
    }

    /**
     * Generates a string representation of the `BookWaitList` object, including the
     * patron's ID, priority number, and
     * the time of their reservation.
     *
     * @return A string representation of the `BookWaitList` object.
     */
    @Override
    public String toString() {
        return "BookNode{" +
                "patronId=" + patronId +
                ", priorityNumber='" + priorityNumber + '\'' +
                ", time='" + timeOfReservation +
                '}';
    }

}
