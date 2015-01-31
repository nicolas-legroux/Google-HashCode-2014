package DataStructure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Graph {
	
	private Vertex root;
	private int maxTimeAllowed;
	private int numberOfCars;
	private int completeLength;
	private List<Arc> arcs;
	public Map<Integer, Vertex> vertices;
	
	public Graph() {
		vertices = new HashMap<Integer, Vertex>();
		arcs = new LinkedList<Arc>();
	}
	
	public void buildFromFile(String path) {		
		int intOfRoot = -1;	
		int completeDistance = 0;
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
			int numberOfVertices = Integer.parseInt(split[0]);
			int numberOfArcs = Integer.parseInt(split[1]);
			maxTimeAllowed = Integer.parseInt(split[2]);
			numberOfCars = Integer.parseInt(split[3]);
			intOfRoot = Integer.parseInt(split[4]);
						
			//Read the lines describing the set of vertices
			for(int i=0; i<numberOfVertices; i++){
				String line = sc.nextLine();
				split = line.split(" ");
				float lat = Float.parseFloat(split[0]);
				float lng = Float.parseFloat(split[1]);
				Vertex v = new Vertex(lat, lng, i);
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
				
				completeDistance += distance;
				
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
				
				//Add the arc to the outgoing arcs of the starting vertex and to the set of arcs
				start.addOutgoingArc(arc);
				arcs.add(arc);
				if(bidirectionnal){
					end.addOutgoingArc(returnArc);	
					arcs.add(returnArc);
				}				
			}			
		}		
		
		//Do not forget root
		root = vertices.get(new Integer(intOfRoot));	
		
		completeLength = completeDistance;
	}
	
		
	public void resetAllDistance() {
		for(Arc arc : arcs)
			arc.resetDistance();
	}	
	
	public Vertex getRoot() {
		return root;
	}
	
	public int getNumberOfCars(){
		return numberOfCars;
	}
	
	public int getMaxTimeAllowed(){
		return maxTimeAllowed;
	}
	
	public int getCompleteLength(){
		return completeLength;
	}
	
	private Arc getArcBetweenVertices(Vertex start, Vertex end){
		for(Arc arc : start.getOutgoingArcs()){
			if (arc.getEnd() == end)
				return arc;
		}
		return null;
	}
	
	private Vertex getNextClosestVertex(Collection<Vertex> unvisitedVertices, Map<Vertex, Integer> distancesFromSource){
		Vertex bestVertex = null;
		int minDistance = Integer.MAX_VALUE;
		
		for(Vertex vertex : unvisitedVertices){			
			if(distancesFromSource.get(vertex) < minDistance){
				bestVertex = vertex;
				minDistance = distancesFromSource.get(vertex);
			}
		}
		
		return bestVertex;
	}
	
	public Solution computeShortestPath(Vertex startingVertex, Vertex endingVertex){
		Map<Vertex, Vertex> previousVertex = new HashMap<Vertex, Vertex>();
		Collection<Vertex> unvisitedVertices = vertices.values();
		Map<Vertex, Integer> distanceFromSource = new HashMap<Vertex, Integer>();
		
		distanceFromSource.put(startingVertex, 0);
		previousVertex.put(startingVertex, null);
		
		for(Vertex v : vertices.values()){
			if(v != startingVertex){
				previousVertex.put(v, null);
				distanceFromSource.put(v, Integer.MAX_VALUE);
			}
		}
		
		while(!unvisitedVertices.isEmpty()){
			Vertex u = getNextClosestVertex(unvisitedVertices, distanceFromSource);
			
			if(u == endingVertex){
				break;
			}
			
			unvisitedVertices.remove(u);
			
			for(Arc arc : u.getOutgoingArcs()){
				Vertex neighbor = arc.getEnd();
				int altDistance = distanceFromSource.get(u) + arc.getDuration();
				if(altDistance<distanceFromSource.get(neighbor)){
					previousVertex.put(neighbor, u);
					distanceFromSource.put(neighbor, altDistance);
				}
			}
		}
		
		List<Vertex> route = new LinkedList<Vertex>();
		Vertex v = endingVertex;
		while(v != null){
			route.add(v);
			v = previousVertex.get(v);
		}
		
		Collections.reverse(route);		
		
		Solution solution = new Solution(route.get(0));
		
		for(int i=1; i<route.size(); i++){
			solution.addVertex(getArcBetweenVertices(route.get(i-1), route.get(i)), route.get(i));
		}	
		
		return solution;
	}
}

