import java.util.ArrayList;

//
// PRIORITYQUEUE.JAVA
// A priority queue class supporting sundry operations needed for
// Dijkstra's algorithm.
//

class PriorityQueue<T> {
    
	private ArrayList<Node<T>> list;
	private int length = 0;
	
    // constructor
    //
    public PriorityQueue()
    {
    	list = new ArrayList<Node<T>>();
    }
    
    // Return true iff the queue is empty.
    //
    public boolean isEmpty()
    {
    	return length == 0;
    }
    
    // Insert a pair (key, value) into the queue, and return
    // a Handle to this pair so that we can find it later in
    // constant time.
    //
    Handle insert(int key, T value)
    {
    	length++;
    	Handle handle = new Handle(length - 1);
        Node<T> node = new Node<T>(key, value, handle);
        list.add(node);
        
        int currentIndex = handle.index;
        int parentIndex = handle.parentIndex();
 
        while (list.get(currentIndex).key < list.get(parentIndex).key)
        {
            swap(list.get(currentIndex), list.get(parentIndex));
            currentIndex = parentIndex;
            parentIndex = handle.parentIndex();
        }
		
		return handle;
    }
    
    // Return the smallest key in the queue.
    //
    public int min()
    {
    	return list.get(0).key;
    }
    
    // Extract the (key, value) pair associated with the smallest
    // key in the queue and return its "value" object.
    //
    public T extractMin()
    {
    	if (length == 0) {
    		return null;
    	}
    	
    	T value = list.get(0).value;
    	swap(list.get(0), list.get(length-1));
    	
    	list.get(length-1).handle.valid = false;
    	list.remove(length-1);
    	length--;
    	
    	if (!list.isEmpty()) { 
    		fixBrokenHeap(list.get(0).handle); //after removing the min element, we need to send the element that we brought up to the top back down as far as it ought to go
    	}
    	
    	return value;
    }
    
    private void fixBrokenHeap(Handle h) {
    	if (!(h.index >= length/2 && h.index < length)) { //stop if the element is now a leaf
    		
    		int key = handleGetKey(h);
    		int leftChildKey = list.get(h.leftChildIndex()).key;
        	int rightChildKey = list.get(h.rightChildIndex()).key;
        	
        	if(leftChildKey <= rightChildKey){
        		if(leftChildKey < key){
        			Node<T> current = list.get(h.index);
            		swap(current, list.get(h.leftChildIndex()));
            		fixBrokenHeap(current.handle);
            	}
        	} else {
        		if(rightChildKey < key){
        			Node<T> current = list.get(h.index);
            		swap(current, list.get(h.rightChildIndex()));
            		fixBrokenHeap(current.handle);
            	}
        	}
    	}
    }
    
    // Look at the (key, value) pair referenced by Handle h.
    // If that pair is no longer in the queue, or its key
    // is <= newkey, do nothing and return false.  Otherwise,
    // replace "key" by "newkey", fixup the queue, and return
    // true.
    //
    public boolean decreaseKey(Handle h, int newkey)
    {
    	if(!h.valid || handleGetKey(h) <= newkey) {
    		return false;
    	}
    
    	list.get(h.index).key = newkey;
    	while(h.index > 0 && list.get(h.parentIndex()).key > handleGetKey(h)){
    		
    		swap(list.get(h.parentIndex()), list.get(h.index));
    		
    	}
    	return true;
    	
    }
    
    private void swap(Node<T> n1, Node<T> n2) {
    	list.set(n2.handle.index, n1);
    	list.set(n1.handle.index, n2);
    	
    	int temp = n1.handle.index;
    	n1.handle.index = n2.handle.index;
    	n2.handle.index = temp;
    }
    
    
    // Get the key of the (key, value) pair associated with a 
    // given Handle. (This result is undefined if the handle no longer
    // refers to a pair in the queue.)
    //
    public int handleGetKey(Handle h)
    {
    	if (h.valid) {
    		return list.get(h.index).key;
    	} else {
    		throw new IllegalArgumentException();
    	}
    }

    // Get the value object of the (key, value) pair associated with a 
    // given Handle. (This result is undefined if the handle no longer
    // refers to a pair in the queue.)
    //
    public T handleGetValue(Handle h)
    {
    	if (h.valid) {
    		return list.get(h.index).value;
    	} else {
    		throw new IllegalArgumentException();
    	}
    }
    
    // Print every element of the queue in the order in which it appears
    // in the implementation (i.e. the array representing the heap).
    public String toString()
    {
    	return list.toString();
    }
}
