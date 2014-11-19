package bayesGame.bayesbayes.nodeCPD;

import org.apache.commons.math3.fraction.Fraction;

import bayesGame.bayesbayes.BayesNode;

public class DeterministicNot implements NodeCPD {

	@Override
	public BayesNode getNode(BayesNode sourceBayesNode, Object[] parents) {
		if (parents.length > 1){
			throw(new IllegalArgumentException("NOT only accepts one parent"));
		}
		
		Object nodeType = sourceBayesNode.type;
		sourceBayesNode.setProbabilityOfUntrueVariables(Fraction.ZERO);
		sourceBayesNode.setProbabilityOfUntrueVariables(Fraction.ONE, nodeType);
		sourceBayesNode.setProbabilityOfUntrueVariables(Fraction.ONE, parents[0]);
		sourceBayesNode.setProbabilityOfUntrueVariables(Fraction.ZERO, nodeType, parents[0]);
		
        String description = "<html>This is a <b>conditional probability variable</b> of type <b>not</b>.<br>It is true if its parent is false, and vice versa.</html>";
        sourceBayesNode.cptDescription = description;
		
		return sourceBayesNode;
	}

}
