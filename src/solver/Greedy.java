package solver;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ArcComparator.ArcSpeedComparator;
import DataStructure.Arc;
import DataStructure.Graph;
import DataStructure.Solution;
import DataStructure.SolutionsSet;
import DataStructure.Vertex;


public class Greedy {
	
	Graph graph;
	int maxTime;
	int numberVehicules;
	
	Comparator<Arc> comparator;
	
	public Greedy(Graph graph, int maxTime, int numberVehicules, Comparator<Arc> comp) {
		this.graph = graph;
		this.maxTime = maxTime;
		this.numberVehicules = numberVehicules;
		this.comparator = comp;
	}
	
	public SolutionsSet compute(){
		SolutionsSet set = new SolutionsSet();
		for(int i=0; i<numberVehicules; ++i){
			Solution solution = new Solution(graph.getRoot());
			set.addSolution(solution);
		}
		
		return compute(set);
	}
	
	private Arc chooseArc(Solution solution, Vertex current){
		Arc nextArc = null;		
		List<Arc> currentOutgoing = current.getOutgoingArcs();
		
		List<Arc> currentOutgoingNotVisited = new LinkedList<Arc>();
		for(Arc arc : currentOutgoing){
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
			nextArc = currentOutgoing.get(random.nextInt(currentOutgoing.size()));
		}
		
		if(solution.getTotalTime() + nextArc.getDuration() >= maxTime)
			return null;
		else		
			return nextArc;
	}
	
	public SolutionsSet compute(SolutionsSet set) {		
			
		for(Solution solution: set.getSolutions()) {			
			Vertex current = solution.getLastVertex();				
			while(true) {				
				Arc nextArc = chooseArc(solution, current);
				if(nextArc == null)
					break;
				
				solution.addVertex(nextArc, nextArc.getEnd());				
				//TODO compute should not be the method that calls setVisited!
				nextArc.setVisited(true);				
				current = nextArc.getEnd();				
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