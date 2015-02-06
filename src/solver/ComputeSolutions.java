package solver;

import java.util.Comparator;
import java.util.List;

import DataStructure.Arc;
import DataStructure.Graph;
import DataStructure.Solution;
import DataStructure.SolutionsSet;
import DataStructure.Vertex;

//Class that builds the the set of solutions
public class ComputeSolutions {

	Graph graph;
	int maxTime;
	int numberVehicules;

	ChooseArcsAlgorithm chooseArcsAlgorithm;

	public ComputeSolutions(Graph g, int maxT, int numberV,
			Comparator<Arc> comp, ChooseArcsAlgorithm algo) {
		this.graph = g;
		this.maxTime = maxT;
		this.numberVehicules = numberV;
		this.chooseArcsAlgorithm = algo;

		chooseArcsAlgorithm.initialize(graph, maxTime, comp);
	}
	
	//When iterative is set to true : the path for the cars are built at the same time.
	//Otherwise : the path for each car is built one at a time
	public SolutionsSet compute(boolean iterative) {
		SolutionsSet set = new SolutionsSet();
		for (int i = 0; i < numberVehicules; ++i) {
			Solution solution = new Solution(graph.getRoot(), i);
			set.addSolution(solution);
		}

		if (iterative)
			return computeIteratively(set);
		else
			return computeCarByCar(set);
	}
	
	private void addArcToSolution(Arc arc, Solution solution){
		solution.addArc(arc, arc.getEnd());		
	}

	public SolutionsSet compute(SolutionsSet set, boolean iterative) {
		if (iterative)
			return computeIteratively(set);
		else
			return computeCarByCar(set);
	}

	public SolutionsSet computeIteratively(SolutionsSet set) {

		int nombre_iteration = 0;

		while (true) {

			for (Solution solution : set.getSolutions()) {
				if (!solution.isFinished()) {
					Vertex current = solution.getLastVertex();
					List<Arc> nextArcs = chooseArcsAlgorithm.chooseArcs(
							solution, current);

					if (nextArcs == null) {
						solution.setFinished(true);
						break;
					}

					Arc lastArc = null;
					for (Arc arc : nextArcs) {
						addArcToSolution(arc, solution);
						lastArc = arc;
					}

					if (lastArc == null) {
						solution.setFinished(true);
						break;
					}
				}
			}

			if (set.allSolutionsAreFinished()) {
				break;
			}

			if (nombre_iteration % 1000 == 0) {
				//set.printStatusOfSolutions();
			}
		}

		return set;
	}

	public SolutionsSet computeCarByCar(SolutionsSet set) {
		
		int nombre_iteration = 0;

		for (Solution solution : set.getSolutions()) {
			Vertex current = solution.getLastVertex();
			while (true) {
				List<Arc> nextArcs = chooseArcsAlgorithm.chooseArcs(solution,
						current);
				if (nextArcs == null) {
					solution.setFinished(true);
					break;
				}

				Arc lastArc = null;
				for (Arc arc : nextArcs) {
					addArcToSolution(arc, solution);
					lastArc = arc;
				}

				if (lastArc == null) {					
					break;
				}

				current = lastArc.getEnd();	
				
				if(nombre_iteration%1000 == 0){
					//set.printStatusOfSolutions();
				}
			}
		}

		return set;
	}

	public SolutionsSet compute(double[] initialLat, double[] initialLng,
			boolean iterative) {
		SolutionsSet set = getShortestPathToPoints(initialLat, initialLng);
		if (iterative)
			return computeIteratively(set);
		else
			return computeCarByCar(set);
	}
	
	//Builds the shortest path to the starting points
	private SolutionsSet getShortestPathToPoints(double[] initialLat,
			double[] initialLng) {
		if (initialLat.length != initialLng.length
				|| initialLng.length < numberVehicules) {
			throw new IllegalArgumentException(
					"The size of the arrays must match the number of vehicles");
		}

		SolutionsSet set = new SolutionsSet();
		Arc arc = null;

		for (int i = 0; i < numberVehicules; i++) {

			Solution solution = new Solution(graph.getRoot(), i);
			Vertex current = graph.getRoot();
			solution.setStartingPoint(initialLat[i], initialLng[i]);

			List<Vertex> shortestPath = graph.computeShortestPath(graph
					.getRoot(), graph.findClosestVertexToPoint(initialLat[i],
					initialLng[i]));

			for (int j = 1; j < shortestPath.size(); j++) {
				arc = graph.getArcBetweenVertices(shortestPath.get(j - 1),
						shortestPath.get(j));

				if (solution.getTotalTime() + arc.getDuration() >= maxTime)
					break;

				solution.addArc(arc, arc.getEnd());
				arc.setVisited(true);
				current = arc.getEnd();
			}

			set.addSolution(solution);
		}

		return set;
	}
}
