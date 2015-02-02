package solver;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import DataStructure.Arc;
import DataStructure.Graph;
import DataStructure.Solution;
import DataStructure.SolutionsSet;
import DataStructure.Vertex;
import Helpers.GeographicDistances;
import Helpers.StartingPoints;

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

		List<Arc> arcs = new LinkedList<Arc>();
		Arc arc = null;

		List<Vertex> shortestPath = graph.pathToNearestUnvisitedArcInArea(from, s.getLatStart(), s.getLngStart(), maxDistanceFromStart);

		for (int j = 1; j < shortestPath.size(); j++) {
			arc = graph.getArcBetweenVertices(shortestPath.get(j - 1),
					shortestPath.get(j));
			arcs.add(arc);
		}

		if(arcs.isEmpty()){
			System.out.println("Car #" + s.getId() + " finished in "  + s.getTotalTime());
		}
		return arcs;
	}
}
