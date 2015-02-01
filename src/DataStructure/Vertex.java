package DataStructure;

import java.util.LinkedList;
import java.util.List;

public class Vertex {

	private float lat;
	private float lng;
	private int id;
	
	private List<Arc> outgoingArcs;
	
	private List<SolutionArcNode> solutions;
	
	public Vertex(float lat, float lng, int id) {
		this.lat = lat;
		this.lng = lng;
		this.id = id;
		this.outgoingArcs = new LinkedList<Arc>();
		this.solutions = new LinkedList<SolutionArcNode>();
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
	
	public void addNodeSolution(SolutionArcNode s) {
		solutions.add(s);
	}
	
	public List<SolutionArcNode> getArcSolutions() {
		return solutions;
	}
}
