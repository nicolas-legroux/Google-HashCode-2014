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
	
	private double latStart;
	private double lngStart;
	
	private boolean finished;
	
	public Solution(Vertex start, int i){
		arcs = new LinkedList<Arc>();
		vertices = new LinkedList<Vertex>();
		vertices.add(start);
		id = i;
		finished = false;
	}
	
	public void addVertex(Arc arc, Vertex v) {
		arcs.add(arc);
		vertices.add(v);
		totaldistance += arc.getDistance();
		totaltime += arc.getDuration();
		arc.setVisited(true);
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
	
	public boolean isFinished(){
		return finished;
	}
	
	public void setFinished(boolean finish){
		finished = finish;
	}
	
	public Vertex getLastVertex(){
		return vertices.get(vertices.size()-1);
	}
	
	public void setStartingPoint(double lat, double lng){
		this.latStart = lat;
		this.lngStart = lng;
	}
	
	public double getLatStart(){
		return latStart;		
	}
	
	public double getLngStart(){
		return lngStart;
	}	
}
