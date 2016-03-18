//
// QUERY.JAVA
//
// Describes a query on an event database


class Query {
    
    public enum Command {
	FINDRANGE,
	FINDMOSTRECENT,
	DELETE
    };
    
    public Command command;
    public int year1;             // year argument 1
    public int year2;             // year argument 2
    
    // constructor
    public Query(Command icommand, int iyear)
    {
	command = icommand;
	year1 = iyear;
    }

    public Query(Command icommand, int iyear1, int iyear2)
    {
	command = icommand;
	year1 = iyear1;
	year2 = iyear2;
    }
    
    // print method
    public String toString()
    {
	switch (command)
	    {
	    case FINDRANGE:
		return "FINDRANGE " + String.valueOf(year1) + 
		    " " + String.valueOf(year2);
		
	    case FINDMOSTRECENT:
		return "FINDMOSTRECENT " + String.valueOf(year1);
		
	    case DELETE:
		return "DELETE " + String.valueOf(year1);
	    }
	
	return null;
    }
}
