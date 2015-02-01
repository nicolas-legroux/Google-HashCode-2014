package solver;

import java.util.LinkedList;
import java.util.List;

import DataStructure.Graph;
import DataStructure.Solution;
import DataStructure.SolutionArcList;
import DataStructure.SolutionArcNode;
import DataStructure.SolutionsSet;
import DataStructure.Vertex;

public class LocalSearch {

	List<Solution> solutions;
	Graph g;
	
	public LocalSearch(SolutionsSet set, Graph g) {
		solutions = set.getSolutions();
		this.g = g;
	}
	
	public void computeSwitch() {
		for(Solution s : solutions) {
			
			
			SolutionArcList list = s.getArcs();
			SolutionArcNode current = list.getStart();
			SolutionArcNode temp;
			Vertex v = null;
			List<SolutionArcNode> otherSolutions;
			
			int totalDistance = list.getTotalDistance();
			
			while(current != null) {
				v = current.getArc().getStart();
				otherSolutions = v.getArcSolutions(); //TODO need refactoring
				
				if(otherSolutions.size() > 1) {
					for(SolutionArcNode osn : otherSolutions) {
						if(osn == current) 
							continue;
						
						list.switchList(current, osn);
						
						temp = current;
						current = osn;
						osn = current;
						
						int newDistance = osn.getList().getTotalDistance() ;
						
						if(g.getMaxTimeAllowed() < osn.getList().getTotalTime() ||newDistance <= totalDistance) //not acceptable or used to be better
						{
							list.switchList(osn, current);
							current = osn;
							System.out.println("Youha");
						}
						else {
							totalDistance = newDistance;
							System.out.println("Youhou");
						}
					}
				}
				
				current = current.getNext();
			}
			
		}
	}

}
