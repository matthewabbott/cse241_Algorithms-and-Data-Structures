//
// MAIN.JAVA
// CSE241 Skip List Lab Driver Code
//
// This driver code exercises your EventList collection type.
//
// The driver's command line syntax is:
//
//    java Main <event file> <query file>
// 
// where <event file> is a database of events, and <query file> is a file
// of queries against the database.
//
// WARNING: ANY CHANGES YOU MAKE TO THIS FILE WILL BE DISCARDED
// BY THE AUTO-GRADER!  Make sure your code works with the
// unmodified original driver before you turn it in.

import java.util.*;

public class Main {
    
    static class EventComparator implements Comparator<Event> {
	
	public int compare(Event e1, Event e2)
	{
	    if (e1.year < e2.year)
		return -1;
	    else if (e1.year > e2.year)
		return 1;
	    else
		{
		    return (e1.description.compareTo(e2.description));
		}
	}
    }
    
    public static void main(String args[])
    {
	if (args.length < 2)
	    {
		System.out.println("Syntax: Main <event file> <query file>");
		return;
	    }
	
	// NOTE: replace arguments with explicit filename if
	// you can't pass arguments on the command line.
	//
	Event events[] = EventReader.readEvents(args[0]);
	if (events == null) // I/O error
	    {
		return;
	    }
	
	EventList list = new EventList();
	for (Event e : events) 
	    list.insert(e);
	
        RunTest(list, args[1]);
    }
    
    //
    // RunTest()
    // parse all the events from a query file and apply them to an event 
    // list in order. Print each query and its output.
    //
    static void RunTest(EventList list, String queryFile)
    {
	EventComparator ec = new EventComparator();
	
	Query queries[] = QueryReader.readQueries(queryFile);
	if (queries == null) // I/O error
	    {
		return;
	    }
	
	for (Query q : queries)
	    {
		Event [] el;
		
		System.out.println("> " + q);
		
		switch (q.command)
		    {
		    case FINDRANGE:
			el = list.findRange(q.year1, q.year2);
			if (el == null)
			    System.out.println("No events found.");
			else
			    {
				Arrays.sort(el, ec);
				for (Event e : el)
				    System.out.println(e.toString());
			    }
			break;
			
		    case FINDMOSTRECENT:
			el = list.findMostRecent(q.year1);
			if (el == null)
			    System.out.println("No events found.");
			else
			    {
				Arrays.sort(el, ec);
				for (Event e : el)
				    System.out.println(e.toString());
			    }
			break;
			
		    case DELETE:
			list.remove(q.year1);
			break;
		    }
		
		// terminate with whitespace
		System.out.println("");
	    }
    }
}
