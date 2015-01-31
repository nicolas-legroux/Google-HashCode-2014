package DataStructure;

import java.util.LinkedList;
import java.util.List;


//NOT THREAD SAFE

public class Solution {
	
	private List<Vertex> vertices;
	private List<Arc> arcs;
	
	private int totaltime = 0;
	private int totaldistance = 0;
	
	
	public Solution(Vertex start) {
		arcs = new LinkedList<Arc>();
		vertices = new LinkedList<Vertex>();
		vertices.add(start);
	}
	
	public void addVertex(Arc arc, Vertex v) {
		arcs.add(arc);
		vertices.add(v);
		totaldistance += arc.getDistance();
		totaltime += arc.getDuration();
	}
	
	public List<Vertex> getVertices() {
		return vertices;
	}
	
	public int getTotalTime() {
		return totaltime;
	}
	
	public int getTotalDistance() {
		return totaldistance;
	}
}
