package DataStructure;

import java.util.Comparator;

public class SolutionComparator implements Comparator<Solution> {

	@Override
	public int compare(Solution o1, Solution o2) {
		return o1.getTotalDistance() - o2.getTotalDistance();
	}


}
