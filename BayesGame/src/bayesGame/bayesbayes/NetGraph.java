package bayesGame.bayesbayes;

import org.apache.commons.math3.util.Pair;

import edu.uci.ics.jung.graph.DirectedSparseGraph;

public class NetGraph extends DirectedSparseGraph<BayesNode,Pair<Integer,Integer>> {
	
	//TODO: fix this...
	
	private final BayesNet net;

	public NetGraph(BayesNet net) {
		super();
		this.net = net;
	}
	
	public BayesNet getNet(){
		return net;
	}

}
