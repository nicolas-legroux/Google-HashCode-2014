import solver.Greedy;
import DataStructure.*;
import Helpers.StartingPoints;

public class Test {

	public static void main(String[] args) {
		
		Graph g = new Graph();
		g.buildFromFile("paris_54000.txt");
		System.out.println("Finished building the graph.");
		System.out.println("The MaxTime is " + g.getMaxTimeAllowed() + ".");
		System.out.println("The number of cars is " + g.getNumberOfCars() + ".");		
		System.out.println("The sum the lengths of the arcs is " + g.getCompleteLength() + ".");	

		SolutionsSet set = null;
		Greedy greedy = new Greedy(g, g.getMaxTimeAllowed(), 8);
		set = greedy.compute(StartingPoints.lat, StartingPoints.lng);		
		
		int totalOfTotal = set.getTotalScore();
		System.out.println(totalOfTotal);			
		
		set.writeToFile();			
	}
}
