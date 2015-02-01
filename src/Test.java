import solver.Greedy;
import solver.LocalSearch;
import DataStructure.*;


public class Test {

	public static void main(String[] args) {
		
		Graph g = new Graph();
		g.buildFromFile("paris_54000.txt");
		System.out.println("Finished building the graph.");
		System.out.println("The graph has " + g.getNumberOfVertices() + " vertices.");
		System.out.println("The graph has " + g.getNumberOfArcs() + " arcs.");
		System.out.println("The MaxTime is " + g.getMaxTimeAllowed() + ".");
		System.out.println("The number of cars is " + g.getNumberOfCars() + ".");		
		System.out.println("The sum the lengths of the arcs is " + g.getCompleteLength() + ".");
		
		SolutionsSet set = null;
		
		for(int i = 0; i < 1; i++) {
			Greedy greedy = new Greedy(g, g.getMaxTimeAllowed(), 100);
			set = greedy.compute();
		}
		System.out.println("Total score : " + set.getTotalScore());
		
		LocalSearch ls = new LocalSearch(set, g);
		ls.computeSwitch();
		
	}

}
