package solver;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import DataStructure.Arc;
import DataStructure.ArcRatioComparator;
import DataStructure.Graph;
import DataStructure.Solution;
import DataStructure.SolutionsSet;
import DataStructure.Vertex;
import Helpers.StartingPointsDivisionAlgorithm;

public class GreedyWithIntelligentStartingPoint {
	
	private Graph graph;
	private int maxTime;
	private int numberVehicules;
	
	public GreedyWithIntelligentStartingPoint(Graph graph, int maxTime, int numberVehicules) {
		this.graph = graph;
		this.maxTime = maxTime;
		this.numberVehicules = numberVehicules;
	}
	
	public SolutionsSet compute() {
		
		Arc arc = null;
		
		List<Arc> currentOutgoing;
		ArcRatioComparator comparator = new ArcRatioComparator();
		SolutionsSet set = new SolutionsSet();

		
		for(int i = 0; i < numberVehicules; i++) {
			
			Solution solution = new Solution(graph.getRoot());
			Vertex current = graph.getRoot();
			Random random = new Random();
			
			List<Vertex> shortestPath = graph.computeShortestPath(graph.getRoot(), 
					graph.findClosestVertexToPoint(StartingPointsDivisionAlgorithm.lat[i], 
							StartingPointsDivisionAlgorithm.lng[i]));
			for(int j=1; j<shortestPath.size(); j++){
				arc = graph.getArcBetweenVertices(shortestPath.get(j-1), shortestPath.get(j));
				
				if(solution.getTotalTime() + arc.getDuration() >= maxTime) 
					break;
				
				solution.addVertex(arc, arc.getEnd());
				arc.setVisited(true);
				current = arc.getEnd();
			}	
			
			while(true) {			
				
				currentOutgoing = current.getOutgoingArcs();
				Collections.sort(currentOutgoing, comparator);
				arc = currentOutgoing.get(0);				
				
				if(arc.getDistance() == 0) {
					arc = currentOutgoing.get(random.nextInt(currentOutgoing.size()));
				}
				
				if(solution.getTotalTime() + arc.getDuration() >= maxTime) 
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
