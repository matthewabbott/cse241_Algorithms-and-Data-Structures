import java.util.*;

class XComparator implements Comparator<XYPoint> {
    public int compare(XYPoint p1, XYPoint p2)
    {
	return (p1.isLeftOf(p2) ? -1 : (p2.isLeftOf(p1) ? 1 : 0));
    }
}
