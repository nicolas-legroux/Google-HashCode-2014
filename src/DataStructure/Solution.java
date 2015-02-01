package DataStructure;

import java.util.LinkedList;
import java.util.List;


//NOT THREAD SAFE

public class Solution {
	
	private List<Vertex> vertices;
	private List<Arc> arcs;
	
	private int totaltime = 0;
	private int totaldistance = 0;	
	
	private int id;
	
	public Solution(Vertex start) {
		arcs = new LinkedList<Arc>();
		vertices = new LinkedList<Vertex>();
		vertices.add(start);
		id=-1;
	}
	
	public Solution(Vertex start, int i){
		arcs = new LinkedList<Arc>();
		vertices = new LinkedList<Vertex>();
		vertices.add(start);
		id = i;
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
	
	public int getId(){
		return id;
	}
	
	public Vertex getLastVertex(){
		return vertices.get(vertices.size()-1);
	}
}
