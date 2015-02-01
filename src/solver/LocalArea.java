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

public class LocalArea {

	Graph graph;
	int maxTime;
	int numberVehicules;
	final double maxDistance;

	Comparator<Arc> comparator;

	public LocalArea(Graph graph, int maxTime, int numberVehicules, Comparator<Arc> comp, double maxDist) {
		this.graph = graph;
		this.maxTime = maxTime;
		this.numberVehicules = numberVehicules;
		this.comparator = comp;
		this.maxDistance = maxDist;
		
	}

	public SolutionsSet compute() {
		SolutionsSet set = new SolutionsSet();
		for (int i = 0; i < numberVehicules; ++i) {
			Solution solution = new Solution(graph.getRoot(), i);
			set.addSolution(solution);
		}

		return compute(set);
	}

	private Arc chooseArc(Solution solution, Vertex current){
		Arc nextArc = null;		
		List<Arc> currentOutgoing = current.getOutgoingArcs();
		
		List<Arc> currentOutgoingPotential = new LinkedList<Arc>();
		
		for(Arc arc : currentOutgoing){
			Vertex endVertex = arc.getEnd();
			if(GeographicDistances.distance(endVertex, new Vertex(
					StartingPoints.lat[solution.getId()],
					StartingPoints.lng[solution.getId()], -1)) < maxDistance){
				currentOutgoingPotential.add(arc);
			}
		}
		
		
		//There is some arc which stays in the local area, choose an arc from that set with Greedy algorithm
		if(!currentOutgoingPotential.isEmpty()){
			List<Arc> currentOutgoingNotVisited = new LinkedList<Arc>();
			for(Arc arc : currentOutgoingPotential){
				if (!arc.getVisited()){
					currentOutgoingNotVisited.add(arc);
				}
			}
			
			//The set of outgoing arcs contains arcs that are not visited
			//Choose the best arc among the set
			if(!currentOutgoingNotVisited.isEmpty()){
				Collections.sort(currentOutgoingNotVisited, comparator);
				nextArc = currentOutgoingNotVisited.get(0);
			}
			
			//Else all the arcs are visited
			//Choose a random arc
			//TODO - is there a better choice?
			else{
				Random random = new Random();
				nextArc = currentOutgoingPotential.get(random.nextInt(currentOutgoingPotential.size()));
			}
		}
		
		//Choose an arc to return to the local area
		else{
			double bestDistance = Double.MAX_VALUE;
			
			for(Arc arc : currentOutgoing){
				Vertex endVertex = arc.getEnd();
				if(GeographicDistances.distance(endVertex, new Vertex(
						StartingPoints.lat[solution.getId()],
						StartingPoints.lng[solution.getId()], -1)) < bestDistance){
					bestDistance = GeographicDistances.distance(endVertex, new Vertex(
							StartingPoints.lat[solution.getId()],
							StartingPoints.lng[solution.getId()], -1));
					nextArc = arc;				
				}
			}			
		}
		
		if(solution.getTotalTime() + nextArc.getDuration() >= maxTime)
			return null;
		else		
			return nextArc;
	}

	public SolutionsSet compute(SolutionsSet set) {

		for (Solution solution : set.getSolutions()) {
			Vertex current = solution.getLastVertex();
			while (true) {
				Arc nextArc = chooseArc(solution, current);
				if (nextArc == null)
					break;

				solution.addVertex(nextArc, nextArc.getEnd());
				// TODO compute should not be the method that calls setVisited!
				nextArc.setVisited(true);
				current = nextArc.getEnd();
			}
		}
		return set;
	}

	public SolutionsSet compute(double[] initialLat, double[] initialLng) {
		SolutionsSet set = getShortestPathToPoints(initialLat, initialLng);
		return compute(set);
	}

	private SolutionsSet getShortestPathToPoints(double[] initialLat,
			double[] initialLng) {
		if (initialLat.length != initialLng.length
				|| initialLng.length < numberVehicules) {
			throw new IllegalArgumentException(
					"The size of the arrays must match the number of vehicles");
		}

		SolutionsSet set = new SolutionsSet();
		Arc arc = null;

		for (int i = 0; i < numberVehicules; i++) {

			Solution solution = new Solution(graph.getRoot(), i);
			Vertex current = graph.getRoot();

			List<Vertex> shortestPath = graph.computeShortestPath(graph
					.getRoot(), graph.findClosestVertexToPoint(initialLat[i],
					initialLng[i]));

			for (int j = 1; j < shortestPath.size(); j++) {
				arc = graph.getArcBetweenVertices(shortestPath.get(j - 1),
						shortestPath.get(j));

				if (solution.getTotalTime() + arc.getDuration() >= maxTime)
					break;

				solution.addVertex(arc, arc.getEnd());
				arc.setVisited(true);
				current = arc.getEnd();
			}

			set.addSolution(solution);
		}

		return set;
	}

}
