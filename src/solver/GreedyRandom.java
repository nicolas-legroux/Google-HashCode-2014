package solver;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import DataStructure.Arc;
import DataStructure.Graph;
import DataStructure.Solution;
import DataStructure.Vertex;

//The tactic is the following : 
//When starting from the current Vertex :
//Find an unvisited arc in the outgoing arcs that maximises speed
//If there is no such arc, go to a random arc
public class GreedyRandom implements ChooseArcsAlgorithm{
	
	private Graph graph;
	private Comparator<Arc> comparator;
	private int maxTime;	
	
	public GreedyRandom() {
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
		//Go to a random arc
		else{
			Random random = new Random();
			nextArcs = Collections.singletonList(currentOutgoing.get(random.nextInt(currentOutgoing.size())));
		}
		
		int newTotal = solution.getTotalTime();
		for(Arc arc : nextArcs)
			newTotal += arc.getDuration();
		
		if(newTotal > maxTime)
			return null;
		else		
			return nextArcs;
	}	
}
