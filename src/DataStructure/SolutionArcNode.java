package DataStructure;

public class SolutionArcNode {
	
	SolutionArcList list;
	
	SolutionArcNode next;
	SolutionArcNode previous;
	
	
	Arc arc;
	
	public SolutionArcNode(SolutionArcList list, Arc arc) {
		next = null;
		this.arc = arc;
		this.list = list;
	}
	
	public SolutionArcList getList() {
		return list;
	}
	
	public void setList(SolutionArcList list) {
		if(next != null)
			next.setList(list);
		
		this.list = list;
	}
	
	public SolutionArcNode getPrevious() {
		return previous;
	}
	
	protected void setPrevious(SolutionArcNode previous) {
		this.previous = previous;
	}
	
	public SolutionArcNode getNext() {
		return next;
	}
	
	public void setNext(SolutionArcNode next) {
		this.next = next;
	}
	
	public int getTotalDistanceStartingHere() {
		int total = 0;
		
		if(next != null)
			total += next.getTotalDistanceStartingHere();
		
		total += arc.getDistance();
		
		return total;
	}
	
	
	public int getTotalTimeStartingHere() {
		int total = 0;
		
		if(next != null)
			total += next.getTotalTimeStartingHere();
		
		total += arc.getDuration();
		
		return total;
	}
	
	public Arc getArc() {
		return arc;
	}
}
