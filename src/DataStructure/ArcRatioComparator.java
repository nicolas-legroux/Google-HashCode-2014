package DataStructure;

import java.util.Comparator;

public class ArcRatioComparator implements Comparator<Arc> {
	@Override
	public int compare(Arc o1, Arc o2) {
		
		float r1 = o1.getDistance()/o1.getDuration();
		float r2 = o2.getDistance()/o2.getDuration();
		
		return (int)(r2 - r1);
	}

}
