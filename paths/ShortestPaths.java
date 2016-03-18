import java.util.ArrayList;


//
// SHORTESTPATHS.JAVA
// Compute shortest paths in a graph.
//
// Your constructor should compute the actual shortest paths and
// maintain all the information needed to reconstruct them.  The
// returnPath() function should use this information to return the
// appropriate path of edge ID's from the start to the given end.
//
// Note that the start and end ID's should be mapped to vertices using
// the graph's get() function.
//
// You can ignore the input and startTime arguments to the constructor
// unless you are doing the extra credit.
//
class ShortestPaths {
	
    private int[] shortestPaths;
    private Edge[] edges;
    private Handle[] handles;
    private PriorityQueue<Vertex> queue;
    
    private final int LAYOVER_DELAY = 45;
    
    //
    // constructor
    //
    public ShortestPaths(Multigraph G, int startId, 
			 Input input, int startTime) 
    {
    	if (startTime == 0) { //non layover aware
	    	shortestPaths = new int[G.nVertices()];
	    	queue = new PriorityQueue<Vertex>();
	    	handles = new Handle[G.nVertices()];
	    	edges = new Edge[G.nVertices()];
	    	
	    	for(int i = 0; i < G.nVertices(); i++) {
	    		shortestPaths[i] = Integer.MAX_VALUE;
				Vertex v = G.get(i);
				handles[i] = queue.insert(Integer.MAX_VALUE, v);
	    	}
	    	
	    	shortestPaths[startId] = 0;
	    	queue.decreaseKey(handles[startId], 0);
	    	
	    	while(!queue.isEmpty()) {
	    		Vertex from = queue.extractMin();
	    		if(shortestPaths[from.id()] == Integer.MAX_VALUE) {
	    			break;
	    		}
	    		
	    		Vertex.EdgeIterator t = from.adj();
	    		
	    		while(t.hasNext()) {
	    			Edge e = t.next();
	    			int weight = e.weight();
	    			
	    			Vertex to = e.to();
	    			
	    			Handle h = handles[e.to().id()];
	    			
	    			if(queue.decreaseKey(h, shortestPaths[from.id()] + weight)) {
	    				
	    				shortestPaths[to.id()] = shortestPaths[from.id()] + weight;
	    				edges[to.id()] = e;
	    			}
	    		}
	    	}
    	} else { // layover aware
    		
    		shortestPaths = new int[G.nVertices()];
	    	queue = new PriorityQueue<Vertex>();
	    	handles = new Handle[G.nVertices()];
	    	edges = new Edge[G.nVertices()];
	    	
	    	for(int i = 0; i < G.nVertices(); i++) {
	    		shortestPaths[i] = Integer.MAX_VALUE;
				Vertex v = G.get(i);
				handles[i] = queue.insert(Integer.MAX_VALUE, v);
	    	}
	    	
	    	shortestPaths[startId] = startTime; //can only leave the airport at times after the start time
	    	queue.decreaseKey(handles[startId], 0);
	    	
	    	while(!queue.isEmpty()) {
	    		Vertex from = queue.extractMin();
	    		if(shortestPaths[from.id()] == Integer.MAX_VALUE) {
	    			break;
	    		}
	    		
	    		Vertex.EdgeIterator t = from.adj();
	    		
	    		while(t.hasNext()) {
	    			Edge e = t.next();
	    			int weight = e.weight() + getLayoverTime(shortestPaths[from.id()], input.flights[e.id()]);
	    			
	    			Vertex to = e.to();
	    			
	    			Handle h = handles[e.to().id()];
	    			
	    			if(queue.decreaseKey(h, shortestPaths[from.id()] + weight)) {
	    				
	    				shortestPaths[to.id()] = shortestPaths[from.id()] + weight;
	    				
	    				edges[to.id()] = e;
	    			}
	    		}
	    	}
	    	
	    	shortestPaths[startId] = 0; //return source distance from itself to 0 since the return path method uses 0 as the end point for path distances (and it doesn't seem reasonable to pass startTime to it)
    	}
    }
    
    private int getLayoverTime(int currentTime, Input.Flight flight) {
    	return LAYOVER_DELAY + ((flight.startTime - currentTime - LAYOVER_DELAY + 2880) % 1440);
    }
    
    //
    // returnPath()
    // Return an array containing a list of edge ID's forming
    // a shortest path from the start vertex to the specified
    // end vertex.
    //
    public int [] returnPath(int endId) 
    {
    	ArrayList<Integer> edgeIds = new ArrayList<Integer>();
    	
    	int fromId = endId;
    	while(shortestPaths[fromId] != 0) {
    		
    		Edge e = edges[fromId];
    		edgeIds.add(0, e.id());
    		
    		fromId = e.from().id();
    	}
    		
    	int[] edgeIdArray = new int [edgeIds.size()];
    	for(int i = 0; i < edgeIds.size(); i++) {
    		edgeIdArray[i] = edgeIds.get(i);
    	}
    	
    	return edgeIdArray;
    }
}
