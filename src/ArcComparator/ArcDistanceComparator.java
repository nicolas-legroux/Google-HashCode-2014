package ArcComparator;

import java.util.Comparator;

import DataStructure.Arc;

public class ArcDistanceComparator implements Comparator<Arc> {
	@Override
	//Get the arc with the longest distance
	public int compare(Arc o1, Arc o2) {
		return  o2.getDistance() - o1.getDistance();		
	}
}
