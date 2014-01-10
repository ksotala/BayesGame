package bayesGame.bayesbayes;

import java.util.HashMap;

import org.apache.commons.math3.fraction.Fraction;

public class DecisionNode extends BayesNode {
	
	public DecisionNode(String description) {
		super(description);
	}

	public DecisionNode(String description, Object[] scope) {
		super(description, scope);
	}

	public DecisionNode(String description, Object[] scope,
			HashMap<Object, Integer> strides, Fraction[] cpt) {
		super(description, scope, strides, cpt);
	}
	
	public boolean equals(Object other){
		
		boolean result = false;
		
		if (other instanceof DecisionNode){
			DecisionNode theOther = (DecisionNode)other;
			result = (this.type.equals(theOther.type));
		}
		
		return result;
	}

}
