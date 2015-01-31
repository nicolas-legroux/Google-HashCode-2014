package DataStructure;

public class Arc {
	
	private Vertex start;
	private Vertex end;
	
	private int distance;
	private int realDistance;
	private int duration;
	
	private Arc returnArc;
	
	private int numberOfVisits;
	
	private boolean visited;
	
	public Arc(Vertex start, Vertex end, int distance, int duration) {
		this.start = start;
		this.end = end;
		this.distance = distance;
		this.realDistance = distance;
		this.duration = duration;
		this.returnArc = null;
		this.visited = false;
		this.numberOfVisits = 0;
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
	
	public int getNumberOfVisits() {
		return numberOfVisits;
	}
	
	public void resetDistance() {
		distance = realDistance;
	}
	
	public void setVisited(boolean visited) {
		setVisited(visited, true);
	}
	
	private void setVisited(boolean visited, boolean changeReturn) {

		this.visited = visited;
		if(visited) {
			distance = 0;
			numberOfVisits++;
		}
		else
			distance = realDistance;
		
		if(changeReturn && returnArc != null)
			returnArc.setVisited(visited, false);
	}
}
