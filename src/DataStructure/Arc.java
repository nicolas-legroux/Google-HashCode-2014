package DataStructure;

public class Arc {
	
	private Vertex start;
	private Vertex end;
	
	private int duration;
	private int realDistance;
	
	ArcState currentState;
	ArcState savedState;

	
	private Arc returnArc;
	
	private int numberOfVisits;
		
	public Arc(Vertex start, Vertex end, int distance, int duration) {
		this.start = start;
		this.end = end;
		this.realDistance = distance;
		this.duration = duration;
		this.returnArc = null;
		this.numberOfVisits = 0;
		currentState = new ArcState(distance, false);
		savedState = currentState.clone();
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
		return currentState.getDistance();
	}
	
	public int getDuration() {
		return duration;
	}
	
	public boolean getVisited() {
		return currentState.getVisited();
	}
	
	public int getNumberOfVisits() {
		return numberOfVisits;
	}
	
	public void restoreState() {
		currentState = savedState.clone();
	}
	
	public void saveState() {
		savedState = currentState.clone();
	}
	
	public void setVisited(boolean visited) {
		setVisited(visited, true);
	}
	
	private void setVisited(boolean visited, boolean changeReturn) {

		currentState.setVisited(visited);
		if(visited) {
			currentState.setDistance(0);
			numberOfVisits++;
		}
		else{
			currentState.setDistance(realDistance);
			numberOfVisits = 0;
		}
		
		if(changeReturn && returnArc != null)
			returnArc.setVisited(visited, false);
	}
}
