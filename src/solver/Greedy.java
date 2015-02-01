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


public class Greedy {
	
	Graph graph;
	int maxTime;
	int numberVehicules;
	
	public Greedy(Graph graph, int maxTime, int numberVehicules) {
		this.graph = graph;
		this.maxTime = maxTime;
		this.numberVehicules = numberVehicules;
	}
	
	public SolutionsSet compute(){
		SolutionsSet set = new SolutionsSet();
		for(int i=0; i<numberVehicules; ++i){
			Solution solution = new Solution(graph.getRoot());
			set.addSolution(solution);
		}
		
		return compute(set);
	}
	
	public SolutionsSet compute(SolutionsSet set) {
		
		Arc arc = null;		
		List<Arc> currentOutgoing;
		ArcRatioComparator comparator = new ArcRatioComparator();
			
		for(Solution solution: set.getSolutions()) {
			
			Vertex current = solution.getLastVertex();
			Random random = new Random();
			
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
		}
		
		return set;
	}
	
	public SolutionsSet compute(double[] initialLat, double[] initialLng){
		SolutionsSet set = getShortestPathToPoints(initialLat, initialLng);
		return compute(set);
	}
	
	private SolutionsSet getShortestPathToPoints(double[] initialLat, double[] initialLng){
		if(initialLat.length != initialLng.length || initialLng.length != numberVehicules){
			throw new IllegalArgumentException("The size of the arrays must match the number of vehicles");
		}
		
		SolutionsSet set = new SolutionsSet();
		Arc arc = null;	
		
		for(int i = 0; i < numberVehicules; i++) {
			
			Solution solution = new Solution(graph.getRoot());
			Vertex current = graph.getRoot();
				
			List<Vertex> shortestPath = graph.computeShortestPath(graph.getRoot(), 
					graph.findClosestVertexToPoint(initialLat[i], initialLng[i]));
			
			for(int j=1; j<shortestPath.size(); j++){
				arc = graph.getArcBetweenVertices(shortestPath.get(j-1), shortestPath.get(j));
				
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