//
// XYPOINT.JAVA
// Definition for the XYPoint class.
//
// The variable counter is a static integer that keeps track of the
// number of points objects.  Each point has an x-coord, y-coord, and
// a globally unique value num (for convenience in debugging).
//
public class XYPoint {
    static int counter = 0;
    
    public int x;             // x-coordinate
    public int y;             // y-coordinate

    int num;                  // unique identifier for isLeftOf()/isBelow()
    
    
    XYPoint()
    {                         // constructor that takes no arguments
	x = 0;                // initializes x-coord and y-coord to 0
	y = 0;
	num = counter++;
    }
   
   XYPoint(int xcoord, int ycoord)
    {  
	x = xcoord;              // initializes x-coord and y-coord to 
	y = ycoord;              // given values and the
	num = counter++;
    }
    
    public double dist(XYPoint other)
    { 
	// computes Euclidean distance to p
	double deltax = (x - other.x);    
	double deltay = (y - other.y);                
	return Math.sqrt(deltax*deltax + deltay*deltay);   
    }
    
    public boolean isLeftOf(XYPoint other)
    {
	return (x < other.x || (x == other.x && num < other.num));
    }
    
    public boolean isBelow(XYPoint other)
    {
	return (y < other.y || (y == other.y && num < other.num));
    }
    
    public String toString()
    {
	return ("(" + x + "," + y + ")");
    }
}
