package DataStructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Vertex {

	private double lat;
	private double lng;
	private int id;
	private boolean markedByDijkstra;	
	private List<Arc> outgoingArcs;
	
	public Vertex(double lat, double lng, int id) {
		this.lat = lat;
		this.lng = lng;
		this.id = id;
		this.outgoingArcs = new LinkedList<Arc>();
	}
	
	public void addOutgoingArc(Arc arc) {
		outgoingArcs.add(arc);
	}
	
	public List<Arc> getOutgoingArcs() {
		return outgoingArcs;
	}
	
	public int getId() {
		return id;
	}
	
	public double getLat(){
		return lat;
	}
	
	public double getLng(){
		return lng;
	}
	
	public void setMarkedByDijkstra(boolean marked){
		markedByDijkstra = marked;
	}
	
	public boolean isMarkedByDijkstra(){
		return markedByDijkstra;
	}
}
