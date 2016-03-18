// EDGE.JAVA
// Edge class for the multigraph
//
// A (directed) edge contains four values: a unique identifier, a
// "from" vertex, a "to" vertex, and an integer weight.  Subclass Edge 
// if you want to store more complicated info.

 public class Edge {
     
     public Edge(int id, Vertex from, Vertex to, int weight)	
     {
	 _id = id;
	 
	 _from = from; _to = to; _weight = weight;
     }
     
     public int id() { return _id; }
     
     public Vertex from() { return _from; }
     
     public Vertex to() { return _to; }
     
     public int weight() { return _weight; }
     
     public String toString() { return 
				    "(" + _id + ": " + _from + " --> " + _to + 
				    ", " + _weight + ")"; }
     
     private Vertex _from, _to;
     private int _weight;
     private int _id;
 }
