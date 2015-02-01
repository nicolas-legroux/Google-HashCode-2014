import solver.Greedy;
import solver.GreedyWithIntelligentStartingPoint;
import DataStructure.*;

public class Test {

	public static void main(String[] args) {
		
		Graph g = new Graph();
		g.buildFromFile("paris_54000.txt");
		System.out.println("Finished building the graph.");
		System.out.println("The MaxTime is " + g.getMaxTimeAllowed() + ".");
		System.out.println("The number of cars is " + g.getNumberOfCars() + ".");		
		System.out.println("The sum the lengths of the arcs is " + g.getCompleteLength() + ".");	

		SolutionsSet set = null;
		GreedyWithIntelligentStartingPoint greedy = new GreedyWithIntelligentStartingPoint(g, g.getMaxTimeAllowed(), 8);
		set = greedy.compute();		
		
		int totalOfTotal = set.getTotalScore();
		System.out.println(totalOfTotal);			
		
		set.writeToFile();			
	}
}
