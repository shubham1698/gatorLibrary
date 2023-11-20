import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
/**
 * The `GatorLibServices` class provides services to perform various actions in
 * the Gator Library Book Management System.
 * It interacts with the Red-Black tree (implemented in `RedBlackMethod`) to
 * manage book-related operations.
 */
public class GatorLibServices {

    // Red-Black tree instance
    RedBlackMethod rb;
    // FileWriter for writing output to a file
    FileWriter writer;

    /**
     * Constructor to initialize GatorLibServices with a Red-Black tree instance and
     * a FileWriter.
     *
     * @param rb     The Red-Black tree instance.
     * @param writer The FileWriter to write output.
     */
    public GatorLibServices(RedBlackMethod rb, FileWriter write) {
        this.rb = rb;
        this.writer = write;
    }

    /**
     * Performs the action of inserting a new book into the Red-Black tree.
     *
     * @param newBookNode The BookNode representing the new book to be inserted.
     */
    public void performInsertBookAction(BookNode newBookNode) {
        // Implementation for printing book action
        try {
            RedBlackNode newBookRBNode = new RedBlackNode(newBookNode);
            rb.insertInRedBlackTree(newBookRBNode, rb.getHeadRedBlackNode());
            return;
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * Performs the action of printing details for a specific book based on the
     * provided book ID.
     *
     * @param bookIdToSearch The book ID for which details are to be printed.
     */
    public void performPrintBookAction(int bookIdToSearch) {
        // Implementation for search book action
        try {
            RedBlackNode redBlackBookNode = rb.searchWithInRedBlackTree(rb.getHeadRedBlackNode(), bookIdToSearch);
            if (redBlackBookNode != null) {
                BookNode bookDetails = redBlackBookNode.getBook();
                writer.write("BookID = " + bookDetails.getBookId() + "\n");
                writer.write("Title = " + bookDetails.getBookName() + "\n");
                writer.write("Author = " + bookDetails.getBookAuthorName() + "\n");
                writer.write("Availability = " + (bookDetails.isBookAvailabilityStatus() ? "Yes" : "No") + "\n");
                writer.write("BorrowedBy = "
                        + (bookDetails.getBookBorrowedBy() != -1 ? bookDetails.getBookBorrowedBy() : "None") + "\n");
                ArrayList<BookWaitList> reservationList = bookDetails.getBookReservationQueue()
                        .getBookReservationList();
                String reservation = "";
                for (int i = 0; i < reservationList.size(); i++) {
                    if (i != reservationList.size() - 1) {
                        reservation = reservation + " " + reservationList.get(i).getPatronId() + ",";
                    } else {
                        reservation = reservation + " " + reservationList.get(i).getPatronId() + " ";
                    }
                }
                writer.write("Reservations = [" + reservation + "]\n");
                writer.write("\n");
            } else {
                writer.write("Book " + bookIdToSearch + " not found in the library\n");
                writer.write("\n");
            }

        } catch (Exception e) {

        }

        return;
    }

    /**
     * Performs the action of borrowing a book by a patron with the specified
     * priority number.
     *
     * @param patronID       The ID of the patron borrowing the book.
     * @param bookID         The ID of the book to be borrowed.
     * @param priorityNumber The priority number of the patron in the waitlist.
     */
    public void performBorrowBookAction(int patronID, int bookID, int priorityNumber) {
        // Implementation for borrowing book action
        try {

            writer.write(
                    rb.checkForBorrowInRedBlackTree(rb.getHeadRedBlackNode(), bookID, patronID, priorityNumber) + "\n");
            writer.write("\n");
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    /**
     * Performs the action of returning a book by a patron.
     *
     * @param patronID The ID of the patron returning the book.
     * @param bookID   The ID of the book to be returned.
     */
    public void performReturnBookAction(int patronID, int bookID) {
        // Implementation for returning book action
        try {
            String status = rb.returnBookActionInRedBlackTree(rb.getHeadRedBlackNode(), bookID, patronID);

            writer.write(status.split(";", 2)[0] + "\n");
            writer.write("\n");

            if (!(status.split(";", 2)[1].isEmpty())) {
                writer.write(status.split(";", 2)[1] + "\n");
                writer.write("\n");
            }

        } catch (IOException e) {

        }
    }

    /**
     * Performs the action of deleting a book from the Red-Black tree based on the
     * provided book ID.
     *
     * @param bookID The ID of the book to be deleted.
     */
    public void performDeleteBookAction(int bookID) {
        // Implementation for deleting book action
        try {

            writer.write(rb.deleteFromRedBlackTree(bookID, rb.getHeadRedBlackNode()) + "\n");
            writer.write("\n");
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    /**
     * Performs the action of printing details for books within a specified ID
     * range.
     *
     * @param startbookId The starting book ID of the range.
     * @param endBookID   The ending book ID of the range.
     */
    public void performPrintBooksAction(int startbookId, int endBookID) {
        // Implementation for search book action
        try {
            ArrayList<BookNode> bookArrayList = rb.serachTheRedBlackTreeInRange(startbookId, endBookID,
                    rb.getHeadRedBlackNode(), new ArrayList<>());
            Collections.sort(bookArrayList, (b1, b2) -> Integer.compare(b1.getBookId(), b2.getBookId()));

            for (BookNode bookDetails : bookArrayList) {
                writer.write("BookID = " + bookDetails.getBookId() + "\n");
                writer.write("Title = " + bookDetails.getBookName() + "\n");
                writer.write("Author = " + bookDetails.getBookAuthorName() + "\n");
                writer.write("Availability = " + (bookDetails.isBookAvailabilityStatus() ? "Yes" : "No") + "\n");
                writer.write("BorrowedBy = "
                        + (bookDetails.getBookBorrowedBy() != -1 ? bookDetails.getBookBorrowedBy() : "None") + "\n");
                ArrayList<BookWaitList> reservationList = bookDetails.getBookReservationQueue()
                        .getBookReservationList();
                String reservation = "";
                for (int i = 0; i < reservationList.size(); i++) {
                    if (i != reservationList.size() - 1) {
                        reservation = reservation + " " + reservationList.get(i).getPatronId() + ",";
                    } else {
                        reservation = reservation + " " + reservationList.get(i).getPatronId() + " ";
                    }
                }
                writer.write("Reservations = [" + reservation + "]\n");
                writer.write("\n");


            }
        } catch (Exception e) {

        }

        return;
    }

    /**
     * Performs the action of terminating the program.
     */
    public void performQuitAction() {
        // Implementation for Quit
        try {
            writer.write("Program Terminated!!\n");
            writer.write("\n");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * Performs the action of printing the color flip count of the Red-Black tree.
     */
    public void performColourFlipCountAction() {
        try {
            writer.write(
                    "Color Flip Count: " + rb.getColorFilpCount() + "\n");
            writer.write("\n");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * Performs the action of finding the closest books to a specified book ID.
     *
     * @param bookID The ID of the book for which the closest books are to be found.
     */
    public void performFindClosestBookSearch(int bookID) {
        try {
            ArrayList<BookNode> closestBookFound = rb.findTheClosestBook(rb.getHeadRedBlackNode(), bookID,
                    new ArrayList<BookNode>(), Integer.MAX_VALUE);
            Collections.sort(closestBookFound, (b1, b2) -> Integer.compare(b1.getBookId(), b2.getBookId()));

            for (BookNode bookDetails : closestBookFound) {
                writer.write("BookID = " + bookDetails.getBookId() + "\n");
                writer.write("Title = " + bookDetails.getBookName() + "\n");
                writer.write("Author = " + bookDetails.getBookAuthorName() + "\n");
                writer.write(
                        "Availability = " + (bookDetails.isBookAvailabilityStatus() ? "Yes" : "No") + "\n");
                writer.write("BorrowedBy = "
                        + (bookDetails.getBookBorrowedBy() != -1 ? bookDetails.getBookBorrowedBy()
                                : "None")
                        + "\n");
                ArrayList<BookWaitList> reservationList = bookDetails.getBookReservationQueue()
                        .getBookReservationList();
                String reservation = "";
                for (int i = 0; i < reservationList.size(); i++) {
                    if (i != reservationList.size() - 1) {
                        reservation = reservation + " " + reservationList.get(i).getPatronId() + ",";
                    } else {
                        reservation = reservation + " " + reservationList.get(i).getPatronId() + " ";
                    }
                }
                writer.write("Reservations = [" + reservation + "]\n");
                writer.write("\n");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
