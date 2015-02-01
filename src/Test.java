import solver.Greedy;
import DataStructure.*;


public class Test {

	public static void main(String[] args) {
		
		Graph g = new Graph();
		g.buildFromFile("paris_54000.txt");
		System.out.println("Finished building the graph.");
		System.out.println("The MaxTime is " + g.getMaxTimeAllowed() + ".");
		System.out.println("The number of cars is " + g.getNumberOfCars() + ".");		
		System.out.println("The sum the lengths of the arcs is " + g.getCompleteLength() + ".");
		
		
		SolutionsSet solSet = new SolutionsSet();
		Solution sol = g.computeShortestPath(g.getRoot(), g.findClosestVertexToPoint(48.841486, 2.307888));
		solSet.addSolution(sol);
		solSet.writeToFile();
	}

}
