package DataStructure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import Helpers.GeographicDistances;

public class Graph {

	private Vertex root;
	private int maxTimeAllowed;
	private int numberOfCars;
	private int completeLength;
	private List<Arc> arcs;
	public Vertex[] vertices;

	// To store the result of Dijkstr'as algorithm
	private int[] previousVertex;
	private Vertex dijkstraStartingVertex;

	public Graph() {
		arcs = new LinkedList<Arc>();
		previousVertex = null;
		dijkstraStartingVertex = null;
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

		if (sc != null) {
			// Read the first line of the file
			String firstLine = sc.nextLine();
			String[] split = firstLine.split(" ");
			int numberOfVertices = Integer.parseInt(split[0]);
			int numberOfArcs = Integer.parseInt(split[1]);
			maxTimeAllowed = Integer.parseInt(split[2]);
			numberOfCars = Integer.parseInt(split[3]);
			intOfRoot = Integer.parseInt(split[4]);

			vertices = new Vertex[numberOfVertices];

			// Read the lines describing the set of vertices
			for (int i = 0; i < numberOfVertices; i++) {
				String line = sc.nextLine();
				split = line.split(" ");
				double lat = Double.parseDouble(split[0]);
				double lng = Double.parseDouble(split[1]);
				Vertex v = new Vertex(lat, lng, i);
				vertices[i] = v;
			}

			// Read the lines describing the set of arcs
			for (int i = 0; i < numberOfArcs; i++) {

				String line = sc.nextLine();
				split = line.split(" ");
				int intOfStart = Integer.parseInt(split[0]);
				int intOfEnd = Integer.parseInt(split[1]);
				boolean bidirectionnal = false;
				if (split[2].equals("2")) {
					bidirectionnal = true;
				}

				int duration = Integer.parseInt(split[3]);
				int distance = Integer.parseInt(split[4]);

				completeDistance += distance;

				Vertex start = vertices[intOfStart];
				Vertex end = vertices[intOfEnd];

				Arc arc = new Arc(start, end, distance, duration);
				Arc returnArc = null;

				// Check if the arc is bidirectionnal and, if positive, build
				// the return arc
				if (bidirectionnal) {
					returnArc = new Arc(end, start, distance, duration);
					arc.setReturnArc(returnArc);
					returnArc.setReturnArc(arc);
				}

				// Add the arc to the outgoing arcs of the starting vertex and
				// to the set of arcs
				start.addOutgoingArc(arc);
				arcs.add(arc);
				if (bidirectionnal) {
					end.addOutgoingArc(returnArc);
					arcs.add(returnArc);
				}
			}
		}

		// Do not forget root
		root = vertices[intOfRoot];

		completeLength = completeDistance;
	}

	public void resetAllDistance() {
		for (Arc arc : arcs)
			arc.resetDistance();
	}

	public Vertex getRoot() {
		return root;
	}

	public int getNumberOfCars() {
		return numberOfCars;
	}

	public int getMaxTimeAllowed() {
		return maxTimeAllowed;
	}

	public int getCompleteLength() {
		return completeLength;
	}

	public Arc getArcBetweenVertices(Vertex start, Vertex end) {
		for (Arc arc : start.getOutgoingArcs()) {
			if (arc.getEnd() == end)
				return arc;
		}
		return null;
	}

	private Vertex getNextClosestVertex(Collection<Vertex> unvisitedVertices,
			int[] distancesFromSource) {
		Vertex bestVertex = null;
		int minDistance = Integer.MAX_VALUE;

		for (Vertex vertex : unvisitedVertices) {
			if (distancesFromSource[vertex.getId()] < minDistance) {
				bestVertex = vertex;
				minDistance = distancesFromSource[vertex.getId()];
			}
		}

		return bestVertex;
	}

	public List<Vertex> computeShortestPath(Vertex startingVertex,
			Vertex endingVertex) {

		// Compute if the result is not stored
		if (previousVertex == null || dijkstraStartingVertex != startingVertex) {
			System.out.println("Computing Dijkstra Algorithm...");
			previousVertex = new int[vertices.length];
			Set<Vertex> unvisitedVertices = new HashSet<Vertex>(
					Arrays.asList(vertices));
			int[] distanceFromSource = new int[vertices.length];

			for (Vertex v : Arrays.asList(vertices)) {
				previousVertex[v.getId()] = -1;
				distanceFromSource[v.getId()] = Integer.MAX_VALUE;
			}

			distanceFromSource[startingVertex.getId()] = 0;
			previousVertex[startingVertex.getId()] = -1;

			while (!unvisitedVertices.isEmpty()) {
				Vertex u = getNextClosestVertex(unvisitedVertices,
						distanceFromSource);

				unvisitedVertices.remove(u);

				for (Arc arc : u.getOutgoingArcs()) {
					Vertex neighbor = arc.getEnd();
					int altDistance = distanceFromSource[u.getId()]
							+ arc.getDuration();
					if (altDistance < distanceFromSource[neighbor.getId()]) {
						previousVertex[neighbor.getId()] = u.getId();
						distanceFromSource[neighbor.getId()] = altDistance;
					}
				}
			}

			// Store the starting vertex
			dijkstraStartingVertex = startingVertex;
		}

		// Get the route
		List<Vertex> route = new LinkedList<Vertex>();
		Vertex currentVertex = endingVertex;
		int idOfCurrentVertex = endingVertex.getId();
		while (idOfCurrentVertex != -1) {
			route.add(currentVertex);
			idOfCurrentVertex = previousVertex[idOfCurrentVertex];
			if (idOfCurrentVertex != -1)
				currentVertex = vertices[idOfCurrentVertex];
		}

		Collections.reverse(route);

		return route;
	}

	public List<Vertex> pathToNearestUnvisitedArc(Vertex startingVertex) {

		// WARNING
		// unvisited references here to the dijkstra algo
		// not the cars

		// System.out.println("Finding nearest unvisited arc...");
		previousVertex = new int[vertices.length]; // Should be adapted

		Set<Vertex> unvisitedVertices = new HashSet<Vertex>(
				Arrays.asList(vertices));
		int[] distanceFromSource = new int[vertices.length];

		for (Vertex v : Arrays.asList(vertices)) {
			previousVertex[v.getId()] = -1;
			distanceFromSource[v.getId()] = Integer.MAX_VALUE;
		}

		distanceFromSource[startingVertex.getId()] = 0;
		previousVertex[startingVertex.getId()] = -1;

		while (!unvisitedVertices.isEmpty()) {
			Vertex u = getNextClosestVertex(unvisitedVertices,
					distanceFromSource);

			unvisitedVertices.remove(u);

			for (Arc arc : u.getOutgoingArcs()) {
				Vertex neighbor = arc.getEnd();

				// If the arc is not visited return the route to the arc
				if (!arc.isVisited()) {

					List<Vertex> route = new LinkedList<Vertex>();
					Vertex currentVertex = arc.getStart();
					int idOfCurrentVertex = currentVertex.getId();

					while (idOfCurrentVertex != -1) {
						route.add(currentVertex);
						idOfCurrentVertex = previousVertex[idOfCurrentVertex];
						if (idOfCurrentVertex != -1)
							currentVertex = vertices[idOfCurrentVertex];
					}

					Collections.reverse(route);
					System.out.println("Need " + route.size()
							+ " to return on a unseen route");
					return route;
				}

				int altDistance = distanceFromSource[u.getId()]
						+ arc.getDuration();
				if (altDistance < distanceFromSource[neighbor.getId()]) {
					previousVertex[neighbor.getId()] = u.getId();
					distanceFromSource[neighbor.getId()] = altDistance;
				}
			}
		}

		// Champagne if this ever executed
		System.out.println("And then, there was light.");
		return new LinkedList<Vertex>();
	}

	public Vertex findClosestVertexToPoint(double lat, double lng) {
		double minDistance = 10000.0;
		Vertex point = new Vertex(lat, lng, -1);
		Vertex closestVertex = null;

		for (Vertex v : Arrays.asList(vertices)) {
			if (GeographicDistances.distance(v, point) < minDistance) {
				closestVertex = v;
				minDistance = GeographicDistances.distance(v, point);
			}
		}

		return closestVertex;
	}
}