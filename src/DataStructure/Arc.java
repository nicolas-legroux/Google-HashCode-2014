package DataStructure;

public class Arc {
	
	private Vertex start;
	private Vertex end;
	
	private int distance;
	private int realdistance;
	private int duration;
	
	private Arc returnArc;
	
	boolean visited;
	
	public Arc(Vertex start, Vertex end, int distance, int duration, boolean bidirectionnel) {
		this.start = start;
		this.end = end;
		this.distance = distance;
		realdistance = distance;
		this.duration = duration;
		returnArc = null;
	}
	
	public Arc(Vertex start, Vertex end, int distance, int duration, boolean bidirectionnel, Arc returnArc) {
		this(start, end, distance, duration, bidirectionnel);
		returnArc = null;
	}
	
	public void setReturnArc(Arc returnArc) {
		this.returnArc = returnArc;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public int getDuration() {
		return duration;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
		if(visited)
			distance = 0;
		else
			distance = realdistance;
	}
	
	public void setVisited(boolean visited, boolean changeReturn) {
		this.visited = visited;
		if(visited)
			distance = 0;
		else
			distance = realdistance;
		
		if(changeReturn && returnArc != null)
			returnArc.setVisited(visited, false);
	}
	
	public boolean getVisited() {
		return visited;
	}
	
	public Vertex getEnd() {
		return end;
	}
}
