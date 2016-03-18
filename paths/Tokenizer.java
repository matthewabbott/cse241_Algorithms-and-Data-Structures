//
// TOKENIZER.JAVA
// Given a string, break it into whitespace-delimited tokens.
// Return successive tokens on each nextToken() call.
//

class Tokenizer {
    
    static String s;
    static int posn;
        
    public Tokenizer(String is)
    {
	s = is;
	posn = 0;
    }
    
    // return the next token, or an empty string if no tokens remain
    public String nextToken()
    {
	int start = posn;
	
	// skip leading whitespace
	while (start < s.length() && Character.isWhitespace(s.charAt(start)))
	    start++;
	
	// move fwd to next whitespce character
	int end = start;
	while (end < s.length() && !Character.isWhitespace(s.charAt(end)))
	    end++;
	
	posn = end;
	return s.substring(start, end);
    }
    
    // return untokenized suffix of string
    public String rest()
    {
	return s.substring(posn);
    }
}
