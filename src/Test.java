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
		
		
		SolutionsSet goodCars = new SolutionsSet();
		
		for(int j = 0; j < g.getNumberOfCars(); j++) {
			int best = 0;
			
			g.saveState();
			
			
			for(int i = 0; i < 50000; i++) {
				Greedy greedy = new Greedy(g, g.getMaxTimeAllowed(), 1);
				SolutionsSet set = greedy.compute();
				
				if(set.getTotalScore() > best) {
					best = set.getTotalScore();
				}
				
				g.restoreState();
			}
			
			System.out.println("Best " + j + " : " + best);
			
			
			while(true){
				Greedy greedy = new Greedy(g, g.getMaxTimeAllowed(), 1);
				SolutionsSet set = greedy.compute();
				
	
				if(set.getTotalScore() > best) {
					goodCars.addSolution(set.getFirstSolution());
					break;
				}
				
				g.restoreState();
			}
			
			System.out.println("Car " + j + " done");
		}
		
		goodCars.writeToFile();

	}

}
