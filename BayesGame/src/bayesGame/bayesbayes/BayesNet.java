package bayesGame.bayesbayes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.util.Pair;

import bayesGame.bayesbayes.nodeCPD.DeterministicOR;
import bayesGame.bayesbayes.nodeCPD.NodeCPD;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

public class BayesNet {

	private int edgeCounter = 0;
	private Map<Object,BayesNode> nodes;
	private NetGraph graph;
	
	private HashSet<BayesNode> visitedDownstreamNodes;
	private Stack<Pair<BayesNode, BayesNode>> downstreamMessagePaths;
	
	public BayesNet() {
		graph = new NetGraph(this);
		nodes = new HashMap<Object,BayesNode>();
	}
	
	public boolean addNode(BayesNode node){
		boolean added = graph.addVertex(node);
		if (added){
			nodes.put(node.type, node);
		}
		return added;
	}
	
	public boolean addNode(Object object){
		BayesNode node = new BayesNode(object);
		return addNode(node);
	}
	
	public boolean addNode(Object object, Object[] scope){
		BayesNode node = getNode(object, scope);
		return addNode(node);
	}
	
	public boolean addNode(Object object, NodeCPD cpd, Object... parents){
		return addNodeWithParents(object, cpd, parents);
	}
	
	public void addNodes(Object... nodes){
		for (Object o : nodes){
			addNode(o);
		}
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
		
	public DirectedSparseGraph<BayesNode, Pair<Integer,Integer>> getGraph(){
		return graph;
	}
	
	public boolean isPresent(Object o){
		return nodes.containsKey(o);
	}
	
	public boolean isFullyAssumed(){
		boolean fullyAssumed = true;
		Collection<BayesNode> nodeValues = nodes.values();
		for (BayesNode existingNode : nodeValues){
			if (!existingNode.isObserved() && !existingNode.isAssumed()){
				fullyAssumed = false;
				break;
			}
		}
		return fullyAssumed;
	}
	
	private BayesNode getNodeIffPresent(Object o){
		return nodes.get(o);
	}
		
	public boolean removeBayesNode(Object object){
		BayesNode node = new BayesNode(object);
		nodes.remove(node);
		return graph.removeVertex(node);
	}
	
	private boolean connectNodes(BayesNode node1, BayesNode node2){
		boolean result = graph.addEdge(new Pair<Integer,Integer>(edgeCounter,0), node1, node2);
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
		return this.connectNodes(node1, node2);
	}
	
	/**
	 * Connects two existing nodes, adding the parent to the child's scope if neither has the
	 * other in its scope. Note that this will rewrite the child's conditional probability table.
	 * To avoid this, use the regular connectNodes function which takes no action for incompatible
	 * scopes, or use the changeNodeCPD function to make the child conform to the desired probability
	 * distribution.
	 * 
	 * @param rawNode1
	 * @param rawNode2
	 * @return
	 */
	public boolean forceConnectNodes(Object rawNode1, Object rawNode2){
		BayesNode node1 = getNode(rawNode1);
		BayesNode node2 = getNode(rawNode2);
		if (!scopesCompatible(node1, node2)){
			node2.addItemToScope(node1.type);
		}
		return this.connectNodes(node1, node2);
	}
	
	/**
	 * Adds a node to the network which evaluates to true (with P = 1) iff at least one
	 * of its parents is true (with P = 1). The parents of the node must already exist
	 * in the network, whereas the node itself must not exist: if these criteria are not
	 * met, or if no parents are provided, the function will return false and do nothing.
	 * 
	 * @param orNode The identifier of the deterministic OR node to be added
	 * @param parents The parents of the OR node
	 * @return true if the node was added, false otherwise
	 */
	public boolean addDeterministicOr(Object orNode, Object... parents){
		return addNodeWithParents(orNode, new DeterministicOR(), parents);
	}
	
	
	public boolean addNodeWithParents(Object object, NodeCPD cpd, Object... parents){
		BayesNode orBayesNode = new BayesNode(object, parents);
		
		if (parents.length == 0){
			return false;
		}
		
	    for (Object o : parents){
	    	if (!isPresent(o)){
	    		return false;
	    	}
	    }
		
	    this.setNodeTo(orBayesNode, parents, cpd);
	    
		boolean added = addNode(orBayesNode);
		if (!added){
			return false;
		}
        
        for (Object o : parents){
        	boolean sanityCheck = connectNodes(o, object);
        	if (!sanityCheck){
        		throw new IllegalStateException("Failed to connect nodes that should be connected fine ??? Shouldn't be possible");
        	}
        }
        
        return true;
	}
	
	private BayesNode setNodeTo(BayesNode node, Object[] parents, NodeCPD cpd){
		return node = cpd.getNode(node, parents);
	}
	
	
		
	/**
	 * Turns an existing node with at least one parent into a deterministic OR node.
	 * 
	 * @param orNode The node to be made into a deterministic OR node
	 * @return false if the node doesn't exist or has no parents, true otherwise.
	 */
	public boolean makeDeterministicOr(Object orObject){
		return changeNodeCPD(orObject, new DeterministicOR());
	}
	
	/**
	 * Gives an existing node with at least one parent the kind of probability distribution specified in the parameter.
	 * 
	 * @param object The node to be changed
	 * @param cpd The desired probability distribution
	 * @return false if the node doesn't exist or has no parents, true otherwise.
	 */
	public boolean changeNodeCPD(Object object, NodeCPD cpd){
		BayesNode node = getNodeIffPresent(object);
		
		// the node has to already exist for us to do anything
		if (node == null){
			return false;
		}
		
		ArrayList<BayesNode> parentNodes = new ArrayList<BayesNode>(graph.getPredecessors(node));
		
		// and it should have parents
		if (parentNodes.size() == 0){
			return false;
		}
				
		Object[] parents = new Object[parentNodes.size()];
		
		// the graph object returns the parents as a list of BayesNodes, so let's extract their
		// types into a separate array
		for (int i = 0; i < parentNodes.size(); i++){
			parents[i] = parentNodes.get(i).type;
		}
		
		// as the node relies on the values of its parents, it should have its parents in its scope
		Set<Object> scopeSet = new HashSet<Object>(Arrays.asList(node.scope));
		for (Object p : parents){
			if (!scopeSet.contains(p)){
				node.addItemToScope(p);
			}
		}
		
		this.setNodeTo(node, parents, cpd);
		
		return true;	
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
	
	public ArrayList<Map<Object,Boolean>> getNonZeroProbabilities(Object object){
		BayesNode node = getNodeIffPresent(object);
		if (node == null){
			throw new IllegalArgumentException("Requested object not found in the graph");
		}
		return node.getNonZeroProbabilities();
	}
	
	public Map<Object,Boolean> getCurrentAssignments(){
		Map<Object,Boolean> assignments = new HashMap<Object,Boolean>(nodes.size());
		Collection<BayesNode> nodeValues = nodes.values();
		for (BayesNode node : nodeValues){
			if (node.isObserved() || node.isAssumed()){
				boolean truthValue = node.getProbability().equals(Fraction.ONE);
				assignments.put(node.type, truthValue);
			}
		}
		return assignments;
	}
	
	public boolean observe(Object object){
		BayesNode node = getNode(object);
		node.observe();
		Fraction probability = node.getProbability();
		return (probability.intValue() == 1);
	}
	
	public void observe(Object object, boolean value){
		BayesNode node = getNode(object);
		node.observe(value);
	}
	
	public boolean isObserved(Object object){
		BayesNode node = getNodeIffPresent(object);
		return node.isObserved();
	}
	
	public void assume(Object object, boolean value){
		BayesNode node = getNodeIffPresent(object);
		if (node == null){
			throw new IllegalArgumentException("Requested object not found in the graph");
		}
		node.assumeValue(value);
	}
	
	public void assume(Object object){
		BayesNode node = getNodeIffPresent(object);
		if (node == null){
			throw new IllegalArgumentException("Requested object not found in the graph");
		}
		node.clearAssumedValue();
	}
	
	public void addProperty(Object object, String property){
		BayesNode node = getNodeIffPresent(object);
		if (node == null){
			throw new IllegalArgumentException("Requested object not found in the graph");
		}
		node.addProperty(property);
	}
	
	public void removeProperty(Object object, String property){
		BayesNode node = getNodeIffPresent(object);
		node.removeProperty(property);
	}
	
	public boolean setProbabilityOfUntrue(Object object, Fraction probability, Object... variables){
		BayesNode node = getNode(object);
		return node.setProbabilityOfUntrueVariables(probability, variables);
	}
	
	public boolean setTrueValue(Object object, boolean value){
		BayesNode node = getNodeIffPresent(object);
		if (node == null){
			return false;
		}
		node.setTrueValue(value);
		return true;
	}
	
	public void clearAssumptions(){
		Collection<BayesNode> nodeValues = nodes.values();
		for (BayesNode node : nodeValues){
			node.clearAssumedValue();
		}
	}
	
	public void resetNetworkBeliefs(){
		Collection<BayesNode> nodeValues = nodes.values();
		for (BayesNode node : nodeValues){
			node.resetPotential();
		}
	}
	
	public void resetNetworkBeliefsObservations(){
		Collection<BayesNode> nodeValues = nodes.values();
		for (BayesNode node : nodeValues){
			node.resetNode();
		}
	}	
	
	// TODO: currently assumes that all the nodes are connected, this should be checked 
	// TODO: only works on polytrees, doesn't check that the network is one
	public void updateBeliefs(){
		if (nodes.size() > 1){
			HashMap<BayesNode,Fraction> calculatedProbabilities = new HashMap<BayesNode,Fraction>();
			Collection<BayesNode> nodeValues = nodes.values();
			for (BayesNode root : nodeValues){
				resetNetworkBeliefs();
				visitedDownstreamNodes = new HashSet<BayesNode>();
				downstreamMessagePaths = new Stack<Pair<BayesNode,BayesNode>>();
					
				sendDownstreamMessages(root);
				visitedDownstreamNodes.clear();
				sendUpstreamMessages();
				root.multiplyPotentialWithMessages();
				
				Fraction rootProbability = root.getProbability();
				calculatedProbabilities.put(root, rootProbability);
				System.out.println("Probability of " + root.toString() + " calculated as " + rootProbability.toString());
			}
			for (BayesNode node : nodeValues){
				Fraction calculatedProbability = calculatedProbabilities.get(node);
				node.setProbability(calculatedProbability);
			}
		}
	}
		
	private void sendDownstreamMessages(BayesNode source){
		visitedDownstreamNodes.add(source);
		
		ArrayList<BayesNode> sourceNeighbors = new ArrayList<BayesNode>(graph.getNeighbors(source));
		for (BayesNode neighbor : sourceNeighbors){
			if (!visitedDownstreamNodes.contains(neighbor)){
				Object[] sharedScope = getScopeDifference(source, neighbor).toArray();
				//b Object[] sharedScope = new Object[]{source.type};
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
			// Object[] sharedScope = new Object[]{source.type};
			Message message = source.generateUpstreamMessage(sharedScope);
			receiver.receiveUpstreamMessage(message);
			source.multiplyPotentialWithMessages();
		}

		
	}
	
		


	

}
