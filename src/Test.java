import java.util.Comparator;

import solver.Greedy;
import solver.LocalArea;
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
		System.out.println("The number of cars is " + g.getNumberOfCars() + ".");		
		System.out.println("The sum the lengths of the arcs is " + g.getCompleteLength() + ".");	
		
		Comparator<Arc> comparator = new ArcSpeedComparator();

		SolutionsSet set = null;
		LocalArea localArea = new LocalArea(g, g.getMaxTimeAllowed(), 8, comparator, 10000.0);
		
		
		long sum = 0;
		int N = 1;
<<<<<<< HEAD
				
		for(int i=0; i<N; i++){		
			set = localArea.compute(StartingPoints.lat, StartingPoints.lng);			
			//set = greedy.compute();
=======
		
		
		for(int i=0; i<N; i++){		
			set = greedy.compute(StartingPoints.lat, StartingPoints.lng);			
			set = greedy.compute();
>>>>>>> master
			int totalOfTotal = set.getTotalScore();
			sum += totalOfTotal;
			System.out.println(totalOfTotal);
			g.resetAllDistance();
		}
		
		set.writeToFile();
		
		System.out.println("Avg : " + sum/N);
		set.printDistanceOfEachSolution();
		set.writeSolutionToFile(7);		
	}
}
