package DataStructure;

public class Arc {
	
	private Vertex start;
	private Vertex end;
	
	private int distance;
	private int duration;
	
	boolean bidirectionnel;
	
	public Arc(Vertex start, Vertex end, int distance, int duration, boolean bidirectionnel) {
		this.start = start;
		this.end = end;
		this.distance = distance;
		this.duration = duration;
		this.bidirectionnel = bidirectionnel;
	}
	
	

}
