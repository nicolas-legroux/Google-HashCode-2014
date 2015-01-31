package DataStructure;

import java.util.LinkedList;
import java.util.List;

public class Vertex {

	private float lat;
	private float lng;
	private List<Arc> arcs;
	
	public Vertex(float lat, float lng) {
		this.lat = lat;
		this.lng = lng;
		this.arcs = new LinkedList<Arc>();
	}
	
	public void addArc(Arc arc) {
		arcs.add(arc);
	}
	
	public List<Arc> getArcs() {
		return arcs;
		
	}
}
