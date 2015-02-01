package DataStructure;

import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

public class SolutionArcList {
	
	private SolutionArcNode start;
	private SolutionArcNode end;
	
	
	public SolutionArcList() {
		start = null;
		end = null;
	}
	
	protected void setEnd(SolutionArcNode end) {
		this.end = end;
	}
	
	protected void setStart(SolutionArcNode start) {
		this.start = start;
	}
	
	public SolutionArcNode getEnd() {
		return end;
	}
	
	public SolutionArcNode getStart() {
		return start;
	}
	
	public SolutionArcNode addArc(Arc arc) {
		SolutionArcNode node = new SolutionArcNode(this, arc);
		
		if(start == null)
			setStart(node);
		
		if(end != null)
			end.setNext(node);
		
		node.setPrevious(end);
		setEnd(node);
		
		return node;
	}
	
	public void switchList(SolutionArcNode node, SolutionArcNode otherListNode) {
		SolutionArcList l2 = otherListNode.getList();
		
		SolutionArcNode e1 = getEnd();
		
		if(node.getPrevious() != null)
			node.getPrevious().setNext(otherListNode);
		
		if(otherListNode.getPrevious() != null)
			otherListNode.getPrevious().setNext(node);
		
		
		
		setEnd(l2.getEnd());
		l2.setEnd(e1);
		
		otherListNode.setList(this);
		node.setList(l2);
	}
	
	public int getTotalDistance() {
		if(start == null)
			return 0;
		
		return start.getTotalDistanceStartingHere();
	}
	
	public int getTotalTime() {
		if(start == null)
			return 0;
		
		return start.getTotalTimeStartingHere();
	}
}
