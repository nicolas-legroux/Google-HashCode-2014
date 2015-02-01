package DataStructure;

import java.util.LinkedList;
import java.util.List;


//NOT THREAD SAFE

//TODO update vertex when printing
//Maybe delete vertex list

public class Solution {
	
	private List<Vertex> vertices;
	private SolutionArcList arcs;
	
	private int totaltime = 0;
	private int totaldistance = 0;
	
	
	public Solution(Vertex start) {
		arcs = new SolutionArcList();
		vertices = new LinkedList<Vertex>();
		vertices.add(start);
	}
	
	public void addVertex(Arc arc, Vertex v) {
		
		vertices.add(v);
		totaldistance += arc.getDistance();
		totaltime += arc.getDuration();
		
		arc.getStart().addNodeSolution(arcs.addArc(arc));
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
	
	public SolutionArcList getArcs() {
		return arcs;
	}
}
