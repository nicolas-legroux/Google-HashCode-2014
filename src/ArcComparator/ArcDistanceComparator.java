package ArcComparator;

import java.util.Comparator;

import DataStructure.Arc;

public class ArcDistanceComparator implements Comparator<Arc> {
	@Override
	//o1<o2 <==> o1 is longer than o2
	public int compare(Arc o1, Arc o2) {
		return  o2.getDistance() - o1.getDistance();		
	}
}
