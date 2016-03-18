public class ClosestPairNaive {
    
    public final static double INF = java.lang.Double.POSITIVE_INFINITY;
    
    //
    // findClosestPair()
    //
    // Given a collection of nPoints points, find and ***print***
    //  * the closest pair of points
    //  * the distance between them
    // in the form "NAIVE (x1, y1) (x2, y2) distance"
    //
    
    // INPUTS:
    //  - points sorted in nondecreasing order by X coordinate
    //  - points sorted in nondecreasing order by Y coordinate
    //
    
    public static void findClosestPair(XYPoint points[], boolean print)
    {
    	int nPoints = points.length;
    	double minDistSquared = INF;
    	XYPoint p1 = points[0];
    	XYPoint p2 = points[0];
    	for (int i = 0; i < nPoints - 1; i++) {

    		for (int j = i + 1; j < nPoints; j++) {

    			double distSquared = (double)(points[i].x - points[j].x)*(points[i].x - points[j].x) + (double)(points[i].y - points[j].y)*(points[i].y - points[j].y);
    			
    			if (distSquared < minDistSquared) {
    				minDistSquared = distSquared;
    				p1 = points[i];
    				p2 = points[j];
    			}
    		}
    	}
    	double minDist = Math.sqrt(minDistSquared);
    	
		if (print) {
			System.out.println("NAIVE " + p1.toString() + " " + p2.toString() + " " + minDist);
		}
    }
}
