//
// QUERYREADER.JAVA
// Parse a file of flight queries.  Return an array of all queries as
// Query objects.
//
// A query contains a start airport followed by one or more end airports,
// all separated by whitespace on a single line.
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
		System.out.println("IOException opening query file " 
				   + fileName + "\n" + e);
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
	ArrayList<String> tos = new ArrayList<String>();
	Query q = new Query();
	
	try {
	    q.startTime = PrettyTime.toTime(Integer.valueOf(t.nextToken()));
	}
	catch (NumberFormatException ex) {
            System.out.println("Could not parse start time from query '" +
                               queryString + "'");
            return null;
        }
	
	q.from = t.nextToken().toUpperCase();
	
	if (q.from.equals(""))
	    {
		System.out.println("No 'from' airport in query '" +
				   queryString + "'");
		return null;
	    }
	
	while (true)
	    {
		String s = t.nextToken().toUpperCase();
		if (s.equals(""))
		    break;
		else
		    tos.add(s);
	    }
	
	if (tos.size() == 0)
	    {
		System.out.println("No 'to' airport in query '" +
				   queryString + "'");
		return null;
	    }
	else
	    {
		q.tos = new String [tos.size()];
		tos.toArray(q.tos);
		return q;
	    }
    }
}
