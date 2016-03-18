//
// MAIN.JAVA
// CSE241 Skip List Lab Driver Code
//
// This driver code exercises your shortest path code.  See the
// lab document for command-line arguments.
//
// WARNING: ANY CHANGES YOU MAKE TO THIS FILE WILL BE DISCARDED
// BY THE AUTO-GRADER!  Make sure your code works with the
// unmodified original driver before you turn it in.
//

class Main {

    public static void main(String args[])
    {
	if (args.length == 2 && args[0].equals("pqtest"))
	    {
		System.out.println("pqtest");
		
		PQTest pqtest = new PQTest();
		pqtest.runTest(Integer.parseInt(args[1]));
		return;
	    }
	    
	if (args.length < 3)
	    {
		System.err.println("Syntax: Main <airports> <flights> <queries>");
		return;
	    }
	
	// read the input
	Input input = new Input();
	input.readAirports(args[0]);
	System.out.println("Read " + input.airports.length + " airports");
	input.readFlights(args[1]);
	System.out.println("Read " + input.flights.length + " flights");
	
	//
	// construct the input graph
	//
	
	Multigraph G = new Multigraph();
	
	// Allocate a vertex for every airport.  Logically, the jth 
	// vertex corresponds to the jth entry in input.airports[].
	//
	for (int j = 0; j < input.airports.length; j++)
	    {
		input.airports[j].id = j;
		
		Vertex v = new Vertex(j);
		G.addVertex(v);
	    }
	
	// While we allocate all the edges, set the id for each
	// edge to the index of the corresponding flight in the
	// input.flights[] array.
	//
	for (int k = 0; k < input.flights.length; k++)
	    {
		Input.Flight fl = input.flights[k];
		
		Vertex from = G.get(fl.startAirport.id);
		Vertex to   = G.get(fl.endAirport.id);
		
		int len = fl.endTime - fl.startTime;
		if (len < 0) len += 24 * 60; // next day
		
		Edge e = new Edge(k, from, to, len);
		
		from.addEdge(e);
	    }
	
	// Do the shortest-paths computation
	findPaths(input, G, args[2]);
    }
    
    
    //
    // findPaths()
    // Construct shortest paths from a specified starting
    // airport to every vertex of G, and then answer queries
    // asking for the shortest path (least total travel time)
    // from the start to other airports.
    //
    static void findPaths(Input input, Multigraph G, String queryFile)
    {
	Query queries[] = QueryReader.readQueries(queryFile);
        if (queries == null) // I/O error
            {
                return;
            }

        for (Query q : queries)
	    {
		// 
		// Fix the starting airport
		//
		System.out.println("> " + q);
		
		Input.Airport fromap = input.airportMap.get(q.from);
		
		if (fromap == null)
		    {
			System.err.println("Error: airport code " + q.from +
					   " is unknown");

			continue;
		    }
		
		//
		// YOUR CODE CALLED HERE -- find shortest paths from start
		// 
		ShortestPaths sp = new ShortestPaths(G, fromap.id,
						     input,
						     q.startTime);
		
		//
		// Field shortest-path queries to other airports
		//
		for (String to : q.tos)
		    {
			Input.Airport toap = input.airportMap.get(to);
			
			if (toap == null)
			    {
				System.err.println("Error: airport code " 
						   + to +
						   " is unknown");
				continue;
			    }
			
			if (toap == fromap)
			    {
				System.out.println("You're already there!");
				continue;
			    }
			
			int flightIds[] = sp.returnPath(toap.id);
			if (flightIds.length == 0)
			    System.out.println("No path to " + to);
			else
			    {
				printPath(input, flightIds);
				validatePath(input, q.startTime, flightIds);
			    }
			
			System.out.println("");
		    }
	    }
    }
    
    //
    // printPath()
    // print a path of edges (flight identifiers) between airports
    //
    static void printPath(Input input, int flightIds[])
    {
	System.out.println(input.flights[flightIds[0]].startAirport.name);
	
	for (int flightId : flightIds)
	    {
		Input.Flight fl = input.flights[flightId];
		
		System.out.println("---> " + fl.endAirport.name +
				   " (" + fl.name + ", " +
				   PrettyTime.toString(fl.startTime) + "-" + 
				   PrettyTime.toString(fl.endTime) + ", " +
				   PrettyTime.elapsedToString(fl.startTime, 
							      fl.endTime) +
				   ")");
	    }
    }
    
    
    // validatePath()
    // validate that a proposed path of flights is feasible (i.e., all
    // legs are connected), and compute and print the total time spent
    // flying (+ time spent waiting if startTime != 0).
    //
    static void validatePath(Input input, int startTime, int flightIds[])
    {
	int prevEndTime = startTime;
	Input.Flight prevFl = null;
	int totalTime = 0;
	
	for (int f = 0; f < flightIds.length; f++)
	    {
		Input.Flight fl = input.flights[flightIds[f]];
		
		// verify that the path of flights is connected
		if (f > 0)
		    {
			if (fl.startAirport.id != prevFl.endAirport.id)
			    {
				System.out.println("ERROR: path is not " +
						   "connected in G");
				break;
			    }
			
			if (startTime != 0 &&
			    PrettyTime.elapsed(prevEndTime, fl.startTime) < 45)
			    {
				System.out.println("ERROR: connection time " +
						   "is too short!");
				break;
			    }
		    }
		
		// add the time spent in flight.  If the start time is
		// non-zero, we are minimizing time including layovers,
		// and we need to account waiting time as well.
		if (startTime != 0)
		    totalTime += PrettyTime.elapsed(prevEndTime, fl.startTime);
		
		totalTime += PrettyTime.elapsed(fl.startTime, fl.endTime);
		
		prevFl = fl;
		prevEndTime = fl.endTime;
	    }
	
	System.out.println("@LENGTH = " + totalTime + " minutes");
    }
}
