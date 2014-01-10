package bayesGame.separationGame;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.graph.Graph;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class BooleanNode {
	
	private int id;
	private boolean observed = false;
	private boolean startNode = false;
	private boolean endNode = false;
	private boolean currentNode = false;
	private boolean reachable = false;
	
	public boolean isCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(boolean currentNode) {
		this.currentNode = currentNode;
	}

	public boolean isReachable() {
		return reachable;
	}

	public void setReachable(boolean reachable) {
		this.reachable = reachable;
	}



	
	
	public BooleanNode(int id){
		
		this.id = id;
		
	}
	
	public BooleanNode(int id, boolean observed){
		
		this.id = id;
		this.observed = observed;
		
	}
	
	public String toString(){
		
		return String.valueOf(id);
		
	}
	
	public boolean isStartNode() {
		return startNode;
	}

	public void setStartNode(boolean startNode) {
		this.startNode = startNode;
	}

	public boolean isEndNode() {
		return endNode;
	}

	public void setEndNode(boolean endNode) {
		this.endNode = endNode;
	}

	public int getId() {
		return id;
	}

	public void setObserved(boolean observed) {
		this.observed = observed;
	}

	public boolean getObserved(){
		
		return observed;
		
	}
	
	public boolean equals(Object other){
		
		boolean result = false;
		if (other instanceof BooleanNode){
			BooleanNode theOther = (BooleanNode)other;
			result = (this.id == theOther.id);
		}
		
		return result;
	}
	
	public int hashCode(){
		
		return id;
	}
	

	
	
	
	
	public static List<BooleanNode> getActiveTrails(Graph g, BooleanNode startNode, List<BooleanNode> observations){
		
		List<BooleanNode> nodesToVisit = new ArrayList<BooleanNode>(observations);
		List<BooleanNode> ancestorNodes = new ArrayList<BooleanNode>();
		List<BooleanNode> reachableNodes = new ArrayList<BooleanNode>();
		
		// part 1
		
		while (!nodesToVisit.isEmpty()){
			
			BooleanNode node = nodesToVisit.get(0);
			nodesToVisit.remove(0);
			
			if (!ancestorNodes.contains(node)){
				Collection<BooleanNode> ancestorCollection = g.getPredecessors(node);
				nodesToVisit.addAll(ancestorCollection);	
			} else {
			}
			
			ancestorNodes.add(node);
		}
		
		List<DirectedPair> directedNodesToVisit = new ArrayList<DirectedPair>();
		List<DirectedPair> visitedNodes = new ArrayList<DirectedPair>();
		
		directedNodesToVisit.add(new DirectedPair(startNode, true));
		
		while (!directedNodesToVisit.isEmpty()){
			DirectedPair pair = directedNodesToVisit.get(0);
			directedNodesToVisit.remove(0);
			BooleanNode node = pair.node;
			if (!visitedNodes.contains(pair)){
				if (!observations.contains(node)){
					reachableNodes.add(node);
					System.out.println(node + " is reachable");
				}
			
				visitedNodes.add(pair);
				if (pair.up && !observations.contains(node)){
					BooleanNode[] parents = (BooleanNode[]) g.getPredecessors(node).toArray(new BooleanNode[g.getPredecessorCount(node)]);
					for (BooleanNode parentNode : parents){
							DirectedPair newPair = new DirectedPair(parentNode, true);
							directedNodesToVisit.add(newPair);
					}
					BooleanNode[] children = (BooleanNode[]) g.getSuccessors(node).toArray(new BooleanNode[g.getSuccessorCount(node)]);
					for (BooleanNode childNode : children){
						DirectedPair newPair = new DirectedPair(childNode, false);
						directedNodesToVisit.add(newPair);
					}	
				} else if (!pair.up) {
					if (!observations.contains(node)){
					
						BooleanNode[] children = (BooleanNode[]) g.getSuccessors(node).toArray(new BooleanNode[g.getSuccessorCount(node)]);
						for (BooleanNode childNode : children){
							DirectedPair newPair = new DirectedPair(childNode, false);
							directedNodesToVisit.add(newPair);
						}
					}
						if (ancestorNodes.contains(node)){
							BooleanNode[] parents = (BooleanNode[]) g.getPredecessors(node).toArray(new BooleanNode[g.getPredecessorCount(node)]);
							for (BooleanNode parentNode : parents){
								DirectedPair newPair = new DirectedPair(parentNode, true);
								directedNodesToVisit.add(newPair);
						}
					}
				}
			}
		}
		
		return reachableNodes;
		
	}
	
	
	
	public static class BooleanNodeFactory implements Factory<BooleanNode>{
		
		private static int nodeCount = 7;
		private static BooleanNodeFactory instance = new BooleanNodeFactory();

		public BooleanNode create() {
			BooleanNode bn = new BooleanNode(nodeCount);
			nodeCount++;
			return bn;
		}
		
		public static BooleanNodeFactory getInstance(){
			return instance;
		}
		
		
		
		
	}
	
	
	

}
