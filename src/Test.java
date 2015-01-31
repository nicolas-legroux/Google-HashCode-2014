import solver.Greedy;
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
		
		int total = 0;
		
		for(int i = 0; i < 1000; i++) {
			Greedy greedy = new Greedy(g, g.getMaxTimeAllowed(), 1);
			SolutionsSet set = greedy.compute();
			g.resetAllDistance();
			total += set.getTotalScore();
		}
		
		System.out.println("Total of the totals : " + total);
		
		g.resetAllDistance();
		
		
		for(int i = 0; i < 10; i++) {
			Greedy greedy = new Greedy(g, g.getMaxTimeAllowed(), g.getNumberOfCars());
			SolutionsSet set = greedy.compute();
			System.out.println("Total :" + set.getTotalScore());
			set.writeToFile();
			g.resetAllDistance();
		}
	}

}
