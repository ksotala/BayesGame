package bayesGame.bayesbayes.nodeCPD;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.fraction.Fraction;

import bayesGame.bayesbayes.BayesNode;

public class DeterministicOR implements NodeCPD {

	public DeterministicOR() {
		// TODO Auto-generated constructor stub
	}

	public BayesNode getNode(BayesNode orBayesNode, Object[] parents) {
		Object orNode = orBayesNode.type;
		
		ArrayList<Object> allItems = new ArrayList<Object>(Arrays.asList(parents));
		allItems.add(0, parents);
		
		int rows = (int) Math.pow(2, allItems.size());
		
        for (int i=0; i<rows; i++) {
        	boolean trueSeen = false;
        	ArrayList<Object> falseObjects = new ArrayList<Object>();
            for (int j=allItems.size()-1; j>=0; j--) {
                if (j > 0){
                	if((i/(int) Math.pow(2, j))%2 == 1){
                    	trueSeen = true;
                    } else {
                    	falseObjects.add(allItems.get(j));
                    }
                } else {
                	Fraction probability;
                	if((i/(int) Math.pow(2, j))%2 == 1){
                		if (trueSeen){
                			probability = Fraction.ONE;
                		} else {
                			probability = Fraction.ZERO;
                		}
                	} else {
                		falseObjects.add(orNode);
                		if (trueSeen){
                			probability = Fraction.ZERO;
                		} else {
                			probability = Fraction.ONE;
                		}
                	}
                	
                	orBayesNode.setProbabilityOfUntrueVariables(probability, falseObjects.toArray());
                }
            }
        }
        
		return orBayesNode;
	}

}
