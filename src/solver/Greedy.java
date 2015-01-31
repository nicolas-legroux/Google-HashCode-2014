package solver;

import DataStructure.Graph;
import DataStructure.Solution;
import DataStructure.Vertex;

public class Greedy {
	
	Graph graph;
	int maxTime;
	
	public Greedy(Graph graph, int maxTime) {
		this.graph = graph;
		this.maxTime = maxTime;
	}
	
	public void compute() {
		Vertex current = graph.getRoot();
		Solution solution = new Solution();
		
		
		while(solution.getTotalTime() <= maxTime) {
			
		}
	}

}
