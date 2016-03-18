//
// VERTEX.JAVA
// Vertex class for the multigraph
//
// A Vertex is created with an integer identifier. Subclass 
// it if you want to store more complex info.
//
// To enumerate a vertex's adjacency list, you call its adj()
// method, which returns an iterator "ei" of type EdgeIterator for 
// the list. You can call ei.hasNext() to see if there is another
// edge available, and ei.next() to get it.
//

import java.util.*;

public class Vertex {
    
    // Constructor (takes an identifier)
    public Vertex(int id) 
    { 
	_id = id;
	neighbors = new ArrayList<Edge>();
    }
    
    
    // id()
    // Return our identifier.
    //
    public int id() { return _id; }
    
    
    // adj()
    // Return an iterator to list all of our edges.
    //
    public EdgeIterator adj() 
    {
	EdgeIterator a = new EdgeIterator(this);
	return a;
    }
    
    
    // addEdge()
    // Add an edge to our adjacency list.
    //
    public void addEdge(Edge e) 
    {
	neighbors.add(e);
    }
    
    
    // toString()
    // Identify us by our id.
    //
    public String toString() { return "" + _id; }
    
    
    public class EdgeIterator {
	
	public EdgeIterator(Vertex iv) 
	{
	    v = iv;
	    posn = 0;
	}
	
	public boolean hasNext() { return (posn < v.neighbors.size()); }
	public Edge next() { return v.neighbors.get(posn++); }
	
	private Vertex v;
	private int posn;
    }
    
    private int _id;
    private ArrayList<Edge> neighbors;
}
