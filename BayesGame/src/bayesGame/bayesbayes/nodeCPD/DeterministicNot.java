package bayesGame.bayesbayes.nodeCPD;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.fraction.Fraction;

import bayesGame.bayesbayes.BayesNode;

public class DeterministicNot implements NodeCPD {

	@Override
	public BayesNode getNode(BayesNode sourceBayesNode, Object[] parents) {
		Object notNode = sourceBayesNode.type;
		
		ArrayList<Object> allItems = new ArrayList<Object>(Arrays.asList(parents));
		allItems.add(0, parents);
		
		// generate a truth table as per http://stackoverflow.com/a/10761325/2130838
		int rows = (int) Math.pow(2, allItems.size());
		
        for (int i=0; i<rows; i++) {
        	boolean trueSeen = false;
        	ArrayList<Object> falseObjects = new ArrayList<Object>();
        	for (int j=allItems.size()-1; j>=0; j--) {
                // as we traverse each generated row from the end of the row, we keep track of
            	// whether we've hit an item that should code for the value of FALSE. Also, for
        		// each FALSE item on the row, we add that to the list of false items on this
        		// row.
            	if (j > 0){
                	if((i/(int) Math.pow(2, j))%2 == 1){
                    	trueSeen = true;
                    } else {
                    	falseObjects.add(allItems.get(j));
                    }
                } else {
                	Fraction probability;
                	// once we get to the beginning of the row, we check whether
                	// this row codes for the node being TRUE or FALSE
                	if((i/(int) Math.pow(2, j))%2 == 1){
                		// if this row codes for the node being TRUE and we have
                		// seen a true variable, then we set the probability of
                		// this row to zero, since a deterministic NOT is true with
                		// P=0 if any input variable is false. If we haven't seen
                		// a FALSE variable, we set the probability to one.
                		if (trueSeen){
                			probability = Fraction.ZERO;
                		} else {
                			probability = Fraction.ONE;
                		}
                	} else {
                		// Similarly, if this row codes for the node being FALSE,
                		// we do the reverse.
                		falseObjects.add(notNode);
                		if (trueSeen){
                			probability = Fraction.ONE;
                		} else {
                			probability = Fraction.ZERO;
                		}
                	}
                	
                	sourceBayesNode.setProbabilityOfUntrueVariables(probability, falseObjects.toArray());
                }
            }
        }
		
		
		
		
		
		
		
		
/*		
		Object nodeType = sourceBayesNode.type;
		sourceBayesNode.setProbabilityOfUntrueVariables(Fraction.ZERO);
		sourceBayesNode.setProbabilityOfUntrueVariables(Fraction.ONE, nodeType);
		sourceBayesNode.setProbabilityOfUntrueVariables(Fraction.ONE, parents[0]);
		sourceBayesNode.setProbabilityOfUntrueVariables(Fraction.ZERO, nodeType, parents[0]);
	*/
		
        String description = "<html>This is a <b>conditional probability variable</b> of type <b>not</b>.<br>It is true if all of its parents are false, and true otherwise.";
        sourceBayesNode.cptDescription = description;
        if (parents.length == 1){
        	sourceBayesNode.cptName = "DetNOT";
        } else {
        	sourceBayesNode.cptName = "DetNOTAnd";
        }
        
		return sourceBayesNode;
	}

}
