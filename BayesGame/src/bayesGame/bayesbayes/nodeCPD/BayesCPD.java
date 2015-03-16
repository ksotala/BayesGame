package bayesGame.bayesbayes.nodeCPD;

import org.apache.commons.math3.fraction.Fraction;

import bayesGame.bayesbayes.BayesNode;

public class BayesCPD implements NodeCPD {
	
	private Fraction probabilityIfParentTrue;
	private Fraction probabilityIfParentFalse;

	public BayesCPD(Fraction trueProbability,
			Fraction falseProbability) {
		probabilityIfParentTrue = trueProbability;
		probabilityIfParentFalse = falseProbability;
	}

	@Override
	public BayesNode getNode(BayesNode sourceBayesNode, Object[] parents) {
		if (parents.length > 1){
			throw(new IllegalArgumentException("Conditional probability only accepts one parent"));
		}
		
		Object nodeType = sourceBayesNode.type;
		sourceBayesNode.setProbabilityOfUntrueVariables(probabilityIfParentTrue);
		sourceBayesNode.setProbabilityOfUntrueVariables(Fraction.ONE.subtract(probabilityIfParentTrue), nodeType);
		sourceBayesNode.setProbabilityOfUntrueVariables(probabilityIfParentFalse, parents[0]);
		sourceBayesNode.setProbabilityOfUntrueVariables(Fraction.ONE.subtract(probabilityIfParentFalse), nodeType, parents[0]);
		
        String description = "<html>This is a <b>conditional probability variable</b> of type <b>Bayes</b>.<br>It has a " + probabilityIfParentTrue.getNumerator() + 
        		" in " + probabilityIfParentTrue.getDenominator() + " chance of being true if its parent is true,<br>and a " + probabilityIfParentFalse.getNumerator() 
        		+ " in " + probabilityIfParentFalse.getDenominator() + " chance of being true if its parent is false.";
        sourceBayesNode.cptDescription = description;
        sourceBayesNode.cptName = "Bayes";
		
		return sourceBayesNode;
	}

}
