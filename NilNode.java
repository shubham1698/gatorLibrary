/**
 * The `NilNode` class represents a special type of Red-Black Tree node used in
 * the Gator Library Book Management System.
 * It is a subclass of `RedBlackNode` and serves as a placeholder for leaves in
 * the Red-Black Tree.
 * The `NilNode` is always colored black to maintain Red-Black Tree properties.
 */
class NilNode extends RedBlackNode {

  /**
   * Constructs a new `NilNode` instance with a null book reference, indicating an
   * empty or placeholder node.
   * The node color is set to black, as specified by Red-Black Tree conventions.
   */
  NilNode() {
    super(null);
    this.nodeColor = LibraryActionConstant.RED_BLACK_BLACK_COLOR_NODE;
  }
}