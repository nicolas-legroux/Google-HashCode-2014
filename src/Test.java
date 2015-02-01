import java.util.Comparator;

import solver.Greedy;
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
		Greedy greedy = new Greedy(g, g.getMaxTimeAllowed(), 8, comparator);
		
		
		long sum = 0;
		int N = 1;
		
		
		for(int i=0; i<N; i++){		
			set = greedy.compute(StartingPoints.lat, StartingPoints.lng);			
			set = greedy.compute();
			int totalOfTotal = set.getTotalScore();
			sum += totalOfTotal;
			System.out.println(totalOfTotal);
			g.resetAllDistance();
		}
		
		set.writeToFile();
		
		System.out.println("Avg : " + sum/N);
		//set.writeToFile();			
	}
}
