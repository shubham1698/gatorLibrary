import java.util.ArrayList;

public class BookPriorityQueue {

    ArrayList<BookWaitList> waitListHeap = new ArrayList<>();

    /**
     * Inserts a patron's details into the wait queue based on their priority number
     * and the time of their reservation.
     *
     * @param patronDetails The details of the patron to be inserted into the
     *                      priority queue.
     */
    public void insertPatronToWaitQueue(BookWaitList patronDetails) {
        if (waitListHeap.size() < LibraryActionConstant.PRORITY_MAX_HEAP_SIZE) {
            waitListHeap.add(patronDetails);
            int index = waitListHeap.size() - 1;
            int parentIndex = (index - 1) / 2;
            while (index != 0) {

                if (waitListHeap.get(parentIndex).getPriorityNumber() > waitListHeap.get(index).getPriorityNumber()) {
                    heapifyTree(parentIndex, index);
                    index = parentIndex;
                } else if (waitListHeap.get(parentIndex).getPriorityNumber() == waitListHeap.get(index)
                        .getPriorityNumber()) {

                    if (waitListHeap.get(parentIndex).getTimeOfReservation()
                            .isBefore(waitListHeap.get(index).getTimeOfReservation())) {
                        heapifyTree(parentIndex, index);
                        index = parentIndex;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }

    /**
     * Rearranges the elements in the heap to maintain the min-heap property during
     * insertion.
     *
     * @param parentIndex The index of the parent node.
     * @param index       The index of the node to be inserted.
     */
    private void heapifyTree(int parentIndex, int index) {
        BookWaitList tempNode = waitListHeap.get(parentIndex);
        waitListHeap.set(parentIndex, waitListHeap.get(index));
        waitListHeap.set(index, tempNode);
    }

    /**
     * Retrieves the patron with the highest priority from the wait queue, removes
     * them from the queue, and reorganizes
     * the heap.
     *
     * @return The patron with the highest priority.
     */
    public BookWaitList getPatronWithHighestPriority() {
        BookWaitList minNode = waitListHeap.get(0);
        waitListHeap.set(0, waitListHeap.get(waitListHeap.size() - 1));
        waitListHeap.remove(waitListHeap.size() - 1);

        int index = 0;
        while (index < waitListHeap.size()) {
            int leftChildIdx = index * 2 + 1;
            int rightChildIdx = index * 2 + 2;
            int smallerChildIdx = leftChildIdx;

            if (rightChildIdx < waitListHeap.size() &&
                    waitListHeap.get(rightChildIdx).getPriorityNumber() < waitListHeap.get(leftChildIdx)
                            .getPriorityNumber()) {
                smallerChildIdx = rightChildIdx;
            }

            if (smallerChildIdx < waitListHeap.size() &&
                    waitListHeap.get(smallerChildIdx).getPriorityNumber() < waitListHeap.get(index)
                            .getPriorityNumber()) {
                heapifyTree(smallerChildIdx, index);
                index = smallerChildIdx;
            } else {
                break;
            }
        }
        return minNode;
    }

    /**
     * Retrieves the list of patrons in the book reservation queue.
     *
     * @return The list of patrons in the book reservation queue.
     */
    public ArrayList<BookWaitList> getBookReservationList() {
        return waitListHeap;
    }

    /**
     * Retrieves the next patron in the book reservation queue without removing
     * them.
     *
     * @return The next patron in the book reservation queue, or null if the queue
     *         is empty.
     */
    public BookWaitList getNextPatron() {
        if (!waitListHeap.isEmpty())
            return waitListHeap.get(0);
        return null;
    }
}