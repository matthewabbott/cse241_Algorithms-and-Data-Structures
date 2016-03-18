//
// QUERY.JAVA
//
// Describes a flight query 

class Query {
    
    public int startTime;
    public String from;
    public String[] tos;
    
    // print method
    public String toString()
    {
	StringBuilder sb = new StringBuilder();
	
	if (startTime != 0)
	    sb.append("@" + PrettyTime.toString(startTime) + " ");
	
	if (tos.length == 1)
	    sb.append(from + " to " + tos[0] + "?");
	else
	    {
		sb.append(from + " to {" + tos[0]);
		
		for (int j = 1; j < tos.length; j++)
		    sb.append(", " + tos[j]);
		
		sb.append("}?");
	    }
	
	return sb.toString();
    }
}
