import java.util.Comparator;

import solver.ChooseArcsAlgorithm;
import solver.ComputeSolutions;
import solver.FindUnvisitedGreedy;
import solver.FindUnvisitedInGivenArea;
import ArcComparator.ArcLongestDistanceComparator;
import ArcComparator.ArcSpeedComparator;
import DataStructure.*;
import Helpers.StartingPoints;

public class Test {
	public static void main(String[] args) {
		Graph g = new Graph();
		g.buildFromFile("paris_54000.txt");
		System.out.println("Finished building the graph.");
		System.out.println("The MaxTime is " + g.getMaxTimeAllowed() + ".");
		System.out
				.println("The number of cars is " + g.getNumberOfCars() + ".");
		System.out.println("The sum the lengths of the arcs is "
				+ g.getCompleteLength() + ".");
		
		Comparator<Arc> comparator = new ArcSpeedComparator();
		boolean iterative = false;
		
		SolutionsSet set = null;
		
		ChooseArcsAlgorithm chooseArcsAlgorithm = new FindUnvisitedGreedy();
		ComputeSolutions computeSolutions = new ComputeSolutions(g, g.getMaxTimeAllowed(), g.getNumberOfCars(), comparator, chooseArcsAlgorithm);
				
		long sum = 0;
		int N = 1;
		
		for (int i = 0; i < N; i++) {
			set = computeSolutions.compute(StartingPoints.lat, StartingPoints.lng, iterative);
			//set = computeSolutions.compute();
			int totalOfTotal = set.getTotalScore();
			sum += totalOfTotal;
			g.resetAllDistance();
			set.printStats();
		}		
		
		
		
		set.writeToFile();
	}
}
