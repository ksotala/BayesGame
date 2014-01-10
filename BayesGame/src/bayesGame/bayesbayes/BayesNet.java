package bayesGame.bayesbayes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.util.Pair;

import edu.uci.ics.jung.graph.DirectedSparseGraph;

public class BayesNet {

	private int edgeCounter = 0;
	private ArrayList<BayesNode> nodes;
	private DirectedSparseGraph<BayesNode, Integer> graph;
	
	private HashSet<BayesNode> visitedDownstreamNodes;
	private Stack<Pair<BayesNode, BayesNode>> downstreamMessagePaths;
	
	public BayesNet() {
		graph = new DirectedSparseGraph<BayesNode, Integer>();
		nodes = new ArrayList<BayesNode>();
	}
	
	private boolean addNode(BayesNode node){
		boolean added = graph.addVertex(node); 
		if (added && !nodes.contains(node)){
			nodes.add(node);
		}
		return added;
	}
	
	public boolean addChanceNode(Object object){
		BayesNode node = getNode(object);
		return addNode(node);
	}
	
	public boolean addChanceNode(Object object, Object[] scope){
		BayesNode node = getNode(object, scope);
		return addNode(node);
	}
	
	private BayesNode getNode(Object o){
		BayesNode newNode = getNodeIffPresent(o);
		if (newNode == null){
			newNode = new BayesNode(o);
		}
		return newNode;
	}
	
	private BayesNode getNode(Object o, Object[] scope){
		BayesNode newNode = getNodeIffPresent(o);
		if (newNode == null){
			newNode = new BayesNode(o, scope);
		}
		return newNode;
	}
		
	public boolean isPresent(Object o){
		if (getNodeIffPresent(o) == null){
			return false;
		} else {
			return true;
		}
	}
	
	private BayesNode getNodeIffPresent(Object o){
		for (BayesNode existingNode : nodes){
			if (existingNode.type.equals(o)){
				return existingNode;
			}
		}
		return null;
	}
		
	public boolean removeNode(Object object){
		BayesNode node = new BayesNode(object);
		nodes.remove(node);
		return graph.removeVertex(node);
	}
	
	private boolean connectBayesNodes(BayesNode node1, BayesNode node2){
		boolean result = graph.addEdge(edgeCounter, node1, node2);
		if (result){
			edgeCounter++;
		}
		return result;
	}
	
	public boolean connectNodes(Object rawNode1, Object rawNode2){
		BayesNode node1 = getNode(rawNode1);
		BayesNode node2 = getNode(rawNode2);
		if (!scopesCompatible(node1, node2)){
			return false;
		}
		return this.connectBayesNodes(node1, node2);
	}
	
	private boolean scopesCompatible(BayesNode node1, BayesNode node2){
		ArrayList<Object> difference = getScopeDifference(node1, node2);
		if (difference.isEmpty()){
			return false;
		}
		return true;
	}
	
	private ArrayList<Object> getScopeDifference(BayesNode node1, BayesNode node2){
		ArrayList<Object> list1 = new ArrayList<Object>(Arrays.asList(node1.scope));
		ArrayList<Object> list2 = new ArrayList<Object>(Arrays.asList(node2.scope));
		list1.retainAll(list2);
		return list1;
	}
	
	public boolean containsNode(Object rawNode){
		return isPresent(rawNode);
	}
	
	public Fraction getProbability(Object object){
		BayesNode node = getNodeIffPresent(object);
		if (node == null){
			throw new IllegalArgumentException("Requested object not found in the graph");
		}
		return node.getProbability();
	}
	
	public void observe(Object object, boolean value){
		BayesNode node = getNode(object);
		node.observe(value);
	}
	
	public boolean setProbabilityOfUntrue(Object object, Fraction probability, Object... variables){
		BayesNode node = getNode(object);
		return node.setProbabilityOfUntrueVariables(probability, variables);
	}
	
	public void resetNetworkBeliefs(){
		for (BayesNode node : nodes){
			node.resetPotential();
		}
	}
	
	public void resetNetworkBeliefsObservations(){
		for (BayesNode node : nodes){
			node.resetNode();
		}
	}	
	
	// TODO: currently assumes that all the nodes are connected, this should be checked 
	// TODO: only works on polytrees, doesn't check that the network is one
	public void updateBeliefs(){
		resetNetworkBeliefs();
		if (nodes.size() > 1){
			BayesNode root = nodes.get(0);
			visitedDownstreamNodes = new HashSet<BayesNode>();
			downstreamMessagePaths = new Stack<Pair<BayesNode,BayesNode>>();
				
			sendDownstreamMessages(root);
			visitedDownstreamNodes.clear();
			sendUpstreamMessages();
			root.multiplyPotentialWithMessages();
		}
	}
		
	private void sendDownstreamMessages(BayesNode source){
		visitedDownstreamNodes.add(source);
		ArrayList<BayesNode> sourceNeighbors = new ArrayList<BayesNode>(graph.getNeighbors(source));
		for (BayesNode neighbor : sourceNeighbors){
			if (!visitedDownstreamNodes.contains(neighbor)){
				Object[] sharedScope = getScopeDifference(source, neighbor).toArray();
				Message message = source.generateDownstreamMessage(sharedScope);
				neighbor.receiveDownstreamMessage(message);
				downstreamMessagePaths.push(new Pair<BayesNode,BayesNode>(source, neighbor));
				sendDownstreamMessages(neighbor);
			}
		}
	}
	
	private void sendUpstreamMessages(){
		while (!downstreamMessagePaths.isEmpty()){
			Pair<BayesNode,BayesNode> path = downstreamMessagePaths.pop();
			BayesNode source = path.getSecond();
			BayesNode receiver = path.getFirst();
			
			Object[] sharedScope = getScopeDifference(source, receiver).toArray();
			Message message = source.generateUpstreamMessage(sharedScope);
			receiver.receiveUpstreamMessage(message);
			source.multiplyPotentialWithMessages();
		}

		
	}
	
		


	

}
