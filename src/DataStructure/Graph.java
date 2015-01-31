package DataStructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {
	
	private Vertex root;
	
	public Graph() {

	}
	
	public void buildFromFile(String path) {
		Map<Integer,Vertex> vertices = new HashMap<Integer,Vertex>();
		Map<Integer,Arc> arcs = new HashMap<Integer,Arc>();
		
		//TODO Do not forget root
		
	}
	
	
	public Vertex getRoot() {
		return root;
	}

}
