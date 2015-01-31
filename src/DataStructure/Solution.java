package DataStructure;

import java.util.LinkedList;
import java.util.List;

public class Solution {
	
	private List<Arc> arcs;
	private int totaltime = 0;
	private int totaldistance = 0;
	
	
	public Solution() {
		arcs = new LinkedList<Arc>();
	}
	
	public void addArc(Arc arc) {
		arcs.add(arc);
		totaltime += arc.getDuration();
		totaltime += arc.getDuration();
	}
	
	public int getTotalTime() {
		return totaltime;
	}
	
	public int getTotalDistance() {
		return totaldistance;
	}
}
