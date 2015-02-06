package DataStructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.Set;

//Class describing an arc (ie. a street)
//Distance gives the score that an arc provides when it is visited; it is set to 0 when an arc has been visites
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

	// This should be used when building the graph
	public void setReturnArc(Arc returnArc) {
		this.returnArc = returnArc;
	}

	public Vertex getStart() {
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

	public boolean isVisited() {
		return visited;
	}

	public int getNumberOfVisits() {
		return numberOfVisits;
	}

	public void resetDistance() {
		distance = realDistance;
		visited = false;
	}

	public boolean isBidirectionnal() {
		return returnArc != null;
	}

	public void setVisited(boolean visited) {
		setVisited(visited, true);
	}

	private void setVisited(boolean visited, boolean changeReturn) {

		this.visited = visited;
		if (visited) {
			distance = 0;
			numberOfVisits++;
		} else {
			distance = realDistance;
			numberOfVisits = 0;
		}

		if (changeReturn && returnArc != null)
			returnArc.setVisited(visited, false);
	}
}
