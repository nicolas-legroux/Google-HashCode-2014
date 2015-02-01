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
	
	public int getTotalScore() {
		int total = 0;
		
		for(Solution s : solutions)
			total += s.getTotalDistance();
		
		return total;
	}
	
	public List<Solution> getSolutions(){
		return solutions;
	}
	
	public void writeToFile() {
		PrintWriter writer;
		try {
			
			writer = new PrintWriter("output.txt", "UTF-8");
			writer.println(solutions.size());
			
			for(Solution s : solutions) {
				List<Vertex> vs = s.getVertices();
				
				writer.println(vs.size());
				
				for(Vertex v : vs) {
					writer.println(v.getId());
				}
			}
			writer.close();
			
			
		} catch (FileNotFoundException e) {
			System.err.println("Could not open file : " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
}
