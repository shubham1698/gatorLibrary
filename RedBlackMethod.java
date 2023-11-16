import java.util.ArrayList;

/**
 * The `RedBlackMethod` class encapsulates the methods and operations related to
 * the Red-Black Tree
 * used in the Gator Library Book Management System. It provides functionalities
 * for inserting, deleting,
 * searching, and performing various actions on the Red-Black Tree to manage the
 * library's book inventory.
 */
public class RedBlackMethod {

    RedBlackNode headRedBlackNode = null;
    int colorFlipCount = 0;

    public RedBlackNode getHeadRedBlackNode() {
        return headRedBlackNode;
    }

    /**
     * This method searches for a book with a specified bookId within the Red-Black
     * Tree.
     *
     * @param head   The root node of the Red-Black Tree or its subtree.
     * @param bookId The bookId to be searched within the Red-Black Tree.
     * @return The RedBlackNode containing the book with the specified bookId, or
     *         null if not found.
     */
    public RedBlackNode searchWithInRedBlackTree(RedBlackNode head, int bookId) {
        // Base case: if the current node is null, or the book with the specified bookId
        // is found
        if (head == null || head.getBook().getBookId() == bookId) {
            return head;
        }

        // Recursive case: search in the left subtree if the bookId is smaller,
        // or search in the right subtree if the bookId is larger
        if (head.getBook().getBookId() > bookId)
            return searchWithInRedBlackTree(head.getLeftRedBlackNode(), bookId);

        return searchWithInRedBlackTree(head.getRightRedBlackNode(), bookId);
    }

    /**
     * Checks and performs the borrow operation for a book with the specified bookId
     * within the Red-Black Tree.
     *
     * @param head           The root node of the Red-Black Tree or its subtree.
     * @param bookId         The bookId for which the borrow operation is to be
     *                       checked and performed.
     * @param patronID       The ID of the patron attempting to borrow the book.
     * @param priorityNumber The priority number of the patron for book reservation.
     * @return A status message indicating the result of the borrow operation.
     *         If the book is available, it is borrowed by the patron.
     *         If the book is already reserved, the patron is added to the
     *         reservation queue.
     */
    public String checkForBorrowInRedBlackTree(RedBlackNode head, int bookId, int patronID, int priorityNumber) {
        // Base case: if the current node is null, or the book with the specified bookId
        // is found
        if (head == null || head.getBook().getBookId() == bookId) {
            String status = "";
            System.out.println("PatronID-->" + patronID + " " + head.getBook().isBookAvailabilityStatus());
            if (head.getBook().isBookAvailabilityStatus()) {
                // Book is available, perform borrow operation
                head.getBook().setBookAvailabilityStatus(false);
                head.getBook().setBookBorrowedBy(patronID);
                status = "Book " + head.getBook().getBookId() + " Borrowed by Patron "
                        + head.getBook().getBookBorrowedBy();
            } else {
                // Book is reserved, add patron to the reservation queue
                status = "Book " + head.getBook().getBookId() + " Reserved by Patron " + patronID;
                updateHeap(head, patronID, priorityNumber);
            }
            return status;
        }

        // Recursive case: search in the left subtree if the bookId is smaller,
        // or search in the right subtree if the bookId is larger
        if (head.getBook().getBookId() > bookId)
            return checkForBorrowInRedBlackTree(head.getLeftRedBlackNode(), bookId, patronID, priorityNumber);

        return checkForBorrowInRedBlackTree(head.getRightRedBlackNode(), bookId, patronID, priorityNumber);
    }

    /**
     * Updates the priority queue (heap) for book reservations based on the given
     * patron's information.
     *
     * @param head           The root node of the Red-Black Tree or its subtree.
     * @param patronID       The ID of the patron to be added to the reservation
     *                       queue.
     * @param priorityNumber The priority number of the patron for book reservation.
     */
    public void updateHeap(RedBlackNode head, int patronID, int priorityNumber) {
        // Get the priority queue (heap) associated with the book
        BookPriorityQueue priorityQueue = head.getBook().getBookReservationQueue();

        // Insert the patron with the given ID and priority number into the reservation
        // queue
        priorityQueue.insertPatronToWaitQueue(new BookWaitList(patronID, priorityNumber));
    }

    /**
     * Handles the return of a book in the Red-Black Tree, updates its availability
     * status,
     * and manages book allotment based on reservation priority.
     *
     * @param head     The root node of the Red-Black Tree or its subtree.
     * @param bookId   The ID of the book to be returned.
     * @param patronID The ID of the patron returning the book.
     * @return A status message indicating the result of the return action.
     */
    public String returnBookActionInRedBlackTree(RedBlackNode head, int bookId, int patronID) {
        // Base case: if the current node is null, or the book with the specified bookId
        // is found
        if (head == null || head.getBook().getBookId() == bookId) {
            String status = "";
            BookPriorityQueue priorityQueue = head.getBook().getBookReservationQueue();
            int previousBookHolder = head.getBook().getBookBorrowedBy();

            // Check if there are patrons in the reservation queue
            if (priorityQueue.getBookReservationList().size() > 1) {
                // Update the book borrower to the patron with the highest priority
                head.getBook().setBookBorrowedBy(priorityQueue.getPatronWithHighestPriority().getPatronId());
                status = "Book " + head.getBook().getBookId() + " Return by Patron " + previousBookHolder + ";"
                        + "Book " + head.getBook().getBookId() + " Alloted to Patron "
                        + head.getBook().getBookBorrowedBy();
            } else {
                // No patrons in the reservation queue, mark the book as available
                head.getBook().setBookAvailabilityStatus(true);
                head.getBook().setBookBorrowedBy(-1);
                status = "Book " + head.getBook().getBookId() + " Return by Patron " + previousBookHolder + ";";
            }
            return status;
        }

        // Recursive case: search in the left subtree if the bookId is smaller,
        // or search in the right subtree if the bookId is larger
        if (head.getBook().getBookId() > bookId)
            return returnBookActionInRedBlackTree(head.getLeftRedBlackNode(), bookId, patronID);

        return returnBookActionInRedBlackTree(head.getRightRedBlackNode(), bookId, patronID);
    }

    /**
     * Inserts a new node into the Red-Black Tree and maintains the properties of
     * the Red-Black Tree.
     *
     * @param newBookNode The RedBlackNode to be inserted into the Red-Black Tree.
     * @param head        The root node of the Red-Black Tree or its subtree.
     */
    public void insertInRedBlackTree(RedBlackNode newBookNode, RedBlackNode head) {
        try {
            RedBlackNode parent = null;
            System.out.println(newBookNode.getBook().toString());
            //System.out.println(head.getBook().toString());
            // Traverse the tree to the left or right depending on the key
            while (head != null) {
                parent = head;
                if (newBookNode.getBook().getBookId() < head.getBook().getBookId()) {
                    head = head.getLeftRedBlackNode();
                } else if (newBookNode.getBook().getBookId() > head.getBook().getBookId()) {
                    head = head.getRightRedBlackNode();
                } else {
                    throw new IllegalArgumentException("BST already contains a node with key ");
                }
            }

            // Insert new node
            if (parent == null) {
                // If the tree is empty, set the new node as the root
                headRedBlackNode = newBookNode;
                
            } else if (newBookNode.getBook().getBookId() < parent.getBook().getBookId()) {
                parent.setLeftRedBlackNode(newBookNode);
            } else {
                parent.setRightRedBlackNode(newBookNode);
            }
            newBookNode.setParentRedBlackNode(parent);
            
            fixRedBlackNodePropertiesAfterInsert(newBookNode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Fixes the Red-Black Tree properties after inserting a new node. This method
     * is called recursively
     * to maintain the balance and color properties of the Red-Black Tree.
     *
     * @param node The RedBlackNode that was inserted and may violate the Red-Black
     *             Tree properties.
     * @throws IllegalArgumentException If there is an attempt to violate Red-Black
     *                                  Tree properties.
     */
    private void fixRedBlackNodePropertiesAfterInsert(RedBlackNode node) {
        RedBlackNode parent = node.getParentRedBlackNode();

        // Case 1: Parent is null, we've reached the root, the end of the recursion
        if (parent == null) {
            redBlackTreeColourFilpTracker(node, LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            return;
        }

        // Parent is black --> nothing to do
        if (parent.getRedBlackNodeColor() == LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE) {
            return;
        }

        // From here on, parent is red
        RedBlackNode grandparent = parent.getParentRedBlackNode();

        // Case 2:
        // Not having a grandparent means that parent is the root. If we enforce black
        // roots
        // (rule 2), grandparent will never be null, and the following if-then block can
        // be
        // removed.
        if (grandparent == null) {
            // As this method is only called on red nodes (either on newly inserted ones -
            // or -
            // recursively on red grandparents), all we have to do is to recolor the root
            // black.
            // parent.setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            redBlackTreeColourFilpTracker(parent, LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            return;
        }

        // Get the uncle (may be null/nil, in which case its color is BLACK)
        RedBlackNode uncle = getRedBlackUncleNode(parent);

        // Case 3: Uncle is red -> recolor parent, grandparent and uncle
        if (uncle != null && uncle.getRedBlackNodeColor() == LibraryActionConstant.RED_BLACK_RED_COLOR_NODE) {
            // parent.setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            redBlackTreeColourFilpTracker(parent, LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            // grandparent.setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_RED_COLOR_NODE);
            redBlackTreeColourFilpTracker(grandparent, LibraryActionConstant.RED_BLACK_RED_COLOR_NODE);
            // uncle.setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            redBlackTreeColourFilpTracker(uncle, LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);

            // Call recursively for grandparent, which is now red.
            // It might be root or have a red parent, in which case we need to fix more...
            fixRedBlackNodePropertiesAfterInsert(grandparent);
        }

        // Parent is left child of grandparent
        else if (parent == grandparent.getLeftRedBlackNode()) {
            // Case 4a: Uncle is black and node is left->right "inner child" of its
            // grandparent
            if (node == parent.getRightRedBlackNode()) {
                rotateRedBlackTreeToLeft(parent);

                // Let "parent" point to the new root node of the rotated sub-tree.
                // It will be recolored in the next step, which we're going to fall-through to.
                parent = node;
            }

            // Case 5a: Uncle is black and node is left->left "outer child" of its
            // grandparent
            rotateRedBlackTeeToRight(grandparent);

            // Recolor original parent and grandparent
            // parent.setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            redBlackTreeColourFilpTracker(parent, LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);

            // grandparent.setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_RED_COLOR_NODE);
            redBlackTreeColourFilpTracker(grandparent, LibraryActionConstant.RED_BLACK_RED_COLOR_NODE);

        }

        // Parent is right child of grandparent
        else {
            // Case 4b: Uncle is black and node is right->left "inner child" of its
            // grandparent
            if (node == parent.getLeftRedBlackNode()) {
                rotateRedBlackTeeToRight(parent);

                // Let "parent" point to the new root node of the rotated sub-tree.
                // It will be recolored in the next step, which we're going to fall-through to.
                parent = node;
            }

            // Case 5b: Uncle is black and node is right->right "outer child" of its
            // grandparent
            rotateRedBlackTreeToLeft(grandparent);

            // Recolor original parent and grandparent
            // parent.setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            redBlackTreeColourFilpTracker(parent, LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            // grandparent.setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_RED_COLOR_NODE);
            redBlackTreeColourFilpTracker(grandparent, LibraryActionConstant.RED_BLACK_RED_COLOR_NODE);
        }
    }

    /**
     * Performs a right rotation on the Red-Black Tree, adjusting the positions of
     * nodes to maintain
     * the binary search tree properties and the balance of the Red-Black Tree after
     * an insertion.
     *
     * @param node The node around which the right rotation is performed.
     * @throws IllegalArgumentException If there is an attempt to violate Red-Black
     *                                  Tree properties.
     */
    private void rotateRedBlackTeeToRight(RedBlackNode node) {
        RedBlackNode parent = node.getParentRedBlackNode();
        RedBlackNode leftChild = node.getLeftRedBlackNode();

        node.setLeftRedBlackNode(leftChild.getRightRedBlackNode());
        if (leftChild.getRightRedBlackNode() != null) {
            leftChild.getRightRedBlackNode().setParentRedBlackNode(null);
        }

        leftChild.setRightRedBlackNode(node);
        node.setParentRedBlackNode(leftChild);

        replaceParentsChild(parent, node, leftChild);
    }

    /**
     * Performs a left rotation on the Red-Black Tree, adjusting the positions of
     * nodes to maintain
     * the binary search tree properties and the balance of the Red-Black Tree after
     * an insertion.
     *
     * @param node The node around which the left rotation is performed.
     * @throws IllegalArgumentException If there is an attempt to violate Red-Black
     *                                  Tree properties.
     */
    private void rotateRedBlackTreeToLeft(RedBlackNode node) {
        RedBlackNode parent = node.getParentRedBlackNode();
        RedBlackNode rightChild = node.getRightRedBlackNode();

        node.setRightRedBlackNode(rightChild.getLeftRedBlackNode());
        if (rightChild.getLeftRedBlackNode() != null) {
            rightChild.getLeftRedBlackNode().setParentRedBlackNode(node);
        }

        rightChild.setLeftRedBlackNode(node);
        node.setParentRedBlackNode(rightChild);

        replaceParentsChild(parent, node, rightChild);
    }

    /**
     * Replaces the child of a given parent node with a new child node, maintaining
     * the parent-child relationship.
     *
     * @param parent   The parent node whose child is to be replaced.
     * @param oldChild The current child node to be replaced.
     * @param newChild The new child node to be set for the parent.
     * @throws IllegalStateException If the provided node is not a child of its
     *                               parent.
     */
    private void replaceParentsChild(RedBlackNode parent, RedBlackNode oldChild, RedBlackNode newChild) {
        if (parent == null) {
            headRedBlackNode = newChild;
        } else if (parent.getLeftRedBlackNode() == oldChild) {
            parent.setLeftRedBlackNode(newChild);
        } else if (parent.getRightRedBlackNode() == oldChild) {
            parent.setRightRedBlackNode(newChild);
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }

        if (newChild != null) {
            newChild.setParentRedBlackNode(parent);
        }
    }

    /**
     * Returns the uncle node of a given parent node in the Red-Black Tree.
     * The uncle node is the sibling of the parent's parent.
     *
     * @param parent The parent node for which to find the uncle.
     * @return The uncle node of the given parent node.
     * @throws IllegalStateException If the provided parent node is not a child of
     *                               its grandparent.
     */
    private RedBlackNode getRedBlackUncleNode(RedBlackNode parent) {
        RedBlackNode grandparent = parent.getParentRedBlackNode();
        if (grandparent.getLeftRedBlackNode() == parent) {
            return grandparent.getRightRedBlackNode();
        } else if (grandparent.getRightRedBlackNode() == parent) {
            return grandparent.getLeftRedBlackNode();
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }

    /**
     * Deletes a node with the specified book ID from the Red-Black Tree and
     * maintains its properties.
     * If the node to be deleted has reservations, the corresponding reservations
     * are cancelled.
     *
     * @param bookID The book ID of the node to be deleted.
     * @param head   The head node of the Red-Black Tree.
     * @return A status message indicating the result of the deletion.
     */
    public String deleteRedBlackNode(int bookID, RedBlackNode head) {
        RedBlackNode node = head;

        // Find the node to be deleted
        while (node != null && node.getBook().getBookId() != bookID) {
            // Traverse the tree to the left or right depending on the key
            if (bookID < node.getBook().getBookId()) {
                node = node.getLeftRedBlackNode();
            } else {
                node = node.getRightRedBlackNode();
            }
        }

        // Node not found?
        if (node == null) {
            return "";
        }

        // At this point, "node" is the node to be deleted

        // In this variable, we'll store the node at which we're going to start to fix
        // the R-B
        // properties after deleting a node.
        
        String returnStatusMessage = node.getBook().getBookId() + " is no longer available.";
        String returnSecondHalfString = "";
        if (!node.getBook().getBookReservationQueue().waitListHeap.isEmpty()) {
            String waitingPatronID = "";
            for (BookWaitList element : node.getBook().getBookReservationQueue().waitListHeap) {
                waitingPatronID = waitingPatronID + element.getPatronId();
            }
            returnSecondHalfString = returnSecondHalfString + "Reservations made by Patrons " + waitingPatronID
                    + " have been cancelled!";
        }

        RedBlackNode movedUpNode;
        String deletedNodeColor;

        // Node has zero or one child
        if (node.getLeftRedBlackNode() == null || node.getRightRedBlackNode() == null) {
            movedUpNode = deleteNodeWithZeroOrOneChild(node);
            deletedNodeColor = node.getRedBlackNodeColor();
        }

        // Node has two children
        else {
            // Find minimum node of right subtree ("inorder successor" of current node)
            RedBlackNode inOrderSuccessor = findMinimum(node.getRightRedBlackNode());

            // Copy inorder successor's data to current node (keep its color!)
            node.setBook(inOrderSuccessor.getBook());

            // Delete inorder successor just as we would delete a node with 0 or 1 child
            movedUpNode = deleteNodeWithZeroOrOneChild(inOrderSuccessor);
            deletedNodeColor = inOrderSuccessor.getRedBlackNodeColor();
        }

        if (deletedNodeColor == LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE) {
            fixRedBlackPropertiesAfterDelete(movedUpNode);

            // Remove the temporary NIL node
            if (movedUpNode.getClass() == NilNode.class) {
                replaceParentsChild(movedUpNode.getParentRedBlackNode(), movedUpNode, null);
            }
        }
        return returnStatusMessage + returnSecondHalfString;
    }

    /**
     * Deletes a node with zero or one child from the Red-Black Tree and adjusts its
     * parent's child reference.
     * If the deleted node is black, it is replaced by a temporary NIL node to
     * maintain Red-Black Tree properties.
     *
     * @param node The node to be deleted, which has either zero or one child.
     * @return The node that has replaced the deleted node, or a temporary NIL node
     *         if the deleted node was black.
     */
    private RedBlackNode deleteNodeWithZeroOrOneChild(RedBlackNode node) {
        // Node has ONLY a left child --> replace by its left child
        if (node.getLeftRedBlackNode() != null) {
            replaceParentsChild(node.getParentRedBlackNode(), node, node.getLeftRedBlackNode());
            return node.getLeftRedBlackNode(); // moved-up node
        }

        // Node has ONLY a right child --> replace by its right child
        else if (node.getRightRedBlackNode() != null) {
            replaceParentsChild(node.getParentRedBlackNode(), node, node.getRightRedBlackNode());
            return node.getRightRedBlackNode(); // moved-up node
        }

        // Node has no children -->
        // * node is red --> just remove it
        // * node is black --> replace it by a temporary NIL node (needed to fix the R-B
        // rules)
        else {
            RedBlackNode newChild = node.getRedBlackNodeColor() == LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE
                    ? new NilNode()
                    : null;
            replaceParentsChild(node.getParentRedBlackNode(), node, newChild);
            return newChild;
        }
    }

    /**
     * Finds and returns the node with the minimum key value in the subtree rooted
     * at the given node.
     *
     * @param node The root node of the subtree to search.
     * @return The node with the minimum key value in the subtree.
     */
    private RedBlackNode findMinimum(RedBlackNode node) {
        while (node.getLeftRedBlackNode() != null) {
            node = node.getLeftRedBlackNode();
        }
        return node;
    }

    /**
     * Fixes the Red-Black Tree properties after a node deletion, addressing cases
     * where the deletion may violate
     * the balance and coloring rules of the Red-Black Tree.
     *
     * @param node The node at which to start fixing Red-Black Tree properties after
     *             deletion.
     */
    private void fixRedBlackPropertiesAfterDelete(RedBlackNode node) {
        // Case 1: Examined node is root, end of recursion
        if (node == headRedBlackNode) {
            // Uncomment the following line if you want to enforce black roots (rule 2):
            // node.color = BLACK;
            return;
        }

        RedBlackNode sibling = getRedBlackNodeSibling(node);

        // Case 2: Red sibling
        if (sibling.getRedBlackNodeColor() == LibraryActionConstant.RED_BLACK_RED_COLOR_NODE) {
            handleRedSibling(node, sibling);
            sibling = getRedBlackNodeSibling(node); // Get new sibling for fall-through to cases 3-6
        }

        // Cases 3+4: Black sibling with two black children
        if (isColorOfRedBlackNodeBlack(sibling.getLeftRedBlackNode())
                && isColorOfRedBlackNodeBlack(sibling.getRightRedBlackNode())) {
            // sibling.setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_RED_COLOR_NODE);
            redBlackTreeColourFilpTracker(sibling, LibraryActionConstant.RED_BLACK_RED_COLOR_NODE);

            // Case 3: Black sibling with two black children + red parent

            if (node.getParentRedBlackNode().getRedBlackNodeColor() == LibraryActionConstant.RED_BLACK_RED_COLOR_NODE) {
                // node.getParentRedBlackNode().setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
                redBlackTreeColourFilpTracker(node.getParentRedBlackNode(),
                        LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            }

            // Case 4: Black sibling with two black children + black parent
            else {
                fixRedBlackPropertiesAfterDelete(node.getParentRedBlackNode());
            }
        }

        // Case 5+6: Black sibling with at least one red child
        else {
            handleBlackSiblingWithAtLeastOneRedChild(node, sibling);
        }
    }

    /**
     * Handles the case where the sibling of a deleted node is red during the
     * Red-Black Tree deletion fix process.
     * It performs recoloring and rotation to restore Red-Black Tree properties.
     *
     * @param node    The deleted node.
     * @param sibling The sibling of the deleted node.
     */
    private void handleRedSibling(RedBlackNode node, RedBlackNode sibling) {
        // Recolor...
        // sibling.setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
        redBlackTreeColourFilpTracker(sibling, LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
        // node.getParentRedBlackNode().setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_RED_COLOR_NODE);
        redBlackTreeColourFilpTracker(node.getParentRedBlackNode(), LibraryActionConstant.RED_BLACK_RED_COLOR_NODE);
        // ... and rotate
        if (node == node.getParentRedBlackNode().getLeftRedBlackNode()) {
            rotateRedBlackTreeToLeft(node.getParentRedBlackNode());
        } else {
            rotateRedBlackTeeToRight(node.getParentRedBlackNode());
        }
    }

    /**
     * Handles the case where the sibling of a deleted node is black with at least
     * one red child during the Red-Black
     * Tree deletion fix process. It performs recoloring and rotation to restore
     * Red-Black Tree properties.
     *
     * @param node    The deleted node.
     * @param sibling The sibling of the deleted node.
     */
    private void handleBlackSiblingWithAtLeastOneRedChild(RedBlackNode node, RedBlackNode sibling) {
        boolean nodeIsLeftChild = node == node.getParentRedBlackNode().getLeftRedBlackNode();

        // Case 5: Black sibling with at least one red child + "outer nephew" is black
        // --> Recolor sibling and its child, and rotate around sibling
        if (nodeIsLeftChild && isColorOfRedBlackNodeBlack(sibling.getRightRedBlackNode())) {
            // sibling.getLeftRedBlackNode().setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            redBlackTreeColourFilpTracker(sibling.getLeftRedBlackNode(),
                    LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            // sibling.setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_RED_COLOR_NODE);
            redBlackTreeColourFilpTracker(sibling, LibraryActionConstant.RED_BLACK_RED_COLOR_NODE);
            rotateRedBlackTeeToRight(sibling);
            sibling = node.getParentRedBlackNode().getRightRedBlackNode();
        } else if (!nodeIsLeftChild && isColorOfRedBlackNodeBlack(sibling.getLeftRedBlackNode())) {
            // sibling.getRightRedBlackNode().setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            redBlackTreeColourFilpTracker(sibling.getRightRedBlackNode(),
                    LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            // sibling.setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_RED_COLOR_NODE);
            redBlackTreeColourFilpTracker(sibling, LibraryActionConstant.RED_BLACK_RED_COLOR_NODE);
            rotateRedBlackTreeToLeft(sibling);
            sibling = node.getParentRedBlackNode().getLeftRedBlackNode();
        }

        // Fall-through to case 6...

        // Case 6: Black sibling with at least one red child + "outer nephew" is red
        // --> Recolor sibling + parent + sibling's child, and rotate around parent
        // sibling.setRedBlackNodeColor(node.getParentRedBlackNode().getRedBlackNodeColor());
        redBlackTreeColourFilpTracker(sibling, node.getParentRedBlackNode().getRedBlackNodeColor());
        // node.getParentRedBlackNode().setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
        redBlackTreeColourFilpTracker(node.getParentRedBlackNode(), LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
        if (nodeIsLeftChild) {
            // sibling.getRightRedBlackNode().setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            redBlackTreeColourFilpTracker(sibling,
                    LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            rotateRedBlackTreeToLeft(node.getParentRedBlackNode());
        } else {
            // sibling.getLeftRedBlackNode().setRedBlackNodeColor(LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            redBlackTreeColourFilpTracker(sibling,
                    LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
            rotateRedBlackTeeToRight(node.getParentRedBlackNode());
        }
    }

    /**
     * Returns the sibling node of a given node in the Red-Black Tree.
     *
     * @param node The node for which to find the sibling.
     * @return The sibling node of the given node.
     * @throws IllegalStateException If the provided node is not a child of its
     *                               parent.
     */
    private RedBlackNode getRedBlackNodeSibling(RedBlackNode node) {
        RedBlackNode parent = node.getParentRedBlackNode();
        if (node == parent.getLeftRedBlackNode()) {
            return parent.getRightRedBlackNode();
        } else if (node == parent.getRightRedBlackNode()) {
            return parent.getLeftRedBlackNode();
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }

    /**
     * Checks whether the color of a Red-Black Tree node is black.
     *
     * @param node The node to check.
     * @return True if the color of the node is black or the node is null; false
     *         otherwise.
     */
    private boolean isColorOfRedBlackNodeBlack(RedBlackNode node) {
        return (node == null || node.getRedBlackNodeColor() == LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE);
    }

    /**
     * Searches the Red-Black Tree for nodes within the specified range of book IDs
     * and collects them into an ArrayList.
     *
     * @param startBookID The starting book ID of the range.
     * @param endBookID   The ending book ID of the range.
     * @param head        The head node of the Red-Black Tree.
     * @param books       The ArrayList to store the matching BookNode objects.
     * @return An ArrayList containing BookNode objects within the specified book ID
     *         range.
     */
    public ArrayList<BookNode> serachTheRedBlackTreeInRange(int startBookID, int endBookID, RedBlackNode head,
            ArrayList<BookNode> books) {
        // Base case: if the current node is null, or the book with the specified bookId
        // is found

        if (head == null) {
            return books;
        }
        if (startBookID <= head.getBook().getBookId() && head.getBook().getBookId() <= endBookID) {
            books.add(head.getBook());
        }

        books = serachTheRedBlackTreeInRange(startBookID, endBookID, head.getLeftRedBlackNode(), books);
        books = serachTheRedBlackTreeInRange(startBookID, endBookID, head.getRightRedBlackNode(), books);

        return books;
    }

    /**
     * Updates the color of a Red-Black Tree node and increments a counter if the
     * color is changed.
     *
     * @param currNode             The node whose color is to be updated.
     * @param newRedBlackNodeColor The new color to set for the node.
     */
    public void redBlackTreeColourFilpTracker(RedBlackNode currNode, String newRedBlackNodeColor) {
        if (currNode.getRedBlackNodeColor() != newRedBlackNodeColor) {
            colorFlipCount++;
        }
        currNode.setRedBlackNodeColor(newRedBlackNodeColor);
    }

    /**
     * Gets the count of color flips that occurred during Red-Black Tree operations.
     *
     * @return The count of color flips.
     */
    public int getColorFilpCount() {
        return colorFlipCount;
    }

    /**
     * Finds the book with the closest book ID to the specified book ID in a
     * Red-Black Tree,
     * considering the absolute difference between book IDs. The method searches the
     * tree
     * recursively, updating the closest book information and the minimum difference
     * as it traverses
     * the tree nodes.
     *
     * @param head            The head node of the Red-Black Tree.
     * @param bookID          The target book ID for which to find the closest book.
     * @param closestBookList The ArrayList to store the closest BookNode objects.
     * @param min             The minimum absolute difference between book IDs
     *                        encountered during the search.
     * @return An ArrayList containing the BookNode object(s) with the closest book
     *         ID(s) to the specified book ID.
     */
    public ArrayList<BookNode> findTheClosestBook(RedBlackNode head, int bookID, ArrayList<BookNode> closestBookList,
            int min) {
        if (head == null) {

            return closestBookList;
        }

        if ((Math.abs(head.getBook().getBookId() - bookID)) < min) {
            closestBookList.clear();
            min = Math.abs(head.getBook().getBookId() - bookID);
            closestBookList.add(head.getBook());
        } else if ((Math.abs(head.getBook().getBookId() - bookID)) == min) {
            closestBookList.add(head.getBook());
        }
        ArrayList<BookNode> leftnode = findTheClosestBook(head.getLeftRedBlackNode(), bookID, closestBookList, min);
        for (BookNode element : leftnode) {
            if (!closestBookList.contains(element)) {
                closestBookList.add(element);
            }
        }
        ArrayList<BookNode> rightnode = findTheClosestBook(head.getRightRedBlackNode(), bookID, closestBookList, min);
        for (BookNode element : rightnode) {
            if (!closestBookList.contains(element)) {
                closestBookList.add(element);
            }
        }
        return closestBookList;
    }
}
