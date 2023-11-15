/**
 * The `BookNode` class represents a node in the Gator Library Book Management
 * System. Each node
 * corresponds to a book and contains information such as the book ID, title,
 * author, availability
 * status, borrower ID, and a priority queue for book reservations.
 */
public class BookNode {

    private int bookId;
    private String bookName;
    private String bookAuthorName;
    private boolean bookAvailabilityStatus;
    private int bookBorrowedBy;
    private BookPriorityQueue bookPriorityQueue;

    /**
     * Constructor to initialize a new `BookNode` with the provided information.
     *
     * @param bookId                 The unique identifier for the book.
     * @param bookName               The title of the book.
     * @param bookAuthorName         The author of the book.
     * @param bookAvailabilityStatus The availability status of the book.
     */
    public BookNode(int bookId, String bookName, String bookAuthorName, boolean bookAvailabilityStatus) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthorName = bookAuthorName;
        this.bookAvailabilityStatus = bookAvailabilityStatus;
        this.bookBorrowedBy = -1;
        this.bookPriorityQueue = new BookPriorityQueue();
    }

    // Getter and Setter methods for all fields

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthorName() {
        return bookAuthorName;
    }

    public void setBookAuthorName(String bookAuthorName) {
        this.bookAuthorName = bookAuthorName;
    }

    public boolean isBookAvailabilityStatus() {
        return bookAvailabilityStatus;
    }

    public void setBookAvailabilityStatus(boolean bookAvailabilityStatus) {
        this.bookAvailabilityStatus = bookAvailabilityStatus;
    }

    public int getBookBorrowedBy() {
        return bookBorrowedBy;
    }

    public void setBookBorrowedBy(int bookBorrowedBy) {
        this.bookBorrowedBy = bookBorrowedBy;
    }

    public BookPriorityQueue getBookReservationQueue() {
        return bookPriorityQueue;
    }

    /**
     * Returns a string representation of the `BookNode` object, useful for
     * debugging.
     *
     * @return A string containing the book's ID, title, author, and availability
     *         status.
     */
    @Override
    public String toString() {
        return "BookNode{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookAuthorName='" + bookAuthorName + '\'' +
                ", bookAvailabilityStatus=" + bookAvailabilityStatus +
                '}';
    }
}
