/**
 * The `RedBlackNode` class represents a node in the Red-Black Tree used in the
 * Gator Library Book Management System.
 * Each node contains information about a specific book and maintains references
 * to its parent, left child, and right child
 * nodes in the Red-Black Tree. Additionally, the node is assigned a color
 * (either red or black) to satisfy Red-Black Tree
 * properties.
 */
public class RedBlackNode {

    private RedBlackNode parentRedBlackNode;
    private BookNode book;
    private RedBlackNode leftRedBlackNode;
    private RedBlackNode rightRedBlackNode;
    public String nodeColor;

    /**
     * Constructs a new `RedBlackNode` with the specified book information.
     *
     * @param book The book information to be stored in the node.
     */
    public RedBlackNode(BookNode book) {
        this.book = book;
        this.parentRedBlackNode = null;
        this.leftRedBlackNode = null;
        this.rightRedBlackNode = null;
        this.nodeColor = LibraryActionConstant.RED_BLACK_RED_COLOR_NODE;
    }

    public RedBlackNode getParentRedBlackNode() {
        return parentRedBlackNode;
    }

    public void setParentRedBlackNode(RedBlackNode parentRedBlackNode) {
        this.parentRedBlackNode = parentRedBlackNode;
    }

    public BookNode getBook() {
        return book;
    }

    public void setBook(BookNode book) {
        this.book = book;
    }

    public RedBlackNode getLeftRedBlackNode() {
        return leftRedBlackNode;
    }

    public void setLeftRedBlackNode(RedBlackNode leftRedBlackNode) {
        this.leftRedBlackNode = leftRedBlackNode;
    }

    public RedBlackNode getRightRedBlackNode() {
        return rightRedBlackNode;
    }

    public void setRightRedBlackNode(RedBlackNode rightRedBlackNode) {
        this.rightRedBlackNode = rightRedBlackNode;
    }

    public String getRedBlackNodeColor() {
        return nodeColor;
    }

    public void setRedBlackNodeColor(String newRedBlackNodeColor) {
        this.nodeColor = newRedBlackNodeColor;
    }

    @Override
    public String toString() {
        return "RedBlackNode{" +
                "parentRedBlackNode=" + parentRedBlackNode +
                ", book=" + book +
                ", leftRedBlackNode=" + leftRedBlackNode +
                ", rightRedBlackNode=" + rightRedBlackNode +
                '}';
    }
}
