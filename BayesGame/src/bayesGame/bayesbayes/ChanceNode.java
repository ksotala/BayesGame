package bayesGame.bayesbayes;

import java.util.HashMap;

import org.apache.commons.math3.fraction.Fraction;

public class ChanceNode extends BayesNode {

	public ChanceNode(Object type) {
		super(type);
	}

	public ChanceNode(Object type, Object[] scope) {
		super(type, scope);
	}

	public ChanceNode(Object type, Object[] scope,
			HashMap<Object, Integer> strides, Fraction[] cpt) {
		super(type, scope, strides, cpt);
	}

}
