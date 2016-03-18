//
// MAIN.JAVA
// Main driver code for CSE 241 Closest Pair Lab.
//
// WARNING: ANY CHANGES YOU MAKE TO THIS FILE WILL BE DISCARDED BY THE
// AUTO-GRADER!  Make sure your code works with the unmodified
// original driver before you turn it in.  (You may wish to modify
// your local copy to do the timing experiments requested by the lab.)

import java.util.*;

public class Main {
    
    static final long seed = 87654321;
    
    public static void main(String args[])
    {
	PRNG prng = new PRNG(seed); // seed random number generator
	XYPoint points [];
	int nPoints = 0;
	String fileName;
	
	if (args.length >= 1)
	    {
		fileName = args[0];
	    }
	else
	    {
		System.out.println("Syntax: Main [ <filename> | @<numPoints> ]");
		return;
	    }
			
	
	// A filename argument of the form '@x', where x is a non-negative
	// integer, allocates x random points.  Any other argument is
	// assumed to be a file from which points are read.
	
	if (fileName.charAt(0) != '@')
	    {
		points = PointReader.readXYPoints(fileName);
		nPoints = points.length;
	    }
	else
	    {
		nPoints = Integer.parseInt(fileName.substring(1));
		points = null;
	    }
	
	if (nPoints < 2)
	    {
		System.err.println("ERROR: need at least two points");
		return;
	    }
	
	// Generate a set of points if one was not read.
	// When timing, call genPointsAtRandom() as shown
	// each time you want a new set of points.
	if (points == null)
	    points = genPointsAtRandom(nPoints, prng);
	
	// run the DC algorithm
	{
		
	    XComparator lessThanX = new XComparator();
	    YComparator lessThanY = new YComparator();
	    
	    /////////////////////////////////////////////////////////////////
	    // DC CLOSEST-PAIR ALGORITHM STARTS HERE	
	    	
	    Date startTime = new Date();
	    
	    // Ensure sorting precondition for divide-and-conquer CP
	    // algorithm.  NB: you should *not* have to call sort() in
	    // your own code!
	    
	    // The algorithm expects two arrays containing the same points.
	    XYPoint pointsByX [] = Arrays.copyOf(points, points.length);
	    XYPoint pointsByY [] = Arrays.copyOf(points, points.length);
	    
	    Arrays.sort(pointsByX, lessThanX); // sort by x-coord
	    Arrays.sort(pointsByY, lessThanY); // sort by y-coord
	    	    
	    ClosestPairDC.findClosestPair(pointsByX, pointsByY, true);
	    
	    Date endTime = new Date();
	    
	    // DC CLOSEST-PAIR ALGORITHM ENDS HERE
	    /////////////////////////////////////////////////////////////////
	    
	    long elapsedTime = endTime.getTime() - startTime.getTime();
	    
	    System.out.println("For n = " + points.length + 
			     ", the elapsed time is " +
			     elapsedTime + " milliseconds.");
	    System.out.println("");
	    
	}
	
	
	// run the naive algorithm
	{
	    Date startTime = new Date();
	    
	    ClosestPairNaive.findClosestPair(points, true);
	    
	    Date endTime = new Date();
	    
	    long elapsedTime = endTime.getTime() - startTime.getTime();
	    
	    System.out.println("For n = " + points.length + 
			     ", the naive elapsed time is " +
			     elapsedTime + " milliseconds.");
	    System.out.println("");
	}
    }
    
    
    //
    // genPointsAtRandom()
    // Generate an array of specified size containing
    // points with coordinates chosen at random, using
    // the specified random sequence generator.
    //
    
    static XYPoint[] genPointsAtRandom(int nPoints, 
				       PRNG prng) 
    {
	XYPoint points[] = new XYPoint [nPoints];
	
	double x = 0.0;
	double y = 0.0;
	
	double step = Math.sqrt(nPoints);
	
	for (int j = 0; j < nPoints; j++) 
	    {
		// jitter next point's X coordinate
		x += 10000.0 * (prng.nextDouble() - 0.5);
		
		// move the Y coordinate a random amount up,
		// while keeping it within limits [0 .. nPoints)
		y = (y + step * prng.nextDouble()) % nPoints;
		
		points[j] = new XYPoint((int) Math.round(x), 
					(int) Math.round(y));
	    }
	
	return points;
    }
}
