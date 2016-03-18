//
// FileParser.java
//
// Parse a text file in as sane a way as Java will permit.
// We open the file in the constructor, then repeatedly
// call readWord() to get strings and readInt() to get integers
// until we reach eof.  Call isEof() to see if we're there yet.
//

import java.io.*;

class FileParser {
    
    StreamTokenizer tokenizer;
    boolean eof;
    
    //
    // constructor -- open the file
    //
    public FileParser(String fileName)
    {
	//
        // Open the input file and create a tokenizer for it.
        //
        try {
            InputStream is = new FileInputStream(fileName);
            
            Reader r = new BufferedReader(new InputStreamReader(is));
            
            tokenizer = new StreamTokenizer(r);
            tokenizer.eolIsSignificant(true);
        }
	catch (IOException e) {
	    System.out.println("IOException opening command file " + fileName
                               + "\n" + e);
        }
	
	eof = false;
    }
    
    //
    // isEof()
    // Return true iff we have received an EOF from the file
    //
    boolean isEof() { return eof; }
    

    //
    // readInt()
    // Read an integer from the file, skipping any intervening tokens
    // (result will be bogus if eof is reached)
    //
    public int readInt() 
    {
        scanTo(StreamTokenizer.TT_NUMBER);
	return ((int) tokenizer.nval);
    }
    
    //
    // readWord()
    // read a String from the file, skipping any intervening numeric
    // tokens (result will be bogus if eof is reached)
    //
    public String readWord()
    {
        scanTo(StreamTokenizer.TT_WORD);
        if (tokenizer.sval != null) 
	    return(tokenizer.sval);
        else 
	    return("" + ((char) tokenizer.ttype));
    }
    
    
    //
    // scanTo()
    // read the next token of type tokenType
    //
    void scanTo(int tokenType) 
    {
	// scans to a given token type:
	// TT_NUMBER or TT_WORD
	
	boolean found = false;
	try {
	    while (!found) 
		{
		    int ttype = tokenizer.nextToken();
		    if (ttype == tokenType) 
			{
			    found = true;
			}
		    else if (ttype == StreamTokenizer.TT_EOF) 
			{
			    found = true;
			    eof = true;
			}
		    else if (ttype == StreamTokenizer.TT_EOL) 
			{
			    // skip over end of line
			}
		    else if ((tokenType == StreamTokenizer.TT_WORD)
			     && (ttype != StreamTokenizer.TT_NUMBER)) 
			{
			    found = true;
			}
		}
	}
	catch (IOException e) {
	    System.out.println("IOException while scanning for input.");
	}
    }
}
