package bayesGame.bayesbayes.nodeCPD;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.fraction.Fraction;

import bayesGame.bayesbayes.BayesNode;

public class MajorityVote implements NodeCPD {

	public MajorityVote() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public BayesNode getNode(BayesNode sourceBayesNode, Object[] parents) {
		
		Object nodeType = sourceBayesNode.type;
		
		ArrayList<Object> allItems = new ArrayList<Object>(Arrays.asList(parents));
		allItems.add(0, parents);
		
		// generate a truth table as per http://stackoverflow.com/a/10761325/2130838
		int rows = (int) Math.pow(2, allItems.size());
		
        for (int i=0; i<rows; i++) {
        	int balance=0;
        	boolean trueSeen = false;
        	ArrayList<Object> falseObjects = new ArrayList<Object>();
        	for (int j=allItems.size()-1; j>=0; j--) {
                // as we traverse each generated row from the end of the row, we keep track of
            	// for the balance of TRUE-FALSE items. 
        		// Also, for each FALSE item on the row, we add that to the list of false items
        		// on this row.
            	if (j > 0){
                	if((i/(int) Math.pow(2, j))%2 == 1){
                    	balance++;
                    } else {
                    	balance--;
                    	falseObjects.add(allItems.get(j));
                    }
                } else {
                	Fraction probability;
                	// once we get to the beginning of the row, we check whether
                	// this row codes for the node being TRUE or FALSE
                	if((i/(int) Math.pow(2, j))%2 == 1){
                		// if this row codes for the node being TRUE and the
                		// balance is positive, then we set the probability of
                		// this row to one. if the balance is negative we set
                		// the probability of this row to zero, and if the balance
                		// is even we set the probability to .5.
                		if (balance > 0){
                			probability = Fraction.ONE;
                		} else if (balance < 0){
                			probability = Fraction.ZERO;
                		} else{
                			probability = Fraction.ONE_HALF;
                		}
                	} else {
                		// Similarly, if this row codes for the node being FALSE,
                		// we do the reverse.
                		falseObjects.add(nodeType);
                		if (balance > 0){
                			probability = Fraction.ZERO;
                		} else if (balance < 0){
                			probability = Fraction.ONE;
                		} else{
                			probability = Fraction.ONE_HALF;
                		}
                	}
                	
                	sourceBayesNode.setProbabilityOfUntrueVariables(probability, falseObjects.toArray());
                }
            }
        }
        
        String description = "<html>This is a <b>conditional probability variable</b> of type <b>majority vote</b>.<br>It is true if the majority of its parents are true, and false if the majority of its parents are false. If an equal number of parents are true and false, it has a 50% chance of being true.";
        sourceBayesNode.cptDescription = description;
        
		return sourceBayesNode;
	}

}
