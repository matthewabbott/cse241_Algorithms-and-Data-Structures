//
// QUERYREADER.JAVA
// Parse a file of queries against an event database.  Return an array
// of all queries as Query objects.
//
// A query is one of the following:
//    F <year1> <year2>  
//        return all events between <year1> and <year2>, inclusive
//    M <year>
//        return all events in the greatest year <= <year>
//    D <year>
//        remove all events with year <year> from the database
// Here, <year> is given by an integer value (negative numbers for BC/BCE)
// and <word> is a string not containing whitespace. Fields are separated
// by whitespace.
//

import java.util.*;
import java.io.*;

class QueryReader {
    
    //
    // readQueries()
    // Given the name of a query file, read all the queries in
    // that file and return them as an array of Query objects.
    // If any error is encountered parsing the file, return a
    // NULL array.
    //
    public static Query[] readQueries(String fileName)
    {
	// This array temporarily holds the queries as they are read.
	ArrayList<Query> a = new ArrayList<Query>();
	BufferedReader r = null;
	
	parsing: {
	    
	    try {
		InputStream is = new FileInputStream(fileName);
		
		r = new BufferedReader(new InputStreamReader(is));
	    }
	    catch (IOException e) {
		System.out.println("IOException opening query file " + fileName
				   + "\n" + e);
		break parsing;
	    }
	    
	    // Read each query, one per line.
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
				    Query q = parseQuery(ts);
				    if (q == null) return null;
				    a.add(q);
				}
			}
		    }
		    catch (IOException ex) {
			System.out.println("IOException reading query file" +
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
	
	Query queries[] = new Query [a.size()];
	return a.toArray(queries);
    }
    
    
    //
    // parseQuery()
    // Parse a single query from a line read from the query file.
    // Return null if we cannot parse the query.
    //
    static Query parseQuery(String queryString)	
    {
	Tokenizer t = new Tokenizer(queryString);
	
	String cmdString = t.nextToken().toUpperCase();
	int year1, year2;
	
	try {
	    switch(cmdString.charAt(0))
		{
		case 'F':
		    year1 = Integer.valueOf(t.nextToken());
		    year2 = Integer.valueOf(t.nextToken());
		    return new Query(Query.Command.FINDRANGE, year1, year2);
		    
		case 'M':
		    year1 = Integer.valueOf(t.nextToken());
		    return new Query(Query.Command.FINDMOSTRECENT, year1);
		    
		case 'D':
		    year1 = Integer.valueOf(t.nextToken());
		    return new Query(Query.Command.DELETE, year1);
		}
	}
	catch (NumberFormatException ex) {
	    System.out.println("Could not parse year from query '" +
			       queryString + "'");
	    return null;
	}
	
	// default fall-through
	System.out.println("Unknown query type: '" +
			   queryString + "'");
	return null;
    }
}
