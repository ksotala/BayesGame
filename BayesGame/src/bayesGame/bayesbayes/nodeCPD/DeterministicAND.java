package bayesGame.bayesbayes.nodeCPD;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.fraction.Fraction;

import bayesGame.bayesbayes.BayesNode;

public class DeterministicAND implements NodeCPD {

	@Override
	public BayesNode getNode(BayesNode sourceBayesNode, Object[] parents) {
		Object andNode = sourceBayesNode.type;
		
		ArrayList<Object> allItems = new ArrayList<Object>(Arrays.asList(parents));
		allItems.add(0, parents);
		
		// generate a truth table as per http://stackoverflow.com/a/10761325/2130838
		int rows = (int) Math.pow(2, allItems.size());
		
        for (int i=0; i<rows; i++) {
        	boolean falseSeen = false;
        	ArrayList<Object> falseObjects = new ArrayList<Object>();
        	for (int j=allItems.size()-1; j>=0; j--) {
                // as we traverse each generated row from the end of the row, we keep track of
            	// whether we've hit an item that should code for the value of FALSE. Also, for
        		// each FALSE item on the row, we add that to the list of false items on this
        		// row.
            	if (j > 0){
                	if((i/(int) Math.pow(2, j))%2 == 1){
                    	// no action
                    } else {
                    	falseSeen = true;
                    	falseObjects.add(allItems.get(j));
                    }
                } else {
                	Fraction probability;
                	// once we get to the beginning of the row, we check whether
                	// this row codes for the node being TRUE or FALSE
                	if((i/(int) Math.pow(2, j))%2 == 1){
                		// if this row codes for the node being TRUE and we have
                		// seen a false variable, then we set the probability of
                		// this row to zero, since a deterministic AND is true with
                		// P=0 if any input variable is false. If we haven't seen
                		// a FALSE variable, we set the probability to one.
                		if (falseSeen){
                			probability = Fraction.ZERO;
                		} else {
                			probability = Fraction.ONE;
                		}
                	} else {
                		// Similarly, if this row codes for the node being FALSE,
                		// we do the reverse.
                		falseObjects.add(andNode);
                		if (falseSeen){
                			probability = Fraction.ONE;
                		} else {
                			probability = Fraction.ZERO;
                		}
                	}
                	
                	sourceBayesNode.setProbabilityOfUntrueVariables(probability, falseObjects.toArray());
                }
            }
        }
        
        String description = "<html>'" + sourceBayesNode.type + "' is a <b>conditional probability variable</b><br> of type <b>deterministic";
        String description2;
        String typename;
        
        if (parents.length > 1){
        	description2 = " and</b>.<p>It is true if all of its parent variables are true.";
        	typename = "DetAND";
        } else {
        	description2 = " is</b>.<p><p>It is true if its parent variable is true.";
        	typename = "DetIS";
        }
        description = description + description2;
        
        sourceBayesNode.cptName = typename;
        sourceBayesNode.cptDescription = description;
        
        System.out.println(sourceBayesNode.cptDescription);
        
		return sourceBayesNode;
		
	}

}
