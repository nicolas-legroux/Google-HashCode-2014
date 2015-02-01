package DataStructure;

import java.util.LinkedList;
import java.util.List;

public class Vertex {

	private double lat;
	private double lng;
	private int id;
	
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
}
