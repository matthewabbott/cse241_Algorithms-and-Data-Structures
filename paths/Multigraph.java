// MULTIGRAPH.JAVA
// Multigraph class
//
// A Multigraph contains a collection of Vertex objects, each of which
// maintains its own adjacency list of edges (see the Vertex and Edge 
// classes).
//
// To enumerate the vertices in the Multigraph, you can simply ask for
// the number of vertices n, then call get() successively on 0 .. n-1.
//

import java.util.*;

public class Multigraph {
    
    // Constructor
    public Multigraph() 
    {
	vertices = new ArrayList<Vertex>();
    }
    
    
    // nVertices()
    // Return number of vertices in graph.
    // 
    public int nVertices() 
    {
	return vertices.size();
    }
    
    
    // get()
    // Return a specified vertex
    //
    public Vertex get(int id)
    {
	return vertices.get(id);
    }
        
    // addVertex()
    // Add a vertex to the graph.
    //
    public void addVertex(Vertex v) 
    {
	vertices.add(v);
    }
    
    private ArrayList<Vertex> vertices;
}
