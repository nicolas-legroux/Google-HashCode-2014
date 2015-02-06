package ArcComparator;

import java.util.Comparator;
import DataStructure.*;
import java.util.List;

//path1<path2 <==> speed along path1 > speed along path2
//Takes into account visited arcs
public class PathSpeedComparator implements Comparator<List<Arc>> {
	
	@Override
	public int compare(List<Arc> path1, List<Arc> path2) {
		int distancePath1 = 0;
		int durationPath1 = 0;
		int distancePath2 = 0;
		int durationPath2 = 0;
		
		for(Arc arc : path1){
			distancePath1 += arc.getDistance();
			durationPath1 += arc.getDuration();
		}
		
		for(Arc arc : path2){
			distancePath2 += arc.getDistance();
			durationPath2 += arc.getDuration();
		}
		
		double speed1 = distancePath1/durationPath1;
		double speed2 = distancePath2/durationPath2;
		
		return (int)(1000.0*(speed2-speed1));
	}
}
