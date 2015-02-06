package ArcComparator;

import java.util.Comparator;

import DataStructure.Arc;


//Chooses the arc that offers the best Distance/Time ratio, ie the best Speed
//o1 < o2 <==> speed(o1)>speed(o2)

public class ArcSpeedComparator implements Comparator<Arc> {
	@Override
	public int compare(Arc o1, Arc o2) {
		
		double r1 = (o1.getDistance()/o1.getDuration());
		double r2 = (o2.getDistance()/o2.getDuration());		
		
		return (int)(1000.0*(r2 - r1));
	}
}
