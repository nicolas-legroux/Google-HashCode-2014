package DataStructure;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

public class SolutionsSet {
	
	List<Solution> solutions;
	
	public SolutionsSet() {
		solutions = new LinkedList<Solution>();
	}
	
	public void addSolution(Solution s) { 
		solutions.add(s);
	}
	
	public void writeToFile() {
		PrintWriter writer;
		try {
			
			writer = new PrintWriter("output.txt", "UTF-8");
			writer.println(solutions.size());
			
			for(Solution s : solutions) {
				List<Arc> arcs
			}
			writer.println("The second line");
			writer.close();
			
			
		} catch (FileNotFoundException e) {
			System.err.println("Could not open file : " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
}