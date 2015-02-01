package solver;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
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
	
	private List<Arc> chooseArcs(Solution solution, Vertex current){

		List<Arc> nextArcs;		
		
		List<Arc> currentOutgoing = current.getOutgoingArcs();
		
		List<Arc> currentOutgoingNotVisited = new LinkedList<Arc>();
		for(Arc arc : currentOutgoing){
			if (!arc.isVisited()){
				currentOutgoingNotVisited.add(arc);
			}
		}
		
		//The set of outgoing arcs contains arcs that are not visited
		//Choose the best arc among the set
		if(!currentOutgoingNotVisited.isEmpty()){
			Collections.sort(currentOutgoingNotVisited, comparator);
			nextArcs = Collections.singletonList(currentOutgoingNotVisited.get(0));
		}
		
		//Else all the arcs are visited
		//Go to the nearest unvisited arc
		//TODO - is there a better choice?
		else{
			nextArcs = pathToNearestUnvisitedArc(current);
		}
		
		int newTotal = solution.getTotalTime();
		for(Arc arc : nextArcs)
			newTotal += arc.getDuration();
		
		if(newTotal > maxTime)
			return null;
		else		
			return nextArcs;
	}
	
	public SolutionsSet compute(SolutionsSet set) {		
			
		for(Solution solution: set.getSolutions()) {			
			Vertex current = solution.getLastVertex();				
			while(true) {				
				List<Arc> nextArcs = chooseArcs(solution, current);
				if(nextArcs == null)
					break;
				
				Arc lastArc = null;
				for(Arc arc : nextArcs) {
					solution.addVertex(arc, arc.getEnd());		
					lastArc = arc;
				}
				
				if(lastArc == null)
					break;
				
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
	
	private List<Arc> pathToNearestUnvisitedArc(Vertex from){
		
		List<Arc> arcs = new LinkedList<Arc>();		
		Arc arc = null;	
		
		List<Vertex> shortestPath = graph.pathToNearestUnvisitedArc(from);
		
		for(int j=1; j<shortestPath.size(); j++){
			arc = graph.getArcBetweenVertices(shortestPath.get(j-1), shortestPath.get(j));
			arcs.add(arc);
		}
	
		return arcs;
	}
}