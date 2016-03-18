import java.util.Arrays;

public class ClosestPairDC {
    
    public final static double INF = java.lang.Double.POSITIVE_INFINITY;

    //
    // findClosestPair()
    //
    // Given a collection of nPoints points, find and ***print***
    //  * the closest pair of points
    //  * the distance between them
    // in the form "DC (x1, y1) (x2, y2) distance"
    //
    
    // INPUTS:
    //  - points sorted in nondecreasing order by X coordinate
    //  - points sorted in nondecreasing order by Y coordinate
    //
    
    public static void findClosestPair(XYPoint pointsByX[], 
				       XYPoint pointsByY[],
				       boolean print)
    {
    	int nPoints = pointsByX.length;
    	
    	double[] minDistCoords = closestDistance(pointsByX, pointsByY, nPoints);
    	double minDist = Math.sqrt(minDistCoords[0]);
    	XYPoint p1 = new XYPoint((int)minDistCoords[1], (int)minDistCoords[2]);
    	XYPoint p2 = new XYPoint((int)minDistCoords[3], (int)minDistCoords[4]);
    	if (print)
    		System.out.println("DC " + p1.toString() + " " + p2.toString() + " " + minDist);
    }
    
    /*
     * returns an array of 5 doubles such that index 0 is the the minimum distance between points in the given array, 
     * index 1 and 2 are the x and y coordinates of the first point, and index 3 and 4 the x and y coordinates of the second.
     */
    private static double[] closestDistance(XYPoint pointsByX[], XYPoint pointsByY[], int length) {
    	if (length == 1) {
    		return new double[] {INF, (double)pointsByX[0].x, (double)pointsByX[0].y, (double)pointsByX[0].x, (double)pointsByX[0].y};
    	} else if (length == 2) {
    		return new double[] {distanceSquared(pointsByX[0], pointsByX[1]), (double)pointsByX[0].x, (double)pointsByX[0].y, (double)pointsByX[1].x, (double)pointsByX[1].y};
    	}
    	
    	XYPoint leftByX[] = Arrays.copyOfRange(pointsByX, 0, length/2);
    	XYPoint rightByX[] = Arrays.copyOfRange(pointsByX, length/2, length);
    	
    	XYPoint leftByY[] = new XYPoint[length/2];
    	XYPoint rightByY[] = new XYPoint[length - length/2];
    	
    	int j = 0;
    	int k = 0;
    	// iterate through the array already sorted by Y and separate it into two arrays (both still sorted by Y) where one array has only the points left of the middle point and the other does not
    	for (int i = 0; i < length; i++) { 	
    		if (pointsByY[i].isLeftOf(pointsByX[length/2])) {
    			leftByY[j] = pointsByY[i];
    			j++;
    		} else  {
    			rightByY[k] = pointsByY[i];
    			k++;
    		}
    	}
    	
    	double[] leftDistArray = closestDistance(leftByX, leftByY, length/2);
    	double[] rightDistArray = closestDistance(rightByX, rightByY, length - length/2);
    	double lrDistArray[] = new double[5];
    	if (leftDistArray[0] < rightDistArray[0]) {
    		lrDistArray = leftDistArray;
    	} else {
    		lrDistArray = rightDistArray;
    	}
    	// I am leaving distances squared to avoid square roots until the shortest distance has been found
    	
    	return combine(pointsByY, pointsByX[length/2], lrDistArray);
    }
    
    private static double[] combine(XYPoint pointsByY[], XYPoint midPoint, double[] lrDistArray) {
    	
    	int yStripLength = 0;
    	XYPoint yStrip[] = new XYPoint[pointsByY.length];
    	for (int i = 0; i < pointsByY.length; i++) {
    		if (Math.pow(pointsByY[i].x - midPoint.x, 2) < lrDistArray[0]) { 
    			yStrip[yStripLength] = pointsByY[i];
    			yStripLength++;
    		}
    	}
    	
    	double minDistSquared = lrDistArray[0];
    	// yStrip only has meaningful values up to index yStripLength - 1, since I created it with the maximum possible length (pointsByY's length) in mind and never shortened it, since doing so is unnecessary given the presence of yStripLength
    		// yStripLength is not the actual length of the array yStrip, if that wasn't clear, just the number of meaningful values in the array.
    	for (int j = 0; j <= yStripLength - 2; j++) {

    		for (int k = j + 1; k <= yStripLength - 1 && Math.pow(yStrip[k].y - yStrip[j].y, 2) < minDistSquared; k++) {
    			double d = distanceSquared(yStrip[j], yStrip[k]);
    			if (d < minDistSquared) {
    				lrDistArray[0] = d;
    				lrDistArray[1] = (double)yStrip[j].x;
    				lrDistArray[2] = (double)yStrip[j].y;
    				lrDistArray[3] = (double)yStrip[k].x;
    				lrDistArray[4] = (double)yStrip[k].y;
    			}
    		}
    	}
    	
    	return lrDistArray;
    }
    
    private static double distanceSquared(XYPoint a, XYPoint b) {
    	return (double)(a.x - b.x)*(a.x - b.x) + (double)(a.y - b.y)*(a.y - b.y); // casting values as doubles to avoid integer overflow when multiplying. Since the the final value will be returned as a double anyways the potential loss of precision is not a big deal.
    }
}
