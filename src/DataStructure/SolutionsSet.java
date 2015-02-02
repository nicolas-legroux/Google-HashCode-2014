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
	
	public void printDistanceOfEachSolution(){
		for(int i=0; i<solutions.size(); i++){
			System.out.println("Solution #" + i + " achieved a score of " + solutions.get(i).getTotalDistance());
		}
		
		System.out.println("Total Score : " + getTotalScore());
	}
	
	public void writeSolutionToFile(int i){
		Solution s = solutions.get(i);
		PrintWriter writer;
		try {
			
			writer = new PrintWriter("car-" + i + ".txt", "UTF-8");
			writer.println(1);
			
			List<Vertex> vs = s.getVertices();
				
			writer.println(vs.size());
				
			for(Vertex v : vs) {
				writer.println(v.getId());
			}
			
			writer.close();			
			
		} catch (FileNotFoundException e) {
			System.err.println("Could not open file : " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
	}
	
	public void writeToFile() {
		PrintWriter writer;
		try {
			
			writer = new PrintWriter("output.txt", "UTF-8");
			writer.println(solutions.size());
			
			for(Solution s : solutions) {
				
				writeSolutionToFile(s.getId());
				
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
