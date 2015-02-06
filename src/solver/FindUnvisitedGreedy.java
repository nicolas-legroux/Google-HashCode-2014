package solver;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import DataStructure.Arc;
import DataStructure.Graph;
import DataStructure.Solution;
import DataStructure.Vertex;

//The tactic is the following : 
//When starting from the current Vertex :
//Find an unvisited arc in the outgoing arcs that maximises speed
//If there is no such arc, go to the nearest unvisited arc
public class FindUnvisitedGreedy implements ChooseArcsAlgorithm {
	
	private Graph graph;
	private Comparator<Arc> comparator;
	private int maxTime;	
	
	public FindUnvisitedGreedy() {
		this.graph = null;
		this.maxTime = -1;
		this.comparator = null;		
	}
	
	public void initialize(Graph g, int maxT,  Comparator<Arc> comp){
		this.graph = g;
		this.comparator = comp;
		this.maxTime = maxT;		
	}
	
	public List<Arc> chooseArcs(Solution solution, Vertex current){

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