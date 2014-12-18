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
		
		// generate a truth table as per http://stackoverflow.com/a/10761325/2130838
		int rows = (int) Math.pow(2, allItems.size());
		
        for (int i=0; i<rows; i++) {
        	boolean trueSeen = false;
        	ArrayList<Object> falseObjects = new ArrayList<Object>();
        	for (int j=allItems.size()-1; j>=0; j--) {
                // as we traverse each generated row from the end of the row, we keep track of
            	// whether we've hit an item that should code for the value of TRUE. Also, for
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
                		// this row to one, since a deterministic OR is true with
                		// P=1 if any input variable is true. If we haven't seen
                		// a TRUE variable, we set the probability to zero.
                		if (trueSeen){
                			probability = Fraction.ONE;
                		} else {
                			probability = Fraction.ZERO;
                		}
                	} else {
                		// Similarly, if this row codes for the node being FALSE,
                		// we do the reverse.
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
        
        String description = "<html>'" + orBayesNode.type + "' is a <b>conditional probability variable</b><br> of type <b>deterministic";
        String description2;
        String typename;
        
        if (parents.length > 1){
        	description2 = " or</b>.<p>It is true of any of its parent variables are true.";
        	typename = "DetOR";
        } else {
        	description2 = " is</b>.<p><p>It is true if its parent variable is true.";
        	typename = "DetIS";
        }
        description = description + description2;
        
        orBayesNode.cptName = typename;
        orBayesNode.cptDescription = description;
        
        System.out.println(orBayesNode.cptDescription);
        
		return orBayesNode;
	}

}
