//
// POINTREADER.JAVA
// Read a series of points from a file.  The first line of the file gives
// the number of points, while each subsequent line contains
// X- and Y-coordinates for one point.
//

import java.io.*;

public class PointReader {
    // Extract an integer from a string, which may contain whitespace
    static int parseInteger(String s)
    {
	int x = 0;
	
	try {
	    x = Integer.parseInt(s.trim());
	} catch (NumberFormatException e) {
	    System.out.println("Cannot parse integer from string \"" +
			       s + "\"\n" + e);
	}
	
	return x;
    }
    
    // Extract a pair of integers from a string, which may contain whitespace.
    // Treat the pair as x,y coordinates for a point.
    static XYPoint parsePoint(String s)
    {
	int x, y;
	
	s = s.trim().replace('\t', ' ');
	int firstSpaceIdx = s.indexOf(' ');
	String sx = s.substring(0, firstSpaceIdx);
	String sy = s.substring(firstSpaceIdx).trim();
	
	x = parseInteger(sx);
	y = parseInteger(sy);
	
	return new XYPoint(x,y);
    }
    
    // Read a collection of points from a file.
    public static XYPoint [] readXYPoints(String fileName)
    {
        XYPoint points [] = null;
	BufferedReader r = null;
	int nPoints;
	
	parsing: {
	    
	    // Create a reader for the file
	    //
	    try {
		InputStream is = new FileInputStream(fileName);
		r = new BufferedReader(new InputStreamReader(is));
	    }
	    catch (IOException e) {
		System.out.println("IOException while opening " +
				   fileName + "\n" + e);
		break parsing;
	    }
	    
	    try {
		String nextline = r.readLine();
		if (nextline == null) // end of file
		    {
			System.out.println("Error: point file " +
					   fileName + " is empty!\n");
			break parsing;
		    }
		
		nPoints = parseInteger(nextline);
		
	    } catch (IOException e) {
		System.out.println("Error getting # of points from " +
				   fileName + "\n" + e);
		break parsing;
	    }
	    
	    
	    points = new XYPoint [nPoints];
	    
	    for (int j = 0; j < nPoints; j++)
		{
		    try {
			String nextline = r.readLine();
			if (nextline == null) // end of file
			    {
				System.out.println("Error: point file ends " +
						   "after " + j + " points\n" +
						   " (expected " + nPoints + 
						   ")\n");
				break parsing;
			    }
			
			points[j] = parsePoint(nextline);
			
		    } catch (IOException e) {
			System.out.println("IOException while reading line " +
					   j+2 + " from " + fileName + "\n" +
					   e);
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
	
	return points;
    }
}
