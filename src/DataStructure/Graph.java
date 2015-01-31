package DataStructure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Graph {
	
	private Vertex root;
	private int numberOfVertices;
	private int numberOfArcs;
	private int maxTimeAllowed;
	private int numberOfCars;
	
	public Graph() {

	}
	
	public void buildFromFile(String path) {
		Map<Integer,Vertex> vertices = new HashMap<Integer,Vertex>();
		
		int intOfRoot = -1;		
		Scanner sc = null;
		
		try {
			sc = new Scanner(new File(path));
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
		
		if(sc != null){
			//Read the first line of the file
			String firstLine = sc.nextLine();
			String[] split = firstLine.split(" ");
			numberOfVertices = Integer.parseInt(split[0]);
			numberOfArcs = Integer.parseInt(split[1]);
			maxTimeAllowed = Integer.parseInt(split[2]);
			numberOfCars = Integer.parseInt(split[3]);
			intOfRoot = Integer.parseInt(split[4]);
						
			//Read the lines describing the set of vertices
			for(int i=0; i<numberOfVertices; i++){
				String line = sc.nextLine();
				split = line.split(" ");
				float lat = Float.parseFloat(split[0]);
				float lng = Float.parseFloat(split[1]);
				Vertex v = new Vertex(lat, lng);
				vertices.put(new Integer(i), v);				
			}
			
			//Read the lines describing the set of arcs
			for(int i=0; i<numberOfArcs; i++){
				
				String line = sc.nextLine();
				split = line.split(" ");
				int intOfStart = Integer.parseInt(split[0]);
				int intOfEnd = Integer.parseInt(split[1]);
				boolean bidirectionnal = false;
				if(split[2].equals("2")){
					bidirectionnal = true;					
				}		
				
				int duration = Integer.parseInt(split[3]);
				int distance = Integer.parseInt(split[4]);
				
				Vertex start = vertices.get(new Integer(intOfStart));
				Vertex end = vertices.get(new Integer(intOfEnd));
				
				Arc arc = new Arc(start, end, distance, duration);
				Arc returnArc = null;
				
				//Check if the arc is bidirectionnal and, if positive, build the return arc
				if(bidirectionnal){
					returnArc = new Arc(end, start, distance, duration);
					arc.setReturnArc(returnArc);
					returnArc.setReturnArc(arc);
				}
				
				//Add the arc to the outgoing arcs of the starting vertex
				start.addOutgoingArc(arc);				
				if(bidirectionnal){
					end.addOutgoingArc(returnArc);					
				}
			}			
		}		
		
		//Do not forget root
		root = vertices.get(new Integer(intOfRoot));		
	}
	
	
	public Vertex getRoot() {
		return root;
	}
	
	public int getNumberOfVertices(){
		return numberOfVertices;
	}
	
	public int getNumberOfArcs(){
		return numberOfArcs;		
	}
	
	public int getNumberOfCars(){
		return numberOfCars;
	}
	
	public int getMaxTimeAllowed(){
		return maxTimeAllowed;
	}
}
