//
// EVENTREADER.JAVA
// Parse a file of historical events.  Return an array
// of all parsed events as Event objects.
//
// An event file contains a list of events, one per line.  Each
// event has the form 
//
//      <year> <description>
//
// where <year> is given by an integer value (negative numbers for BC/BCE)
// and <description> is an arbitrary string (not quoted).  The two fields 
// are separated by whitespace.
//

import java.util.*;
import java.io.*;

class EventReader {

    
    //
    // readEvents()
    // Given the name of an event file, read all the events in
    // that file and return them as an array of Event objects.
    // If any error is encountered parsing the file, return a
    // NULL array.
    //
    public static Event[] readEvents(String fileName)
    {
	// This array temporarily holds the events as they are read.
	ArrayList<Event> a = new ArrayList<Event>();
	BufferedReader r = null;
	
	parsing: {
	    
	    try {
		InputStream is = new FileInputStream(fileName);
		
		r = new BufferedReader(new InputStreamReader(is));
	    }
	    catch (IOException e) {
		System.out.println("IOException opening event file " + fileName
				   + "\n" + e);
		break parsing;
	    }
	    
	    // Read each event, one per line.
	    while (true)
		{
		    try {
			String nextLine = r.readLine();
			if (nextLine == null) // EOF
			    break;
			else
			    {
				String ts = nextLine.trim();
				if (ts.equals("")) // skip blank lines
				    continue;
				else
				    {
					Event e = parseEvent(ts);
					if (e == null) 
					    break parsing;
					a.add(e);
				    }
			    }
		    }
		    catch (IOException ex) {
			System.out.println("IOException reading event file" +
					   "\n" + ex);
			break parsing;
		    }
		    
		}
	}
	
	
        //
        // final cleanup
        //
	
        if (r != null)
            {
                try {
                    r.close();
                } catch (IOException e) {
                    // hukairs
                }
            }
	
	Event events[] = new Event [a.size()];
	return a.toArray(events);
    }
    
    
    //
    // parseEvent()
    // Parse a single event from a line read from the event file.
    // Return null if we cannot parse the event.
    //
    static Event parseEvent(String eventString)
    {
	Tokenizer t = new Tokenizer(eventString);
	int year;
	
	try {
	    year = Integer.valueOf(t.nextToken());
	} catch (NumberFormatException ex) {
	    System.out.println("Could not parse year from event '" +
			       eventString + "'");
	    return null;
	}
	
	return new Event(year, t.rest().trim());
    }
}
