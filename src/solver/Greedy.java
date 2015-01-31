package solver;

import java.util.Collections;
import java.util.LinkedList;
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
	
	public SolutionsSet compute() {
		
		Arc arc = null;
		

		List<Arc> currentOutgoing;
		ArcRatioComparator comparator = new ArcRatioComparator();
		SolutionsSet set = new SolutionsSet();

		
		for(int i = 0; i < numberVehicules; i++) {
			
			Solution solution = new Solution(graph.getRoot());
			Vertex current = graph.getRoot();
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
			//System.out.println("Done vehicule " + i);
			//System.out.println("Score vehicule " + i + ": " + solution.getTotalDistance());
			
			set.addSolution(solution);
		}
		
		return set;
	}
	
	

}
