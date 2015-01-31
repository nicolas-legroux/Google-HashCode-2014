package solver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
	
	public void compute() {
		
		Arc arc = null;
		

		List<Arc> currentOutgoing;
		ArcRatioComparator comparator = new ArcRatioComparator();
		SolutionsSet set = new SolutionsSet();
		
		
		for(int i = 0; i < numberVehicules; i++) {
			
			Solution solution = new Solution(graph.getRoot());
			Vertex current = graph.getRoot();
			
			while(solution.getTotalTime() <= maxTime) {
				currentOutgoing = current.getOutgoingArcs();
				Collections.sort(currentOutgoing, comparator);
				arc = currentOutgoing.get(0);
				
				solution.addVertex(arc, arc.getEnd());
				arc.setVisited(true);
				current = arc.getEnd();
			}
			
			System.out.println("Done vehicule " + i);
			
			set.addSolution(solution);
		}
	}

}
