public class Handle {
	
	public int index;
	public boolean valid;
	
	public Handle(int index) {
		valid = true;
		this.index = index;
	}
	
	public int leftChildIndex() {
		return index * 2;
	}
	
	public int rightChildIndex() {
		return index * 2 + 1;
	}
	
	public int parentIndex() {
		return index / 2;
	}
}
