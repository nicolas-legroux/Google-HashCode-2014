package solver;

import java.util.Comparator;
import java.util.List;

import DataStructure.Arc;
import DataStructure.Graph;
import DataStructure.Solution;
import DataStructure.SolutionsSet;
import DataStructure.Vertex;

public class ComputeSolutions {
	
	Graph graph;
	int maxTime;
	int numberVehicules;	
	
	ChooseArcsAlgorithm chooseArcsAlgorithm;
	
	public ComputeSolutions(Graph g, int maxT, int numberV, Comparator<Arc> comp, ChooseArcsAlgorithm algo){
		this.graph = g;
		this.maxTime = maxT;
		this.numberVehicules = numberV;
		this.chooseArcsAlgorithm = algo;
		
		chooseArcsAlgorithm.initialize(graph, maxTime, comp);
	}
	
	public SolutionsSet compute() {
		SolutionsSet set = new SolutionsSet();
		for (int i = 0; i < numberVehicules; ++i) {
			Solution solution = new Solution(graph.getRoot(), i);
			set.addSolution(solution);
		}

		return compute(set);
	}
	
	public SolutionsSet compute(SolutionsSet set) {		
		
		for(Solution solution: set.getSolutions()) {			
			Vertex current = solution.getLastVertex();				
			while(true) {				
				List<Arc> nextArcs = chooseArcsAlgorithm.chooseArcs(solution, current);
				if(nextArcs == null){
					System.out.println("Car #" + solution.getId() + " is done.");
					break;
				}
				
				Arc lastArc = null;
				for(Arc arc : nextArcs) {
					solution.addVertex(arc, arc.getEnd());		
					lastArc = arc;
				}
				
				if(lastArc == null){
					System.out.println("Car #" + solution.getId() + " is done.");
					break;
				}
				
				current = lastArc.getEnd();				
			}
		}		
		
		return set;
	}
	
	public SolutionsSet compute(double[] initialLat, double[] initialLng){
		SolutionsSet set = getShortestPathToPoints(initialLat, initialLng);
		return compute(set);
	}
	
	private SolutionsSet getShortestPathToPoints(double[] initialLat, double[] initialLng){
		if(initialLat.length != initialLng.length || initialLng.length < numberVehicules){
			throw new IllegalArgumentException("The size of the arrays must match the number of vehicles");
		}
		
		SolutionsSet set = new SolutionsSet();
		Arc arc = null;	
		
		for(int i = 0; i < numberVehicules; i++) {
			
			Solution solution = new Solution(graph.getRoot(), i);
			Vertex current = graph.getRoot();
			solution.setStartingPoint(initialLat[i], initialLng[i]);
				
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
