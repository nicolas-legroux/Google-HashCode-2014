package DataStructure;

public class ArcState {

	private boolean visited;
	private int distance;
	
	public ArcState(int distance, boolean visited) {
		this.distance = distance;
		this.visited = visited;
	}
	
	
	public int getDistance() {
		return distance;
	}
	
	public boolean getVisited() {
		return visited;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public ArcState clone() {
	    return new ArcState(this.getDistance(), this.getVisited());
	}
}
