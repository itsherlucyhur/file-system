import java.util.Comparator; 
import java.util.Iterator; 

public class NLNode <T> {
	// 3 instance variables
	private NLNode<T> parent; 
	private ListNodes<NLNode<T>> children; 		// list storing children of this node
	private T data; 
	
	// constructors
	public NLNode() {
		parent = null; 
		data = null; 
		this.children = new ListNodes<NLNode<T>>(); 	// initialize children
	}
	
	public NLNode (T d, NLNode<T> p) {
		this.data = d; 
		this.parent = p; 
		this.children = new ListNodes<NLNode<T>>(); 
	}
	
	// setter method for parent 
	public void setParent(NLNode<T> p) {
		this.parent = p;
	}
	
	// getter method for parent 
	public NLNode<T> getParent() {
		return parent; 
	}
	
	public void addChild(NLNode<T> newChild) {
		newChild.setParent(this);		 
		this.children.add(newChild);		// adds the newChild to the list of children 
	}
	
	public Iterator<NLNode<T>> getChildren() {
		return children.list.iterator(); 		// returns iterator containing children of this node
		
	}
	
	public Iterator<NLNode<T>> getChildren(Comparator<NLNode<T>> sorter) {
		Iterator<NLNode<T>> sortedChildren = children.sortedList(sorter); 		// sort the list of children of this node 
		return sortedChildren; 	// return iterator of children
	}
	
	public T getData() {
		return data; 
	}
	
	public void setData(T d) {
		this.data = d; 
	}
}
