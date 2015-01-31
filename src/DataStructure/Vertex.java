package DataStructure;

import java.util.LinkedList;
import java.util.List;

public class Vertex {

	private float lat;
	private float lng;
	
	private List<Arc> outgoingArcs;
	
	public Vertex(float lat, float lng) {
		this.lat = lat;
		this.lng = lng;
		this.outgoingArcs = new LinkedList<Arc>();
	}
	
	public void addOutgoingArc(Arc arc) {
		outgoingArcs.add(arc);
	}
	
	public List<Arc> getOutgoingArcs() {
		return outgoingArcs;
	}
}
