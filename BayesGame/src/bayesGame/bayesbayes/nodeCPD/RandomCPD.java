package bayesGame.bayesbayes.nodeCPD;

import java.util.Random;

import bayesGame.bayesbayes.BayesNode;

public class RandomCPD implements NodeCPD {

	private NodeCPD chosenCPD;
	
	public RandomCPD(NodeCPD ... candidates){
		Random rn = new Random();
		int chosenCandidatePosition = rn.nextInt(candidates.length);
		chosenCPD = candidates[chosenCandidatePosition];
	}
	
	@Override
	public BayesNode getNode(BayesNode sourceBayesNode, Object[] parents) {
		return chosenCPD.getNode(sourceBayesNode, parents);
	}

}
