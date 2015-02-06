package solver;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import DataStructure.Arc;
import DataStructure.Graph;
import DataStructure.Solution;
import DataStructure.Vertex;
import Helpers.GeographicDistances;

//The tactic is the following : 
//When starting from the current Vertex :
//Find an unvisited arc in the outgoing arcs that maximises speed
//If there is no such arc, go to the most advantageous unvisited arc (considers multiple choices with a max depth)
//Additional constraint : the arcs that are chosen must be in a given area
public class FindUnvisitedInGivenArea implements ChooseArcsAlgorithm {

	private Graph graph;
	private Comparator<Arc> comparator;
	private int maxTime;

	private double maxDistanceFromStart;

	public FindUnvisitedInGivenArea(double maxD) {
		this.graph = null;
		this.maxTime = -1;
		this.comparator = null;
		this.maxDistanceFromStart = maxD;
	}

	public void initialize(Graph g, int maxT, Comparator<Arc> comp) {
		this.graph = g;
		this.comparator = comp;
		this.maxTime = maxT;
	}

	public List<Arc> chooseArcs(Solution solution, Vertex current) {

		List<Arc> nextArcs;

		List<Arc> currentOutgoing = current.getOutgoingArcs();
		List<Arc> currentOutgoingStillInAreaNotVisited = new LinkedList<Arc>();

		for (Arc arc : currentOutgoing) {
			Vertex endVertex = arc.getEnd();
			if (GeographicDistances.distance(endVertex,
					new Vertex(solution.getLatStart(), solution.getLngStart(),
							-1)) < maxDistanceFromStart) {
				if (!arc.isVisited()) {
					currentOutgoingStillInAreaNotVisited.add(arc);
				}
			}
		}

		// The set of outgoing arcs contains arcs that are not visited
		// Choose the best arc among the set
		if (!currentOutgoingStillInAreaNotVisited.isEmpty()) {
			Collections.sort(currentOutgoingStillInAreaNotVisited, comparator);
			nextArcs = Collections
					.singletonList(currentOutgoingStillInAreaNotVisited.get(0));
		}

		// Else all the arcs are visited
		// Go to the nearest unvisited arc in the area
		// TODO - is there a better choice?
		else {
			nextArcs = pathToNearestUnvisitedArcInArea(current, solution);
		}

		int newTotal = solution.getTotalTime();
		for (Arc arc : nextArcs)
			newTotal += arc.getDuration();

		if (newTotal > maxTime || nextArcs.isEmpty())
			return null;
		else
			return nextArcs;
	}

	private List<Arc> pathToNearestUnvisitedArcInArea(Vertex from, Solution s) {

		int maxDepthSearch = 1;
		List<Arc> arcs = graph.pathToBestUnvisitedArcInAreaWithDepthSearch(from, s.getLatStart(), s.getLngStart(), maxDistanceFromStart, maxDepthSearch);

		if(arcs.isEmpty()){
			System.out.println("Car #" + s.getId() + " finished in "  + s.getTotalTime());
		}
		
		return arcs;
	}
}
