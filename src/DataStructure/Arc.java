package DataStructure;

public class Arc {
	
	private Vertex start;
	private Vertex end;
	
	private int distance;
	private int realDistance;
	private int duration;
	
	private Arc returnArc;
	
	private boolean visited;
	
	public Arc(Vertex start, Vertex end, int distance, int duration) {
		this.start = start;
		this.end = end;
		this.distance = distance;
		this.realDistance = distance;
		this.duration = duration;
		this.returnArc = null;
		this.visited = false;
	}	
	
	public void setReturnArc(Arc returnArc) {
		this.returnArc = returnArc;
	}
	
	public Vertex getStart(){
		return start;
	}
	
	public Vertex getEnd() {
		return end;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public boolean getVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		setVisited(visited, true);
	}
	
	private void setVisited(boolean visited, boolean changeReturn) {
		this.visited = visited;
		if(visited)
			distance = 0;
		else
			distance = realDistance;
		
		if(changeReturn && returnArc != null)
			returnArc.setVisited(visited, false);
	}
}
