package bayesGame.bayesbayes.nodeCPD;

import bayesGame.bayesbayes.BayesNode;

public interface NodeCPD {
	
	public BayesNode getNode(BayesNode orBayesNode, Object[] parents);

}
