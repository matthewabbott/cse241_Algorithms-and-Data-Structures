
public class Node<T> {

	int key;
	T value;
	Handle handle;
	
	public Node(int key, T value, Handle handle) {
		this.key = key;
		this.value = value;
		this.handle = handle;
	}
	
	public String toString() {
		return "" + key + ", " + value;
	}
	
}
