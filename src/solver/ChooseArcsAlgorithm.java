package solver;

import java.util.Comparator;
import java.util.List;

import DataStructure.Graph;
import DataStructure.Solution;
import DataStructure.Arc;
import DataStructure.Vertex;


public interface ChooseArcsAlgorithm {
	
	void initialize(Graph g, int maxTime, Comparator<Arc> comparator);
	List<Arc> chooseArcs(Solution s, Vertex current);

}
