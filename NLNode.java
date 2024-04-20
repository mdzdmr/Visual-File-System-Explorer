// Importing Comparator and Iterator as required.
import java.util.Comparator;
import java.util.Iterator;
// This class represents the nodes of the non-linear data structure that
// will store the information about the file system.
public class NLNode <T> {

    // This class has 3 instance variables.
    private NLNode<T> parent; // A reference to the parent of this node.
    private ListNodes<NLNode<T>> children; // A reference to a list storing the children of this node.
    private T data; // A reference to the data object stored in this node.

    // Sets instance variables parent and data to null, while children is initialized
    // to an empty ListNodes<NLNode<T>> object
    public NLNode() {

        this.parent = null;
        this.data = null;
        this.children = new ListNodes<NLNode<T>>();

    }

    // Initializing instance variable children to an empty
    // ListNodes<NLNode<T>>, while data is set to d and parent to p.
    public NLNode (T d, NLNode<T> p) {

        this.children = new ListNodes<NLNode<T>>();
        this.data = d;
        this.parent = p;

    }

    // Sets the parent of this node to p.
    public void setParent(NLNode<T> p) { this.parent = p; }

    // Returns the parent of this node.
    public NLNode<T> getParent() { return parent;}

    //  Adding the given node newChild to the list of children of this node.
    public void addChild(NLNode<T> newChild) {

        this.setParent(this);
        this.children.add(newChild);

    }

    // Returns an iterator containing the children of this node.
    public Iterator<NLNode<T>> getChildren() { return this.children.getList(); }

    // Returns an iterator containing the children of this node sorted
    // in the order specified by the parameter sorter.
    public Iterator<NLNode<T>> getChildren(Comparator<NLNode<T>> sorter) { return children.sortedList(sorter); }

    // Returns the data stored in this node.
    public T getData() { return this.data; }

    // Stores in this node the data object referenced by d.
    public void setData(T d) { this.data = d; }

}
